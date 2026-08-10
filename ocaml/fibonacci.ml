(* fibonacci.ml *)

let fib n =
    let rec ifib i a b =
        if i = n then b
        else ifib (i + 1) b (a + b)
    in
    ifib 0 0 1
