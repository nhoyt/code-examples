/* array-2d.js */

const ROWS = 12, COLS = 12;

function create2dArray (rows, cols) {
  const rowsArray = [];
  for (let ri = 0; ri < rows; ri++) {
    const colsArray = [];
    for (let ci = 0; ci < cols; ci++) {
      colsArray.push(false);
    }
    rowsArray.push(colsArray);
  }
  return rowsArray;
}

function checkAllTrue (arr) {
  return arr.every(row => row.every(col => col === true));
}

function getNumberOfTrueItems (arr) {
  let counter = 0;
  for (let row of arr) {
    for (let col of row) {
      if (col === true) { counter++; }
    }
  }
  return counter;
}

const myArray = create2dArray(ROWS, COLS);

console.log(`myArray[0][4]: ${myArray[0][4]}`);
myArray[3][7] = true;
myArray[7][3] = true;

// Display all of the entries
for (let row of myArray) {
  console.log(`row: ${row}`);
}

const allTrue = checkAllTrue(myArray);
if (allTrue) {
  console.log(`allTrue: ${allTrue}`);
}
else {
  const numEntries = ROWS * COLS;
  const numTrue = getNumberOfTrueItems(myArray);
  console.log(`Out of ${numEntries} entries, ${numEntries - numTrue} are not yet completed.`);
}
