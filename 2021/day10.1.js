const fs = require('fs');


raw = `
[({(<(())[]>[[{[]{<()<>>
[(()[<>])]({[<{<<[]>>(
{([(<{}[<>[]}>{[]{[(<()>
(((({<>}<{<{<>}{[]{[]{}
[[<[([]))<([[{}[[()]]]
[{[{({}]{}}([{[{{{}}([]
{<[[]]>}<{[{[{[]{()[[[]
[<(<(<(<{}))><([]([]()
<{([([[(<>()){}]>(<<{{
<{([{{}}[<[[[<>{}]]]>[]]
`

raw = fs
  .readFileSync("./day10.input")
  .toString('utf8');

const lines = raw.split("\n").filter(i => i.length);

const delimiters = {
  '(':')',
  '[':']',
  '{':'}',
  '<':'>',
}

const score = {
  ')': 3,
  ']': 57,
  '}': 1197,
  '>': 25137,
}

function isClosing(char) {
  return Object.keys(delimiters).map(i => delimiters[i]).includes(char);
}

function isOpening(char) {
  return typeof delimiters[char] !== 'undefined';
}

function expected(char) {
  return delimiters[char]
}

function parse(line, pile = []) {
  if(line.length <= 0) {
    return undefined;
  }
  if(pile.length <= 0) {
    pile.push(line.shift());
    return parse(line, pile);
  }
  const current = pile.pop();
  const token = line.shift();
  if(token === expected(current)) {
    return parse(line, pile);
  } else {
    if(isOpening(token)) {
      pile.push(current);
      pile.push(token);
      return parse(line, pile);
    } else {
      return token;
    }
  }
}

console.log(lines.map(i => i.split('')).map(i => parse(i)));
console.log(lines.map(i => i.split('')).map(i => parse(i)).filter(i => i).map(i => score[i]).reduce((a,b) => a+b, 0));
