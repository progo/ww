(ns ww.world
  )

(def world-width 800)
(def world-height 600)

(defn make-world
  [width height]
  (into (hash-map)
        (for [x (range width)
              y (range height)]
          [[x y] "blue"])))

(defn random-point
  []
  [(rand-int world-width)
   (rand-int world-height)])

(defn give-random-points-world
  [world points]
  (into world
        (for [_ (range points)]
          [(random-point) "red"])))

(defn neighbors
  [[x y]]
  [[(inc x)     y]
   [(dec x)     y]
   [(inc x) (dec y)]
   [(dec x) (dec y)]
   [     x  (dec y)]
   [     x  (inc y)]
   [(inc x) (inc y)]
   [(dec x) (inc y)]])

(defn minesweeper
  [world]
  (let [top? #(= "red" (second %))
        tops (filter top? world)
        to-inc (mapcat (comp neighbors first) tops)]
    (reduce (fn [world c]
              (assoc world c "pink"))
            world
            to-inc)))

(do
  (def world-map
    (let []
      (-> (make-world world-width world-height)
          (give-random-points-world 40000)
          minesweeper)))
 ;; (ww.core/draw-map! (first @ww.core/ws-clients))
 )

