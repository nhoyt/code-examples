let primes n =
  let root = 1 + int_of_float (sqrt (float_of_int n)) in
  let rec sieve i a prime_list =
    if i >= n then
      List.rev prime_list
    else
      sieve (i + 2) (
        if i < root then
          let k = 2 * i in
          let rec rem_mult arr j =
            if j >= n then
              arr
            else
              rem_mult ((Array.set arr j true); arr) (k + j)
          in rem_mult a (i * i)
        else
          a
      ) (if (Array.get a i) = false then
    i :: prime_list
          else
        prime_list)
  in sieve 3 (Array.make n false) [2];;

(* Main function to handle input and print results *)
let print_primes n =
  let results = primes n in
  List.iter (fun p -> print_int p; print_string " ") results;
  print_newline ()

(* Example: Print the first 10 prime numbers *)
let () =
  let n = 100 in
  print_string ("The primes up to " ^ string_of_int n ^ " are: ");
  print_primes n
