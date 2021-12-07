const fs = require('fs');

const raw = fs
  .readFileSync("./day06.input")
  .toString('utf8')
  .split('\n')
  .filter(i => i.length > 0);

raw[0] = "3,4,3,1,2";
let fishes = Int8Array.from(raw[0].split(',').map(i => parseInt(i)));

function newFishCount(fishes) {
  return fishes.reduce((sum, fish) => {
    if(fish === 0) {
      return sum + 1;
    }
    return sum;
  }, 0);
}

function killFish(fishes) {
  for(let i = 0; i < fishes.length; i++) {
    if(fishes[i] > 0) {
      fishes[i] = fishes[i] - 1;
    } else {
      fishes[i] = 6;
    }
  }
}

const bornAt = [fishes.length]
console.log(fishes.join(','));
for (let i = 0; i < 80; i++) {
  const current = fishes.length;
  const oldFishes = fishes;
  const newFishes = new Int8Array(newFishCount(fishes) + oldFishes.length).fill(8);
  killFish(oldFishes);
  newFishes.set(oldFishes, 0);
  fishes = newFishes;
  bornAt[i+1] = fishes.length - current;
  // console.log('Day ', i + 1,fishes.join(','))
}

console.log(bornAt);
console.log(fishes.length);
