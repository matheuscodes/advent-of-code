const {read, sum} = require("../common");
const testInput = `addx 15
addx -11
addx 6
addx -3
addx 5
addx -1
addx -8
addx 13
addx 4
noop
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx 5
addx -1
addx -35
addx 1
addx 24
addx -19
addx 1
addx 16
addx -11
noop
noop
addx 21
addx -15
noop
noop
addx -3
addx 9
addx 1
addx -3
addx 8
addx 1
addx 5
noop
noop
noop
noop
noop
addx -36
noop
addx 1
addx 7
noop
noop
noop
addx 2
addx 6
noop
noop
noop
noop
noop
addx 1
noop
noop
addx 7
addx 1
noop
addx -13
addx 13
addx 7
noop
addx 1
addx -33
noop
noop
noop
addx 2
noop
noop
noop
addx 8
noop
addx -1
addx 2
addx 1
noop
addx 17
addx -9
addx 1
addx 1
addx -3
addx 11
noop
noop
addx 1
noop
addx 1
noop
noop
addx -13
addx -19
addx 1
addx 3
addx 26
addx -30
addx 12
addx -1
addx 3
addx 1
noop
noop
noop
addx -9
addx 18
addx 1
addx 2
noop
noop
addx 9
noop
noop
noop
addx -1
addx 2
addx -37
addx 1
addx 3
noop
addx 15
addx -21
addx 22
addx -6
addx 1
noop
addx 2
addx 1
noop
addx -10
noop
noop
addx 20
addx 1
addx 2
addx 2
addx -6
addx -11
noop
noop
noop
`;


const input = read('./day10.input', testInput)
    .split('\n')
    .filter(i => i)
    .map(i => i.split(' '));

let registerX = 1;
let cycle = 0;

const snapshots = [];
const crt = [];

input.forEach(instruction => {
   let cycleAdd = instruction[0] === 'noop' ? 1 : 2;
   let registerAdd = parseInt(instruction[1]);
   if((cycle + cycleAdd) >= 20 && snapshots.length <= 0) {
       snapshots.push([20, registerX]);
   }

   if((cycle + cycleAdd) >= 60) {
       const previous = Math.floor((cycle - 20) / 40);
       const next = Math.floor((cycle + cycleAdd - 20) / 40);
       if(next > previous) {
           snapshots.push([next * 40 + 20, registerX]);
       }
   }
   for(let i = 0; i < cycleAdd; i++) {
       const crtCycle = cycle % 40;
       if(registerX >= crtCycle - 1 && registerX <= crtCycle + 1) {
           crt.push('#');
       } else {
           crt.push('.');
       }
       cycle += 1;
   }
   registerX += instruction[0] === 'addx' ? registerAdd : 0;
});

const signalStrengths = snapshots.map(i => i[0] * i[1]).reduce(sum, 0);

console.log("Sum of signal strengths", signalStrengths);

const chunkSize = 40;
for (let i = 0; i < crt.length; i += chunkSize) {
    const chunk = crt.slice(i, i + chunkSize);
    console.log(chunk.join(''));
}