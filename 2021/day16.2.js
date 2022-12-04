const {
  read,
  min,
  max,
  sum,
  prod,
} = require('./common.js');


let
// raw = `C200B40A82`
// raw = `04005AC33890`
// raw = `880086C3E88112`
// raw = `CE00C43D881120`
// raw = `D8005AC2A8F0`
// raw = `F600BC2D8F`
// raw = `9C005AC2F8F0`
// raw = `9C0141080250320F1802104A08`
raw = read("./day16.input");


function hex2bin(hex){
  return (parseInt(hex, 16).toString(2)).padStart(4,'0');
}

const binaryGroups = raw.replace('\n','').split('').map(i => hex2bin(i));

let versionSum = 0;

function ensureSize(size, current, from) {
  let reading = current;
  while(reading.length < size) reading = reading.concat(from.shift().split(''));
  return reading;
}

const operations = [
  '+',
  '*',
  'min',
  'max',
  ' ',
  '>',
  '<',
  '='
]

function parse(reading = [], binaryGroups, numberValues = []) {
  reading = ensureSize(3, reading, binaryGroups);
  const version = parseInt(reading.slice(0,3).join(''), 2);
  reading = ensureSize(6, reading, binaryGroups);
  const type = parseInt(reading.slice(3,6).join(''), 2);
  versionSum += version;
  if(type === 4) {
    let pointer = 6;
    reading = ensureSize(pointer+5, reading, binaryGroups);
    let prefix = reading[pointer];
    const values = [];
    while(prefix === '1') {
      values.push(reading.slice(pointer+1,pointer+5).join(''));
      pointer += 5;
      reading = ensureSize(pointer+5, reading, binaryGroups);
      prefix = reading[pointer];
    }

    reading = ensureSize(pointer+5, reading, binaryGroups);
    values.push(reading.slice(pointer+1,pointer+5).join(''));
    numberValues.push({
      "operation": "&",
      "numberValues": [parseInt(values.join(''), 2)],
    });
    return reading.slice(pointer+5, reading.length);
  } else {
    reading = ensureSize(7, reading, binaryGroups);
    const id = reading[6];
    const thisOperation = {
      "operation": operations[type],
      "numberValues": [],
    }
    numberValues.push(thisOperation);
    if(id === '0') {
      reading = ensureSize(22, reading, binaryGroups);
      const size = parseInt(reading.slice(7,22).join(''), 2);
      reading = ensureSize((22 + size), reading, binaryGroups);
      let rest = reading.slice(22, 22+size);
      while(rest.length > 0) {
        rest = parse(rest, [], thisOperation.numberValues);
      }
      return reading.slice(22+size, reading.length);
    } else {
      reading = ensureSize(18, reading, binaryGroups);
      const count = parseInt(reading.slice(7,18).join(''), 2);
      let rest = reading.slice(18,reading.length);
      for(let i = 0; i < count; i++) {
        rest = parse(rest, binaryGroups, thisOperation.numberValues);
      }
      return rest;
    }
  }
}

function operate(operation, values) {
  switch (operation) {
    case '&':
      if(values.length !== 1) throw Error(`${operation} length mismatch ${values.length}/1`);
      return values[0];
    case '+':
      return values.reduce(sum,0);
    case '*':
      return values.reduce(prod,1);
    case 'min':
      return values.reduce(min,values[0]);
    case 'max':
      return values.reduce(max,values[0]);
    case '>':
      if(values.length !== 2) throw Error(`${operation} length mismatch ${values.length}/2`);
      return values[0] > values[1] ? 1 : 0;
    case '<':
      if(values.length !== 2) throw Error(`${operation} length mismatch ${values.length}/2`);
      return values[0] < values[1] ? 1 : 0;
    case '=':
      if(values.length !== 2) throw Error(`${operation} length mismatch ${values.length}/2`);
      return values[0] == values[1] ? 1 : 0;
    default:
      throw Error(`${operation} unsupported.`);
  }
}

function traverseTree(tree) {
  return tree.map(i => {
    if(i.operation) {
      return operate(i.operation, traverseTree(i.numberValues));
    }
    return i;
  });
}

console.log(binaryGroups.join(''));

const tree = [];
parse([], binaryGroups, tree);

console.log(versionSum);
console.dir(tree, {depth:null});
console.log(traverseTree(tree));
