const {
  read,
  min,
  max,
  sum,
  prod,
} = require('./common.js');

function magnitude(number) {
  const magnitudes = [0,0];
  if(number[0].length) {
    magnitudes[0] = magnitude(number[0]);
  } else {
    magnitudes[0] = number[0];
  }

  if(number[1].length) {
    magnitudes[1] = magnitude(number[1]);
  } else {
    magnitudes[1] = number[1];
  }

  return 3*magnitudes[0] + 2*magnitudes[1];
}

// console.log("magnitude");
// console.log(magnitude([[1,2],[[3,4],5]]),143)
// console.log(magnitude([[[[0,7],4],[[7,8],[6,0]]],[8,1]]),1384)
// console.log(magnitude([[[[1,1],[2,2]],[3,3]],[4,4]]),445)
// console.log(magnitude([[[[3,0],[5,3]],[4,4]],[5,5]]),791)
// console.log(magnitude([[[[5,0],[7,4]],[5,5]],[6,6]]),1137)
// console.log(magnitude([[[[8,7],[7,7]],[[8,6],[7,7]]],[[[0,7],[6,6]],[8,7]]]),3488)

function explode(number) {
  const input = JSON.stringify(number).split('');
  const stack = [];
  const pending = [];
  let open = 0;
  let token = input.shift();
  while(token) {
    if(token === '[') open += 1;
    if(open > 4 && token === ']') {
      open -= 1;
      const second = stack.pop();
      stack.pop(); //comma
      const first = stack.pop();
      stack.pop(); //]
      for(let i = stack.length - 1; i >= 0; i--) {
        if(typeof stack[i] === 'number') {
          stack[i] += first;
          break;
        }
      }
      stack.push(0);
      pending.push(second);
    } else {
      if(token !== '[' && token !== ']' && token !== ',') {
        const lookahead = input.shift();
        if(lookahead !== '[' && lookahead !== ']' && lookahead !== ',') {
          token = parseInt(token + lookahead);
        } else {
          token = parseInt(token);
          input.unshift(lookahead);
        }
        token += pending.length ? pending.shift() : 0;
      } else if(token === ']') {
        open -= 1;
      }
      stack.push(token);
    }
    token = input.shift();
  }
  return JSON.parse(stack.join(''));
}

// console.log("explode")
// console.log(JSON.stringify(explode([[[[[9,8],1],2],3],4])));
// console.log(JSON.stringify(explode([7,[6,[5,[4,[3,2]]]]])));
// console.log(JSON.stringify(explode([[6,[5,[4,[3,2]]]],1])));
// console.log(JSON.stringify(explode([[3,[2,[1,[7,3]]]],[6,[5,[4,[3,2]]]]])));
// console.log(JSON.stringify(explode([[3,[2,[8,0]]],[9,[5,[4,[3,2]]]]])));

function split(number) {
  const input = JSON.stringify(number).split('');
  const stack = [];
  const pending = [];
  let open = 0;
  let token = input.shift();
  let splitted = false;
  while(token) {
    if(token !== '[' && token !== ']' && token !== ',') {
      const lookahead = input.shift();
      if(lookahead !== '[' && lookahead !== ']' && lookahead !== ',') {
        token = parseInt(token + lookahead);
      } else {
        token = parseInt(token);
        input.unshift(lookahead);
      }
      if(token < 10 || splitted) {
        stack.push(token);
      } else {
        splitted = true;
        stack.push('[')
        stack.push(Math.floor(token/2));
        stack.push(',')
        stack.push(Math.ceil(token/2));
        stack.push(']')
      }
    } else {
      stack.push(token);
    }
    token = input.shift();
  }
  return JSON.parse(stack.join(''));
}


// console.log("split")
// console.log(JSON.stringify(split([[[[0,7],4],[15,[0,13]]],[1,1]])));
// console.log(JSON.stringify(split([[[[0,7],4],[[7,8],[0,13]]],[1,1]])));

let raw;
// raw = `[1,1]
// [2,2]
// [3,3]
// [4,4]
// [5,5]
// [6,6]`

// raw = `
// [[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
// [7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
// [[2,[[0,8],[3,4]]],[[[6,7],1],[7,[1,6]]]]
// [[[[2,4],7],[6,[0,5]]],[[[6,8],[2,8]],[[2,1],[4,5]]]]
// [7,[5,[[3,8],[1,4]]]]
// [[2,[2,2]],[8,[8,1]]]
// [2,9]
// [1,[[[9,3],9],[[9,0],[0,7]]]]
// [[[5,[7,4]],7],1]
// [[[[4,2],2],6],[8,7]]
// `

raw = `
[[[0,[4,5]],[0,0]],[[[4,5],[2,6]],[9,5]]]
[7,[[[3,7],[4,3]],[[6,3],[8,8]]]]
`
// raw = `
// [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
// [[[5,[2,8]],4],[5,[[9,9],0]]]
// [6,[[[6,2],[5,6]],[[7,6],[4,7]]]]
// [[[6,[0,7]],[0,9]],[4,[9,[9,0]]]]
// [[[7,[6,4]],[3,[1,3]]],[[[5,5],1],9]]
// [[6,[[7,3],[3,2]]],[[[3,8],[5,7]],4]]
// [[[[5,4],[7,7]],8],[[8,3],8]]
// [[9,3],[[9,9],[6,[4,9]]]]
// [[2,[[7,7],7]],[[5,8],[[9,3],[0,2]]]]
// [[[[5,2],5],[8,[3,7]]],[[5,[7,5]],[4,4]]]
// `


// raw = `
// [[[[4,3],4],4],[7,[[8,4],9]]]
// [1,1]`

raw = read("./day18.input");


const list = raw.split('\n').filter(i => i.length).map(i => JSON.parse(i));

function changed(added, checksum) {
  return JSON.stringify(added) !== checksum;
}

const result = list
.reduce((sum, current) => {
  if(typeof sum === 'undefined') return current;
  let added = [sum, current];
  let checksum;
  const operations = []
  do {
    checksum = JSON.stringify(added);
    added = explode(added);
    added = split(added);
  } while(JSON.stringify(added) !== checksum);
  return added;
})

console.log("result");

console.log(JSON.stringify(result));
console.log(magnitude(result));
