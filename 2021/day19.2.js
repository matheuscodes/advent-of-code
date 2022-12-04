const {
  read,
  sum,
  clone,
  concat,
  max,
} = require('./common.js');

let raw;

function translateTo(from, to) {
  return [from[0]-to[0],from[1]-to[1],from[2]-to[2]];
}

function rotationMatrixX(multiple) {
  const angle = multiple * Math.PI / 2;
  return [
    [1, 0, 0],
    [0, Math.cos(angle), -Math.sin(angle)],
    [0, Math.sin(angle), Math.cos(angle)],
  ]
}

function rotationMatrixY(multiple) {
  const angle = multiple * Math.PI / 2;
  return [
    [Math.cos(angle), 0, Math.sin(angle)],
    [0, 1, 0],
    [-Math.sin(angle), 0, Math.cos(angle)],
  ]
}

function rotationMatrixZ(multiple) {
  const angle = multiple * Math.PI / 2;
  return [
    [Math.cos(angle), -Math.sin(angle), 0],
    [Math.sin(angle), Math.cos(angle), 0],
    [0, 0, 1],
  ]
}

function multiplyMatrix(matrix, point) {
  return [
    Math.round(matrix[0][0]*point[0] + matrix[0][1]*point[1] + matrix[0][2]*point[2]),
    Math.round(matrix[1][0]*point[0] + matrix[1][1]*point[1] + matrix[1][2]*point[2]),
    Math.round(matrix[2][0]*point[0] + matrix[2][1]*point[1] + matrix[2][2]*point[2]),
  ]
}

function rotation(x,y,z,point) {
  const rotatedX = multiplyMatrix(rotationMatrixX(x),point);
  const rotatedY = multiplyMatrix(rotationMatrixY(y),rotatedX);
  const rotatedZ = multiplyMatrix(rotationMatrixZ(z),rotatedY);
  return rotatedZ;
}

function module(from, to) {
  return [0,1,2].map(i => (from[i]-to[i])*(from[i]-to[i])).reduce(sum,0);
}

const scanners = [];

const COMMONALITY = 12;

class Scanner {
  id;
  position;
  beacons = [];
  rotation;

  constructor(id) {
    this.id = id;
  }

  add(beacon) {
    this.beacons.push(beacon);
  }

  commonality(scanner) {
    return scanner.beacons.map(beacon => {
      return {
        beacon,
        matches: this.beacons.map(j => beacon.compare(j) === COMMONALITY ? j : undefined).filter(i => i),
      }
    })
    .map(i => {
      i.matches.forEach(beacon => beacon.found = true);
      return i;
    })
    .filter(i => i.matches.length > 0);
  }

  rotate(point) {
    if(!this.rotation) {
      return point;
    } else {
      return rotation(this.rotation[0],this.rotation[1],this.rotation[2],point);
    }
  }

  translate(point) {
    if(!this.position) {
      return point
    } else {
      return [
        point[0] + this.position[0],
        point[1] + this.position[1],
        point[2] + this.position[2],
      ]
    }
  }
}

class Beacon {
  id;
  scanner;
  position;
  star = [];
  found = false;

  constructor(id, scanner, position, neighbours) {
      this.id = id;
      this.scanner = scanner;
      this.position = position;
      this.star = neighbours.map(i => module([0,0,0], translateTo(i, position)));
  }

  compare(beacon) {
    const beaconStar = clone(beacon.star);
    const thisStar = clone(this.star);
    thisStar.forEach((i, index) => {
      if(beaconStar.includes(i)) {
        thisStar[index] = -1;
      }
    })
    const result = thisStar.filter(i => i < 0).length;
    return result;
  }

  get absolutePosition() {
    return this.scanner.translate(this.scanner.rotate(this.position));
  }

  get onlyRotatedPosition() {
    return this.scanner.rotate(this.position);
  }
}

function parse(rawData) {
  const list = rawData.split('\n').filter(i => i.length);
  let currentScanner;
  let pending = [];
  list.forEach(line => {
    if(line.includes('scanner')) {
      if(typeof currentScanner !== 'undefined') {
        pending.forEach((beacon, index) => {
          currentScanner.add(new Beacon(index, currentScanner, beacon, pending));
        });
        pending = [];
      }
      const scannerId = parseInt(line.replace('--- scanner ','').replace(' ---',''));
      currentScanner = new Scanner(scannerId);
      scanners.push(currentScanner);
    } else {
      pending.push(line.split(',').map(i => parseInt(i)));
    }
  });
  pending.forEach((beacon,index) => {
    currentScanner.add(new Beacon(index,currentScanner, beacon, pending));
  });
  pending = [];
}


raw = read("./day19.input");
// raw = read("./day19.2.test");
// raw = read("./day19.1.test");
// raw = read("./day19.0.test");

parse(raw);

scanners[0].position = [0,0,0];
scanners[0].rotation = [0,0,0];

function selectUniquePoints(acc,current) {
  if(!acc.some(i => {
    return i[0] === current[0] &&
           i[1] === current[1] &&
           i[2] === current[2]
  })) acc.push(current);
  return acc;
}

function doTheMagic(scanner0, scanner1) {
  if(scanner0.position && !scanner1.position) {
    for(let i = 0; i < 4; i++) {
      for(let j = 0; j < 4; j++) {
        for(let k = 0; k < 4; k++) {
          if(scanner1.position) break;
          scanner1.rotation = [i,j,k];
          const positions = scanner0.commonality(scanner1).map(i => {
            return [i.matches[0].onlyRotatedPosition, i.beacon.onlyRotatedPosition];
          }).map(i => {
            return [i[1][0] - i[0][0], i[1][1] - i[0][1], i[1][2] - i[0][2]];
          }).reduce(selectUniquePoints, [])
          if(positions.length === 1) {
            console.log("found",scanner1.id,"using",scanner0.id);
            scanner1.position = translateTo(scanner0.position, positions[0]);
          } else {
            scanner1.rotation = undefined;
          }
        }
      }
    }
  }
}

for(let i = 0; i < scanners.length; i++) {
  scanners.forEach(scanner0 => {
    scanners.forEach(scanner1 => {
      doTheMagic(scanner0,scanner1);
    });
  });
}

scanners.forEach( i => {
  console.log("Scanner",i.id,"-",i.position,'-',i.rotation);
})

console.log(scanners.map(i => i.beacons).reduce(concat).map(i => i.absolutePosition).reduce(selectUniquePoints,[]).length);

function manhattanDistance(pointA, pointB) {
  return [
    Math.abs(pointB[0] - pointA[0]),
    Math.abs(pointB[1] - pointA[1]),
    Math.abs(pointB[2] - pointA[2]),
  ].reduce(sum);
}

console.log(scanners.map(i => scanners.map(j => [i.position, j.position])).reduce(concat).map(i => manhattanDistance(i[0],i[1])).reduce(max));
