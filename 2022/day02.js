const testInput = `A Y
B X
C Z
`;

const { read, sum } = require('../common.js');

// First part: points on assumed strategy.

const input = read('./day02.input', testInput);
// A Rock B Paper C Scissor
// Y Paper X Rock Z Scissor
// Score: <value_played> + <value_match>
const score = {
    'A Y': 2 + 6,
    'A X': 1 + 3,
    'A Z': 3 + 0,
    'B Y': 2 + 3,
    'B X': 1 + 0,
    'B Z': 3 + 6,
    'C Y': 2 + 0,
    'C X': 1 + 6,
    'C Z': 3 + 3,
}

const result = input
    .split('\n') // Read matches
    .filter(i => i) // Clean empty entries
    .map(i => score[i]) // Map the score
    .reduce(sum, 0)

console.log('Score on assumed strategy:', result);

// Second part: points on given strategy.

// A Rock B Paper C Scissor
// Y Paper X Rock Z Scissor
// Score: <value_match> + <value_played>
const newScore = {
    'A Y': 3 + 1,
    'A X': 0 + 3,
    'A Z': 6 + 2,
    'B Y': 3 + 2,
    'B X': 0 + 1,
    'B Z': 6 + 3,
    'C Y': 3 + 3,
    'C X': 0 + 2,
    'C Z': 6 + 1,
}

const newResult = input
    .split('\n') // Read matches
    .filter(i => i) // Clean empty entries
    .map(i => newScore[i]) // Map the new score
    .reduce(sum, 0)

console.log('Score on given strategy:', newResult);
