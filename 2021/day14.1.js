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

let polymer = start;
for(let step = 0; step < 10; step++) {
  let current = [];
  for(let i = 0; i + 1 < polymer.length; i++) {
    let pair = polymer.slice(i, i + 2);
    current.push(pair[0]);
    current.push(instructions[pair]);
    if(i + 2 >= polymer.length) {
      current.push(pair[1]);
    }
  }
  polymer = current.join('');
}

const counts = {}
polymer.split('').forEach(i => {
  if(!counts[i]) counts[i] = 0;
  counts[i] += 1;
})

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
console.log(polymer);
console.log(counts);
console.log(counting.reduce(max), counting.reduce(min),counting.reduce(max) - counting.reduce(min));
