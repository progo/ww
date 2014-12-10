(ns ww.world
  )

(def world-map
  (let [colors ["red" "orange" "yellow" "gold"]]
    (into (hash-map)
          (for [x (range 228)
                y (range 120)]
            [[x y]
             (rand-nth colors)]))))

