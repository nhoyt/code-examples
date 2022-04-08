const rows = 12, cols = 12;

/* Create two dimensional array */
function create2dArray (numRows, numCols) {
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

function getNumberOfTrueItems (arr) {
  let counter = 0;
  for (let row of arr) {
    for (let col of row) {
      if (col === true) { counter++; }
    }
  }
  return counter;
}

const myArray = create2dArray(rows, cols);

console.log(`myArray[0][4]: ${myArray[0][4]}`);
myArray[3][7] = false;
myArray[7][3] = false;

// Display all of the entries
for (let row of myArray) {
  console.log(`row: ${row}`);
}

const allTrue = checkAllTrue(myArray);
if (allTrue) {
  console.log(`allTrue: ${allTrue}`);
}
else {
  const numEntries = rows * cols;
  const numTrue = getNumberOfTrueItems(myArray);
  console.log(`Out of ${numEntries} entries, ${numEntries - numTrue} are incorrect.`);
}
