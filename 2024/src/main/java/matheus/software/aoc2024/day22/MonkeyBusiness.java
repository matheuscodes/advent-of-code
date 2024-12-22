package matheus.software.aoc2024.day22;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@Component
public final class MonkeyBusiness {
    public long secretNumbers(final String raw, final int next) {
        String[] secrets = raw.split("\\n");
        long sum = 0;
        for (String secret: secrets) {
            long longSecret = Long.parseLong(secret);
            for (int i = 0; i < next; i += 1) {
                longSecret = nextSecret(longSecret);
            }
            sum += longSecret;
        }
        return sum;
    }

    private long nextSecret(final long secret) {
        long newSecret = mix(secret, secret * 64);
        newSecret = prune(newSecret);
        newSecret = mix(newSecret, newSecret / 32);
        newSecret = prune(newSecret);
        newSecret = mix(newSecret, newSecret * 2048);
        return prune(newSecret);
    }

    private long mix(final long secret, final long number) {
        return secret ^ number;
    }

    private long prune(final long secret) {
        return secret % 16777216L;
    }

    public long bestPriceChange(final String raw, final int next) {
        String[] secrets = raw.split("\\n");
        HashMap<String, HashMap<String, Integer>> secretSequences = new HashMap<>();
        for (String secret: secrets) {
            ArrayList<Integer> changes = new ArrayList<>();
            HashMap<String, Integer> sequences = new HashMap<>();
            secretSequences.put(secret, sequences);
            long longSecret = Long.parseLong(secret);
            for (int i = 0; i < next; i += 1) {
                if (changes.size() > 4) {
                    changes = new ArrayList<>(changes.subList(1, changes.size()));
                }
                changes.add((int) (longSecret % 10));
                if (i >= 5) {
                    String changeKey = (changes.get(1) - changes.get(0)) + ","
                            + (changes.get(2) - changes.get(1)) + ","
                            + (changes.get(3) - changes.get(2)) + ","
                            + (changes.get(4) - changes.get(3));
                    sequences.putIfAbsent(changeKey, (int) (longSecret % 10));
                }
                longSecret = nextSecret(longSecret);
            }
        }
        HashMap<String, Integer> commonSequencesSum = new HashMap<>();
        secretSequences.values().forEach(map -> map.forEach((key, value) -> commonSequencesSum.put(key, commonSequencesSum.getOrDefault(key, 0) + value)));
        return commonSequencesSum.entrySet().stream().sorted(Comparator.comparingLong(a -> -a.getValue())).toList().get(0).getValue();
    }
}
