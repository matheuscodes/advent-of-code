const fs = require('fs');

const raw = fs
  .readFileSync("./day06.input")
  .toString('utf8')
  .split('\n')
  .filter(i => i.length > 0);

// raw[0] = "3,4,3,1,2";
let fishes = raw[0].split(',').map(i => parseInt(i));

const MAX = 256;

let fishesBornAt = new Array(MAX + 1).fill(0);

fishes.forEach(fish => {
  for(let i = fish + 1; i <= MAX; i += 7) {
    fishesBornAt[i] += 1;
  }
});

fishesBornAt.forEach((fishCount, day) => {
  for(let i = day + 9; i <= MAX; i += 7) {
    fishesBornAt[i] += fishCount;
  }
});

fishesBornAt[0] = fishes.length;



console.log(fishes.join(','));
console.log(fishesBornAt);


console.log(fishesBornAt.reduce((a,b) => a + b, 0));
