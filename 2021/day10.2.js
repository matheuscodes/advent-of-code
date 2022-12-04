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
  ')': 1,
  ']': 2,
  '}': 3,
  '>': 4,
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
    return pile.map(i => delimiters[i]).reverse();
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
      return undefined;
    }
  }
}

const parsed = lines.map(i => i.split('')).map(i => parse(i));
console.log(parsed);
const weirdScore = (sum, current) => 5*sum + current;
const autoscored = parsed.filter(i => i).map(i => i.map(j => score[j]).reduce(weirdScore, 0));
console.log(autoscored.sort((a,b) => a - b));
const middle = Math.floor(autoscored.length / 2);
console.log(autoscored.sort((a,b) => a - b)[middle], middle);
