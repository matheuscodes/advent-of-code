const testInput = `    [D]    
[N] [C]    
[Z] [M] [P]
 1   2   3 

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2
`

const { read, clone } = require('../common.js');

const input = read('./day05.input', testInput).split('\n\n');

// First part: moving cranes

const cranesInput = input[0]
    .split('\n')
    .filter(i => i);

cranesInput.pop();

const containers = cranesInput.pop().split(' ').map(i => [i]);

while(cranesInput.length > 0) {
    const row = cranesInput.pop().split('');
    let pile = 0;
    while(row.length > 0) {
        const container = row.splice(0,4).filter(i => i !== ' ');
        if(container.length > 0) {
            containers[pile].push(container.join(''));
        }
        pile += 1;
    }
}

const instructionsInput = input[1]
    .split('\n')
    .filter(i => i)
    .map(i => i.split(' '))
    .map(i => ({
        amount: i[1],
        from: i[3],
        to: i[5],
    }));


const createMover9000 = clone(containers);

instructionsInput.forEach(instruction => {
    for(let i = 0; i < instruction.amount; i += 1) {
        const moved = createMover9000[instruction.from - 1].pop();
        createMover9000[instruction.to - 1].push(moved);
    }
})

const finalWord9000 = createMover9000.map(i => i.pop())
    .join('')
    .split('][')
    .join('');

console.log('Top of each stack Create Mover 9000:', finalWord9000);

// Second part: moving with different mover

const createMover9001 = clone(containers);

instructionsInput.forEach(instruction => {
    const pile = createMover9001[instruction.from - 1];
    const containers = pile.splice(pile.length - instruction.amount);
    createMover9001[instruction.to - 1] = createMover9001[instruction.to - 1].concat(containers);
})

const finalWord9001 = createMover9001.map(i => i.pop())
    .join('')
    .split('][')
    .join('');

console.log('Top of each stack Create Mover 9001:', finalWord9001);