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
    .filter(i => i)
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
  {
    visited: [],
    path: [from],
  }
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

const visited = [];
let count = 0
while((paths.length > 0) && (pathsFound.length < 1000)) {
  const trial = paths.shift();
  const destination = trial.path[trial.path.length - 1];
  // console.log('trying', trial);
  if(count++ % 100 === 0) console.log(trial.path.length, trial.visited?.length, paths.length);
  // if(trial.length > 30) console.log(trial)
  if(destination.toString() === to.toString()) {
    console.log("New", trial.path.length);
    pathsFound.push(trial);
  } else {
    const directions = possibleDirections(input, destination);
    directions
      .filter(i => !trial.path.map(i => i.toString()).includes(i.toString()))
      // .filter(i => !trial.visited.map(i => i.toString()).includes(i.toString()))
      .forEach(i => paths.push({
        // visited: trial.visited.concat([destination]),
        path: trial.path.concat([i]),
      }));
    paths = paths
      .sort((a,b) => b.path.length - a.path.length)
      // .sort((a,b) => -(input[b.path[b.path.length-1][0]][b.path[b.path.length-1][1]] - input[a.path[a.path.length-1][0]][a.path[a.path.length-1][1]]))
  }
}

pathsFound = pathsFound.sort((a,b) => a.length - b.length)
console.log(pathsFound.length, pathsFound[0].length -1, paths.length)
// 1438 too high
// 1418 too high
