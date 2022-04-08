const myArray = [];

/* initialize as two dimensional array */
function init2d (arr, max) {
  for (let i = 0; i < max; i++) {
    for (let j = 0; j < max; j++) {
      arr[i][j] = false;
    }
  }
}

function checkAllTrue (arr) {
  return arr.every(i => i.every(j => j === true))
}

myArray = init2d(myArray);
console.log(checkAllTrue(myArray);
