const testInput = `Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
Valve BB has flow rate=13; tunnels lead to valves CC, AA
Valve CC has flow rate=2; tunnels lead to valves DD, BB
Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
Valve EE has flow rate=3; tunnels lead to valves FF, DD
Valve FF has flow rate=0; tunnels lead to valves EE, GG
Valve GG has flow rate=0; tunnels lead to valves FF, HH
Valve HH has flow rate=22; tunnel leads to valve GG
Valve II has flow rate=0; tunnels lead to valves AA, JJ
Valve JJ has flow rate=21; tunnel leads to valve II
`

const { read, sum } = require('../common.js');

const baseGraph = {};

const input = read('./day16.input', testInput)
                .split('\n')
                .filter(i => i)
                .forEach(i => {
                  const valve = i.split(' has flow rate=')[0].replace('Valve ','');
                  const nextValves = i.split('valve')[1].replace('s ', '').split(', ').map(i => i.trim())
                  baseGraph[valve] = {
                    valve,
                    flow: parseInt(i.split(' has flow rate=')[1].split(';')[0]),
                    nextValves,
                    open: false,
                  }
                });

const graphSize = Object.keys(baseGraph).length;

function shortestPathFromTo2(data, destiny) {
  const paths = data;
  while(paths.length > 0) {
    const path = paths.sort((a,b) => a.length - b.length).shift();
    // console.log(path,paths,destiny)
    const current = path[path.length - 1];
    // console.log(data,path,paths,destiny,current)
    if(current.valve === destiny.valve) return path;
    current.nextValves.forEach(i => {
      if(path.length >= 10) return;
      paths.push(path.concat([baseGraph[i]]))
    });
  }
  return;
}

const maxTime = 26;

function calculateContribution(sequence) {
  // console.log(sequence)
  if(!sequence) return 0;
  return sequence.map(i => ((maxTime - (i[1])) * baseGraph[i[0]].flow)).reduce(sum,0)
}

function nextToOpen(current, trial = [], time = 0, contribution = 0) {
  return Object.keys(baseGraph)
    .map(key => baseGraph[key])
    .filter(i => i.open === false)
    .filter(i => i.flow > 0)
    .map(i => shortestPathFromTo2([[current]],i))
    .filter(i => i)
    .map(i => i.map(i => i.valve))
    // .map(i => shortestPathFromTo(current,i))
    .map(path => {
      // console.log(`wtf (${(maxTime - (time + path.length)) * baseGraph[path[path.length - 1]].flow + contribution})`, maxTime, time, path.length, baseGraph[path[path.length - 1]].flow, contribution)
      return {
        // timeToOpen: path.length,
        // timeOpened: time + path.length,
        // releases,
        valve: path[path.length - 1],
        contribution: (maxTime - (time + path.length)) * baseGraph[path[path.length - 1]].flow + contribution,
        path: path.slice(1,path.length),
      }
    })
    // .sort((a,b) => a.path.length - b.path.length)
    // .sort((a,b) => b.contribution - a.contribution);
    .sort((a,b) => b.contribution - a.contribution);
}

const possibilities = [
  {
    timeToOpen: 0,
    timeOpened: 0,
    timeOpenedElefant: 0,
    releases: [['AA',0]],
    elefant: [['AA',0]],
    contribution: 0,
    path: [ 'AA' ]
  }
]

function closeAllExcept(exceptions) {
  Object.keys(baseGraph).forEach(i => baseGraph[i].open = false);
  exceptions.map(i => i[0]).forEach(i => baseGraph[i].open = true);
}

function mergeContribution(candidate) {
  return calculateContribution(candidate.releases) + calculateContribution(candidate.elefant);
}

console.log(baseGraph);


let elefant = {
  position: 'AA',
  steps: [],
  movingTo: '',
}

let myself = {
  position: 'AA',
  steps: [],
  movingTo: '',
}
let contribution = 0;
for(let time = 0; time <= maxTime; time++) {
  console.log(`== Minute ${time} ==`)
  Object.keys(baseGraph).map(i => baseGraph[i]).forEach(i => {
    if(i.open) {
      contribution += i.flow;
      console.log(`Valve ${i.valve} is open, releasing ${i.flow} pressure.`)
    }
  });
  if(!(myself.steps.length > 0) && baseGraph[myself.position].flow > 0) {
    baseGraph[myself.position].open = true;
    console.log(`You open valve ${baseGraph[myself.position].valve}`);
    myself.movingTo = '';
  }
  if(!(elefant.steps.length > 0) && baseGraph[elefant.position].flow > 0) {
    baseGraph[elefant.position].open = true;
    console.log(`The Elefant opens valve ${baseGraph[elefant.position].valve}`);
    elefant.movingTo = '';
  }
  if(elefant.movingTo === '' && myself.movingTo === '') {
    candidates = {};
  	nextToOpen(baseGraph[myself.position],[],time, contribution)
      .forEach(i => {
        if(!candidates[i.valve]) candidates[i.valve] = {...i}
        if(candidates[i.valve].contribution <= i.contribution)
          candidates[i.valve] = {...candidates[i.valve], myself: i.path.length}
      });
  	nextToOpen(baseGraph[elefant.position],[],time, contribution)
      .forEach(i => {
        if(!candidates[i.valve]) candidates[i.valve] = {...i}
        if(candidates[i.valve].contribution <= i.contribution)
          candidates[i.valve] = {...candidates[i.valve], elefant: i.path.length}
      });

    const sorted = Object.keys(candidates).map(i => candidates[i]).sort((a,b) => b.contribution - a.contribution).slice(0,2);
    let myCandidate;
    let hisCandidate;
    if(sorted.filter(i => (i.myself > 0 && !i.elefant)).length > 0) {
      //exclusive for me.
      myCandidate = sorted.filter(i => i.myself > 0 && !i.elefant)[0];
      hisCandidate = sorted.filter(i => !(i.myself > 0 && !i.elefant))[0];
    } else if(sorted.filter(i => (i.elefant > 0 && !i.myself)).length > 0) {
      //exclusive for elefant.
      hisCandidate = sorted.filter(i => i.elefant > 0 && !i.myself)[0];
      myCandidate = sorted.filter(i => !(i.elefant > 0 && !i.myself))[0];
    } else {
      //any can take any.
      myCandidate = sorted[0];
      hisCandidate = sorted[1];
    }

    if(myCandidate) {
      myself.steps = myself.steps.concat(myCandidate.path)
      myself.movingTo = myself.steps[myself.steps.length - 1];
      console.log('You going to:', myself.movingTo)
    } else {
      myself.stopped = true;
      myself.movingTo = '';
    }

    if(hisCandidate) {
      elefant.steps = elefant.steps.concat(hisCandidate.path)
      elefant.movingTo = elefant.steps[elefant.steps.length - 1];
      console.log('Elefant going to:', elefant.movingTo)
    } else {
      elefant.stopped = true;
      elefant.movingTo = '';
    }

    // if(time === 0) console.log("mf", sorted, hisCandidate, myCandidate)
  } else {
    if(!myself.stopped) {
      if(myself.steps.length > 0) {
        myself.position = myself.steps.shift();
        console.log(`You move to valve ${myself.position}`);
      } else {
        if(time === 0) console.log(nextToOpen(baseGraph[myself.position],[],time, contribution));
        const candidate = nextToOpen(baseGraph[myself.position],[],time, contribution)
        .filter(candidate => candidate.path[candidate.path.length - 1] !== elefant.movingTo)
        .shift();
        if(time === 7) console.log(contribution, nextToOpen(baseGraph[myself.position],[],time, contribution))
        if(candidate) {
          myself.steps = myself.steps.concat(candidate.path)
          myself.movingTo = myself.steps[myself.steps.length - 1];
          console.log('You going to:', myself.movingTo)
        } else {
          myself.stopped = true;
          myself.movingTo = '';
        }
      }
    }

    if(!elefant.stopped) {
      if(elefant.steps.length > 0) {
        elefant.position = elefant.steps.shift();
        console.log(`The Elefant moves to valve ${elefant.position}`);
      } else {
        const candidate = nextToOpen(baseGraph[elefant.position],[],time, contribution)
        .filter(candidate => candidate.path[candidate.path.length - 1] !== myself.movingTo)
        .shift();
        if(time === 7) {
          console.log(contribution, nextToOpen(baseGraph[elefant.position],[],time, contribution))
        }
        if(candidate) {
          elefant.steps = elefant.steps.concat(candidate.path)
          elefant.movingTo = elefant.steps[elefant.steps.length - 1];
          console.log('Elefant going to:', elefant.movingTo)
        } else {
          elefant.stopped = true;
          elefant.movingTo = '';
        }
      }
    }
  }
  // console.log(myself,elefant)
  // break;
}

console.log("Pressure", contribution);

// 1766 too low.
// 2150 too low.
// 1874 too low.


//
// let elefant = {
//   position: 'AA',
//   steps: [],
//   movingTo: '',
// }
//
// let myself = {
//   position: 'AA',
//   steps: [],
//   movingTo: '',
// }
// let contribution = 0;
// for(let time = 0; time <= maxTime; time++) {
//   console.log(`== Minute ${time} ==`)
//   if(time === 8) console.log(myself,elefant);
//   if(!(myself.steps.length > 0) && baseGraph[myself.position].flow > 0) {
//     baseGraph[myself.position].open = true;
//     console.log(`You open valve ${baseGraph[myself.position].valve}`);
//     myself.movingTo = '';
//   }
//   if(!(elefant.steps.length > 0) && baseGraph[elefant.position].flow > 0) {
//     baseGraph[elefant.position].open = true;
//     console.log(`The Elefant opens valve ${baseGraph[elefant.position].valve}`);
//     elefant.movingTo = '';
//   }
//
//   if(!myself.stopped) {
//     if(myself.steps.length > 0) {
//       myself.position = myself.steps.shift();
//       console.log(`You move to valve ${myself.position}`);
//     } else {
//       if(time === 0) console.log(nextToOpen(baseGraph[myself.position],[],time, contribution));
//       const candidate = nextToOpen(baseGraph[myself.position],[],time, contribution)
//       .filter(candidate => candidate.path[candidate.path.length - 1] !== elefant.movingTo)
//       .shift();
//       if(time === 7) console.log(contribution, nextToOpen(baseGraph[myself.position],[],time, contribution))
//       if(candidate) {
//         myself.steps = myself.steps.concat(candidate.path)
//         myself.movingTo = myself.steps[myself.steps.length - 1];
//         console.log('You going to:', myself.movingTo)
//       } else {
//         myself.stopped = true;
//         myself.movingTo = '';
//       }
//     }
//   }
//
//   if(!elefant.stopped) {
//     if(elefant.steps.length > 0) {
//       elefant.position = elefant.steps.shift();
//       console.log(`The Elefant moves to valve ${elefant.position}`);
//     } else {
//       const candidate = nextToOpen(baseGraph[elefant.position],[],time, contribution)
//       .filter(candidate => candidate.path[candidate.path.length - 1] !== myself.movingTo)
//       .shift();
//       if(time === 7) {
//         console.log(contribution, nextToOpen(baseGraph[elefant.position],[],time, contribution))
//       }
//       if(candidate) {
//         elefant.steps = elefant.steps.concat(candidate.path)
//         elefant.movingTo = elefant.steps[elefant.steps.length - 1];
//         console.log('Elefant going to:', elefant.movingTo)
//       } else {
//         elefant.stopped = true;
//         elefant.movingTo = '';
//       }
//     }
//   }
//
//   Object.keys(baseGraph).map(i => baseGraph[i]).forEach(i => {
//     if(i.open) {
//       contribution += i.flow;
//       console.log(`Valve ${i.valve} is open, releasing ${i.flow} pressure.`)
//     }
//   });
//   // console.log(myself,elefant)
//   // break;
// }
//
// console.log("Pressure", contribution);
