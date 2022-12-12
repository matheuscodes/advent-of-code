const testInput = `R 4
U 4
L 3
D 1
R 4
D 1
L 5
R 2
`

const longerInput = `R 5
U 8
L 8
D 3
R 17
D 10
L 25
U 20
`

const { read } = require('../common.js');

const input = read('./day09.input', longerInput)
    .split('\n')
    .filter(i => i)
    .map(i => i.split(' '));

// First part: trace tail movements

const coordinateChanges = input
    .map(i => {
        const parsed = parseInt(i[1]);
        switch (i[0]) {
            case 'D': return [0,-parsed];
            case 'U': return [0,parsed];
            case 'L': return [-parsed,0];
            case 'R': return [parsed, 0];
            default: throw Error('Impossible state');
        }
    });

function changeCoordinate(point, change) {
    return [point[0] + change[0], point[1] + change[1]];
}

function discreteMoves(positionHead, positionTail, meet = false) {
    const rightMoves = []
    const xDiff = (positionHead[0]-positionTail[0]);
    const yDiff = (positionHead[1]-positionTail[1]);
    const mod = (xDiff) ** 2 + (yDiff) ** 2
    // console.log('here', mod)
    if(mod > 2 || meet) {
        const movesNeeded = (xDiff ** 2 > yDiff ** 2) ? Math.abs(xDiff) : Math.abs(yDiff);
        const diagonalNeeded = (xDiff ** 2 < yDiff ** 2) ? Math.abs(xDiff) : Math.abs(yDiff);
        const directionX = Math.abs(xDiff) > 0 ? xDiff / Math.abs(xDiff) : 0;
        const directionY = Math.abs(yDiff) > 0 ? yDiff / Math.abs(yDiff) : 0;
        if(positionHead[0] !== positionTail[0] && positionHead[1] !== positionTail[1]) {
            // Needs to match diagonals first
            for(let i = 0; i < diagonalNeeded; i++) {
                rightMoves.push([directionX, directionY]);
                rightMoves.push([directionX, directionY]);
            }
            if(Math.abs(xDiff) === Math.abs(yDiff)) rightMoves.pop();
            const move = xDiff ** 2 > yDiff ** 2 ? [directionX, 0] : [0, directionY];
            for(let i = diagonalNeeded; i < movesNeeded; i++) {
                rightMoves.push(move);
            }
        } else {
            // console.log('manoo', movesNeeded)
            for(let i = 0; i < movesNeeded; i++) {
                rightMoves.push([directionX, directionY]);
            }
        }
    }
    return rightMoves;
}

let traces = [[0,0]];
let head = [0,0];
let tail = [0,0];

coordinateChanges.forEach(change => {
    head = changeCoordinate(head, change);
    const moves = discreteMoves(head, tail);
    moves.forEach(move => {
        tail = changeCoordinate(tail, move);
        traces.push(tail);
    });
});

const uniquePositions = new Set(traces.map(i => `${i[0]},${i[1]}`)).size
console.log("Positions visited at least once:", uniquePositions);


// Second Part: Much longer rope

traces = [[0,0]];
const headIndex = 0;
const tailIndex = 9;
let rope = [
    [0,0], // head is 0
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0],
    [0,0], // tail is 9
]

function drawRope() {
    // return;
    const board = [];
    for(let i = 0; i < 50; i++) {
        board[i] = [];
        for(let j = 0; j < 50; j++) {
            board[i][j] = '.'
        }
    }
    rope.forEach((position, index) => {
        // console.log(position)
        board[16-position[1]][16+position[0]] = index ? index : 'H'
    });
    console.log(board.map(i => i.join('')).join('\n'));
}

function drawTail() {
    // return;
    const board = [];
    for(let i = 0; i < 50; i++) {
        board[i] = [];
        for(let j = 0; j < 50; j++) {
            board[i][j] = '.'
        }
    }
    traces.forEach((position, index) => {
        // console.log(position)
        board[16-position[1]][16+position[0]] = index ? '#' : 's'
    });
    console.log(board.map(i => i.join('')).join('\n'));
}

// coordinateChanges.forEach((change, cI) => {
//     console.log('>>>> step:', change)
//     const expectedHead = changeCoordinate(rope[headIndex], change);
//     console.log('>>>> step:', change, rope[headIndex], expectedHead)
//     let loop = 0
//     while(rope[headIndex][0] !== expectedHead[0] || rope[headIndex][1] !== expectedHead[1]) {
//         console.log(rope[headIndex], expectedHead)
//         rope.forEach((position, index) => {
//             if (index === tailIndex) return; // Tail has moved already...
//             // If head it moves according to instructions, else move to the previous next knot
//             const headMoves = discreteMoves(position, changeCoordinate(position, index === headIndex ? change : [0, 0]));
//             headMoves.slice(0,1).forEach(move => {
//                 rope[index] = changeCoordinate(position, move);
//             })
//             // rope[index] = changeCoordinate(position, index === headIndex ? change : [0, 0]);
//             // drawRope();
//             // if(index === headIndex) console.log("h",rope[index], change);
//             const moves = discreteMoves(rope[index], rope[index + 1]);
//             // console.log(rope[index], rope[index+1], index, index+1,moves)
//             moves.slice(0,1).forEach(move => {
//                 rope[index + 1] = changeCoordinate(rope[index + 1], move);
//                 // drawRope();
//                 if ((index + 1) === tailIndex) traces.push(rope[index + 1]);
//                 // if((index + 1) === tailIndex) console.log("t",rope[index+1], move)
//             });
//             // if(cI === 1 && index === 6) {
//             //     console.log(moves);
//             //     drawRope();
//             //     throw Error('chega')
//             // }
//             // console.log("rope", rope)
//         });
//         drawRope();
//         if(loop++ > 4) throw Error("aaaa" + loop)
//     }
//     drawRope();
//     drawTail()
// });

coordinateChanges.forEach((change, cI) => {
    const expectedHead = changeCoordinate(rope[headIndex], change);
    // console.log('>>>> step:', change, rope[headIndex], expectedHead)
    let loop = 0
    while(rope[headIndex][0] !== expectedHead[0] || rope[headIndex][1] !== expectedHead[1]) {
        rope.forEach((position, index) => {
            const moves = discreteMoves(index === headIndex ? changeCoordinate(position, change) : rope[index - 1], position, index === headIndex)
            // if(index === headIndex) console.log(index, moves, change, position, changeCoordinate(position, change))
            moves.slice(0,1).forEach(move => {
                rope[index] = changeCoordinate(position, move);
                if (index === tailIndex) traces.push(rope[index]);
            });
        });
        // drawRope();
        // if(loop++ > 0) throw Error();
    }
    if(Math.abs(rope[headIndex][0]) > 10000) throw Error("Rope overflow! " + rope[headIndex] )
    if(Math.abs(rope[headIndex][1]) > 10000) throw Error("Rope overflow! " + rope[headIndex] )
    // drawRope();
    // drawTail();
});

const moreUniquePositions = new Set(traces.map(i => `${i[0]},${i[1]}`)).size
console.log("Positions visited at least once:", moreUniquePositions);
// drawTail()

// 2548 too low