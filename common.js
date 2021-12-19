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

module.exports = {
  read: (file) => {
    return fs
      .readFileSync(file)
      .toString('utf8');
  },
  sum: (a,b) => a+b,
  prod: (a,b) => a*b,
  min,
  max,
  concat,
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
