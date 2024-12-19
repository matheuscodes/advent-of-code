package matheus.software.aoc2024.day19;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public final class OnsenTowels {
    public long possibleCombinedTowels(final String raw) {
        return combine(raw).values().stream().filter(a -> a > 0).count();
    }

    public long allTowelCombinations(final String raw) {
        return combine(raw).values().stream().reduce(0L, Long::sum);
    }

    private HashMap<String, Long> combine(final String raw) {
        String[] split = raw.split("\\n\\n");
        String[] towels = split[0].replaceAll(" ", "").split(",");
        String[] possibleCombinations = split[1].split("\\n");
        HashMap<String, Long> combinations = new HashMap<>();
        HashMap<String, Long> cache = new HashMap<>();
        for (String possibleCombination: possibleCombinations) {
            combinations.put(possibleCombination, countCombinations(towels, possibleCombination, cache));
        }
        return combinations;

    }

    private long countCombinations(final String[] towels, final String possibleCombination, final HashMap<String, Long> cache) {
        if (cache.containsKey(possibleCombination)) {
            return cache.get(possibleCombination);
        }
        long count = 0;
        for (String towel: towels) {
            if (possibleCombination.startsWith(towel)) {
                long subcombinations = countCombinations(towels, possibleCombination.substring(towel.length()), cache);
                if (subcombinations > 0) {
                    count += countCombinations(towels, possibleCombination.substring(towel.length()), cache);
                } else if (possibleCombination.equals(towel)) {
                    count += 1;
                }
            }
        }
        cache.put(possibleCombination, count);
        return count;
    }
}
