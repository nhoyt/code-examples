/*
*   sieve.js - Implement the classic Sieve of Eratosthenes algorithm, which
*              finds all primes up to the provided input integer.
*/

function sieveOfEratosthenes (n) {
  // Initialize an array of n + 1 length with all index values set to true.
  // Since we will be using index values themselves later, the length needs
  // to be 'n + 1' to account for zero indexing.
  const primes = new Array(n + 1).fill(true);

  // By definition, both 0 and 1 are not prime numbers, so exclude them.
  primes[0] = false;
  primes[1] = false;

  // Loop through primes array marking all non-prime indices as false.
  for (let i = 2; i <= Math.sqrt(n); i++) {
    if (primes[i]) {
      for (let j = i * i; j <= n; j += i) {
        primes[j] = false;
      }
    }
  }

  // Create a new array whose values are the indices of the primes array
  // that are still set to true (were not marked as non-prime).
  const result = [];

  for (let i = 2; i <= n; i++) {
    if (primes[i]) {
       result.push(i);
    }
  }

  return result;
}

// Check for one command line argument
if (process.argv.length !== 3) {
  console.error(`Expected one command line argument!`);
  process.exit(1);
}

const input = Number(process.argv[2]);
const maximum = 10000;

// Check that input is an integer lte maximum value
if (Number.isInteger(input) && input <= maximum) {
  console.log(`All prime numbers less than or equal to ${input}:`);
  const result = sieveOfEratosthenes(input);
  console.dir(result, {depth: null, colors: true, maxArrayLength: null});
}
else {
  console.log(`Expected an integer argument <= ${maximum}!`);
  process.exit(1);
}
