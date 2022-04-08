/* Create two dimensional array */
function createArray (numRows, numCols) {
  const rows = [];
  for (let row = 0; row < numRows; row++) {
    const cols = [];
    for (let col = 0; col < numCols; col++) {
      cols.push(true);
    }
    rows.push(cols);
  }
  return rows;
}

function checkAllTrue (arr) {
  return arr.every(row => row.every(col => col === true));
}

let myArray = createArray(12, 12);

console.log(`myArray[0][4]: ${myArray[0][4]}`);
myArray[3][7] = false;

for (let item of myArray) {
  console.log(`item: ${item}`);
}

console.log(checkAllTrue(myArray));
