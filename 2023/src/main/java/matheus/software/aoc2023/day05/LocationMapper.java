package matheus.software.aoc2023.day05;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public final class LocationMapper {
    private Long[] findLocations(
            final List<String> input,
            final List<Long> seeds
    ) {
        Long[] mapped = seeds.toArray(new Long[0]);
        List<String> lines = doMappping(input, "seed-to-soil map:", mapped);
        lines = doMappping(lines, "soil-to-fertilizer map:", mapped);
        lines = doMappping(lines, "fertilizer-to-water map:", mapped);
        lines = doMappping(lines, "water-to-light map:", mapped);
        lines = doMappping(lines, "light-to-temperature map:", mapped);
        lines = doMappping(lines, "temperature-to-humidity map:", mapped);
        doMappping(lines, "humidity-to-location map:", mapped);

        return mapped;
    }

    public long findMinIndividualLocation(final String raw) {
        List<String> lines = Arrays.stream(raw.split("\\n"))
                .filter(i -> i.length() > 0)
                .toList();

        List<Long> seeds = parseIndividualSeeds(lines);
        lines = lines.subList(1, lines.size());

        return Arrays.stream(findLocations(lines, seeds))
                .reduce(Long::min)
                .orElse(0L);
    }

    private List<Long> parseIndividualSeeds(final List<String> lines) {
        return Arrays.stream(
                lines.get(0).replaceAll("seeds: ", "").split("\\s")
        ).map(Long::parseLong).toList();
    }


    private List<String> doMappping(
            final List<String> lines,
            final String name,
            final Long[] map
    ) {
        assert Objects.equals(lines.get(0), name);
        int linesParsed = 1;
        boolean[] mapped = new boolean[map.length];
        Arrays.fill(mapped, false);
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).contains(":")) {
                break;
            }
            List<Long> parsed = Arrays.stream(lines.get(i).split("\\s"))
                    .map(Long::parseLong).toList();
            Long start = parsed.get(1);
            Long end = start + parsed.get(2);
            Long offset = parsed.get(0);
            for (int j = 0; j < map.length; j++) {
                if (map[j] >= start && map[j] < end && !mapped[j]) {
                    map[j] = (map[j] - start) + offset;
                    mapped[j] = true;
                }
            }
            linesParsed += 1;
        }
        return lines.subList(linesParsed, lines.size());
    }

    public long findMinRangeLocation(
            final String raw,
            final boolean fullSearch
    ) {
        List<String> lines = Arrays.stream(raw.split("\\n"))
                .filter(i -> i.length() > 0)
                .toList();

        List<Long> seedRanges =  Arrays.stream(
                lines.get(0).replaceAll("seeds: ", "").split("\\s")
        ).map(Long::parseLong).toList();

        Set<Long> relevantNumbers = new HashSet<>();
        if (!fullSearch) {
            List<String> relevant = Arrays.stream(raw.split("\\n"))
                    .filter(i -> i.length() > 0)
                    .filter(i -> !i.contains(":"))
                    .toList();

            for (String r: relevant) {
                List<Long> mapRanges = Arrays.stream(r.split("\\s"))
                        .map(Long::parseLong).toList();
                relevantNumbers.add(mapRanges.get(0));
                relevantNumbers.add(mapRanges.get(1));
                relevantNumbers.add(mapRanges.get(0) + mapRanges.get(2) - 1);
                relevantNumbers.add(mapRanges.get(1) + mapRanges.get(2) - 1);
            }
            for (int i = 0; i < seedRanges.size(); i += 2) {
                relevantNumbers.add(seedRanges.get(i));
                relevantNumbers.add(
                        seedRanges.get(i) + seedRanges.get(i + 1) - 1
                );
            }
        }

        lines = lines.subList(1, lines.size());

        int pairs = seedRanges.size();
        List<Long> seeds = new ArrayList<>();
        for (int pair = 0; pair < pairs; pair += 2) {
            for (int i = 0; i < seedRanges.get(pair + 1); i++) {
                if (relevantNumbers.contains(seedRanges.get(pair) + i)
                    || fullSearch) {
                    seeds.add(seedRanges.get(pair) + i);
                }
            }
        }

        return Arrays.stream(findLocations(lines, seeds))
                .reduce(Long::min)
                .orElse(0L);
    }
}
