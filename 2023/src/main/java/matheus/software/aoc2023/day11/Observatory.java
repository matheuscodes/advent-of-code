package matheus.software.aoc2023.day11;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class Observatory {

    public long sumLengths(final String raw, final long expansion) {
        List<String> universe = readAndExpandUniverse(raw);
        List<Galaxy> galaxies = new ArrayList<>();

        long ids = 1;
        long eI = 0;
        for (int i = 0; i < universe.size(); i++) {
            if (universe.get(i).replaceAll("e", "").length() == 0) {
                eI += 1;
            } else {
                char[] line = universe.get(i).toCharArray();
                long eJ = 0;
                for (int j = 0; j < line.length; j++) {
                    if (line[j] == '#') {
                        galaxies.add(
                            new Galaxy(
                                ids++,
                                i + (eI > 0 ? eI * expansion : 0L),
                                j + (eJ > 0 ? eJ * expansion : 0L)
                            )
                        );
                    } else if (line[j] == 'e') {
                        eJ += 1;
                    }
                }
            }

        }

        List<Galaxy.GalaxyPair> pairs = new ArrayList<>();
        for (int i = 0; i < galaxies.size(); i++) {
            for (int j = i + 1; j < galaxies.size(); j++) {
                pairs.add(
                        new Galaxy.GalaxyPair(galaxies.get(i), galaxies.get(j))
                );
            }
        }

        return pairs.stream()
                .map(Galaxy.GalaxyPair::getLength)
                .reduce(Long::sum)
                .orElseThrow();
    }

    private List<String> readAndExpandUniverse(final String raw) {
        String[] compressed = raw.split("\\n");
        List<String> decompressed = new ArrayList<>();
        HashSet<Integer> columnsToDecompress = new HashSet<>();
        char[] firstRow = compressed[0].toCharArray();
        for (int i = 0; i < firstRow.length; i++) {
            boolean foundGalaxy = false;
            for (String c: compressed) {
                if (c.charAt(i) != '.') {
                    foundGalaxy = true;
                    break;
                }
            }
            if (!foundGalaxy) {
                columnsToDecompress.add(i);
            }
        }
        for (String rawLine: compressed) {
            List<Character> compose = new ArrayList<>();
            char[] chars = rawLine.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (columnsToDecompress.contains(i)) {
                    compose.add('e');
                } else {
                    compose.add(chars[i]);
                }
            }
            String line = compose.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining());
            if (line.replaceAll("[.e]", "").length() == 0) {
                    decompressed.add(line.replaceAll("\\.", "e"));
            } else {
                decompressed.add(line);
            }
        }
        return decompressed;
    }
}
