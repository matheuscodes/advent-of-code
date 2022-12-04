const fs = require('fs');

const raw = fs
  .readFileSync("./day07.input")
  .toString('utf8')
  .split('\n')
  .filter(i => i.length > 0);

// raw[0] = "16,1,2,0,4,2,7,1,2,14";
let crabs = raw[0].split(',').map(i => parseInt(i));

function range(from, to) {
  const range = [];
  if(from > to) {
    for(let i = from; i >= to; i--) range.push(i);
  } else {
    for(let i = from; i <= to; i++) range.push(i);
  }
  return range;
}

function calculateFuel(distance) {
  const absolute = Math.abs(distance);
  return range(1, absolute).reduce((a,b) => a + b, 0);
}

function min(a, b) {
  if(typeof a !== 'undefined' && a < b) {
    return a;
  }
  return b;
}

function max(a, b) {
  if(typeof a !== 'undefined' && a > b) {
    return a;
  }
  return b;
}

console.log(crabs.reduce(min), crabs.reduce(max));


let distances = range(crabs.reduce(min), crabs.reduce(max)).map((crabHorizontal) => {
  return {
    horizontal: crabHorizontal,
    distance: crabs.map(i => calculateFuel(i - crabHorizontal)).reduce((a,b) => a+b, 0)
  }
});

const minimum = distances.reduce((min, current) => {
  if(typeof min !== 'undefined') {
    if(min.distance > current.distance) {
      return current;
    }
    return min;
  }
  return current;
}, undefined)

console.log(distances);
console.log(minimum);
