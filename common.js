const fs = require('fs');

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

function concat(acc, current) {
  if(!acc) return current;
  return acc.concat(current);
}

function uniquePoints(acc,current) {
  if(!acc.some(i => {
    return i[0] === current[0] &&
           i[1] === current[1] &&
           i[2] === current[2]
  })) acc.push(current);
  return acc;
}

function overlap(a, b) {
  return [...a].filter(j => b.has(j))
}

module.exports = {
  read: (file, fallback) => {
    if(fallback && process.argv[2] === '--test') {
      return fallback;
    }
    return fs
      .readFileSync(file)
      .toString('utf8');
  },
  sum: (a,b) => Number(a) + Number(b),
  prod: (a,b) => a*b,
  min,
  max,
  desc: (a,b) => (b - a),
  asc: (a,b) => (a - b),
  concat,
  overlap,
  uniquePoints,
  clone: i => JSON.parse(JSON.stringify(i)),
  movements: (row,column,board) => {
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
}
