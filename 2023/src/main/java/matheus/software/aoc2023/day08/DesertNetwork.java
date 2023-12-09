package matheus.software.aoc2023.day08;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Component
public final class DesertNetwork {
    public long stepsRequired(final String raw) {
        List<String> lines = Arrays.stream(raw.split("\\n"))
                .filter(i -> i.length() > 0).toList();

        char[] instructions = lines.get(0).toCharArray();

        Map<String, BranchPoint> network = buildNetwork(lines);

        return countSteps(
            network,
            instructions,
            "AAA",
            (BranchPoint i) -> i.getName().equals("ZZZ")
        );

    }

    public long ghostParanoia(final String raw) {
        List<String> lines = Arrays.stream(raw.split("\\n"))
                .filter(i -> i.length() > 0).toList();

        char[] instructions = lines.get(0).toCharArray();

        Map<String, BranchPoint> network = buildNetwork(lines);

        List<Long> steps = network.values()
                .stream()
                .filter(i -> i.getName().endsWith("A"))
                .map(current -> countSteps(
                        network,
                        instructions,
                        current.getName(),
                        (BranchPoint i) -> i.getName().endsWith("Z")
                )).toList();

        // Find least common multiple.
        long maxSteps = steps.stream().reduce(Long::max).orElseThrow();
        long multiple = 0;
        do {
            multiple += 1;
            long finalMultiple = multiple;
            boolean common = steps.stream()
                    .allMatch(j -> (finalMultiple * maxSteps) % j == 0);
            if (common) {
                break;
            }
        } while (true);

        return multiple * maxSteps;
    }

    private long countSteps(
            final Map<String, BranchPoint> network,
            final char[] instructions,
            final String wanted,
            final Function<BranchPoint, Boolean> condition
    ) {
        long steps = 0L;
        int iterator = 0;
        BranchPoint current = network.get(wanted);
        do {
            current = switch (instructions[iterator]) {
                case 'L' -> current.getLeft();
                case 'R' -> current.getRight();
                default -> throw new RuntimeException("Impossible.");
            };
            if (iterator < (instructions.length - 1)) {
                iterator += 1;
            } else {
                iterator = 0;
            }
            steps += 1;
        } while (!condition.apply(current));
        return steps;
    }
    private Map<String, BranchPoint> buildNetwork(final List<String> lines) {
        Map<String, BranchPoint> network = new HashMap<>(lines.size() - 1);

        List<String[]> toParse = lines
                .subList(1, lines.size())
                .stream()
                .map(i -> i.split(" = "))
                .toList();

        for (String[] tP: toParse) {
            network.put(tP[0], new BranchPoint(tP[0]));
        }

        for (String[] tP: toParse) {
            BranchPoint current = network.get(tP[0]);
            String[] clean = tP[1]
                    .replaceAll("\\(", "")
                    .replaceAll("\\)", "")
                    .split(",");
            BranchPoint left = network.get(clean[0].trim());
            BranchPoint right = network.get(clean[1].trim());
            current.setBranch(left, right);
        }
        return network;
    }
}
