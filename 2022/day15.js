const testInput = `Sensor at x=2, y=18: closest beacon is at x=-2, y=15
Sensor at x=9, y=16: closest beacon is at x=10, y=16
Sensor at x=13, y=2: closest beacon is at x=15, y=3
Sensor at x=12, y=14: closest beacon is at x=10, y=16
Sensor at x=10, y=20: closest beacon is at x=10, y=16
Sensor at x=14, y=17: closest beacon is at x=10, y=16
Sensor at x=8, y=7: closest beacon is at x=2, y=10
Sensor at x=2, y=0: closest beacon is at x=2, y=10
Sensor at x=0, y=11: closest beacon is at x=2, y=10
Sensor at x=20, y=14: closest beacon is at x=25, y=17
Sensor at x=17, y=20: closest beacon is at x=21, y=22
Sensor at x=16, y=7: closest beacon is at x=15, y=3
Sensor at x=14, y=3: closest beacon is at x=15, y=3
Sensor at x=20, y=1: closest beacon is at x=15, y=3
`

const { read, min, clone, max } = require('../common.js');

const input = read('./day15.input', testInput)
    .split('\n')
    .filter(i => i)
    .flatMap(i => {
      const strings = i.split(' ');
      const sensor = {
        type: 'sensor',
        x: parseInt(strings[2].split('=')[1]),
        y: parseInt(strings[3].split('=')[1]),
      };
      const beacon = {
        type: 'beacon',
        x: parseInt(strings[8].split('=')[1]),
        y: parseInt(strings[9].split('=')[1]),
      };

      sensor.radius = Math.abs(sensor.x - beacon.x) + Math.abs(sensor.y - beacon.y);

      return [sensor, beacon];
    });

const minX = input.map(i => i.x - (i.radius || 0)).reduce(min,1000000);
const maxX = input.map(i => i.x + (i.radius || 0)).reduce(max,-1000000);
const minY = input.map(i => i.y - (i.radius || 0)).reduce(min,1000000);
const maxY = input.map(i => i.y + (i.radius || 0)).reduce(max,-1000000);

// First part: 

const space = {};

function getSpace(position) {
  if(!space[position.y - minY]) return '.';
  if(!space[position.y - minY][position.x - minX]) return '.';
  return space[position.y - minY][position.x - minX];
}

function putSpace(position, what = '.') {
  if(what === '#' && position.y !== 10 && position.y !== 2000000) return;
  if(!space[position.y - minY]) space[position.y - minY] = {};
  if(position.x < minX || position.x > maxX) return;
  if(position.y < minY || position.y > maxY) return;
  if(getSpace(position) !== '.') return;
  space[position.y - minY][position.x - minX] = what;
}

function printSpace() {
  if(maxX > 1000) return;
  for(let i = minX; i <= maxX; i++) {
    const string = []
    for(let j = minY; j <= maxY; j++) {
      string.push(getSpace({x:j, y:i}));
    }
    console.log(string.join(''))
  }
}

function putItem(item) {
  const marker = item.type === 'sensor' ? 'S' : 'B'
  putSpace(item, marker);
}

function fillSensor(item) {
  if(item.type === 'sensor') {
    for(let j = 0; j <= item.radius; j++){
      for(let i = -item.radius + j; i <= (item.radius - j); i++) {
        putSpace({x: item.x+i, y: item.y+j},'#')
      }
    }
    for(let j = 0; j <= item.radius; j++){
      for(let i = -item.radius + j; i <= (item.radius - j); i++) {
        putSpace({x: item.x+i, y: item.y-j},'#')
      }
    }
  }
}

function getRow(y) {
  if(!space[y - minY]) return;
  return Object.keys(space[y - minY]).map(key => space[y - minY][key]);
}

console.log(minX, maxX, minY, maxY);

function inRange(position, item) {
  if(item.radius) {
    const distanceX = Math.abs(position.x - item.x);
    const distanceY = Math.abs(position.y - item.y);
    // console.log(position, (distanceX + distanceY),item.radius)
    return (distanceX + distanceY) <= item.radius;
  }
  return false;
}

function scanRow(y) {
  for(let x = minX; x <= maxX; x++) {
    input
      .filter(i => i.type !== 'beacon')
      .forEach(i => {
        if(inRange({x, y}, i)) putSpace({x, y}, '#');
      });
  }
}

console.log('Start')
input.forEach(putItem)
printSpace();
console.log("\nAnswers:")
scanRow(10);
if(getRow(10)) console.log('Positions without a beacon at 10:',getRow(10).filter(i => i === '#').length);
scanRow(2000000);
if(getRow(2000000)) console.log('Positions without a beacon at 2000000:',getRow(2000000).filter(i => i === '#').length);
printSpace();



function searchArea(from,to) {
  const tenPercent = Math.floor((to.x - from.x)/20);
  const nonBeacon = input.filter(i => i.type !== 'beacon');
  for(let x = from.x; x <= to.x; x++) {
    if(!(x % tenPercent)) console.log("Completion", (100*x/(to.x - from.x)).toFixed(2), '%');
    for(let y = from.y; y <= to.y; y++) {
      let found = false;
      for(i in nonBeacon) {
        if(inRange({x, y}, nonBeacon[i])) {
          y += nonBeacon[i].radius - Math.abs(x - nonBeacon[i].x) - Math.abs(y - nonBeacon[i].y)
          found = true;
          break;
        }
      }
      if(!found) return {x,y}
    }
  }
}

const small = searchArea({x:0, y:0},{x:20,y:20}) || {};
console.log(small, small.x*4000000 + small.y);
const found = searchArea({x:0, y:0},{x:4000000,y:4000000}) || {};
console.log(found, found.x*4000000 + found.y);
