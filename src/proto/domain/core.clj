(ns proto.domain.core)

(defn brownian-motion
  "Generate a sequence of points representing Brownian Motion."
  [steps step-size]
  (let [initial [0.0 0.0]]
    (loop [i steps points [initial]]
      (if (zero? i)
        points
        (let [[x y] (last points)
              angle (* 2 Math/PI (rand))
              dx (* step-size (Math/cos angle))
              dy (* step-size (Math/sin angle))]
          (recur (dec i) (conj points [(+ x dx) (+ y dy)])))))))
