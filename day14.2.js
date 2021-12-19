const fs = require('fs');

raw = `
NNCB

CH -> B
HH -> N
CB -> H
NH -> C
HB -> C
HC -> B
HN -> C
NN -> C
BH -> H
NC -> B
NB -> B
BN -> B
BB -> N
BC -> B
CC -> N
CN -> C
`

raw = fs
  .readFileSync("./day14.input")
  .toString('utf8');

const start = raw.split('\n').filter(i => i.length)[0];

const instructions = {}
raw.split('\n').filter(i => i.length).slice(1).map(i => i.split(' -> ')).map(i => instructions[i[0]] = i[1]);

const counts = {}

Object.defineProperty(Array.prototype, 'chunk', {
  value: function(chunkSize) {
    var R = [];
    for (var i = 0; i < this.length; i += chunkSize)
      R.push(this.slice(i, i + chunkSize));
    return R;
  }
});

function addCount(i) {
  if(!counts[i]) counts[i] = 0;
  counts[i] += 1;
}

function mergeCount(merger,counts) {
  Object.keys(merger).forEach(i => {
    if(!counts[i]) counts[i] = 0;
    counts[i] += merger[i];
  });
  return counts;
}

const cache = {};

function calculatePolymer(start, steps) {
  if(steps <= 0) {
    // console.log(start.split(''));
    // start.split('').forEach(i => addCount(i));
    // addCount(start[0]);
    return {[start[0]]: 1};
  } else {
    let polymer = start;
    let current = '';
    for(let i = 0; i + 1 < polymer.length; i++) {
      let pair = polymer.slice(i, i + 2);

      if(i + 2 >= polymer.length) {
        current = current.concat([pair[0],instructions[pair],pair[1]].join(''));
      } else {
        current = current.concat([pair[0],instructions[pair]].join(''));
      }
    }
    const counts = {}
    for(let i = 0; i < current.length - 1; i++) {
      const index = [current[i], current[i+1], steps - 1].join('');
      if(!cache[index]) {
        cache[index] = calculatePolymer([current[i],current[i+1]].join(''), steps - 1);
      }
      mergeCount(cache[index],counts);
    }
    return counts;
  }
}

mergeCount(calculatePolymer(start, 40), counts);
addCount(start[start.length - 1]);

const counting = Object.keys(counts).map(i => counts[i]);

const max = (max, i) => {
  if(i > max || typeof max === 'undefined') return i;
  return max
}

const min = (min, i) => {
  if(i < min || typeof min === 'undefined') return i;
  return min
}


console.log(instructions);
console.log(counts);
console.log(counting.reduce(max), counting.reduce(min),counting.reduce(max) - counting.reduce(min));
