package matheus.software.aoc2023.day13;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
public final class ReflectionFinder {
    public long summarizeSmugeless(
            final String raw,
            final boolean cleanSmudge
    ) {
        String[] patterns = raw.split("\\n\\n");

        long count = 0;
        for (String patternRaw: patterns) {
            String[] pattern = patternRaw.split("\\n");
            count += calculateValue(pattern, cleanSmudge);
        }

        return count;
    }

    private long calculateValue(
            final String[] pattern,
            final boolean cleanSmudge
    ) {
        long horizontal = countRowsAboveMirror(pattern, cleanSmudge);
        String[] antipattern = transpose(pattern);
        long vertical = countRowsAboveMirror(antipattern, cleanSmudge);
        if (horizontal > 0) {
            return horizontal * 100L;
        } else {
            return vertical;
        }
    }

    private String[] transpose(final String[] origin) {
        List<String>[] transposed = new List[origin[0].length()];
        for (int i = 0; i < origin[0].length(); i++) {
            transposed[i] = new ArrayList<>();
        }
        for (String s: origin) {
            String[] chars = s.split("");
            for (int i = 0; i < chars.length; i++) {
                transposed[i].add(chars[i]);
            }
        }
        String[] transposedFinal = new String[transposed.length];
        for (int i = 0; i < transposed.length; i++) {
            transposedFinal[i] = String.join("", transposed[i]);
        }
        return transposedFinal;
    }

    private long countRowsAboveMirror(
            final String[] pattern,
            final boolean cleanSmudge
    ) {
        List<int[]> potentials = findPotentials(pattern, cleanSmudge).stream()
                .filter(potential -> isMirror(potential, pattern, cleanSmudge))
                .sorted(Comparator.comparingInt(a -> a[0]))
                .toList();
        if (potentials.size() > 0) {
            return potentials.get(0)[1];
        }
        return 0L;
    }

    private boolean isMirror(
            final int[] start,
            final String[] pattern,
            final boolean cleanSmudge
    ) {
        int min = Math.min(start[0], pattern.length - start[1] - 1);
        boolean cleanedSmugde = false;
        for (int i = 0; i <= min; i++) {
            int thisDistance = EditDistanceRecursive
                    .calculate(pattern[start[0] - i], pattern[start[1] + i]);
            if (thisDistance > 0) {
                if (thisDistance == 1 && !cleanedSmugde && cleanSmudge) {
                    cleanedSmugde = true;
                } else {
                    return false;
                }
            }
        }
        return cleanedSmugde || !cleanSmudge;
    }

    private List<int[]> findPotentials(
            final String[] pattern,
            final boolean cleanSmudge
    ) {
        if (pattern.length < 2) {
            return Collections.emptyList();
        }
        List<int[]> potentials = new ArrayList<>();
        for (int i = 0; i < pattern.length - 1; i++) {
            int thisDistance = EditDistanceRecursive
                    .calculate(pattern[i], pattern[i + 1]);
            if (thisDistance == 0) {
                potentials.add(new int[] {i, i + 1});
            } else if (thisDistance == 1 && cleanSmudge) {
                potentials.add(new int[] {i, i + 1});
            }
        }
        return potentials;
    }

}
