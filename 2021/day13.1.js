const fs = require('fs');

raw = `
6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5
`

raw = fs
  .readFileSync("./day13.input")
  .toString('utf8');

const foldings = [raw.split('\n')
                    .filter(i => i.includes('fold along'))
                    .map(i => i.replace('fold along ',''))
                    .map(i => i.split('='))[0]];
const dots = raw.split('\n')
                 .filter(i => !i.includes('fold along'))
                 .filter(i => i.length)
                 .map(i => i.split(',').map(i => parseInt(i)));

const max = (max, current) => {
  if(max < current) return current;
  return max;
}

const sum = (sum, current) => {
  return sum + current;
}


function prettyPrint(dots) {
  const maxX = dots.map(i => i[0]).reduce(max, 0);
  const maxY = dots.map(i => i[1]).reduce(max, 0);
  const paper = [];
  for(let i = 0; i <= maxY; i++) {
    paper.push(new Array(maxX + 1).fill(false));
  }

  dots.forEach(dot => {
    paper[dot[1]][dot[0]] = true;
  });

  console.log(paper.map(i => i.map(i => i ? '#' : '.').join('')).join('\n'));

  return paper;
}

const fold = {
  'y': (point, y) => {
    if(point[1] > y) {
      return [point[0], 2*y - point[1]];
    } else if(point[1] < y) {
      return point;
    } else {
      console.log("point on the fold", point, y)
      throw Error('Oh no!')
    }
  },
  'x': (point, x) => {
    console.log(point,x)
    if(point[0] > x) {
      return [2*x - point[0], point[1]];
    } else if(point[0] < x) {
      return point;
    } else {
      console.log("point on the fold", point, y)
      throw Error('Oh no!')
    }
  },
}

function doFoldings(dots, foldings) {
  if(foldings.length > 0) {
    const instruction = foldings.shift();
    return doFoldings(dots.map(i => fold[instruction[0]](i,instruction[1])), foldings);
  } else {
    return dots;
  }
}



// console.log(foldings);
// console.log(dots.map(i => fold['y'](i,7)).map(i => fold['x'](i,5)));
// prettyPrint(dots.map(i => fold['y'](i,7)).map(i => fold['x'](i,5)));
const folded = doFoldings(dots, foldings);
const seeThrough = prettyPrint(folded);
console.log(seeThrough.map(i => i.map(i => i ? 1 : 0).reduce(sum, 0)).reduce(sum, 0));
