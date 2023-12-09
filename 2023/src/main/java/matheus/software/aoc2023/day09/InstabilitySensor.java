package matheus.software.aoc2023.day09;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.function.Function;

@Component
public final class InstabilitySensor {


    public long sumExtrapolations(final String raw) {
        return calculateForward(raw).stream()
                .reduce(Long::sum)
                .orElse(0L);
    }

    public long sumPrepolations(final String raw) {
        return calculateBackwards(raw).stream()
                .reduce(Long::sum)
                .orElse(0L);
    }

    private List<List<Long>> parseHistories(final String raw) {
        return Arrays.stream(raw.split("\\n"))
                .map(i -> i.split("\\s"))
                .map(i -> Arrays.stream(i)
                        .map(Long::parseLong)
                        .toList()
                ).toList();
    }

    private void calculateDifferences(
            final List<Long> history,
            final Function<List<Long>, Long> extract
    ) {
        List<Long> diffs = history;
        while (!diffs.stream().allMatch(i -> i == 0L)) {
            List<Long> newDiffs = new ArrayList<>();
            for (int i = 1; i < diffs.size(); i++) {
                long diff = diffs.get(i) - diffs.get(i - 1);
                newDiffs.add(diff);
            }
            extract.apply(diffs);
            diffs = newDiffs;
        }
    }

    private List<Long> calculateForward(final String raw) {
        List<Long> forwardExtrapolations = new ArrayList<>();

        List<List<Long>> histories = parseHistories(raw);

        for (List<Long> history: histories) {
            Stack<Long> lastDiffs = new Stack<>();

            calculateDifferences(
                    history,
                    diffs -> lastDiffs.push(diffs.get(diffs.size() - 1))
            );

            lastDiffs.push(0L);
            while (lastDiffs.size() > 1) {
                Long first = lastDiffs.pop();
                Long second = lastDiffs.pop();
                lastDiffs.push(first + second);
            }

            forwardExtrapolations.add(lastDiffs.pop());
        }

        return forwardExtrapolations;
    }

    private List<Long> calculateBackwards(final String raw) {
        List<Long> backwardsExtrapolations = new ArrayList<>();

        List<List<Long>> histories = parseHistories(raw);

        for (List<Long> history: histories) {
            Stack<Long> firstDiffs = new Stack<>();

            calculateDifferences(
                    history,
                    diffs -> firstDiffs.push(diffs.get(0))
            );

            firstDiffs.push(0L);
            while (firstDiffs.size() > 1) {
                Long first = firstDiffs.pop();
                Long second = firstDiffs.pop();
                firstDiffs.push(second - first);
            }

            backwardsExtrapolations.add(firstDiffs.pop());
        }

        return backwardsExtrapolations;
    }

}
