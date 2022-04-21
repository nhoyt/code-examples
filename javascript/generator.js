/*
*   generator.js
*/

function *nextValue () {
  let counter = 0;
  while (true) {
    yield ++counter;
  }
}

const valueIterator = nextValue();

function getDataId (prefix) {
  const suffix = valueIterator.next().value;
  return `${prefix}-${suffix}`;
}

console.log(`getDataId: ${getDataId('a')}`);
console.log(`getDataId: ${getDataId('b')}`);
console.log(`getDataId: ${getDataId('c')}`);
console.log(`getDataId: ${getDataId('a')}`);
