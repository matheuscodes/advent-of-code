package matheus.software.aoc2023.day03;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class PartNumberSearcher {

    public List<PartNumber> getAllNumbers(final String raw) {
        List<PartNumber> numbers = new ArrayList<>();
        List<Character> currentNumber = new ArrayList<>();
        List<Position> currentNumberPositions = new ArrayList<>();
        int rows = 0;
        int columns = 0;
        for (char token: raw.toCharArray()) {
            if (token <= '9' && token >= '0') {
                currentNumber.add(token);
                currentNumberPositions.add(new Position(rows, columns));
                columns += 1;
            } else {
                if (token == '\n') {
                    rows += 1;
                    columns = 0;
                } else {
                    columns += 1;
                }
                if (currentNumber.size() > 0) {
                    numbers.add(
                        new PartNumber(currentNumber, currentNumberPositions)
                    );
                    currentNumber = new ArrayList<>();
                    currentNumberPositions = new ArrayList<>();
                }
            }
        }
        return numbers;
    }

    public List<PartNumber> findPartNumbers(final String raw) {
        List<PartNumber> allNumbers = this.getAllNumbers(raw);
        char[][] engine = extractEngine(raw);
        return allNumbers.stream()
                .filter(a -> a.isRealPartNumber(engine)).toList();
    }

    public long partNumbersSum(final String raw) {
        List<PartNumber> found = findPartNumbers(raw);
        return found.stream()
                .map(PartNumber::getNumber)
                .reduce(Long::sum)
                .orElse(0L);
    }

    public char[][] extractEngine(final String raw) {
        String[] rows = raw.split("\\n");
        char[][] engine = new char[rows.length][0];
        for (int i = 0; i < rows.length; i++) {
            engine[i] = rows[i].toCharArray();
        }
        return engine;
    }
}
