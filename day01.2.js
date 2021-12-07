const fs = require('fs');

const input = fs.readFileSync("./day01.input").toString('utf8').split('\n').filter(i => i.length > 0).map(i => parseInt(i));

function getSumWindow(array, index, windowSize = 3) {
  return array.slice(index, index + windowSize).reduce((a,b) => a + b, 0);
}

let previous = getSumWindow(input, 0);
let increased = 0;

input.forEach((item, index) => {
  const depth = getSumWindow(input, index);
  if(parseInt(depth) - previous > 0) {
    increased++;
    console.log(depth, "increased");
  }
  else if (parseInt(depth) - previous === 0) {
    console.log(depth, "same");
  }
  else {
    console.log(depth, "decreased");
  }
  previous = parseInt(depth);
});

console.log("increased so many times: ", increased);
