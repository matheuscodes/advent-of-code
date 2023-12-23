package matheus.software.aoc2023.day23;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
public final class HikingTrail {
    public long bruteForce(final String raw, final boolean slippery) {
        var lines = raw.split("\\n");
        String[][] forest = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            forest[i] = lines[i].split("");
        }
        var start = new Point(0, 1);
        var end = new Point(forest.length - 1, forest[0].length - 2);

        var queue = new LinkedList<LinkedList<Point>>();
        LinkedList<Point> longest = null;
        queue.add(new LinkedList<>(List.of(start)));
        while (!queue.isEmpty()) {
            queue.sort(Comparator.comparingInt(i -> -i.size()));
            var first = queue.poll();
            assert first != null;
            if (first.getLast().equals(end)
                    && (longest == null || longest.size() < first.size())) {
                longest = first;
            }
            if (!first.getLast().equals(end)) {
                first.getLast().getNext(forest, slippery).forEach(next -> {
                    if (!first.contains(next)) {
                        var newPath = new LinkedList<>(first);
                        newPath.add(next);
                        queue.add(newPath);
                    }
                });
            }
        }

        return longest != null ? longest.size() - 1 : 0L;
    }

    public long countLongestPathSteps(
            final String raw,
            final boolean slippery
    ) {
        var lines = raw.split("\\n");
        String[][] forest = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            forest[i] = lines[i].split("");
        }
        var start = new Point(0, 1);
        var end = new Point(forest.length - 1, forest[0].length - 2);

        return depthFirstSearch(
                buildGraph(forest, slippery),
                start, end,
                new HashMap<>()
        );
    }

    private long depthFirstSearch(
            final HashMap<Point, HashMap<Point, Integer>> graph,
            final Point start,
            final Point end,
            final HashMap<Point, Integer> visited
    ) {
        if (start.equals(end)) {
            return visited.values().stream().reduce(0, Integer::sum);
        }

        // TODO: Ugly AF. Find a better way to update.
        var candidate = new HashMap<String, Long>();
        candidate.put("best", -1L);
        graph.get(start).forEach((neighbor, steps) -> {
            if (!visited.containsKey(neighbor)) {
                visited.put(neighbor, steps);
                var res = depthFirstSearch(graph, neighbor, end, visited);
                if (candidate.get("best") == null
                        || res > candidate.get("best")) {
                    candidate.put("best", res);
                }
                visited.remove(neighbor);
            }
        });
        return candidate.get("best");
    }

    private HashMap<Point, HashMap<Point, Integer>> buildGraph(
            final String[][] forest,
            final boolean slippery
    ) {
        HashMap<Point, HashMap<Point, Integer>> graph = new HashMap<>();
        for (int r = 0; r < forest.length; r++) {
            for (int c = 0; c < forest[r].length; c++) {
                if (!"#".equals(forest[r][c])) {
                    var point = new Point(r, c);
                    var next = new HashMap<Point, Integer>();
                    graph.put(point, next);
                    point.getNext(forest, slippery)
                            .forEach(i -> next.put(i, 1));
                }
            }
        }

        var list = graph.keySet().stream().toList();
        list.forEach(key -> {
            var neighbors = graph.get(key);
            if (neighbors.size() == 2) {
                var keys = neighbors.keySet().stream().toList();
                var left = keys.get(0);
                var right = keys.get(1);
                var totalSteps = neighbors.get(left) + neighbors.get(right);
                graph.get(left).merge(right, totalSteps, Integer::max);
                graph.get(right).merge(left, totalSteps, Integer::max);
                graph.get(left).remove(key);
                graph.get(right).remove(key);
                graph.remove(key);
            }
        });

        return graph;
    }

    @Data
    public static final class Point {
        private int r;
        private int c;

        public Point(final int thisI, final int thisJ) {
            this.r = thisI;
            this.c = thisJ;
        }

        @Override
        public String toString() {
            return r + "," + c;
        }

        @Override
        public boolean equals(final Object o) {
            if (o instanceof Point) {
                return ((HikingTrail.Point) o).r == this.r
                        && ((HikingTrail.Point) o).c == this.c;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }

        public List<Point> getNext(
                final String[][] forest,
                final boolean slippery
        ) {
            var points = new LinkedList<Point>();
            switch (forest[r][c]) {
                case "." -> {
                    points.add(new Point(r - 1, c));
                    points.add(new Point(r + 1, c));
                    points.add(new Point(r, c + 1));
                    points.add(new Point(r, c - 1));
                }
                case ">" -> {
                    points.add(new Point(r, c + 1));
                    if (!slippery) {
                        points.add(new Point(r - 1, c));
                        points.add(new Point(r + 1, c));
                        points.add(new Point(r, c - 1));
                    }
                }
                case "^" -> {
                    points.add(new Point(r - 1, c));
                    if (!slippery) {
                        points.add(new Point(r + 1, c));
                        points.add(new Point(r, c + 1));
                        points.add(new Point(r, c - 1));
                    }
                }
                case "<" -> {
                    points.add(new Point(r, c - 1));
                    if (!slippery) {
                        points.add(new Point(r - 1, c));
                        points.add(new Point(r + 1, c));
                        points.add(new Point(r, c + 1));
                    }
                }
                case "v" -> {
                    points.add(new Point(r + 1, c));
                    if (!slippery) {
                        points.add(new Point(r - 1, c));
                        points.add(new Point(r, c + 1));
                        points.add(new Point(r, c - 1));
                    }
                }
                default ->
                        throw new RuntimeException("Impossible.");
            }
            return points.stream()
                    .filter(i -> i.r >= 0 && i.r < forest.length)
                    .filter(i -> i.c >= 0 && i.c < forest[0].length)
                    .filter(i -> !"#".equals(forest[i.r][i.c])).toList();
        }
    }
}
