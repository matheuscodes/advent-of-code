const fs = require('fs');

const bitCount = {
  '0': -1,
  '1': 1,
}

const raw = fs
  .readFileSync("./day03.input")
  .toString('utf8')
  .split('\n')
  .filter(i => i.length > 0);

const input = raw
  .map(i => i.split('').map(i => bitCount[i]))
  .reduce((previous, current) => {
    return previous.map((i, index) => i + current[index])
  }, [0,0,0,0,0,0,0,0,0,0,0,0]);

function oxygen(input, index = 0) {
  if(input.length === 1) return input[0];

  if(index > 12) throw Error("Overflow!");

  const data = input.map(i => i.split('').map(i => bitCount[i]))
                    .reduce((previous, current) => {
                      return previous.map((i, index) => i + current[index])
                    }, [0,0,0,0,0,0,0,0,0,0,0,0]);

  if(data[index] >= 0) {
    return oxygen(input.filter(i => i[index] === '1'), index + 1) ;
  } else {
    return oxygen(input.filter(i => i[index] === '0'), index + 1) ;
  }
}

function co2(input, index = 0) {
  if(input.length === 1) return input[0];

  if(index >= 12) throw Error("Overflow!");

  const data = input.map(i => i.split('').map(i => bitCount[i]))
                    .reduce((previous, current) => {
                      return previous.map((i, index) => i + current[index])
                    }, [0,0,0,0,0,0,0,0,0,0,0,0]);

  if(data[index] >= 0) {
    return co2(input.filter(i => i[index] === '0'), index + 1) ;
  } else {
    return co2(input.filter(i => i[index] === '1'), index + 1) ;
  }
}

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
console.log(parseInt(oxygen(raw), 2))
console.log(parseInt(co2(raw), 2))
console.log(parseInt(oxygen(raw), 2) * parseInt(co2(raw), 2))
