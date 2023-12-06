package matheus.software.aoc2023.day06;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public final class Speedometer {

    public long errorMargin(final String raw) {
        String[] clean = raw
                .replaceAll("Time:", "")
                .replaceAll("Distance:", "")
                .split("\\n");

        List<Long> times = Arrays.stream(clean[0].split("\\s"))
                .filter(i -> i.length() > 0)
                .map(Long::parseLong).toList();
        List<Long> distances = Arrays.stream(clean[1].split("\\s"))
                .filter(i -> i.length() > 0)
                .map(Long::parseLong).toList();

        assert times.size() == distances.size();

        return countDifferentWays(times, distances).reduce(1L, (a, b) -> a * b);
    }

    public long singleErrorMargin(final String raw) {
        String[] clean = raw
                .replaceAll("Time:", "")
                .replaceAll("Distance:", "")
                .split("\\n");

        List<Long> times = new ArrayList<>();
        List<Long> distances = new ArrayList<>();
        times.add(Long.parseLong(clean[0].replaceAll("\\s", "")));
        distances.add(Long.parseLong(clean[1].replaceAll("\\s", "")));

        return countDifferentWays(times, distances).reduce(1L, (a, b) -> a * b);
    }

    // TODO: This was a lazy bruteforce solution, Tara was sick last night.
    //  Find min, max using math would be more elegant.
    private Stream<Long> countDifferentWays(
            final List<Long> times,
            final List<Long> distances
    ) {
        List<Long> differentWays = new ArrayList<>();
        for (int i = 0; i < times.size(); i++) {
            Long min = null;
            Long max = null;
            for (int j = 0; j < times.get(i); j++) {
                long finalDistance = finalDistance(j, times.get(i));
                if (finalDistance > distances.get(i) && min == null) {
                    min = (long) j;
                }
                if (finalDistance <= distances.get(i) && min != null) {
                    max = (long) j;
                    break;
                }
            }
            differentWays.add(max - min);
        }
        return differentWays.stream();
    }

    private long finalDistance(final long pressTime, final long raceTime) {
        return (raceTime - pressTime) * pressTime;
    }
}
