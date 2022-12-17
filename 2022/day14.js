const testInput = `498,4 -> 498,6 -> 496,6
503,4 -> 502,4 -> 502,9 -> 494,9
`



const { read, min, clone, max } = require('../common.js');

const input = read('./day14.input', testInput)
    .split('\n')
    .filter(i => i);


const minX = 0//parseInt(input.flatMap(i => i.split(' -> ')).map(i => parseInt(i.split(',')[0])).reduce(min,1000));
const maxX = 1000//parseInt(input.flatMap(i => i.split(' -> ')).map(i => parseInt(i.split(',')[0])).reduce(max,0));
const minY = 0//parseInt(input.flatMap(i => i.split(' -> ')).map(i => parseInt(i.split(',')[1])).reduce(min,1000));
const maxY = parseInt(input.flatMap(i => i.split(' -> ')).map(i => parseInt(i.split(',')[1])).reduce(max,0)) + 2;

console.log(minX, maxX, minY, maxY);

const space = [];

for(let i = 0; i <= maxY; i += 1) {
  space[i] = [];
  for(let j = minX; j <= maxX; j += 1) {
    space[i][j - minX] = '.'
  }
}

for(let j = minX; j <= maxX; j += 1) {
  space[maxY][j - minX] = '#'
}

space[0][500 - minX] = '+';

const paths = input.map(i => i.split(' -> ')).map(i => i.map(i => i.split(',').map(i => parseInt(i))));

paths.forEach(path => {
  // console.log(path)
  let start = path.shift();
  if(start) space[start[1]][start[0] - minX] = '#'
  while(path.length > 0) {
    const next = path.shift();
    const iterator = next[1] != start[1] ? ((next[1] - start[1])/Math.abs(next[1] - start[1])) : 1;
    // console.log(start,next);
    for(let i = start[1]; iterator > 0 ? i <= next[1] : i >= next[1]; i += iterator) {
      const jterator = next[0] != start[0] ? ((next[0] - start[0])/Math.abs(next[0] - start[0])) : 1;
      for(let j = start[0]; jterator > 0 ? j <= next[0] : j >= next[0]; j += jterator) {
        // console.log('ij',i,j)
        space[i][j - minX] = '#'
      }
    }
    // print(spa  ce);
    space[next[1]][next[0] - minX] = '#'
    start = next;
  }
})

function print(space) {
  space.forEach(i => {
    console.log(i.join(''));
  });
}

// print(space);

function getSpace(point) {
  // console.log(point)
  return space[point[1]][point[0] - minX];
}

function putSpace(point, what = 'o') {
  return space[point[1]][point[0] - minX] = what;
}

function nextMove(grain) {
  if(!isFalling(grain)) {
    return [
      [grain[0], grain[1] + 1],
      [grain[0] - 1, grain[1] + 1],
      [grain[0] + 1, grain[1] + 1],
    ]
    // .filter(i => !isFalling(i))
    .filter(i => getSpace(i) !== '#' && getSpace(i) !== 'o');
  }
  return [];
}

function isFalling(grain) {
  if(grain[0] < minX || grain[0] > maxX) return true;
  if(grain[1] > maxY) return true;
}

let i = 0;
while(1) {
  let sand = [500, 0];
  let next = nextMove(sand);
  while(next.length > 0) {
    sand = next.shift();
    next = nextMove(sand);
  }
  if(isFalling(sand)) break;
  if(sand) {
    i += 1;
    putSpace(sand);
  }
  if(sand[0] === 500 && sand[1] === 0) break;
}

console.log(i)

let sand = [500, 0];
let next = nextMove(sand);
while(next.length > 0) {
  putSpace(sand,'~');
  sand = next.shift();
  next = nextMove(sand);
}
if(sand) {
  putSpace(sand,'~');
}

print(space);

//1310 too high
//1300 too high
//1238 too low
