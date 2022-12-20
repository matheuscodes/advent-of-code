let testInput = `2,2,2
1,2,2
3,2,2
2,1,2
2,3,2
2,2,1
2,2,3
2,2,4
2,2,6
1,2,5
3,2,5
2,1,5
2,3,5
`;

const { read, clone, sum, max } = require('../common.js');

const coreGraph = {};

const input = read('./day18.repolho2', testInput)
                .split('\n')
                .filter(i => i)
                .map(i => i.split(','))
                .map(i => ({
                  x: parseInt(i[0]),
                  y: parseInt(i[1]),
                  z: parseInt(i[2]),
                }));

// First part: counting neighbours

function findCubes(list,x,y,z) {
  return list.filter(i => {
    return (i.x === x && i.y === y && i.z === z)
  });
}

function findNeighbours(list, { x,y,z }) {
  return findCubes(list,x,y,z+1)
    .concat(findCubes(list,x,y,z-1))
    .concat(findCubes(list,x,y+1,z))
    .concat(findCubes(list,x,y-1,z))
    .concat(findCubes(list,x+1,y,z))
    .concat(findCubes(list,x-1,y,z))
}

let surfaceArea = 0;
input.forEach(cube => {
  surfaceArea += (6 - findNeighbours(input, cube).length);
})

console.log("Surface Area:", surfaceArea)


// Second part: finding pockets of air

function findAir(list, { x,y,z }) {
  const neighbours = findNeighbours(list, {x,y,z})
    .map(({x,y,z}) => `${x},${y},${z}`);
  return [
    {x:x,y:y,z:z+1},
    // {x:x+1,y:y,z:z+1},
    // {x:x-1,y:y,z:z+1},
    // {x:x,y:y+1,z:z+1},
    // {x:x+1,y:y+1,z:z+1},
    // {x:x-1,y:y+1,z:z+1},
    // {x:x,y:y-1,z:z+1},
    // {x:x+1,y:y-1,z:z+1},
    // {x:x-1,y:y-1,z:z+1},
    {x:x,y:y,z:z-1},
    // {x:x+1,y:y,z:z-1},
    // {x:x-1,y:y,z:z-1},
    // {x:x,y:y+1,z:z-1},
    // {x:x+1,y:y+1,z:z-1},
    // {x:x-1,y:y+1,z:z-1},
    // {x:x,y:y-1,z:z-1},
    // {x:x+1,y:y-1,z:z-1},
    // {x:x-1,y:y-1,z:z-1},
    // {x:x,y:y,z:z},
    {x:x+1,y:y,z:z},
    {x:x-1,y:y,z:z},
    {x:x,y:y+1,z:z},
    // {x:x+1,y:y+1,z:z},
    // {x:x-1,y:y+1,z:z},
    {x:x,y:y-1,z:z},
    // {x:x+1,y:y-1,z:z},
    // {x:x-1,y:y-1,z:z},
  ].filter(({x,y,z}) => {
    return !neighbours.includes(`${x},${y},${z}`)
  });
}


surfaceArea = 0;
const neighbours = {}

function neighboursAsInput() {
  return Object.keys(neighbours)
    .map(i => i.split(','))
    .map(i => ({
      x: parseInt(i[0]),
      y: parseInt(i[1]),
      z: parseInt(i[2]),
    }));
}

function addNeighbour({x,y,z}, value = 1) {
  const key = `${x},${y},${z}`;
  if(!neighbours[key]) neighbours[key] = 0;
  neighbours[key] += value;
}

input.forEach(cube => {
  findAir(input,cube).forEach(i => addNeighbour(i));
  surfaceArea += (6 - findNeighbours(input, cube).length);
})

const clonedNeighbours = clone(neighbours);

// neighboursAsInput().forEach(air => {
//   const droplets = input.map(({x,y,z}) => `${x},${y},${z}`)
//   findAir(neighboursAsInput(), air)
//     .filter(({x,y,z}) => !droplets.includes(`${x},${y},${z}`))
//     .forEach(i => addNeighbour(i, 0))
// });

  neighboursAsInput().forEach(air => {
    findNeighbours(neighboursAsInput(), air)
      .forEach(i => addNeighbour(i))
  })

const maxCoordinates = {x:0, y:0, z:0}
input.forEach(({x,y,z}) => {
  if(x > maxCoordinates.x) maxCoordinates.x = x;
  if(y > maxCoordinates.y) maxCoordinates.y = y;
  if(z > maxCoordinates.z) maxCoordinates.z = z;
});
console.log(maxCoordinates);

const maxOfMax = [maxCoordinates.x, maxCoordinates.y, maxCoordinates.z].reduce(max,-10000);

console.log("Max of max:", maxOfMax, (maxOfMax + 20)*(maxOfMax + 20)*6);

const limits = {xy:{}, xz:{}, yz:{}}
for(let i = -10; i < maxOfMax + 10; i++) {
  for(let j = -10; j < maxOfMax + 10; j++) {
    if(!limits.xy[`${i},${j}`]) limits.xy[`${i},${j}`] = {max: 0, min: maxCoordinates.z}
    input.filter(({x,y,z}) => (x === i && y === j))
      .forEach(({x,y,z}) => {
        if(limits.xy[`${i},${j}`].max < z) limits.xy[`${i},${j}`].max = z;
        if(limits.xy[`${i},${j}`].min > z) limits.xy[`${i},${j}`].min = z;
      });
  }
}

for(let i = -10; i < maxOfMax + 10; i++) {
  for(let j = -10; j < maxOfMax + 10; j++) {
    if(!limits.xz[`${i},${j}`]) limits.xz[`${i},${j}`] = {max: 0, min: maxCoordinates.y}
    input.filter(({x,y,z}) => (x === i && z === j))
      .forEach(({x,y,z}) => {
        if(limits.xz[`${i},${j}`].max < y) limits.xz[`${i},${j}`].max = y;
        if(limits.xz[`${i},${j}`].min > y) limits.xz[`${i},${j}`].min = y;
      });
  }
}

for(let i = -10; i < maxOfMax + 10; i++) {
  for(let j = -10; j < maxOfMax + 10; j++) {
    if(!limits.yz[`${i},${j}`]) limits.yz[`${i},${j}`] = {max: 0, min: maxCoordinates.x}
    input
      .filter(({x,y,z}) => (y === i && z === j))
      .forEach(({x,y,z}) => {
        if(limits.yz[`${i},${j}`].max < x) limits.yz[`${i},${j}`].max = x;
        if(limits.yz[`${i},${j}`].min > x) limits.yz[`${i},${j}`].min = x;
      });
  }
}

const newInput = [];

for(let i = -10; i < maxOfMax + 10; i++) {
  for(let j = -10; j < maxOfMax + 10; j++) {
    for(let k = -10; k < maxOfMax + 10; k++) {
      // console.log(i,j,k, limits.yz[`${j},${k}`], limits.xz[`${i},${k}`],limits.xy[`${i},${j}`])
      if(!(
        (i >= limits.yz[`${j},${k}`].min && i <= limits.yz[`${j},${k}`].max) &&
        (j >= limits.xz[`${i},${k}`].min && j <= limits.xz[`${i},${k}`].max) &&
        (k >= limits.xy[`${i},${j}`].min && k <= limits.xy[`${i},${j}`].max)
      ))
      newInput.push({x:i,y:j,z:k});
    }
  }
}

// console.lo g(newInput, limits);

let newSurfaceArea = 0;
newInput.forEach(cube => {
  newSurfaceArea += (6 - findNeighbours(newInput, cube).length);
})

console.log('New surface area', newSurfaceArea, newSurfaceArea - ((maxOfMax + 20)*(maxOfMax + 20)*6))

//
// console.log(limits)
// const oldSA = surfaceArea
// surfaceArea -= Object.keys(neighbours)
//   // .filter(key => !key.includes('-')) // removes any negatives
//   // .filter(key => neighbours[key] >= 6)
//   .filter(key => {
//     const i = key.split(',');
//     const x = parseInt(i[0]);
//     const y = parseInt(i[1]);
//     const z = parseInt(i[2]);
//
//     return (
//       // (limits.yz[`${y},${z}`] && limits.xz[`${x},${z}`] && limits.xy[`${x},${y}`]) &&
//       (x > limits.yz[`${y},${z}`].min && x < limits.yz[`${y},${z}`].max) &&
//       (y > limits.xz[`${x},${z}`].min && y < limits.xz[`${x},${z}`].max) &&
//       (z > limits.xy[`${x},${y}`].min && z < limits.xy[`${x},${y}`].max)
//     )
//   })
//   .map(key => (clonedNeighbours[key] || 0))
//   .reduce(sum,0);
//
// console.log("Surface Area 2:", surfaceArea, oldSA, oldSA - surfaceArea);
// 4184 too high
// 3048 too high
// 2970 not right
// 2969 not right
// 2968 not right
// 2500 not right
// 2494 not right
// 2491 not right
// 2372 too low
