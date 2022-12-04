const fs = require('fs');

raw = `
dc-end
HN-start
start-kj
dc-start
dc-HN
LN-dc
HN-end
kj-sa
kj-HN
kj-dc
`

raw = `
fs-end
he-DX
fs-he
start-DX
pj-DX
end-zg
zg-sl
zg-pj
pj-he
RW-he
fs-DX
pj-RW
zg-RW
start-pj
he-WI
zg-he
pj-fs
start-RW
`

raw = fs
  .readFileSync("./day12.input")
  .toString('utf8');

const graph = {}
raw.split('\n').filter(i => i.length).map(i => i.split('-')).forEach(segment => {
  if(!graph[segment[0]]) {
    graph[segment[0]] = {
      next: [],
      visits: 0,
      big: segment[0].toUpperCase() === segment[0],
    }
  }
  if(!graph[segment[1]]) {
    graph[segment[1]] = {
      next: [],
      visits: 0,
      big: segment[1].toUpperCase() === segment[1],
    }
  }
  graph[segment[0]].next.push(segment[1]);
  graph[segment[1]].next.push(segment[0]);
});

const allPaths = [];

function clone(obj) {
  return  JSON.parse(JSON.stringify(obj))
}

function navigate(graph, position, path = []) {
  path.push(position);
  if(position === 'end') {
    allPaths.push(path);
    return;
  }
  graph[position].visits += 1;
  graph[position].next.forEach(nextCave => {
    if(graph[nextCave].visits <= 0 || graph[nextCave].big) {
      navigate(clone(graph), nextCave, clone(path));
    }
  });
}

console.log(graph);

navigate(graph, 'start');

console.log(allPaths.map(i => i.join(',')).join('\n'));
console.log(allPaths.length);
