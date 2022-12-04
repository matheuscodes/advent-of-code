const fs = require('fs');

let raw = `
be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
`
raw = fs
  .readFileSync("./day08.input")
  .toString('utf8')
const signals = raw.split('\n').filter(i => i.length).map(i => i.split(' | ')[0]).map(i => i.split(' '));
const outputs = raw.split('\n').filter(i => i.length).map(i => i.split(' | ')[1]).map(i => i.split(' '));

const digits = [
  {
    segments: 'abcefg',
    digit: 0,
  },
  {
    segments: 'cf',
    digit: 1,
  },
  {
    segments: 'acdeg',
    digit: 2,
  },
  {
    segments: 'acdfg',
    digit: 3,
  },
  {
    segments: 'bcdf',
    digit: 4,
  },
  {
    segments: 'abdfg',
    digit: 5,
  },
  {
    segments: 'abdefg',
    digit: 6,
  },
  {
    segments: 'acf',
    digit: 7,
  },
  {
    segments: 'abcdefg',
    digit: 8,
  },
  {
    segments: 'abcdfg',
    digit: 9,
  },
];

const counts = outputs.reduce((counts, output) => {
  output.forEach(item => {
    digits.filter(digit => digit.segments.length === item.length)
      .map(i => i.digit)
      .forEach(digit => counts[digit] += 1);
  });
  return counts;
}, new Array(10).fill(0));

console.log(counts);
console.log(counts[1], counts[4], counts[7], counts[8], (counts[1]+counts[4]+counts[7]+counts[8]))
