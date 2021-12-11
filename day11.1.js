const fs = require('fs');

raw = `
5483143223
2745854711
5264556173
6141336146
6357385478
4167524645
2176841721
6882881134
4846848554
5283751526
`

raw = fs
  .readFileSync("./day11.input")
  .toString('utf8');

const sumFuncs = (sum, func) => sum + func();

const octopuses = raw.split('\n')
  .filter(i => i.length)
  .map((i,row) => i.split('').map((i,column) => parseInt(i)));

const TIME = 100;

function anyFlashesPending(pendingFlashes) {
  const any = (element) => element === 0;
  return pendingFlashes.map(i => i.some(any)).some(i => i);
}

for(let time = 0; time < TIME; time++) {
  octopuses.map((i,row) => i.map((i,column) => octopuses[row][column] += 1));
  const pendingFlashes = octopuses.map(i => i.map(i => i % 10 === 0 ? 0 : -1));

  while (anyFlashesPending(pendingFlashes)) {
    pendingFlashes.forEach((i,row) => {
      i.forEach((pending, column) => {
        if(pending === 0) {
          pendingFlashes[row][column] += 1;
          [
            [row-1, column-1],
            [row, column-1],
            [row+1, column-1],
            [row-1, column],
            [row+1, column],
            [row-1, column+1],
            [row, column+1],
            [row+1, column+1]
          ].filter(pair => {
            return pair[0] >= 0 && pair[0] < pendingFlashes.length && pair[1] >= 0 && pair[1] < pendingFlashes[pair[0]].length;
          }).forEach(position => {
            if(pendingFlashes[position[0]][position[1]] < 0) {
              octopuses[position[0]][position[1]] += 1;
              if(octopuses[position[0]][position[1]] % 10 === 0) {
                pendingFlashes[position[0]][position[1]] += 1;
              }
            }
          })
        }
      });
    });
  }
}

function prettyState() {
  return octopuses.map(i => i.map(i => i % 10).join('')).join('\n');
}

console.log(prettyState());
console.log(octopuses.map(i => i.map(i => Math.floor(i / 10)).reduce((a,b) => a+b,0)).reduce((a,b) => a+b,0));
