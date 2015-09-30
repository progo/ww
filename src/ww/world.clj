(ns ww.world)

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

;; (do
;;   (def world-map
;;     (let []
;;       (-> (make-world world-width world-height)
;;           (give-random-points-world 40000)
;;           minesweeper)))
;;  )

;;; Diamond Square Algorithm

(def width 17)
(def height 17)

(defn make-zero-grid [width height]
  (into {}
        (for [x (range width)
              y (range height)]
          [[x y] 0])))

(defn init-grid-corners
  [grid corners]
  (let [[a b c d] corners]
    (-> grid
        (assoc [0 0] a)
        (assoc [(dec width) 0] b)
        (assoc [0 (dec height)] c)
        (assoc [(dec width) (dec height)] d))))

(defn corners
  "Assuming even rectangular grid, get the corner values."
  [grid side]
  [(grid [0 0])
   (grid [(dec side) 0])
   (grid [0 (dec side)])
   (grid [(dec side) (dec side)])])

(defn diamond-step
  [grid side]
  (let [corners (corners grid side)
        corner-avg (/ (reduce + corners) 4)
        x (int (/ side 2))
        center-loc [x x]]
    (assoc grid center-loc (+ corner-avg))))

(defn square-step
  [grid])

(def world-map
  (-> (make-zero-grid width height)
      (init-grid-corners [1 0.8 0.6 0.85])
      (diamond-step 17)))

(ww.core/draw-map! (first @ww.core/ws-clients))
