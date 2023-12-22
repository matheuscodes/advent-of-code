package matheus.software.aoc2023.day21;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
public final class Garden {
    public long countPlots(final String raw, final int steps) {
        var rest = steps % 2;
        var lines = raw.split("\\n");
        var garden = new String[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            garden[i] = lines[i].split("");
        }

        Point start = replaceS(garden);
        Map<Point, Integer> distances = new HashMap<>();
        List<Point> reachablePoints = new LinkedList<>();
        reachablePoints.add(start);
        distances.put(start, 0);
        List<Long> totals = new ArrayList<>();
        List<Long> deltas = new ArrayList<>();
        List<Long> deltaDeltas = new ArrayList<>();
        long totalReached = 0;
        int index = 0;
        while (index < steps) {
            index++;
            List<Point> nextPoints = new ArrayList<>();
            for (Point c : reachablePoints) {
                var thisIndex = index;
                c.getNeighbours().forEach(candidate -> {
                    if (distances.get(candidate) == null) {
                        if (isFree(candidate, garden)) {
                            nextPoints.add(candidate);
                            distances.put(candidate, thisIndex);
                        }
                    }
                });
            }
            if (index % 2 == rest) {
                totalReached += nextPoints.size();
                if (index % 262 == 65) {
                    totals.add(totalReached);
                    int currTotals = totals.size();
                    if (currTotals > 1) {
                        deltas.add(
                            totals.get(currTotals - 1)
                                    - totals.get(currTotals - 2)
                        );
                    }
                    int currDeltas = deltas.size();
                    if (currDeltas > 1) {
                        deltaDeltas.add(
                                deltas.get(currDeltas - 1)
                                        - deltas.get(currDeltas - 2)
                        );
                    }
                    if (deltaDeltas.size() > 1) {
                        break;
                    }
                }

            }
            reachablePoints = nextPoints;
        }


        // Solution based on @abnew123's
        // github.com/abnew123/aoc2023/blob/main/src/solutions/Day21.java
        // Worked upon it to generalize.
        // Original only worked for final answer.
        long neededLoopCount = steps / 262 - 1;
        long currentLoopCount = index / 262 - 1;
        long deltaLoopCount = neededLoopCount - currentLoopCount;
        long deltaLoopCountTriangular =
                (neededLoopCount * (neededLoopCount + 1)) / 2
                - (currentLoopCount * (currentLoopCount + 1)) / 2;
        long deltaDelta = !deltaDeltas.isEmpty()
                ? deltaDeltas.get(deltaDeltas.size() - 1)
                : 0;
        long initialDelta = !deltas.isEmpty()
                ? deltas.get(0)
                : 0;
        long thisTotalReached = distances.values().stream()
                .filter(i -> i % 2 == rest).count();
        return deltaDelta * deltaLoopCountTriangular
                + initialDelta * deltaLoopCount
                + thisTotalReached;
    }

    private boolean isFree(final Point p, final String[][] garden) {
        var multipleRow = ((p.r() % garden.length) + garden.length)
                % garden.length;
        var multipleColumn = ((p.c() % garden[0].length) + garden[0].length)
                % garden[0].length;
        return ".".equals(garden[multipleRow][multipleColumn]);
    }

    private Point replaceS(final String[][] garden) {
        for (int r = 0; r < garden.length; r++) {
            for (int c = 0; c < garden.length; c++) {
                if ("S".equals(garden[r][c])) {
                    garden[r][c] = ".";
                    return new Point(r, c);
                }
            }
        }
        return null;
    }

    private record Point(int r, int c) {

        List<Point> getNeighbours() {
            var points = new LinkedList<Point>();
            points.add(new Point(r - 1, c));
            points.add(new Point(r + 1, c));
            points.add(new Point(r, c + 1));
            points.add(new Point(r, c - 1));
            return points;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", r, c);
        }
    }
}
