(ns ww.core
  (:use [compojure.core :only (defroutes POST GET)]
        ring.util.response
        ring.middleware.cors
        [ring.middleware.session :only (wrap-session)]
        org.httpkit.server)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [cheshire.core :as cheshire]
            [ring.middleware.reload :as reload]))

(defonce ws-clients (atom #{}))

(defn send-json!
  "Convert `msg` to JSON and send it to receiver `rec`."
  [rec msg]
  (send! rec (cheshire/generate-string msg)))

(defn ws
  "Take an incoming call & assign it to ws-clients."
  [req]
  (with-channel req con
    (swap! ws-clients conj con)
    (on-receive con (fn [resp]
                      ;; echo service
                      (send! con resp)))
    (on-close con (fn [stat]
                    (swap! ws-clients disj con)))))

(defroutes routes
  (GET "/" [] (file-response "index.html" {:root "resources/public"}))
  (GET "/f" [] ws)
  (route/resources "/"))

(def application (-> routes
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
