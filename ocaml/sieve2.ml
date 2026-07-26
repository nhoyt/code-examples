(* Return list of primes up to n *)

let primes n =
  (* Note: range places each successive value at the front of the list *)
  let rec range acc a b =
    if a > b then acc
    else range (a :: acc) (a + 1) b
  in
  let rec sieve = function
    | [] -> [] (* rec. stopping poing *)
    | p :: xs -> p :: sieve (List.filter (fun x -> x mod p <> 0) xs)
  in
  sieve (List.rev (range [] 2 n))

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
