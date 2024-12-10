package matheus.software.aoc2024.day10;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
public final class TopographicMap {

    public long countHikingTrails(final String raw) {
        return new HashSet<>(findingHikingTrails(raw)).size();
    }

    public long ratingHikingTrails(final String raw) {
        return findingHikingTrails(raw).size();
    }

    public ArrayList<String> findingHikingTrails(final String raw) {
        String[] lines = raw.replaceAll("\\n\\n", "").split("\\n");
        Integer[][] map = new Integer[lines.length][];
        int row = 0;
        for (String line: lines) {
            map[row++] = Arrays.stream(line.split(""))
                    .map(c -> ".".equals(c) ? -1 : Integer.parseInt(c))
                    .toArray(Integer[]::new);
        }
        ArrayList<String> found = new ArrayList<>();
        for (int i = 0; i < map.length; i += 1) {
            for (int j = 0; j < map[i].length; j += 1) {
                if (map[i][j] == 0) {
                    int[] trailhead = new int[]{i, j};
                    hikingRoutes(map, trailhead, trailhead, found);
                }
            }
        }
        return found;
    }

    private void hikingRoutes(final Integer[][] map, final int[] current, final int[] start, final ArrayList<String> found) {
        if (map[current[0]][current[1]] == 9) {
            found.add(start[0] + "_" + start[1] + "+" + current[0] + "_" + current[1]);
        }
        for (int[] next: nextStep(map, current)) {
            hikingRoutes(map, next, start, found);
        }
    }

    private List<int[]> nextStep(final Integer[][] map, final int[] trailhead) {
        List<int[]> steps = Arrays.asList(
            new int[]{trailhead[0] + 1, trailhead[1]},
            new int[]{trailhead[0], trailhead[1] + 1},
            new int[]{trailhead[0] - 1, trailhead[1]},
            new int[]{trailhead[0], trailhead[1] - 1}
        );
        final int currentValue = map[trailhead[0]][trailhead[1]];
        return steps.stream()
                .filter(p -> p[0] >= 0 && p[1] >= 0)
                .filter(p -> p[0] < map.length && p[1] < map[p[0]].length)
                .filter(p -> map[p[0]][p[1]] - currentValue == 1)
                .toList();
    }
}
