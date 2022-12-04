const testInput = `vJrwpWtwJgWrhcsFMMfFFhFp
jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
PmmdzqPrVvPwwTWBwg
wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
ttgJtRGJQctTZtZT
CrZsJsPPZsGzwwsLwLmpwMDw`

const { read, sum } = require('../common.js');

const input = read('./day03.input', testInput);

const priority = i => {
  const charcode = i.charCodeAt(0)
  return charcode > 96 ? charcode - 96 : charcode - 38;
}

const rucksacks = input.split('\n').filter(i => i); // parse and clean

// First part: finding duplicated items

const prioritySum = rucksacks
  .map(i => [
    new Set(i.slice(0,i.length/2).split('')),
    new Set(i.slice(i.length/2).split(''))
  ]) // breaks the items individually and separate compartments
  .map( i =>
    [...i[0]].filter(j => i[1].has(j))[0]
  ) // checks for duplicates
  .map(priority)
  .reduce(sum)

console.log('Sum of priorities for duplications', prioritySum);

// Second part: finding badges

const badges = rucksacks
  .reduce((acc,current) => {
    if(acc[acc.length-1].length < 3) {
      acc[acc.length-1].push(current);
      return acc;
    }
    acc.push([current]);
    return acc;
  }, [[]]) // breaks the rucksacks into their groups
  .map(i => [new Set(i[0]), new Set(i[1]), new Set(i[2])]) // remove duplicates and enables set comparison
  .map(i => [...i[0]].filter(j => (i[1].has(j) && i[2].has(j)))[0]) // finds common item
  .map(priority)
  .reduce(sum)


console.log('Sum of priorities for badges', badges);
