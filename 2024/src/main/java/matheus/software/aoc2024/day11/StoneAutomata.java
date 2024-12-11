package matheus.software.aoc2024.day11;

import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public final class StoneAutomata {

    public long watchAndBlink(final String raw, final long times) {
        String[] stones = raw.replaceAll("\\n", "").split(" ");
        long count = 0;
        for (String stone: stones) {
            count += countStonesAfter(stone, 0, times, new HashMap<>());
        }
        return count;
    }

    private long countStonesAfter(
            final String stone,
            final long blinks,
            final long times,
            final HashMap<String, Long> cache) {
        String key = stone + "_" + blinks + "_" + times;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        if (blinks == times) {
            return 1;
        }

        long value;
        if ("0".equals(stone)) {
           value = countStonesAfter("1", blinks + 1, times, cache);
        } else if (stone.length() % 2 == 0) {
            String split1 = Long.toString(
                    Long.parseLong(
                            stone.substring(0, stone.length() / 2)
                    )
            );
            String split2 = Long.toString(
                    Long.parseLong(
                            stone.substring(stone.length() / 2)
                    )
            );
            value = countStonesAfter(split1, blinks + 1, times, cache)
                   + countStonesAfter(split2, blinks + 1, times, cache);
        } else {
            value = countStonesAfter(
                    (Long.parseLong(stone) * 2024) + "",
                    blinks + 1,
                    times,
                    cache);
        }

        cache.put(key, value);
        return value;
    }
}
