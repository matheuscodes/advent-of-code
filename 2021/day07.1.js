const fs = require('fs');

const raw = fs
  .readFileSync("./day07.input")
  .toString('utf8')
  .split('\n')
  .filter(i => i.length > 0);

// raw[0] = "16,1,2,0,4,2,7,1,2,14";
let crabs = raw[0].split(',').map(i => parseInt(i));

let distances = crabs.map((crabHorizontal, index) => {
  return {
    crab: index,
    distance: crabs.map(i => Math.abs(i - crabHorizontal)).reduce((a,b) => a+b, 0)
  }
});

const min = distances.reduce((min, current) => {
  if(typeof min !== 'undefined') {
    if(min.distance > current.distance) {
      return current;
    }
    return min;
  }
  return current;
}, undefined)

console.log(distances);
console.log(min);
console.log(crabs[min.crab]);
