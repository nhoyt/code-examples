(* first_n_primes.ml *)

(* Function to check if a number is prime *)
let is_prime n =
  if n < 2 then false
  else
    let rec check i =
      if i * i > n then true
      else if n mod i = 0 then false
      else check (i + 1)
    in
    check 2

(* Recursive function to find the first n primes *)
let rec get_primes n current_num acc =
  if List.length acc = n then 
    List.rev acc
  else if is_prime current_num then 
    get_primes n (current_num + 1) (current_num :: acc)
  else 
    get_primes n (current_num + 1) acc

(* Main function to handle input and print results *)
let print_first_n_primes n =
  let primes = get_primes n 2 [] in
  List.iter (fun p -> print_int p; print_string " ") primes;
  print_newline ()

(* Example: Print the first 10 prime numbers *)
let () = 
  let n = 10 in
  print_string ("The first " ^ string_of_int n ^ " primes are: ");
  print_first_n_primes n
