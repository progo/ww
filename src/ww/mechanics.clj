(ns ww.mechanics
  )

;; Ideas to enhance this further:
;; - Radio noise on a space but not on adjacent chars -> resolve the
;;   noise into space.
;; - Study certain phrases (from phonetical POV) that might have
;;   a smaller chance of getting noise (i.e. people recognize these
;;   better over noise)

(defn add-noise [msg p]
  "Apply radio noise to a message"
  (apply str
         (for [character msg]
           (if (> (rand) p)
             character
             \#))))
