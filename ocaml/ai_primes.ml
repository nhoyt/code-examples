(* Return list of primes up to n *)

(*
* Original version of primes that requires call to List.rev, since
* range adds each successively incremented value at front of list.
*)
let primes_original n =
  let rec range acc a b =
    if a > b then acc
    else range (a :: acc) (a + 1) b
  in
  let rec sieve = function
    | [] -> [] (* rec. stopping poing *)
    | p :: xs -> p :: sieve (List.filter (fun x -> x mod p <> 0) xs)
  in
  sieve (List.rev (range [] 2 n))

(*
* Updated version of primes that decrements the param passed as the
* upper bound of range to produce a list in ascending order.
*)
let primes n =
  let rec range acc a b =
    if b < a then acc
    else range (b :: acc) a (b - 1)
  in
  let rec sieve = function
    | [] -> [] (* rec. stopping poing *)
    | p :: xs -> p :: sieve (List.filter (fun x -> x mod p <> 0) xs)
  in
  sieve (range [] 2 n)

(* Main function to handle input and print results *)
let print_results n =
  let results = primes n in
  List.iter (fun p -> print_int p; print_string " ") results;
  print_newline ()

(* Example: Print the prime numbers up to n *)
let () =
  let n = 100 in
  print_string ("The prime numbers up to " ^ string_of_int n ^ " are: ");
  print_results n
