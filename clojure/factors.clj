(defn factor? [n potential]
  (zero? (rem n potential)))

(defn factors [n]
  (filter #(factor? n %) (range 1 (+ n 1))))

(defn sum-factors [n]
  (reduce + (factors n)))

(defn prime? [n]
  (= (inc n) (sum-factors n)))

(defn next-prime-from [n]
  (loop [candidate (inc n)]
    (if (prime? candidate)
      candidate
      (recur (inc candidate)))))

