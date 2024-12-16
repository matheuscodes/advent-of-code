package matheus.software.aoc2024.day16;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public final class ReindeerMaze {

    public enum Face {
        NORTH, SOUTH, EAST, WEST
    }

    @Getter
    @Setter
    static class Reindeer {
        private int[] currentPosition;
        private List<int[]> visited;

        private String[][] maze;

        Reindeer(final String[][] environment) {
            maze = environment;
            currentPosition = new int[] {
                    maze.length - 2,
                    1
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
            int[] current = visited.get(0);
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

        public String getKey() {
            return currentPosition[0] + "_" + currentPosition[1] + "_" + currentFace();
        }

        private int currentFace() {
            return (int) (turns() % 4);
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
                        System.out.print("\u001B[31m" + "x" + "\u001B[0m");
                    } else {
                        System.out.print(maze[i][j]);
                    }
                }
                System.out.println();
            }
            System.out.println("Visited: " + visited.size() + " Turns:" + turns());
        }

        static int compare(final Reindeer x, final Reindeer y) {
            return Long.compare(x.pathPoints(), y.pathPoints());
        }

        public Reindeer variate() {
            return new Reindeer(this);
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
        HashMap<String, LinkedList<Reindeer>> bests = new HashMap<>();
        return findBestPath(maze, bests).pathPoints();
    }
    public int countSpots(final String raw) {
        String[] lines = raw.split("\\n");
        String[][] maze = new String[lines.length][];
        int row = 0;
        for (String line: lines) {
            maze[row++] = line.split("");
        }
        HashMap<String, LinkedList<Reindeer>> bests = new HashMap<>();
        Reindeer best = findBestPath(maze, bests);

        Set<String> set = bests.entrySet()
                .stream()
                .filter(i -> i.getKey().startsWith(1 + "_" + (maze[0].length - 2)))
                .flatMap(i -> i
                        .getValue()
                        .stream()
                        .filter(deer -> deer.pathPoints() == best.pathPoints())
                        .flatMap(deer ->
                                deer
                                        .getVisited()
                                        .stream()
                                        .map(pos -> pos[0] + "_" + pos[1])))
                .collect(Collectors.toSet());
        return set.size();
    }

    private Reindeer findBestPath(final String[][] maze, final HashMap<String, LinkedList<Reindeer>> bests) {
        HashMap<String, Reindeer> distances = new HashMap<>();
        Reindeer firstDeer = new Reindeer(maze);
        distances.put(firstDeer.currentPosition[0] + "_" + firstDeer.currentPosition[1], firstDeer);
        PriorityQueue<Reindeer> unvisited = new PriorityQueue<>(Reindeer::compare);
        unvisited.add(firstDeer);
        HashSet<String> visited = new HashSet<>();
        while (!unvisited.isEmpty()) {
            Reindeer thisDeer = unvisited.poll();
            thisDeer.next().forEach(nextPosition -> {
                Reindeer possibleNextDeer = thisDeer.variate();
                possibleNextDeer.move(nextPosition);
                Reindeer nextDeer = distances.get(possibleNextDeer.getKey());
                if (nextDeer == null) {
                    distances.put(possibleNextDeer.getKey(), possibleNextDeer);
                    bests.put(possibleNextDeer.getKey(), new LinkedList<>());
                    bests.get(possibleNextDeer.getKey()).add(possibleNextDeer);
                } else {
                    if (possibleNextDeer.pathPoints() < nextDeer.pathPoints()) {
                        distances.put(possibleNextDeer.getKey(), possibleNextDeer);
                        bests.put(possibleNextDeer.getKey(), new LinkedList<>());
                        bests.get(possibleNextDeer.getKey()).add(possibleNextDeer);
                    } else if (possibleNextDeer.pathPoints() == nextDeer.pathPoints()) {
                        bests.get(possibleNextDeer.getKey()).add(possibleNextDeer);
                    }
                }
                if (!visited.contains(possibleNextDeer.getKey())) {
                    unvisited.add(possibleNextDeer);
                }
            });
            visited.add(thisDeer.getKey());
        }

        return Arrays.stream(new Reindeer[]{
                distances.get(1 + "_" + (maze[0].length - 2) + "_0"),
                distances.get(1 + "_" + (maze[0].length - 2) + "_1"),
                distances.get(1 + "_" + (maze[0].length - 2) + "_2"),
                distances.get(1 + "_" + (maze[0].length - 2) + "_3")
        }).filter(Objects::nonNull).min(Reindeer::compare).orElseThrow();
    }

}
