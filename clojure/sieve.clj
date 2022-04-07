;; To load this file into Leiningen REPL, use command:
;; (load-file "sieve.clj")

;;----------------------------------------------------------------

(defn init-list-old [n]
  (loop [i 0
         v []]
    (if (> i n)
      v
      (recur (inc i) (if (< i 2) (conj v 1) (conj v 0))))))

;;----------------------------------------------------------------

(defn init-list [n]
  (vec (map #(if (< % 2) 1 0) (range (inc n)))))

;;----------------------------------------------------------------

(defn mark-multiples [v p]
  (defn factor? [num den] (= 0 (rem num den)))
  (let [max (count v)]
    (loop [i 0
           nv []]
      (if (= i max)
        nv
        (if (or (< i p) (= i p))
          (recur (inc i) (conj nv (v i)))
          (recur (inc i) (if (factor? i p) (conj nv 1) (conj nv (v i)))))))))

;;----------------------------------------------------------------

(defn mark-multiples-new [v p]
  (defn factor? [num den] (= 0 (rem num den)))
  (let [v-indexed (map-indexed vector v)]
    (defn pred? [vp]
      (let [i (vp 0)]
        (if (or (< i p) (= i p))
          (v i)
          (if (factor? (vp 0) p)
            1
            (v i)))))
    (vec (map pred? v-indexed))))

;;----------------------------------------------------------------

(defn get-next-prime [v start]
  (let [max (count v)]
    (loop [i (inc start)]
      (if (or (= i max) (> i max))
        start ;; nothing found after start index
        (do
          (if (= 0 (v i))
            i
            (recur (inc i))))))))

;;----------------------------------------------------------------

(defn sieve [n]
  (let [start-list (init-list n)
        prime (get-next-prime start-list 0)]
    (loop [p prime
           l 0
           v start-list]
      ;; (println (format "p: %s, l: %s, v: %s" p l v))
      (if (= p l)
        v
        (do
          (let [nv (mark-multiples v p)]
            (recur (get-next-prime nv p) p nv)))))))

;;----------------------------------------------------------------

(defn primes-up-to [n]
  (let [v (sieve n)]
    (loop [i 0]
      (if (= i n)
        nil
        (do
          (if (= (v i) 0) (print i " "))
          (recur (inc i)))))))


;;----------------------------------------------------------------

(def fib (map first (iterate (fn [[a b]] [b (+ a b)]) [0 1])))

;;----------------------------------------------------------------
;; From Stack Overflow: http://stackoverflow.com/questions/2980505
;; /chain-call-in-clojure

(defn sieve-0 [beg end]
  (letfn [
    (siever [to-sieve sieved]
      (if (empty? to-sieve)
        sieved
        (let [
          [f & r] to-sieve]
          (if (> f (Math/sqrt end))
            (into sieved to-sieve)
            (recur (remove #(zero? (mod % f)) r) (conj sieved f))))))]
    (siever (range beg (inc end)) [])))

;; Above implementation is flawed when (> beg 2)

(defn sieve [end]
  (def debug false)
  (def first-prime 2) ;; define first prime number as starting point
  (letfn [
    (siever [to-sieve sieved]
      (when debug
        (println to-sieve)
        (println sieved))
      (if (empty? to-sieve) ;; only happens with edge cases, e.g. (sieve 0)
        sieved
        (let [[f & r] to-sieve]
          (if (> f (Math/sqrt end))
            (into sieved to-sieve) ;; recur stops here, returns sieved
            (recur (remove #(zero? (mod % f)) r) (conj sieved f))))))]
    (siever (range first-prime (inc end)) [])))

;;----------------------------------------------------------------

(defn primes3 [max]
  (def debug false)
  (let [
    enqueue (fn [sieve n factor]
      (when debug
        (println sieve))
      (let [m (+ n (+ factor factor))]
        (if (sieve m)
          (recur sieve m factor)
          (assoc sieve m factor))))
    next-sieve (fn [sieve candidate]
      (if-let [factor (sieve candidate)]
        (-> sieve
          (dissoc candidate)
          (enqueue candidate factor))
        (enqueue sieve candidate candidate)))]
    (cons 2 (sort (vals (reduce next-sieve {} (range 3 max 2)))))))
