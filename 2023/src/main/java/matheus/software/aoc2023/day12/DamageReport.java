package matheus.software.aoc2023.day12;

import org.javatuples.Triplet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

@Component
public final class DamageReport {

    public long sumReconstructions(final String raw) {
        String[] lines = raw.split("\\n");

        List<Long> processed = Arrays.stream(lines).parallel().map(line -> {
            String[] split = line.split("\\s");
            List<String> allCombinations = new ArrayList<>();
            String checksum = split[1];
            List<Long> realChecksum = Arrays.stream(checksum.split(","))
                    .map(Long::parseLong).toList();
            smartRecursiveCombinations(
                    split[0],
                    0,
                    allCombinations,
                    realChecksum
            );
            return (long) allCombinations.stream()
                    .filter(i -> fullChecksum(partialChecksum(i), realChecksum))
                    .toList()
                    .size();
        }).toList();

        waitFor(processed, lines.length);

        return processed.stream().reduce(Long::sum).orElseThrow();
    }

    public long sumUnfoldedReconstructions(final String raw) {
        String[] lines = raw.split("\\n");

        List<Long> processed = Arrays.stream(lines).parallel().map(line -> {
            final String[] split = line.split("\\s");
            String checksum = unfold(split[1], ",");
            final List<Integer> realChecksum = Arrays
                    .stream(checksum.split(","))
                    .map(Integer::parseInt).toList();
            return countArrangements(
                    new HashMap<>(),
                    unfold(split[0], "?"),
                    realChecksum.stream().mapToInt(i -> i).toArray(),
                    0,
                    0,
                    0
            );
        }).toList();

        waitFor(processed, lines.length);

        return processed.stream().reduce(Long::sum).orElseThrow();
    }

    private void waitFor(final List processed, final int size) {
        // TODO Find better way to wait the collections above.
        while (processed.size() < size) {
            try {
                System.out.println("waiting...");
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String unfold(final String s, final String separator) {
        return s + separator
                + s + separator
                + s + separator
                + s + separator
                + s;
    }

    public long countArrangements(
            final HashMap<Triplet<Integer, Integer, Integer>, Long> blockMap,
            final String map,
            final int[] amounts,
            final int i,
            final int j,
            final int cur
    ) {
        /*
         Original: https://github.com/SimonBaars/AdventOfCode-Java/blob/master
         /src/main/java/com/sbaars/adventofcode/year23/days/Day12.java#L53-L73
         Not sure I understand how this works. TODO: study.
        */
        var key = new Triplet<>(i, j, cur);
        if (blockMap.containsKey(key)) {
            return blockMap.get(key);
        }
        if (i == map.length()) {
            return (j == amounts.length && cur == 0)
                    || (j == amounts.length - 1 && amounts[j] == cur) ? 1 : 0;
        }
        long total = 0;
        char c = map.charAt(i);
        if ((c == '.' || c == '?') && cur == 0) {
            total += countArrangements(blockMap, map, amounts, i + 1, j, 0);
        } else if ((c == '.' || c == '?')
                && cur > 0
                && j < amounts.length && amounts[j] == cur) {
            total += countArrangements(blockMap, map, amounts, i + 1, j + 1, 0);
        }
        if (c == '#' || c == '?') {
            total += countArrangements(
                    blockMap,
                    map,
                    amounts,
                    i + 1,
                    j,
                    cur + 1
            );
        }
        blockMap.put(key, total);
        return total;
    }

    private void smartRecursiveCombinations(
            final String current,
            final int position,
            final List<String> allCombinations,
            final List<Long> checksum
    ) {
        if (!partialChecksumCheck(current, checksum)) {
            return;
        }
        if (position < current.length()) {
            if (current.charAt(position) == '?') {
                smartRecursiveCombinations(
                        current.replaceFirst("\\?", "#"),
                        position + 1,
                        allCombinations,
                        checksum
                );
                smartRecursiveCombinations(
                        current.replaceFirst("\\?", "."),
                        position + 1,
                        allCombinations,
                        checksum
                );
            } else {
                smartRecursiveCombinations(
                        current,
                        position + 1,
                        allCombinations,
                        checksum
                );
            }
        } else {
            allCombinations.add(current);
        }
    }

    private List<Long> partialChecksum(final String item) {
        List<Long> groups = new ArrayList<>();
        for (int i = 0; i < item.length(); i++) {
            if (item.charAt(i) == '?') {
                break;
            }
            if (item.charAt(i) == '#') {
                long size = 0;
                while (i < item.length() && item.charAt(i) == '#') {
                    size += 1;
                    i += 1;
                }
                if (i < item.length() && item.charAt(i) != '?') {
                    groups.add(size);
                } else if (i == item.length()) {
                    groups.add(size);
                }
                i -= 1;
            }
        }
        return groups;
    }

    private boolean partialChecksumCheck(
            final String item,
            final List<Long> checksums
    ) {
        int current = 0;
        for (int i = 0; i < item.length(); i++) {
            if (item.charAt(i) == '?') {
                break;
            }
            if (item.charAt(i) == '#') {
                long size = 0;
                while (i < item.length() && item.charAt(i) == '#') {
                    size += 1;
                    i += 1;
                }
                if ((i < item.length() && item.charAt(i) != '?')
                        || (i == item.length())) {
                    if (current >= checksums.size()) {
                        return false;
                    }
                    if (checksums.get(current++) != size) {
                        return false;
                    }
                }
                i -= 1;
            }
        }
        return true;
    }

    private boolean withinChecksum(final List<Long> from, final List<Long> to) {
        if (from.size() > to.size()) {
            return false;
        }
        for (int i = 0; i < from.size(); i++) {
            if (!from.get(i).equals(to.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean fullChecksum(final List<Long> from, final List<Long> to) {
        return from.size() == to.size() && withinChecksum(from, to);
    }
}
