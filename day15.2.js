const fs = require('fs');

raw = `
1163751742
1381373672
2136511328
3694931569
7463417111
1319128137
1359912421
3125421639
1293138521
2311944581
`

raw = fs
  .readFileSync("./day15.input")
  .toString('utf8');

const rawGraph = raw.split('\n').filter(i => i.length).map(i => i.split('').map(i => parseInt(i)));

function increase(i) {
  if(i > 9) return i - 9;
  return i;
}

const graph = new Array(rawGraph.length*5);
for(let i = 0; i < graph.length; i++) {
  const rowMultiplier = Math.floor(i / rawGraph.length);
  graph[i] = rawGraph[i % rawGraph.length].map(
    i => increase(i+rowMultiplier)
  ).concat(
    rawGraph[i % rawGraph.length].map(i => increase(i+1+rowMultiplier))
  ).concat(
    rawGraph[i % rawGraph.length].map(i => increase(i+2+rowMultiplier))
  ).concat(
    rawGraph[i % rawGraph.length].map(i => increase(i+3+rowMultiplier))
  ).concat(
    rawGraph[i % rawGraph.length].map(i => increase(i+4+rowMultiplier))
  )
}


function movements(row,column,board) {
  return [
    // [row-1, column-1],
    [row, column-1],
    // [row+1, column-1],
    [row-1, column],
    [row+1, column],
    // [row-1, column+1],
    [row, column+1],
    // [row+1, column+1]
  ].filter(pair => {
    return pair[0] >= 0 && pair[0] < board.length && pair[1] >= 0 && pair[1] < board[pair[0]].length;
  })
}

const infinity = 9999999;
const pathRisks = new Array(graph.length);
for(let i = 0; i < pathRisks.length; i++) {
  pathRisks[i] = new Array(graph[i].length).fill(infinity);
}

graph[0][0] = 0;
pathRisks[0][0] = 0;
// pathRisks[0][0] = graph[0][0];
//Dijkstra
for(let i = 0; i < graph.length; i++) {
  for(let j = 0; j < graph[i].length; j++) {
    movements(i,j,graph)
      .forEach(next => {
        const currentRisk = pathRisks[next[0]][next[1]];
        const nextRisk = pathRisks[i][j] + graph[next[0]][next[1]]
        if(currentRisk > nextRisk) {
          pathRisks[next[0]][next[1]] = nextRisk;
        }
      });
  }
}

console.log(pathRisks[pathRisks.length-1][pathRisks[pathRisks.length-1].length-1]);
// Not right. Outputs 3068, right one (guessed) is 3063. =(
// Implementation not optimal.
