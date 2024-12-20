package matheus.software.aoc2024.day20;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

@Component
public final class Racetrack {
    public long countCheatSaves(final String raw, final int picoseconds, final int cheatTime) {
        String[] lines = raw.split("\\n");
        String[][] track = new String[lines.length][];
        int row = 0;
        for (String line: lines) {
            track[row++] = line.split("");
        }
        int[] startPosition = new int[0];
        for (int i = 0; i < track.length; i += 1) {
            for (int j = 0; j < track[i].length; j += 1) {
                if ("S".equals(track[i][j])) {
                    startPosition = new int[] {i, j};
                }
            }
        }
        List<String> path = findPath(track, startPosition);
        if (path == null) {
            return 0L;
        }
        long pathSize = path.size();
        HashSet<String> cheatSpots = new HashSet<>();
        HashSet<String> visited = new HashSet<>();
        int count = 0;
        for (String keys: path) {
            int[] position = fromKeys(keys);
            visited.add(keys);
            int finalCount = count++;
            path.stream().filter(
                    candidate ->
                            distance(position, fromKeys(candidate)) <= cheatTime
                    && !visited.contains(candidate)
            ).forEach(candidate -> {
                int newPosition = path.indexOf(candidate);
                if (newPosition > 0) {
                    long finalTime = finalCount + distance(position, fromKeys(candidate)) + (pathSize - newPosition);
                    if (pathSize - finalTime >= picoseconds) {
                        cheatSpots.add(keys + "+" + candidate);
                    }
                }
            });
        }
        return cheatSpots.size();
    }

    private long distance(final int[] from, final int[] to) {
        return Math.abs(to[0] - from[0]) + Math.abs(to[1] - from[1]);
    }

    private int[] fromKeys(final String keys) {
        return new int[]{
                Integer.parseInt(keys.split("_")[0]),
                Integer.parseInt(keys.split("_")[1])
        };
    }

    private List<String> findPath(final String[][] track, final int[] startPosition) {
        HashSet<String> visited = new HashSet<>();
        long[][] distances = new long[track.length][track[0].length];
        HashMap<String, ArrayList<String>> paths = new HashMap<>();
        int[] endPosition = null;
        for (int i = 0; i < track.length; i += 1) {
            for (int j = 0; j < track[i].length; j += 1) {
                distances[i][j] = Long.MAX_VALUE;
                if ("E".equals(track[i][j])) {
                    endPosition = new int[] {i, j};
                }
            }
        }
        if (startPosition == null) {
            return null;
        }
        PriorityQueue<int[]> unvisited = new PriorityQueue<>(Comparator.comparingLong(a -> distances[a[0]][a[1]]));
        distances[startPosition[0]][startPosition[1]] = 0L;
        ArrayList<String> startList = new ArrayList<>();
        startList.add(asKey(startPosition));
        paths.put(asKey(startPosition), startList);
        unvisited.add(new int[]{startPosition[0], startPosition[1]});
        while (!unvisited.isEmpty()) {
            int[] thisByte = unvisited.poll();
            if (!visited.contains(asKey(thisByte))) {
                long currentDistance = distances[thisByte[0]][thisByte[1]];
                next(thisByte, track).forEach(position -> {
                    if (distances[position[0]][position[1]] > currentDistance + 1) {
                        distances[position[0]][position[1]] = currentDistance + 1;
                        ArrayList<String> newList = new ArrayList<>(paths.get(asKey(thisByte)));
                        newList.add(asKey(position));
                        paths.put(asKey(position), newList);
                    }
                    unvisited.add(position);
                });
                visited.add(asKey(thisByte));
            }
        }
        if (endPosition == null) {
            return null;
        }
        return paths.get(asKey(endPosition));
    }

    private String asKey(final int[] position) {
        return (position[0] + "_" +  position[1]);
    }

    private List<int[]> next(final int[] currentPosition, final String[][] maze) {
        return Arrays.stream(new int[][] {
                        new int[]{currentPosition[0] - 1, currentPosition[1]},
                        new int[]{currentPosition[0], currentPosition[1] + 1},
                        new int[]{currentPosition[0] + 1, currentPosition[1]},
                        new int[]{currentPosition[0], currentPosition[1] - 1}
                })
                .filter(position -> position[0] >= 0 && position[0] < maze.length
                        && position[1] >= 0 && position[1] < maze[0].length
                        && (".".equals(maze[position[0]][position[1]])
                         || "E".equals(maze[position[0]][position[1]])
                         || "S".equals(maze[position[0]][position[1]]))
                ).toList();
    }
}
