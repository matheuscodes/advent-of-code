const testInput = `30373
25512
65332
33549
35390
`

const { read, max } = require('../common.js');

const input = read('./day08.input', testInput)
    .split('\n')
    .filter(i => i);

const grid = input.map(i => i.split('').map(i => parseInt(i))); // complete matrix and make it numeric

function visible(row, column, tree) {
    if(row === 0 || column === 0 || row === grid.length - 1 || column === grid[0].length - 1) return true; // edges

    let visibilities = { north: true, south: true, east: true, west: true } // Visibility from a direction

    for(let i = row + 1; i < grid.length; i += 1) {
        if(grid[i][column] >= tree) visibilities.south = false;
    }

    for(let i = column + 1; i < grid[row].length; i += 1) {
        if(grid[row][i] >= tree) visibilities.east = false;
    }

    for(let i = row - 1; i >= 0; i -= 1) {
        if(grid[i][column] >= tree) visibilities.north = false;
    }

    for(let i = column - 1; i >= 0; i -= 1) {
        if(grid[row][i] >= tree) visibilities.west = false;
    }

    return visibilities.north || visibilities.south || visibilities.east || visibilities.west;
}

const treesVisible = []
for(let r = 0; r < grid.length; r += 1) {
    for(let c = 0; c < grid[r].length; c += 1) {
        const tree = grid[r][c];
        if(visible(r,c,tree)) {
            treesVisible.push([r,c]);
        }
    }
}

console.log("Visible trees on the grid", treesVisible.length);

// Second Part: the scenic score

function scenicScore(row, column, tree) {
    let distances = { north: 0, south: 0, east: 0, west: 0 }

    for(let i = row + 1; i < grid.length; i += 1) {
        distances.south += 1;
        if(grid[i][column] >= tree) break;
    }

    for(let i = column + 1; i < grid[row].length; i += 1) {
        distances.east += 1;
        if(grid[row][i] >= tree) break;
    }

    for(let i = row - 1; i >= 0; i -= 1) {
        distances.north += 1;
        if(grid[i][column] >= tree) break;
    }

    for(let i = column - 1; i >= 0; i -= 1) {
        distances.west += 1;
        if(grid[row][i] >= tree) break;
    }

    return distances.north * distances.south * distances.east * distances.west;
}

const scores = []
for(let r = 0; r < grid.length; r += 1) {
    for(let c = 0; c < grid[r].length; c += 1) {
        const tree = grid[r][c];
        scores.push(scenicScore(r,c,tree));
    }
}

console.log("Highest scenic score possible:", scores.reduce(max, 0));