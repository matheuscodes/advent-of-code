package matheus.software.aoc2024.day07;

import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public final class RopeBridge {

    public long totalCalibrationResult(final String raw, final boolean useExtraOperator) {
        String[] lines = raw.split("\\n");
        long total = 0;
        for (String line: lines) {
            String[] split = line.split(": ");
            long result = Long.parseLong(split[0]);
            Long[] values = Arrays.stream(split[1].split(" "))
                    .map(Long::parseLong)
                    .toArray(Long[]::new);
            if (searchPlus(
                    result,
                    values[0],
                    Arrays.stream(values).skip(1).toArray(Long[]::new),
                    useExtraOperator)) {
                total += result;
            }
        }
        return total;
    }

    private boolean searchPlus(final long value, final long partial, final Long[] rest, final boolean useExtraOperator) {
        if (rest.length == 0) {
            return value == partial;
        }
        if (searchPlus(
                value,
                partial * rest[0],
                Arrays.stream(rest).skip(1).toArray(Long[]::new),
                useExtraOperator)) {
            return true;
        }
        if (searchPlus(
                value,
                partial + rest[0],
                Arrays.stream(rest).skip(1).toArray(Long[]::new),
                useExtraOperator)) {
            return true;
        }
        if (useExtraOperator) {
            // New Operator
            String concatenated = partial + "" + rest[0];
            return searchPlus(
                    value,
                    Long.parseLong(concatenated),
                    Arrays.stream(rest).skip(1).toArray(Long[]::new),
                    true);
        }
        return false;
    }
}
