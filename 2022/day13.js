const testInput = `[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]
`

const { read, clone, sum, concat } = require('../common.js');


const input = read('./day13.input', testInput)
    .split('\n\n')
    .filter(i => i)
    .map(i => i.split('\n'))
    .map(i => [JSON.parse(i[0]), JSON.parse(i[1])]);


const cloned = clone(input);

const indexes = []

function compareNumber(left,right) {
  // console.log(left,'??',right)
  if(left < right) return true;
  if(right < left) return false;
  return undefined;
}

function compare(leftArray, rightArray) {
  // console.log('compare',leftArray,'vs', rightArray)
  if(typeof leftArray === 'number' && typeof rightArray === 'number') {
    return compareNumber(leftArray, rightArray);
  } else if(typeof leftArray.length === 'number' || typeof rightArray.length === 'number') {
    // console.log("wtf"); throw Error()
    const left = typeof leftArray.length !== 'number' ? [leftArray] : clone(leftArray);
    const right = typeof rightArray.length !== 'number' ? [rightArray] : clone(rightArray);

    let found = undefined;
    let i = 0;
    // console.log(left,right)
    while(typeof found === 'undefined' && left.length > 0 && right.length > 0) {
      // console.log('blaa', left, right);
      found = compare(left.shift(), right.shift());
    }
    if(typeof found === 'undefined' && right.length > 0) return true;
    // console.log('blu', found, (left.length > 0 && right.length === 0))
    if(typeof found === 'undefined' && left.length > 0) return false;
    return found;
  }
}

input.forEach((pair, pairIndex) => {
  console.log('pair',pairIndex)
  const ordered = compare(pair[0], pair[1]);
  console.log(ordered);
  if(ordered) {
    indexes.push(pairIndex);
    console.log("ordered!", indexes);
  }
})

console.log(indexes.map(i => i + 1).reduce(sum,0));
// 5046 is too low

cloned.push([[[2]], [[6]]]);

const newIndexes = [];

cloned.reduce(concat, []).sort((a,b) => {
  return compare(a,b) ? -1 : 1
}).forEach((i, index) => {
  if(JSON.stringify(i) === '[[2]]' || JSON.stringify(i) === '[[6]]') newIndexes.push(index + 1);
})

console.log(newIndexes, newIndexes[0] * newIndexes[1])
