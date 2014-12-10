(ns ww.core
  (:use [compojure.core :only (defroutes POST GET)]
        ring.util.response
        ring.middleware.cors
        [ring.middleware.session :only (wrap-session)]
        org.httpkit.server)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cheshire.core :as cheshire]
            [ring.middleware.reload :as reload]

            ww.world))

(def viewport (atom {:x 0 :y 0 :width 72 :height 28}))

(defn get-visible-world
  "Get a collection of blocks of the world that the given viewport
  allows."
  [world-map viewport]
  (for [y (range (:height viewport))
        x (range (:width viewport))]
    (let [x' (+ x (:x viewport))
          y' (+ y (:y viewport))]
      (world-map [x' y'] "#888"))))

(defn draw-map! [con]
  (let [vp @viewport]
    (send-json! con
                {:msg ((juxt :x :y) vp)})
    (send-json! con
                {:draw
                 (get-visible-world ww.world/world-map vp)})))

(defonce ws-clients (atom #{}))

(defn send-json!
  "Convert `msg` to JSON and send it to receiver `rec`."
  [rec msg]
  (send! rec (cheshire/generate-string msg)))

(defn take-json
  "Receive data from the client. This is the entry point for a lot of
  greatness. Dispatches to a multimethod quickly on."
  [con json]
  (let [response (cheshire/parse-string json true)]
    (act-on-message! con response)))

(defn act-dispatcher
  [con response]
  (:action response))

(defmulti act-on-message!
  "Incoming message (JSON converted to a map) from a client takes
  dispatched and handled here."
  #'act-dispatcher)

(defmethod act-on-message!
  "move"
  [con response]
  (let [amt (:amt response)]
    (when-let [[coord amt] (case (:move response)
                             "left" [:x (- amt)]
                             "right" [:x amt]
                             "up"   [:y (- amt)]
                             "down" [:y amt]
                             [:x 0])]
      (swap! viewport update-in [coord] (partial + amt))
      (draw-map! con))))

(defmethod act-on-message!
  "resize-vp"
  [con response]
  (swap! viewport assoc :width (:new-w response))
  (swap! viewport assoc :height (:new-h response))
  (draw-map! con))

(defn ws
  "Take an incoming call & assign it to ws-clients."
  [req]
  (with-channel req con
    (swap! ws-clients conj con)
    (on-receive con (fn [json]
                      (take-json con json)))
    (on-close con (fn [stat]
                    (swap! ws-clients disj con)))))

(defroutes routes
  (GET "/" [] (file-response "index.html" {:root "resources/public"}))
  (GET "/f" [] ws)
  (route/resources "/"))

(def application
  (-> routes
      handler/site
      reload/wrap-reload
      (wrap-cors :access-control-allow-origin #".+")))

;; Note: run-server returns a function that closes it. Be sure to
;; store it for safekeeping, namely when you want to restart the whole
;; REPL (not that often).
(defn -main [& args]
  (let [port (Integer/parseInt
              (or (System/getenv "PORT") "8080"))]
    (run-server #'application {:port port :join? false})))
