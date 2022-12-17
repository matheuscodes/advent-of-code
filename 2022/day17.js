const testInput = `>>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>
`

const { read, max, clone } = require('../common.js');

const coreGraph = {};

const input = read('./day17.input', testInput)
                .split('\n')
                .filter(i => i)
                .map(i => i.split(''))[0];


let cave = [['-','-','-','-','-','-','-']];

class Rock {
  coordinates;

  setCoordinates(coordinates) {
    // console.log('The first rock begins falling:');
    this.coordinates = coordinates;
  }

  fall() {
    if(this.canFall()) {
      // console.log('Rock falls 1 unit:');
      this.coordinates = this.coordinates.map(i => [i[0]-1,i[1]]);
      return true;
    }
    // console.log("Rock falls 1 unit, causing it to come to rest:")
    this.write();
    return false;
  }

  canFall() {
    return this.coordinates.map(i => [i[0]-1,i[1]]).map(position => {
      if(!cave[position[0]]) cave[position[0]] = Array.apply('.', {length: 7});
      return cave[position[0]][position[1]];
    })
    .filter(i => (i && ((i === '#') || (i === '-'))))
    .length <= 0
  }

  move(direction) {
    if(!direction) return;
    if(this.canMove(direction)) {
      this.coordinates.forEach(i => i[1] += (direction ==='>' ? 1 : -1));
      // console.log(`Jet of gas pushes rock ${direction}:`)
      return true;
    }
    // console.log(`Jet of gas pushes rock ${direction}, but nothing happens:`)
    return false;
  }

  canMove(direction) {
    const nextCoordinates = this.coordinates.map(i => [i[0],i[1]+ (direction ==='>' ? 1 : -1)]);

    return !nextCoordinates.some(i => (i[1] < 0 || i[1] > 6)) &&
      (nextCoordinates.map(position => {
        if(!cave[position[0]]) cave[position[0]] = Array.apply('.', {length: 7});
        return cave[position[0]][position[1]];
      })
      .filter(i => (i && ((i === '#') || (i === '-'))))
      .length <= 0);
  }

  write() {
    this.coordinates.forEach(i => setCave([i[0], i[1]]));
  }
}

class HorizontalLine extends Rock{
  constructor(height) {
    super();
    this.setCoordinates([
      [height + 4,2],
      [height + 4,3],
      [height + 4,4],
      [height + 4,5],
    ]);
  }
}

class VerticalLine extends Rock{
  constructor(height) {
    super();
    this.setCoordinates([
      [height + 4,2],
      [height + 5,2],
      [height + 6,2],
      [height + 7,2],
    ]);
  }
}

class Block extends Rock{
  constructor(height) {
    super();
    this.setCoordinates([
      [height + 4,2],
      [height + 5,2],
      [height + 4,3],
      [height + 5,3],
    ]);
  }
}


class Cross extends Rock{
  constructor(height) {
    super();
    this.setCoordinates([
      [height + 5,2],
      [height + 5,3],
      [height + 5,4],
      [height + 4,3],
      [height + 6,3],
    ]);
  }
}

class Corner extends Rock {
  constructor(height) {
    super();
    this.setCoordinates([
      [height + 4,2],
      [height + 4,3],
      [height + 4,4],
      [height + 5,4],
      [height + 6,4],
    ]);
  }
}

let clock = 0;
function nextRock(height) {
  switch (clock % 5) {
    case 4: // #
      return new Block(height);
    case 3: // |
      return new VerticalLine(height);
    case 2: // L
      return new Corner(height);
    case 1: // +
      return new Cross(height);
    case 0: // -
      return new HorizontalLine(height);

  }
}

function setCave(position, marker) {
  if(!cave[position[0]]) cave[position[0]] = Array.apply('.', {length: 7});
  cave[position[0]][position[1]] = (marker || '#')
}

function highest() {
  return cave.map((i, index) => i.some(i => ((i === '#') || (i === '-'))) ? index : undefined).filter(i => (typeof i !== 'undefined')).reduce(max,0);
}

let jets = clone(input);

for(let i = 0; i < 2022; i++) {
  const rock = nextRock(highest());
  do {
    if(jets.length <= 0) jets = clone(input);
    rock.move(jets.shift());
  } while(rock.fall());
  clock += 1;
  // if(i % 100 === 0) printCave();
}

console.log(cave.map(i => i.join('')).filter(i => {
  return (i.length > 0 && i !== '-------')
}).length)

//3049 too high
let pruned = 0;

cave = [['-','-','-','-','-','-','-']];
jets = clone(input);
clock = 0;
for(let i = 0; i < 1000000000000; i++) {
  const rock = nextRock(highest());
  do {
    if(jets.length <= 0) jets = clone(input);
    rock.move(jets.shift());
  } while(rock.fall());
  if(jets.length <= 0) {
    rock.write();
    console.log('t',i);
    break;
  }
  clock += 1;
  if(i % 10000 === 0) console.log('processing', i)
}

printCave();

console.log(pruned, cave.map(i => i.join('')).filter(i => {
  return (i.length > 0 && i !== '-------')
}).length)

function printCave() {
  console.log("Cave", clock);
  console.log(cave.map(i => i.map(i => (i ? i : '.')).join('')).join('\n'));
}
