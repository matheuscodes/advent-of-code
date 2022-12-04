const {
  read,
  min,
  max,
  sum,
  prod,
} = require('./common.js');

//target area: x=20..30, y=-10..-5
// const targetArea = {
//   maxX: 30,
//   minX: 20,
//   maxY: -5,
//   minY: -10,
// }

//target area: x=32..65, y=-225..-177
const targetArea = {
  maxX: 65,
  minX: 32,
  maxY: -177,
  minY: -225,
}


class Probe {
  xInitial = 0;
  yInitial = 0;

  constructor(x, y) {
    this.xInitial = x;
    this.yInitial = y;
  }

  getSpeedAt(step) {
    let currentX = this.xInitial;
    if(this.xInitial >= 0) {
      currentX -= step - 1;
      return {
        y: this.yInitial - step + 1,
        x: currentX < 0 ? 0 : currentX,
      }
    } else if(this.xInitial < 0){
      currentX += step - 1;
      return {
        y: this.yInitial - step + 1,
        x: currentX > 0 ? 0 : currentX,
      }
    }
  }

  getPositionAt(step) {
    if(step <= 0) return {
      x: 0,
      y: 0,
    }

    const currentSpeed = this.getSpeedAt(step);
    const lastPosition = this.getPositionAt(step -1);
    return {
      x: lastPosition.x + currentSpeed.x,
      y: lastPosition.y + currentSpeed.y,
    }
  }
}

const board = new Array(Math.abs(targetArea.minY)*2+1);
for(let i = 0; i < board.length; i++) {
  board[i] = new Array(targetArea.maxX+1).fill('.');
}

const mid = Math.floor(board.length / 2);

for(let i = 0; i < board.length; i++) {
  for(let j = 0; j < board[i].length; j++) {
    if(j <= targetArea.maxX && j >= targetArea.minX
      && (mid - i) <= targetArea.maxY && (mid - i) >= targetArea.minY ){
      board[i][j] = 'T'
    }
  }
}

const maxSpeed = 400;
let highestY = 0;
let highestPair;
for(let i = 1; i < maxSpeed; i++) {
  for(let j = 1; j < maxSpeed; j++) {
    const probe = new Probe(i,j);
    let foundStep;
    let localHighestY = 0;
    let step = 0;
    let position = probe.getPositionAt(step);
    while(!(position.x > targetArea.maxX) && position.y >= targetArea.minY) {
      if(position.y > localHighestY) {
        localHighestY = position.y;
      }
      if(position.x >= targetArea.minX && position.x <= targetArea.maxX
        && position.y >= targetArea.minY && position.y <= targetArea.maxY) {
        foundStep = step;
      }
      step += 1;
      position = probe.getPositionAt(step);
    }
    if(foundStep) {
      if(localHighestY > highestY) {
        highestY = localHighestY;
        highestPair = {pair:[i,j],foundStep};
      }
    }
  }
}

console.log(highestY, highestPair);
