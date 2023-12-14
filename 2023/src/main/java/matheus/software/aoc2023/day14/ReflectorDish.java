package matheus.software.aoc2023.day14;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

import static java.lang.String.join;

@Component
public final class ReflectorDish {
    public long totalLoad(final String raw) {
        String[][] dish = Arrays.stream(raw.split("\n"))
                .map(i -> i.split(""))
                .toArray(String[][]::new);

        tiltNorth(dish);

        return load(dish);
    }

    public long totalLoadAfterCycles(final String raw, final long cycles) {
        String[][] dish = Arrays.stream(raw.split("\n"))
                .map(i -> i.split(""))
                .toArray(String[][]::new);

        HashMap<String, Long> known = new HashMap<>();
        for (long i = 0; i < cycles; i++) {
            String merged = merge(dish);
            if (known.get(merged) != null) {
                long lastIteration = known.get(merged);
                long nextIteration = i + (i - lastIteration);
                if (nextIteration < cycles) {
                    i = nextIteration;
                }
            }
            known.put(merged, i);

            tiltNorth(dish);
            tiltWest(dish);
            tiltSouth(dish);
            tiltEast(dish);
        }

        return load(dish);
    }

    private String merge(final String[][] dish) {
        return Arrays.stream(dish)
                .map(i -> join("", i) + "\n")
                .collect(Collectors.joining());
    }

    private void tiltNorth(final String[][] dish) {
        boolean moving;
        do {
            moving = moveRocks(dish, new int[]{-1, 0});
        } while (moving);
    }

    private void tiltSouth(final String[][] dish) {
        boolean moving;
        do {
            moving = moveRocks(dish, new int[]{1, 0});
        } while (moving);
    }

    private void tiltEast(final String[][] dish) {
        boolean moving;
        do {
            moving = moveRocks(dish, new int[]{0, 1});
        } while (moving);
    }

    private void tiltWest(final String[][] dish) {
        boolean moving;
        do {
            moving = moveRocks(dish, new int[]{0, -1});
        } while (moving);
    }

    private boolean moveRocks(final String[][] dish, final int[] tilt) {
        boolean moved = false;
        for (int i = 0; i < dish.length; i++) {
            for (int j = 0; j < dish[i].length; j++) {
                int[] next = new int[]{i + tilt[0], j + tilt[1]};
                if ("O".equals(dish[i][j]) && canMove(dish, next)) {
                    dish[i][j] = ".";
                    dish[next[0]][next[1]] = "O";
                    moved = true;
                }
            }
        }
        return moved;
    }

    private boolean canMove(final String[][] dish, final int[] next) {
        if (next[0] < 0 || next[1] < 0) {
            return false;
        }
        if (next[0] >= dish.length || next[1] >= dish[0].length) {
            return false;
        }
        return ".".equals(dish[next[0]][next[1]]);
    }

    private long load(final String[][] dish) {
        long load = 0L;
        for (int i = 0; i < dish.length; i++) {
            for (int j = 0; j < dish[i].length; j++) {
                if ("O".equals(dish[i][j])) {
                    load += dish.length - i;
                }
            }
        }
        return load;
    }
}
