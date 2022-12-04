const fs = require('fs');


raw = `
2199943210
3987894921
9856789892
8767896789
9899965678
`

raw = fs
  .readFileSync("./day09.input")
  .toString('utf8');

const heightmap = raw.split('\n').filter(i => i.length).map(i => i.split('').map(i => parseInt(i)));

function sum(a,b) {return a+b}

function checkSurroundings(map, points, depth) {
  const lowerPoints = points
    .filter(pair => {
      return pair[0] >= 0 && pair[0] < map.length && pair[1] >= 0 && pair[1] < map[pair[0]].length;
    })
    .map(pair => {
      return map[pair[0]][pair[1]]
    }).map(i => i <= depth ? 1 : 0).reduce(sum,0);
  return lowerPoints === 0 ? depth + 1 : 0;
}

function scanMap(map) {
  const rowCount = map.length;
  return map.map((row, rowIndex) => {
    const columnCount = row.length;
    return row.map((cell, columnIndex) => {
      return checkSurroundings(map, [
        [rowIndex, columnIndex-1],
        [rowIndex-1, columnIndex],
        [rowIndex, columnIndex+1],
        [rowIndex+1, columnIndex]
      ], cell);
    })
  })
}


console.log(heightmap);
console.log(scanMap(heightmap));
console.log(scanMap(heightmap).reduce((a,b) => a + b.reduce(sum, 0), 0));
