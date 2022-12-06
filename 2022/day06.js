const testInput = `mjqjpqmgbljsphdztnvjfqwrcgsmlb
bvwbjplbgvbhsrlpgdmjqwftvncz
nppdvjthqldpwncqszvftbrmjlhg
nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg
zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw
`

const { read, clone } = require('../common.js');

const input = read('./day06.input', testInput)
    .split('\n') // mostly for testing with multiple streams
    .filter(i => i) // cleaning empty lines
    .map(i => i.split(''));

// First part: find marker.

function findMarker(datastream, length = 4) {
    const clonedData = clone(datastream); // ensuring idempotency
    const window = clonedData.splice(0,length);
    let position = length;
    while(new Set(window).size < length) {
        window.shift();
        window.push(clonedData.shift());
        position += 1;
    }
    return position;
}

input.forEach(datastream => {
   const characterAt = findMarker(datastream);
   console.log("First marker after character:", characterAt);
});

// Second part: finding start of message

input.forEach(datastream => {
    const characterAt = findMarker(datastream, 14);
    console.log("First start-of-message after character:", characterAt);
});
