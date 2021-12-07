const fs = require('fs');

const COMMANDS = {
  'forward': {aim: 0, horizontal: 1},
  'down': {aim: 1, horizontal: 0},
  'up': {aim: -1, horizontal: 0},
}

const position = {
  horizontal: 0,
  depth: 0,
}

let aim = 0;

const input = fs
  .readFileSync("./day02.input")
  .toString('utf8')
  .split('\n')
  .filter(i => i.length > 0)
  .map(i => i.split(' '))
  .map(i => {
    const value = parseInt(i[1]);
    const command = COMMANDS[i[0]];
    aim += value * command.aim;
    return {
      depth: value * aim * command.horizontal,
      horizontal: value * command.horizontal,
    }
  }, 0);

// const result = input.reduce((previous,current) => {
//   console.log(previous, current)
//   return {
//     depth: previous.depth + current.aim * current.horizontal,
//     horizontal: previous.horizontal + current.horizontal,
//   }
// }, position);
const result = input.reduce((a,b) => {
  return {
    depth: a.depth + b.depth,
    horizontal: a.horizontal + b.horizontal,
  }
}, position);

console.log(result);
console.log(result.depth * result.horizontal);
