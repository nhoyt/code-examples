(* Returns a list of the first n primes *)
let primes n =
  let rec sieve = function
    | [] -> []
    | p :: xs -> p :: sieve (List.filter (fun x -> x mod p <> 0) xs)
  in
  let rec range acc a b =
    if a > b then acc
    else range (a :: acc) (a + 1) b
  in
  sieve (range [] 2 n)
