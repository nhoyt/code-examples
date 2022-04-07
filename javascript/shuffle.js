/*
*   shuffle.js - Fisher-Yates shuffle algorithm
*/

// This version makes a copy of the input array before
// shuffling is performed, leaving the input unaltered.
function shuffle (immutableArray) {
  const arr = immutableArray.slice();
  let c = arr.length, // current index
      r; // random index

  // while there remain elements to shuffle
  while (c > 0) {

    // randomly pick a remaining element
    r = Math.floor(Math.random() * c);
    c--;

    // and swap it with the current element
    [arr[c], arr[r]] = [arr[r], arr[c]];
  }

  return arr;
}

// This version modifies the array in place; it
// does not adhere to FP immutability principle.
Array.prototype.shuffle = function () {
  let m = this.length, i;
  while (m) {
    i = (Math.random() * m--) >>> 0;
    [this[m], this[i]] = [this[i], this[m]];
  }
  return this;
}

// Create an array with range 1..length using Array.from
let arr = Array.from({length: 36}, (item, index) => index + 1);
Object.freeze(arr);

console.log(shuffle(arr));

// console.log(arr.shuffle());

let r = Math.random() * 12;
console.log(r, r >>> 0);

console.log(arr);
