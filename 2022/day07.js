const testInput = `$ cd /
$ ls
dir a
14848514 b.txt
8504156 c.dat
dir d
$ cd a
$ ls
dir e
29116 f
2557 g
62596 h.lst
$ cd e
$ ls
584 i
$ cd ..
$ cd ..
$ cd d
$ ls
4060174 j
8033020 d.log
5626152 d.ext
7214296 k
`

const { read, clone, sum, min } = require('../common.js');

const input = read('./day07.input', testInput)
    .split('\n')
    .filter(i => i);

// First part: calculating directory sizes
const calculatedSizes = {};

function calculateSize(directories, sizes) {
    const size = sizes.pop();
    calculatedSizes[directories.join('/')] = size;
    directories.pop();
    // Puts the cumulative value back in the pile.
    const nextSize = sizes.pop() + size;
    sizes.push(nextSize);
}

function replayCommands(input, directories = [], sizes = []) {
    while(input.length > 0) {
        const line = input.shift().split(' ');
        switch (line[1]) {
            case 'ls':
                let thisDirectorySize = 0;
                while (input.length > 0) {
                    const file = input.shift().split(' ');
                    if (file[1] === 'cd') { // Got navigation command, put it back.
                        input.unshift(file.join(' '));
                        break;
                    }
                    const size = parseInt(file[0]);
                    if (!isNaN(size)) thisDirectorySize += size;
                }
                sizes.push(thisDirectorySize);
                break;
            case 'cd':
                if (line[2] !== '..') { //moving forward in stacking sizes
                    directories.push(line[2]);
                } else { //moving back and reporting sizes
                    calculateSize(directories, sizes);
                }
                break;
            default:
                throw Error("Impossible state.");
        }
    }
    // finalize calculations after commands finished playing.
    while(directories.length > 0) calculateSize(directories, sizes);
}

replayCommands(clone(input))

const totalSize = Object.keys(calculatedSizes)
    .map(i => calculatedSizes[i])
    .filter(i => i < 100000)
    .reduce(sum, 0);

console.log("Sum of total sizes < 100000:", totalSize);

// Second part: find the right candidate to free up space
const needed = 30000000;
const available = 70000000
const required = needed - (available - calculatedSizes['/']);

const minRequired = Object.keys(calculatedSizes)
    .map(i => calculatedSizes[i])
    .filter(i => i > required)
    .reduce(min, 70000000);

console.log("Deletion would free:", minRequired);