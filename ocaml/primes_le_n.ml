(* primes_le_n.ml *)

let get_primes n =
  let root = 1 + int_of_float (sqrt (float_of_int n)) in

  (* i: current number in series to test *)
  (* a: Array w/ all item initially set to false *)
  (* primes: List that holds primes that we find, init w/ one item: 2 *)
  let rec sieve i a primes =
    if i >= n then
      List.rev primes
    else
      sieve (i + 2) (
        if i < root then
          let k = 2 * i in
          let rec remove_mult arr j =
            if j >= n then
              arr
            else
              remove_mult ((Array.set arr j true); arr) (j + k)
          in remove_mult a (i * i)
        else
          a
      ) (
      if (Array.get a i) = false then
        i :: primes
      else
        primes)
  in sieve 3 (Array.make n false) [2];;

(* Main function to handle input and print results *)
let print_primes n =
  let results = get_primes n in
  List.iter (fun p -> print_int p; print_string " ") results;
  print_newline ()

(* Example: Print the first 10 prime numbers *)
let () =
  let n = 100 in
  print_string ("The primes up to " ^ string_of_int n ^ " are: ");
  print_primes n
