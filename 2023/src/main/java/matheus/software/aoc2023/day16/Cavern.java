package matheus.software.aoc2023.day16;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import static matheus.software.aoc2023.day16.Ray.Direction.RIGHT;
import static matheus.software.aoc2023.day16.Ray.Direction.LEFT;
import static matheus.software.aoc2023.day16.Ray.Direction.UP;
import static matheus.software.aoc2023.day16.Ray.Direction.DOWN;

@Component
public final class Cavern {
    public long tilesEnergized(
            final String raw,
            final int[] start,
            final Ray.Direction direction
    ) {
        String[] lines = raw.split("\\n");
        String[][] cavern = new String[lines.length][];
        long[][] tiles = new long[lines.length][];
        for (int i = 0; i < lines.length; i++) {
            cavern[i] = lines[i].split("");
            tiles[i] = new long[cavern[i].length];
            Arrays.fill(tiles[i], 0);
        }
        Stack<Ray> beams = new Stack<>();
        beams.push(new Ray(start, direction));
        HashMap<String, List<Ray.Direction>> pastBeams = new HashMap<>();
        while (beams.size() > 0) {
            Ray current = beams.pop();
            int[] position = current.getPosition();
            if (position[0] >= 0
                    && position[0] < tiles.length
                    && position[1] >= 0
                    && position[1] < tiles[position[0]].length) {
                String keyHash = position[0] + "_" + position[1];
                pastBeams.computeIfAbsent(keyHash, k -> new ArrayList<>());
                var past = pastBeams.get(keyHash);
                var tile = cavern[position[0]][position[1]];
                switch (tile) {
                    case "\\", "/", "|", "-" -> {
                        tiles[position[0]][position[1]] += 1;
                        List<Ray> next = current.split(tile);
                        next.forEach(i -> {
                            if (!past.contains(i.getDirection())) {
                                i.move();
                                beams.push(i);
                                past.add(i.getDirection());
                            }
                        });
                    }
                    default -> {
                        tiles[position[0]][position[1]] += 1;
                        if (!past.contains(current.getDirection())) {
                            current.move();
                            beams.push(current);
                            past.add(current.getDirection());
                        }
                    }
                }
            }
        }

        long sum = 0;
        for (long[] line: tiles) {
            for (long tile: line) {
                sum += tile > 0 ? 1 : 0;
            }
        }
        return sum;
    }

    public long maxEnergy(final String raw) {
        String[] lines = raw.split("\\n");
        List<Long> borders = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            int columns = lines[i].split("").length;
            borders.add(tilesEnergized(raw, new int[]{i, 0}, RIGHT));
            borders.add(tilesEnergized(raw, new int[]{i, columns - 1}, LEFT));
        }
        int maxLine = lines[0].split("").length;
        for (int i = 0; i < maxLine; i++) {
            int[] top = new int[]{0, i};
            borders.add(tilesEnergized(raw, top, DOWN));
        }
        for (int i = 0; i < maxLine; i++) {
            int[] bottom = new int[]{lines.length - 1, i};
            borders.add(tilesEnergized(raw, bottom, UP));
        }

        return borders.stream().reduce(Long::max).orElseThrow();
    }
}
