package matheus.software.aoc2024.day06;

import org.javatuples.Triplet;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public final class GuardsRound {

    enum Direction {
        NORTH, SOUTH, EAST, WEST
    }
    static class Guard {
        private Direction face = Direction.NORTH;
        private int i;
        private int j;

        Guard(final int thisI, final int thisJ) {
            this.i = thisI;
            this.j = thisJ;
        }

        private int[] nextPositionFrom(final int[] here) {
            return switch (face) {
                case NORTH -> new int[]{here[0] - 1, here[1]};
                case SOUTH -> new int[]{here[0] + 1, here[1]};
                case EAST -> new int[]{here[0], here[1] + 1};
                case WEST -> new int[]{here[0], here[1] - 1};
            };
        }

        private void turn() {
            this.face = switch (face) {
                case NORTH -> Direction.EAST;
                case EAST -> Direction.SOUTH;
                case SOUTH -> Direction.WEST;
                case WEST -> Direction.NORTH;
            };
        }

        public boolean move(final String[][] map) {
            int[] next = nextPositionFrom(new int[]{i, j});
            if (next[0] < 0 || next[0] >= map.length) {
                return false;
            }
            if (next[1] < 0 || next[1] >= map[next[0]].length) {
                return false;
            }
            if ("#".equals(map[next[0]][next[1]])) {
                this.turn();
                return move(map);
            } else {
                i = next[0];
                j = next[1];
                return true;
            }
        }

        public int marks(final String[][] map) {
            if (!"X".equals(map[i][j])) {
                map[i][j] = "X";
                return 1;
            }
            return 0;
        }

        public long loops(final String[][] map, final int thisI, final int thisJ) {
            this.i = thisI;
            this.j = thisJ;
            this.face = Direction.NORTH;
            LinkedList<Triplet<Integer, Integer, Direction>> visited = new LinkedList<>();
            while (this.move(map)) {
                Triplet<Integer, Integer, Direction> current = new Triplet<>(this.i, this.j, this.face);
                if (visited.contains(current)) {
                    return 1;
                }
                visited.add(current);
            }
            return 0;
        }
    }


    public long followGuard(final String raw, final int thisI, final int thisJ) {
        String[] lines = raw.split("\\n");
        String[][] map = new String[lines.length][];
        int lineCounter = 0;
        for (String line: lines) {
            map[lineCounter++] = line.split("");
        }
        if (!"^".equals(map[thisI][thisJ])) {
            throw new IllegalArgumentException("Wrong start position for guard.");
        }

        Guard she = new Guard(thisI, thisJ);
        long steps = she.marks(map);
        while (she.move(map)) {
            steps += she.marks(map);
        }
        return steps;
    }

    public long loopGuard(final String raw, final int i, final int j) {
        String[] lines = raw.split("\\n");
        String[][] map = new String[lines.length][];
        int lineCounter = 0;
        for (String line: lines) {
            map[lineCounter++] = line.split("");
        }
        if (!"^".equals(map[i][j])) {
            throw new IllegalArgumentException("Wrong start position for guard.");
        }

        Guard she = new Guard(i, j);
        while (she.move(map)) {
            she.marks(map);
        }

        long loops = 0;
        for (int row = 0; row < map.length; row += 1) {
            for (int column = 0; column < map.length; column += 1) {
                if ("X".equals(map[row][column]) && !(row == i && column == j)) {
                    map[row][column] = "#";
                    if (she.loops(map, i, j) > 0) {
                        loops += 1;
                        map[row][column] = "O";
                    } else {
                        map[row][column] = "X";
                    }
                }
            }
        }
        return loops;
    }
}
