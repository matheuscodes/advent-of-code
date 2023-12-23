package matheus.software.aoc2023.day17;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

@Component
public final class CityMap {
    public long leastHeatLoss(
            final String raw,
            final int minInARow,
            final int maxInARow
    ) {
        String[] lines = raw.split("\\n");
        Integer[][] cities = new Integer[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            cities[i] = Arrays.stream(lines[i].split(""))
                    .map(Integer::parseInt).toArray(Integer[]::new);
        }

        return findShortestPath(cities, minInARow, maxInARow);
    }



    private long findShortestPath(
            final Integer[][] grid,
            final int minInARow,
            final int maxInARow
    ) {
        HashMap<Location.Direction, Integer>[][] cache =
                new HashMap[grid.length][grid[0].length];
        for (int i = 0; i < cache.length; i++) {
            for (int j = 0; j < cache[0].length; j++) {
                cache[i][j] = new HashMap<>();
                for (Location.Direction d: Location.Direction.values()) {
                    cache[i][j].put(d, Integer.MAX_VALUE);
                }
            }
        }

        long answer = Integer.MAX_VALUE;

        Queue<Location> queue = new PriorityQueue<>();
        Set<Location> visited = new HashSet<>();
        queue.add(new Location(0, 0, null, 0));
        while (queue.size() > 0) {
            Location c = queue.poll();
            visited.add(c);
            if (c.reached(grid.length - 1, grid[0].length - 1)) {
                answer = Math.min(answer, c.getBestDistance());
            }
            for (Location.Direction d: Location.Direction.values()) {
                if (!d.sameAxis(c)) {
                    int path = c.getBestDistance();
                    for (int movement = 1; movement <= maxInARow; movement++) {
                        var location = d.next(grid, c, movement, path);
                        if (location != null) {
                            path = location.getBestDistance();
                            var current =
                                    cache[location.getR()][location.getC()];
                            if (movement >= minInARow
                                    && path < current.get(d)) {
                                current.put(d, location.getBestDistance());
                                if (!visited.contains(location)) {
                                    queue.add(location);
                                }
                            }
                        }
                    }
                }
            }
        }
        return answer;
    }
}
