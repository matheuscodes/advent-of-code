const fs = require('fs');

Object.defineProperty(Array.prototype, 'chunk', {
  value: function(chunkSize) {
    var R = [];
    for (var i = 0; i < this.length; i += chunkSize)
      R.push(this.slice(i, i + chunkSize));
    return R;
  }
});

const raw = fs
  .readFileSync("./day04.input")
  .toString('utf8')
  .split('\n');

const randoms = raw[0].split(',').map(i => parseInt(i));

const boards = raw.slice(1)
  .filter(i => i.length)
  .chunk(5)
  .map(i => i.map(j => j.replaceAll('  ',' ').trim().split(' ').map(k => parseInt(k))));

function markBoard(board, number) {
  for(let i = 0; i < 5; i++) {
    for(let j = 0; j < 5; j++) {
      if(board[i][j] === number) {
        board[i][j] = 0;
      }
    }
  }
}

function boardWins(board) {
  for(let i = 0; i < 5; i++) {
    let columnSum = 0;
    for(let j = 0; j < 5; j++) {
      columnSum += board[j][i];
    }
    const rowSum = board[i].reduce((a,b) => a+b, 0);

    if(columnSum === 0 || rowSum === 0) {
      return true;
    };
  }
  return false;
}

function sumRest(board) {
  return board.reduce((a,b) => a+b.reduce((a,b) => a+b, 0), 0);
}

function play(numbers, boards) {
  if(numbers.length <= 0) return;

  const number = numbers.shift();

  boards.forEach(board => markBoard(board, number));

  for(let i = 0; i < boards.length; i++) {
    if(boardWins(boards[i])) {
      return sumRest(boards[i]) * number;
    }
  }

  return play(numbers, boards);
}

console.log(play(randoms, boards));
