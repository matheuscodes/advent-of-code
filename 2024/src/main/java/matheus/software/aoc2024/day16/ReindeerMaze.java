package matheus.software.aoc2024.day16;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

@Component
public final class ReindeerMaze {

    public enum Face {
        NORTH, SOUTH, EAST, WEST
    }

    @Getter
    @Setter
    static class Reindeer {
        private int[] currentPosition;
        private int[] goal;
        private List<int[]> visited;

        private String[][] maze;

        Reindeer(final String[][] environment) {
            maze = environment;
            currentPosition = new int[] {
                    maze.length - 2,
                    1
            };
            goal = new int[] {
                    1,
                    maze[0].length - 2
            };
            visited = new ArrayList<>();
            visited.add(currentPosition);
        }

        public long pathPoints() {
            return turns() * 1000 + visited.size() - 1;
        }

        private long turns() {
            long turns = 0;
            Face facing = Face.EAST;
            int[] current = currentPosition;
            for (int[] position: visited) {
                switch (facing) {
                    case EAST, WEST -> {
                        if (position[0] != current[0]) {
                            turns += 1;
                            if (position[0] < current[0]) {
                                facing = Face.NORTH;
                            } else {
                                facing = Face.SOUTH;
                            }
                        }
                    }
                    case NORTH, SOUTH -> {
                        if (position[1] != current[1]) {
                            turns += 1;
                            if (position[1] < current[1]) {
                                facing = Face.WEST;
                            } else {
                                facing = Face.EAST;
                            }
                        }
                    }
                    default -> throw new RuntimeException("Impossible.");
                }
                current = position;
            }
            return turns;
        }

        public boolean finished() {
            return currentPosition[0] == goal[0] && currentPosition[1] == goal[1];
        }

        private List<int[]> next() {
            return Arrays.stream(new int[][] {
                new int[]{currentPosition[0] - 1, currentPosition[1]},
                new int[]{currentPosition[0], currentPosition[1] + 1},
                new int[]{currentPosition[0] + 1, currentPosition[1]},
                new int[]{currentPosition[0], currentPosition[1] - 1}
            }).filter(position ->
                (".".equals(maze[position[0]][position[1]]) || "E".equals(maze[position[0]][position[1]]))
                && visited.stream().noneMatch(visit -> (visit[0] == position[0] && visit[1] == position[1]))
            ).toList();
        }

        Reindeer(final Reindeer previousState) {
            currentPosition = previousState.currentPosition;
            goal = previousState.goal;
            visited = new ArrayList<>(previousState.visited.size());
            visited.addAll(previousState.visited);
            maze = previousState.maze;
        }

        private void printDeer() {
            for (int i = 0; i < maze.length; i += 1) {
                for (int j = 0; j < maze[i].length; j += 1) {
                    int finalI = i;
                    int finalJ = j;
                    if (visited.stream().anyMatch(a -> a[0] == finalI && a[1] == finalJ)) {
                        System.out.print("x");
                    } else {
                        System.out.print(maze[i][j]);
                    }
                }
                System.out.println();
            }
            System.out.println("Visited: " + visited.size());
        }

        static int compare(final Reindeer x, final Reindeer y) {
            return Long.compare(x.pathPoints(), y.pathPoints());
        }

        public List<Reindeer> variants() {
            return next().stream().map(position -> {
                Reindeer deer = new Reindeer(this);
                deer.move(position);
                return deer;
            }).toList();
        }

        private void move(final int[] position) {
            currentPosition = position;
            visited.add(currentPosition);
        }
    }

    public long findPathScore(final String raw) {
        String[] lines = raw.split("\\n");
        String[][] maze = new String[lines.length][];
        int row = 0;
        for (String line: lines) {
            maze[row++] = line.split("");
        }
        PriorityQueue<Reindeer> multiverse = new PriorityQueue<>(Reindeer::compare);
        multiverse.add(new Reindeer(maze));
        while (!multiverse.isEmpty()) {
            Reindeer deer = multiverse.poll();
            if (deer.finished()) {
                deer.printDeer();
                return deer.pathPoints();
            }
            multiverse.addAll(deer.variants());
        }
        return 0L;
    }
}
