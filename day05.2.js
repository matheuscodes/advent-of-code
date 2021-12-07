const fs = require('fs');

const raw = fs
  .readFileSync("./day05.input")
  .toString('utf8')
  .split('\n')
  .filter(i => i.length);

const X = 0;
const Y = 1;

const FROM = 0;
const TO = 1;

const SIZE = 1000;

let pairs = raw.map(i => i.split(' -> ').map(j => j.split(',').map(k => parseInt(k))))

const ocean = []
for(let i = 0; i < SIZE; i++) {
  ocean[i] = [];
  for(let j = 0; j < SIZE; j++) {
    ocean[i][j] = 0;
  }
}

function range(from, to) {
  const range = [];
  if(from > to) {
    for(let i = from; i >= to; i--) range.push(i);
  } else {
    for(let i = from; i <= to; i++) range.push(i);
  }
  return range;
}

// pairs = [
//   [[0,9],[5,9]],
//   [[8,0],[0,8]],
//   [[9,4],[3,4]],
//   [[2,2],[2,1]],
//   [[7,0],[7,4]],
//   [[6,4],[2,0]],
//   [[0,9],[2,9]],
//   [[3,4],[1,4]],
//   [[0,0],[8,8]],
//   [[5,5],[8,2]]
// ]


pairs.forEach(pair => {
  const rangeX = range(pair[FROM][X], pair[TO][X]);
  const rangeY = range(pair[FROM][Y], pair[TO][Y]);

  if(rangeX.length > 1 && rangeY.length > 1) {
    if(Math.abs(pair[FROM][X] - pair[TO][X]) === Math.abs(pair[FROM][Y] - pair[TO][Y])) {
      for(let i = 0; i < rangeX.length; i++) {
        ocean[rangeY[i]][rangeX[i]] += 1;
      }
    }
    return;
  }

  for(let i = 0; i < rangeX.length; i++) {
    for(let j = 0; j < rangeY.length; j++) {
      ocean[rangeY[j]][rangeX[i]] += 1;
    }
  }
});

let overlaps = 0
for(let i = 0; i < SIZE; i++) {
  for(let j = 0; j < SIZE; j++) {
    if(ocean[i][j] >= 2) {
      overlaps++;
    };
  }
}


console.log(ocean.map(i => i.join('')).join('\n'));
console.log(overlaps);
