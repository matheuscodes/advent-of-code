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

const { read } = require('../common.js');

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
const all = [];
function walkAllOnce(current, past, events) {
  // console.log(current,past, events);
  if(past > 30) {
    console.log(events);
    all.push(events);
    return;
  }

  let time = past;
  if(current.flow > 0 && !current.open) {
    time += 1; //opening...
    events.push({valve: current.valve, time});
  }

  current.nextValves.forEach(i => walkAllOnce(baseGraph[i], time + 1, clone(events)))
}

function shortestPathFromTo2(data, destiny) {
  const paths = data;
  while(paths.length > 0) {
    const path = paths.sort((a,b) => a.length - b.length).shift();
    // console.log(path,paths,destiny)
    const current = path[path.length - 1];
    if(current.valve === destiny.valve) return path;
    current.nextValves.forEach(i => {
      if(path.length >= 5) return;
      paths.push(path.concat([baseGraph[i]]))
    });
  }
  return;
}


function shortestPathFromTo(current, destiny, depth = 0) {
  if(current.valve === destiny.valve) return [current.valve];
  if(depth >= graphSize) return;
  const path = [current.valve];
  const possible = current.nextValves.map(i => shortestPathFromTo(baseGraph[i], destiny, depth + 1)).filter(i => i);
  if(!possible.length) return;
  return path.concat(possible.sort((a,b) => a.length - b.length).shift());
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
      return {
        timeToOpen: path.length,
        timeOpened: time + path.length,
        releases: trial.concat([path[path.length - 1]]),
        contribution: (30 - (time + path.length)) * baseGraph[path[path.length - 1]].flow + contribution,
        // path
      }
    })
    .sort((a,b) => b.contribution - a.contribution);
}

const possibilities = [
  {
    timeToOpen: 0,
    timeOpened: 0,
    releases: ['AA'],
    contribution: 0,
    path: [ 'AA' ]
  }
]

function closeAllExcept(exceptions) {
  Object.keys(baseGraph).forEach(i => baseGraph[i].open = false);
  exceptions.forEach(i => baseGraph[i].open = true);
}

console.log(baseGraph);
const done = [];
let best = {contribution: 0}
while(possibilities.length > 0) {
  const top = possibilities.shift();
  closeAllExcept(top.releases);
  const candidates = nextToOpen(baseGraph[top.releases[top.releases.length - 1]],top.releases,top.timeOpened, top.contribution);
  // console.log(candidates);
  candidates.forEach(candidate => {
    if(candidate.timeOpened > 30) done.push(candidate);
    else possibilities.push(candidate);

    if(candidate.contribution > best.contribution) {
      best = candidate;
      // console.log(new Date(), "new best", best)
    }
  })
  possibilities.sort((a,b) => b.contribution - a.contribution);
}
console.log(best)
// console.log(done.sort((a,b) => b.contribution - a.contribution));
