const fs = require('fs');

const COMMANDS = {
  'forward': {depth: 0, horizontal: 1},
  'down': {depth: 1, horizontal: 0},
  'up': {depth: -1, horizontal: 0},
}

const position = {
  depth: 0,
  horizontal: 0,
}

const input = fs
  .readFileSync("./day02.input")
  .toString('utf8')
  .split('\n')
  .filter(i => i.length > 0)
  .map(i => i.split(' '))
  .map(i => {
    const value = parseInt(i[1]);
    const command = COMMANDS[i[0]];
    return {
      depth: value * command.depth,
      horizontal: value * command.horizontal,
    }
  });

const result = input.reduce((a,b) => {
  return {
    depth: a.depth + b.depth,
    horizontal: a.horizontal + b.horizontal,
  }
}, position);

console.log(result.depth * result.horizontal);
