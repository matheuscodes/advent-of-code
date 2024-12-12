package matheus.software.aoc2024.day12;

import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

@Component
public final class GardenPlot {

    public long fencingPrice(final String raw) {
        return fencing(raw, false);
    }

    public long fencingBulkPrice(final String raw) {
        return fencing(raw, true);
    }

    private long sumPerimeter(final String[][] garden, final Set<Integer[]> region) {
        return region.stream().map(position -> {
            long perimeter = 0L;
            if (position[0] == 0 || position[0] == garden.length - 1) {
                perimeter += 1;
            }
            if (position[1] == 0 || position[1] == garden[position[0]].length - 1) {
                perimeter += 1;
            }
            List<Integer[]> neighbors = getNeighbors(position);
            for (Integer[] i: neighbors) {
                if (inBounds(garden, i) && !same(garden, position, i)) {
                    perimeter += 1;
                }
            }
            return perimeter;
        }).reduce(0L, Long::sum);
    }

    private long countEdges(final String[][] garden, final Set<Integer[]> region) {
        long edgeCount = 0;
        HashSet<String> edges = new HashSet<>();
        LinkedList<Integer[]> items = new LinkedList<>(region);
        while (!items.isEmpty()) {
            Integer[] position = items.removeFirst();
            List<Integer[]> neighbors = getNeighbors(position);
            for (int polarity = 0; polarity < neighbors.size(); polarity += 1) {
                Integer[] neighbor = neighbors.get(polarity);
                if (!inBounds(garden, neighbor)
                    || !same(garden, position, neighbor)) {
                    edgeCount += 1;
                    edges.add(asKey(polarity, neighbor));
                    for (Integer[] n2: getNeighbors(neighbor)) {
                        if (edges.contains(asKey(polarity, n2))) {
                            edgeCount -= 1;
                        }
                    }
                }
            }
        }
        return edgeCount;
    }

    private Set<Integer[]> findRegion(final String[][] garden, final Integer[] head) {
        HashSet<String> found = new HashSet<>();
        LinkedList<Integer[]> toVisit = new LinkedList<>();
        toVisit.add(head);
        while (!toVisit.isEmpty()) {
            Integer[] location = toVisit.removeFirst();
            if (!found.contains(location[0] + "_" + location[1])) {
               found.add(location[0] + "_" + location[1]);
               toVisit.addAll(nextStep(garden, location));
            }
        }
        return found.stream().map(c -> Arrays.stream(c.split("_")).map(Integer::parseInt).toArray(Integer[]::new)).collect(Collectors.toSet());
    }

    private List<Integer[]> nextStep(final String[][] map, final Integer[] head) {
        List<Integer[]> steps = Arrays.asList(
                new Integer[]{head[0] + 1, head[1]},
                new Integer[]{head[0], head[1] + 1},
                new Integer[]{head[0] - 1, head[1]},
                new Integer[]{head[0], head[1] - 1}
        );
        return steps.stream()
                .filter(p -> inBounds(map, p))
                .filter(p -> same(map, head, p))
                .toList();
    }

    private List<Integer[]> getNeighbors(final Integer[] head) {
        return Arrays.asList(
            new Integer[]{head[0] + 1, head[1]},
            new Integer[]{head[0], head[1] + 1},
            new Integer[]{head[0] - 1, head[1]},
            new Integer[]{head[0], head[1] - 1}
        );
    }

    private boolean inBounds(final String[][] map, final Integer[] head) {
        return (head[0] >= 0)
                && (head[1] >= 0)
                && (head[0] <= map.length - 1)
                && (head[1] <= map[head[0]].length - 1);
    }

    private boolean same(final String[][] map, final Integer[] first, final Integer[] second) {
        return map[first[0]][first[1]].equals(map[second[0]][second[1]]);
    }

    private String asKey(final Integer[] point) {
        return point[0] + "_" + point[1];
    }

    private String asKey(final Integer value, final Integer[] point) {
        return value + "_" + asKey(point);
    }

    private long fencing(final String raw, final boolean bulkPrice) {
        String[] lines = raw.split("\n");
        String[][] garden = new String[lines.length][];
        int row = 0;
        for (String line: lines) {
            garden[row++] = line.split("");
        }
        final HashSet<String> visited = new HashSet<>();
        long total = 0;
        for (int i = 0; i < garden.length; i += 1) {
            for (int j = 0; j < garden[i].length; j += 1) {
                if (!visited.contains(i + "_" + j)) {
                    Set<Integer[]> region = findRegion(garden, new Integer[]{i, j});
                    long factor;
                    if (bulkPrice) {
                        factor = countEdges(garden, region);
                    } else {
                        factor = sumPerimeter(garden, region);
                    }
                    total += region.size() * factor;
                    region.forEach(c -> visited.add(c[0] + "_" + c[1]));
                }
            }
        }
        return total;
    }


}
