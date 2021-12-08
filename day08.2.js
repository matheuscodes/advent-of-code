const fs = require('fs');

// let raw = `
// be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
// edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
// fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
// fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
// aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
// fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
// dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
// bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
// egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
// gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce
// `

raw = fs
  .readFileSync("./day08.input")
  .toString('utf8');

const signals = raw.split('\n').filter(i => i.length).map(i => i.split(' | ')[0]).map(i => i.split(' '));
const outputs = raw.split('\n').filter(i => i.length).map(i => i.split(' | ')[1]).map(i => i.split(' '));

let containsAll = (arr, target) => target.every(v => arr.includes(v));

function differentiate069(item, four, one) {
  const itemArray = item.split('');
  if(containsAll(itemArray, one.split(''))) {
    if(containsAll(itemArray, four.split(''))) {
      return 9;
    } else {
      return 0;
    }
  } else {
    return 6;
  }
}

function differentiate235(item, nine, one) {
  const itemArray = item.split('');
  if(containsAll(itemArray, one.split(''))) {
    return 3;
  } else {
    if(containsAll(nine.split(''), itemArray)) {
      return 5;
    } else {
      return 2;
    }
  }
}

function matches(coded, reference) {
  if(coded.length === reference.length) {
    if(containsAll(coded.split(''),reference.split(''))) {
      return true;
    }
  }
  return false;
}

const converted = signals.map((signal, index) => {
  const sortedSignal = signal.sort((a,b) => a.length - b.length);
  const map = Array(10);
  map[1] = sortedSignal[0];
  map[7] = sortedSignal[1];
  map[4] = sortedSignal[2];
  map[8] = sortedSignal[9];

  map[differentiate069(sortedSignal[6],map[4],map[1])] = sortedSignal[6];
  map[differentiate069(sortedSignal[7],map[4],map[1])] = sortedSignal[7];
  map[differentiate069(sortedSignal[8],map[4],map[1])] = sortedSignal[8];

  map[differentiate235(sortedSignal[3],map[9],map[1])] = sortedSignal[3];
  map[differentiate235(sortedSignal[4],map[9],map[1])] = sortedSignal[4];
  map[differentiate235(sortedSignal[5],map[9],map[1])] = sortedSignal[5];


  decoded = parseInt(outputs[index].map(coded => {
    return map.indexOf(map.find(i => matches(coded,i)));
  }).join(''));

  return {map, decoded}
});





console.dir(converted,{depth:null});
console.log(converted.reduce((sum, i) => sum + i.decoded, 0));
