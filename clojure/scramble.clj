;; Get index of middle character of s if length is odd, or the
;; starting index of the second half of s if length is even
(defn mid-index [s] (quot (count s) 2))

;; Get first half of string. See note for second-half.
(defn first-half [s] (subs s 0 (mid-index s)))

;; Get second half of string. If length of s is odd, this
;; substring will have one more character than first-half.
(defn second-half [s] (subs s (mid-index s)))

;; Return a scrambled version of string based on its two
;; constituent parts. Note that interleave will exclude
;; the last character of s when the length of s is odd.
(defn scramble [s]
  (let [complete-if-even (interleave (second-half s) (first-half s))
        finalized-value (if (odd? (count s))
                          (concat complete-if-even (str (last s)))
                          complete-if-even)]
    (apply str finalized-value)))
