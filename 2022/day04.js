const testInput = `2-4,6-8
2-3,4-5
5-7,7-9
2-8,3-7
6-6,4-6
2-6,4-8
`

const { read, overlap } = require('../common.js');

const input = read('./day04.input', testInput);

function buildArea(str) {
  const set = str.split('-');
  const array = []
  for(let i = parseInt(set[0]); i <= set[1]; i += 1) array.push(i);
  return new Set(array);
}

const jobs = input
  .split('\n')
  .filter(i => i)
  .map(i => i.split(',')) // split ranges
  .map(i => [buildArea(i[0]), buildArea(i[1])]); // spread ranges for ease of calculation

// First part: count full overlaps

const fullOverlaps = jobs
  .filter(i => {
    return overlap(i[0], i[1]).length === i[0].size ||
    overlap(i[1], i[0]).length === i[1].size
  }) // select full overlaps
  .length;


console.log('Full overlaps:', fullOverlaps);

// Second part: count partial overlaps

const partialOverlaps = jobs
  .filter(i => {
    return overlap(i[0], i[1]).length > 0 ||
    overlap(i[1], i[0]).length > 0
  }) // select partial overlaps
  .length;

console.log('Partial overlaps:', partialOverlaps)
