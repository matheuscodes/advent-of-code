const fs = require('fs');

const input = fs.readFileSync("./day01.input").toString('utf8').split('\n');

let previous = parseInt(input[0]);
let increased = 0;

input.forEach((depth, index) => {
  if(parseInt(depth) - previous > 0) {
    increased++;
    console.log(depth, "increased");
  }
  else if (previous - parseInt(depth) === 0) {
    console.log(depth, "same");
  }
  else {
    console.log(depth, "decreased");
  }
  previous = parseInt(depth);
});

console.log("increased so many times: ", increased);
