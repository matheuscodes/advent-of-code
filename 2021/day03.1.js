const fs = require('fs');

const bitCount = {
  '0': -1,
  '1': 1,
}

const input = fs
  .readFileSync("./day03.input")
  .toString('utf8')
  .split('\n')
  .filter(i => i.length > 0)
  .map(i => i.split('').map(i => bitCount[i]))
  .reduce((previous, current) => {
    return previous.map((i, index) => i + current[index])
  }, [0,0,0,0,0,0,0,0,0,0,0,0]);

function toGammaRate(i) {
  return parseInt(i.map(i => i > 0 ? 1 : (i < 0 ? 0 : 'NULL')).join(''), 2);
}

function toEpsilonRate(i) {
  return parseInt(i.map(i => i > 0 ? 0 : (i < 0 ? 1 : 'NULL')).join(''), 2);
}

console.log(input)
console.log(toGammaRate(input))
console.log(toEpsilonRate(input))
console.log(toGammaRate(input)*toEpsilonRate(input))
