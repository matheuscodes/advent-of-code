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

function listLowPoints(map) {
  return map.map((row, rowIndex) => {
    return row.map((cell, columnIndex) => {
      return cell > 0 ? [rowIndex, columnIndex] : undefined;
    }).filter(i => i);
  }).reduce((all, current) => all.concat(current), []);
}

function navigateBasin(map, point) {
  const depth = map[point[0]][point[1]];
  let walked = 1;
  map[point[0]][point[1]] = -1;
  [
    [point[0], point[1]-1],
    [point[0]-1, point[1]],
    [point[0], point[1]+1],
    [point[0]+1, point[1]],
  ].filter(pair => {
    return pair[0] >= 0 && pair[0] < map.length && pair[1] >= 0 && pair[1] < map[pair[0]].length;
  })
  .forEach(pair => {
    if(map[pair[0]][pair[1]] > depth && map[pair[0]][pair[1]] < 9) {
      walked += navigateBasin(map, pair, map[pair[0]][pair[1]]);
    }
  });
  return walked;
}

function printMap(map) {
  console.log(map.map(i => i.map(i => i < 0 ? 'X' : i).join('')).join('\n'));
}

const lowPoints = listLowPoints(scanMap(heightmap));
console.dir(lowPoints, {depth:null});
const basins = lowPoints.map(i => {
  // console.log(i[0].coordinates);
  // printMap(heightmap);
  return navigateBasin(heightmap, i);
});

const sorted = basins.sort((a,b) => b - a)

console.log(sorted[0] * sorted[1] * sorted[2]);
