const testInput = `Monkey 0:
  Starting items: 79, 98
  Operation: new = old * 19
  Test: divisible by 23
    If true: throw to monkey 2
    If false: throw to monkey 3

Monkey 1:
  Starting items: 54, 65, 75, 74
  Operation: new = old + 6
  Test: divisible by 19
    If true: throw to monkey 2
    If false: throw to monkey 0

Monkey 2:
  Starting items: 79, 60, 97
  Operation: new = old * old
  Test: divisible by 13
    If true: throw to monkey 1
    If false: throw to monkey 3

Monkey 3:
  Starting items: 74
  Operation: new = old + 3
  Test: divisible by 17
    If true: throw to monkey 0
    If false: throw to monkey 1
`
/** 223092870 is the least common multiple of all monkey tests (manually read)
console.log(11, " -> ", 223092870 % 11);
console.log(3, " -> ", 223092870 % 3);
console.log(5, " -> ", 223092870 % 5);
console.log(7, " -> ", 223092870 % 7);
console.log(19, " -> ", 223092870 % 19);
console.log(2, " -> ", 223092870 % 2);
console.log(13, " -> ", 223092870 % 13);
console.log(17, " -> ", 223092870 % 17);
console.log(23, " -> ", 223092870 % 23);
**/

const { read, desc, clone } = require('../common.js');

const input = read('./day11.input', testInput)
    .split('\n\n')
    .filter(i => i);

function bigFloorDivide(value,denominator) {
  return (value - (value % denominator)) / denominator;
}

const multiplyOperation = (decrease,a,b) => (bigFloorDivide((BigInt(a) * BigInt(b)),decrease) % 223092870n);

const squareOperation = (decrease,a) => multiplyOperation(decrease,a,a);

const sumOperation = (decrease,a,b) => (bigFloorDivide((BigInt(a) + BigInt(b)),decrease));

function selectBindingOperation(line, decrease) {
  const operation = line.split('Operation: new = ')[1]
  if(operation === 'old * old') return squareOperation.bind(null, decrease);
  if(operation.includes('*')) return multiplyOperation.bind(null, decrease, BigInt(operation.split('*')[1]));
  if(operation.includes('+')) return sumOperation.bind(null, decrease, BigInt(operation.split('+')[1]));
  throw Error('Impossible operation.');
}

function monkeySelection(test, ifTrue, ifFalse) {
  const value = BigInt(test.split('Test: divisible by ')[1]);
  const monkeyIfTrue = ifTrue.split('If true: throw to ')[1];
  const monkeyIfFalse = ifFalse.split('If false: throw to ')[1];
  return (worry) => ((worry % value === 0n) ? monkeyIfTrue : monkeyIfFalse);
}

const monkeys = {};

input.forEach(i => {
  const lines = i.split('\n');
  monkeys[lines[0].split(':')[0].toLowerCase()] = {
    items: lines[1].split('Starting items:')[1].split(',').map(i => parseInt(i)),
    inspections: 0,
  }
});

// First part: controlled worry

const monkeys1 = {};

input.forEach(i => {
  const lines = i.split('\n');
  const monkeyKey = lines[0].split(':')[0].toLowerCase();
  monkeys1[monkeyKey] = {
    ...clone(monkeys[monkeyKey]),
    operation: selectBindingOperation(lines[2], BigInt(3)),
    selection: monkeySelection(lines[3],lines[4],lines[5]),
  }
});

for(let round = 0; round < 20; round++) {
  Object.keys(monkeys1).forEach(key => {
    const monkey = monkeys1[key];
    while(monkey.items.length > 0) {
      const item = monkey.items.shift();
      const worry = monkey.operation(item);
      const nextMonkey = monkey.selection(worry);
      monkeys1[nextMonkey].items.push(worry);
      monkey.inspections += 1;
    }
  });
  // console.log(monkeys1); break;
}

const shenanigans1 = Object.keys(monkeys1).map(i => monkeys1[i].inspections).sort(desc);
console.log("1) Monkey business after 20 rounds:", shenanigans1[0] * shenanigans1[1]);

// Second part: panic frenzy number overflow

const monkeys2 = {};

input.forEach(i => {
  const lines = i.split('\n');
  const monkeyKey = lines[0].split(':')[0].toLowerCase();
  monkeys2[monkeyKey] = {
    ...clone(monkeys[monkeyKey]),
    operation: selectBindingOperation(lines[2], BigInt(1)),
    selection: monkeySelection(lines[3],lines[4],lines[5]),
  }
});

for(let round = 0; round < 10000; round++) {
  Object.keys(monkeys2).forEach(key => {
    const monkey = monkeys2[key];
    while(monkey.items.length > 0) {
      const item = monkey.items.shift();
      const worry = monkey.operation(item);
      const nextMonkey = monkey.selection(worry);
      monkeys2[nextMonkey].items.push(worry);
      monkey.inspections += 1;
    }
  });
  // if(round === 999) { console.log(monkeys2); break; }
}

const shenanigans2 = Object.keys(monkeys2).map(i => monkeys2[i].inspections).sort(desc);
console.log("2) Monkey business after 10000 rounds:", shenanigans2[0] * shenanigans2[1]);
