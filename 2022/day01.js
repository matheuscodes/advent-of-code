const testInput = `1000
2000
3000

4000

5000
6000

7000
8000
9000

10000`;

const { read, sum, clone, max } = require('../common.js');
const input = read('./day01.input', testInput);

// First part: finding elf with most calories.

const groupedCalories = input.split('\n\n') // split into groups
  .map(i => i.split('\n').filter(i => i)) // split items, clean empty
  .map(i => i.reduce(sum, 0)); // sum calories in group

// Max
const largest = groupedCalories
  .reduce(max, 0);

console.log("Elf with most calories:", largest);

// Second part: top three elfs

// Max without first
const second = groupedCalories
  .filter(i => i !== largest)
  .reduce(max, 0);

// Max without first and second
const third = groupedCalories
  .filter(i => i !== largest && i !== second)
  .reduce(max, 0);

console.log("Elf with 2nd most calories:", second);
console.log("Elf with 3rd most calories:", third);
console.log("Sum of the top 3:",  largest+ second+ third);
