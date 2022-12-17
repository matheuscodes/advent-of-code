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
  if(!(myself.steps.length > 0) && myself.position && baseGraph[myself.position].flow > 0) {
    baseGraph[myself.position].open = true;
    console.log(`You open valve ${baseGraph[myself.position].valve}`);
    myself.movingTo = '';
  }
  if(!(elefant.steps.length > 0) && elefant.position && baseGraph[elefant.position].flow > 0) {
    baseGraph[elefant.position].open = true;
    console.log(`The Elefant opens valve ${baseGraph[elefant.position].valve}`);
    elefant.movingTo = '';
  }

  candidates = {};
	myself.steps.length === 0 ? nextToOpen(baseGraph[myself.position],[],time, contribution)
    .filter(i => (i.valve !== myself.movingTo && i.valve !== elefant.movingTo))
    .forEach(i => {
      if(!candidates[i.valve]) candidates[i.valve] = {...i}
      candidates[i.valve] = {
        ...candidates[i.valve],
        mPath: i.path,
        myself: i.path.length
      }
    }) : '';
	elefant.steps.length === 0 ? nextToOpen(baseGraph[elefant.position],[],time, contribution)
    .filter(i => (i.valve !== myself.movingTo && i.valve !== elefant.movingTo))
    .forEach(i => {
      if(!candidates[i.valve]) candidates[i.valve] = {...i}
      candidates[i.valve] = {
        ...candidates[i.valve],
        ePath: i.path,
        elefant: i.path.length
      }
    }) : '';

    let sorted = Object.keys(candidates).map(i => candidates[i]).sort((a,b) => b.contribution - a.contribution);
    let myCandidate;
    let hisCandidate;
    if(myself.steps.length <= 0 && elefant.steps.length <= 0 && sorted.length >= 2) {
      sorted = sorted.sort(i => i.myself - i.elefant);
    }
    if(myself.steps.length <= 0) myCandidate = sorted.shift();
    if(elefant.steps.length <= 0) hisCandidate = sorted.shift();

    if(myCandidate && myself.steps.length <= 0) {
      myself.steps = myself.steps.concat(myCandidate.mPath)
      myself.movingTo = myself.steps[myself.steps.length - 1];
      console.log("m", candidates,myCandidate)
      console.log('You going to:', myself.movingTo)
    } else {
      if(myself.steps.length > 0) {
        myself.position = myself.steps.shift();
        console.log(`You move to valve ${myself.position}`);
      } else {
        //do nothing... just stopped.
      }
    }

    if(hisCandidate && elefant.steps.length <= 0) {
      elefant.steps = elefant.steps.concat(hisCandidate.ePath)
      elefant.movingTo = elefant.steps[elefant.steps.length - 1];
      console.log("e",candidates,hisCandidate)
      console.log('Elefant going to:', elefant.movingTo)
    } else {
      if(elefant.steps.length > 0) {
        elefant.position = elefant.steps.shift();
        console.log(`The Elefant moves to valve ${elefant.position}`);
      } else {
        //do nothing... just stopped.
      }
    }
}

console.log("Pressure", contribution);

// 1766 too low.
// 2150 too low.
// 1874 too low.
