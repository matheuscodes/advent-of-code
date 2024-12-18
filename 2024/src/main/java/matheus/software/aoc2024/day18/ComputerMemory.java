package matheus.software.aoc2024.day18;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

@Component
public final class ComputerMemory {
    public long safePath(final String raw, final int size, final int bytes) {
        String[][] memory = new String[size][size];
        long[][] distances = new long[size][size];
        for (int i = 0; i < size; i += 1) {
            for (int j = 0; j < size; j += 1) {
                memory[i][j] = ".";
                distances[i][j] = Long.MAX_VALUE;
            }
        }
        String[] lines = raw.split("\\n");
        int count = 0;
        for (String line: lines) {
            if (++count > bytes) {
                break;
            }
            String[] split = line.split(",");
            memory[Integer.parseInt(split[1])][Integer.parseInt(split[0])] = "#";
        }

        PriorityQueue<int[]> unvisited = new PriorityQueue<>(Comparator.comparingLong(a -> distances[a[0]][a[1]]));
        distances[size - 1][size - 1] = 0L;
        unvisited.add(new int[]{size - 1, size - 1});
        HashSet<String> visited = new HashSet<>();
        while (!unvisited.isEmpty()) {
            int[] thisByte = unvisited.poll();
            if (!visited.contains(asKey(thisByte))) {
                long currentDistance = distances[thisByte[0]][thisByte[1]];
                next(thisByte, memory).forEach(position -> {
                    distances[position[0]][position[1]] = Math.min(distances[position[0]][position[1]], currentDistance + 1);
                    unvisited.add(position);
                });
                visited.add(asKey(thisByte));
            }
        }

        return distances[0][0];
    }

    public String findCutOff(final String raw, final int size) {
        String[] lines = raw.split("\\n");
        int left = 0;
        int right = lines.length - 1;
        while (left < right) {
            int middle = (left + right) / 2;
            long distance = safePath(raw, size, middle);
            if (distance == Long.MAX_VALUE) {
                right = middle - 1;
            } else {
                left = middle + 1;
            }
        }
        return lines[left];
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
                        && ".".equals(maze[position[0]][position[1]])
                ).toList();
    }
}
