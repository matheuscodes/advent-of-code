package matheus.software.aoc2024.day03;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public final class CorruptedProgram {

    public long extractMultiplications(final String raw) {
        String pattern = "(mul\\(\\d+,\\d+\\))";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(raw);

        return m.results().map(result -> {
            String token = result.group();
            String numbers = token.replaceAll("mul\\((.*?)\\)", "$1");
            return Arrays.stream(numbers.split(","))
                    .map(Long::parseLong)
                    .reduce(1L, (a, b) -> a * b);
        }).reduce(0L, Long::sum);
    }

    public long runWithConditions(final String raw) {
        String pattern = "(mul\\(\\d+,\\d+\\))|(do\\(\\))|(don't\\(\\))";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(raw);

        AtomicBoolean beSkipping = new AtomicBoolean(false);
        ArrayList<Long> results = new ArrayList<>();
        m.results().forEach(result -> {
            String token = result.group();
            if (token.startsWith("do")) {
                beSkipping.set(token.equals("don't()"));
            } else {
                if (!beSkipping.get()) {
                    String numbers = token.replaceAll("mul\\((.*?)\\)", "$1");
                    long multiplication = Arrays.stream(numbers.split(","))
                            .map(Long::parseLong)
                            .reduce(1L, (a, b) -> a * b);
                    results.add(multiplication);
                }
            }
        });
        return results.stream().reduce(0L, Long::sum);
    }
}
