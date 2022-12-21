const testInput = `Sabqponm
abcryxxl
accszExk
acctuvwj
abdefghi
`

const { read, clone, sum, concat } = require('../common.js');


let from;
let to;

const input = read('./day12.input', testInput)
    .split('\n')
    .filter(i => i);
const data = input
    .map((i, indexi) => i.split('').map((j, indexj) => {
      switch(j) {
        case 'S':
          from = [indexi, indexj];
          return 'a'.charCodeAt(0)
        case 'E':
          to = [indexi, indexj];
          return 'z'.charCodeAt(0)
        default:
          return j.charCodeAt(0)
      }
    }));

console.log(input, from, to);

let paths = [
  [from]
]

let pathsFound = [];

function possibleDirections(heightmap, position) {
  return [
    [position[0]-1, position[1]],
    [position[0]+1, position[1]],
    [position[0], position[1]-1],
    [position[0], position[1]+1],
  ]
  .filter(i => (i[0] >= 0 && i[1] >= 0) && heightmap[i[0]] && heightmap[i[0]][i[1]])
  .filter(i => {
    return (heightmap[i[0]][i[1]] - heightmap[position[0]][position[1]]) <= 1;
  })
}

function printVisited() {
  const screen = [];
  for(let i = 0; i < input.length; i++) {
    screen[i] = Array.apply('.', {length: input[0].length});
    for(let j = 0; j < input[0].length; j++) {
      screen[i][j] = input[i][j];
    }
  }
  visited.forEach(point => {
    screen[point[0]][point[1]] = screen[point[0]][point[1]].toUpperCase();
  });
  console.log(">>>", visited.length, paths.length)
  console.log(screen.map(i => i.join('')).join('\n'))
}

const visited = [];
const maxSize = input.length * input[0].length;
let count = 0
while((paths.length > 0) && (pathsFound.length <= 100) && (visited.length <= maxSize)) {
  const trial = paths.shift();
  const destination = trial[trial.length - 1];
  // console.log(destination)
  if(!visited.map(i => i.join(',').toString()).includes(destination.join(',').toString())) {
    visited.push(destination);
    printVisited()
  }
  if(destination.toString() === to.toString()) {
    console.log("New", trial.length);
    pathsFound.push(trial);
  } else {
    const directions = possibleDirections(data, destination)
      .filter(i => !trial.map(i => i.join(',').toString()).includes(i.join(',').toString()))
      // .filter(i => !visited.map(i => i.toString()).includes(i.toString()));
    // if(directions.length <= 0) console.log("wtf")
    directions
      .forEach(i => {
        // console.log(i)
        paths.push(trial.concat([i]))
      });
    paths = paths
      .filter(i => paths.map(i => JSON.stringify(i)).includes(JSON.stringify(i)))
      // .filter(i => i.length <= maxSize)
      // .filter(i => i.filter(i => !visited.map(i => i.join(',').toString()).includes(i.join(','))).length > 0)
      .sort((a,b) => b.length - a.length)
      // .sort((a,b) => (data[b[b.length-1][0]][b[b.length-1][1]] - data[a[a.length-1][0]][a[a.length-1][1]]))
      // .sort((a,b) => {
      //   const size = (a.length > b.length) ? 1 : -1;
      //   const height = 0;(data[a[a.length-1][0]][a[a.length-1][1]] < data[b[b.length-1][0]][b[b.length-1][1]]) ? 1 : -1;
      //   return size + height;
      // })
  }
  // printVisited();
}
console.log((paths.length > 0), (pathsFound.length <= 100), (visited.length < maxSize))
pathsFound = pathsFound.sort((a,b) => a.length - b.length)
console.log("end", pathsFound.length, pathsFound[0]?.length - 1, paths.length, maxSize)
// 1438 too high
// 1418 too high
