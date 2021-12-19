const {
  read
} = require('./common.js');


let
// raw = `EE00D40C823060`
// raw = `38006F45291200`
// raw = `D2FE28`
// raw = `8A004A801A8002F478`
// raw = `620080001611562C8802118E34`
// raw = `C0015000016115A2E0802F182340`
// raw = `A0016C880162017C3686B18A3D4780`

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

function parse(reading = [], binaryGroups) {
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
    return reading.slice(pointer+5, reading.length);
  } else {
    reading = ensureSize(7, reading, binaryGroups);
    const id = reading[6];
    if(id === '0') {
      reading = ensureSize(22, reading, binaryGroups);
      const size = parseInt(reading.slice(7,22).join(''), 2);
      reading = ensureSize((22 + size), reading, binaryGroups);
      let rest = reading.slice(22, 22+size);
      while(rest.length > 0) {
        rest = parse(rest, []);
      }
      return reading.slice(22+size, reading.length);
    } else {
      reading = ensureSize(18, reading, binaryGroups);
      const count = parseInt(reading.slice(7,18).join(''), 2);
      let rest = reading.slice(18,reading.length);
      for(let i = 0; i < count; i++) {
        rest = parse(rest, binaryGroups);
      }
      return rest;
    }
  }
}

console.log(binaryGroups.join(''));
parse([], binaryGroups);

console.log(versionSum);
