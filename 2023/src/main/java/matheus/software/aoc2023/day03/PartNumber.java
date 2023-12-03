package matheus.software.aoc2023.day03;

import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Long.parseLong;

@Data
public final class PartNumber {

    private List<Position> positions;
    private long number;


    public PartNumber(
            final List<Character> currentNumber,
            final List<Position> currentNumberPositions
    ) {
        String numberString = currentNumber.stream()
                .map(String::valueOf)
                .collect(Collectors.joining());
        this.number = parseLong(numberString);
        this.positions = currentNumberPositions;
    }

    public boolean isRealPartNumber(final char[][] engine) {
        for (Position p: positions) {
            for (Position s: this.surroundings(p.getRow(), p.getColumn())) {
                try {
                    char token = engine[s.getRow()][s.getColumn()];
                    if ((token < '0' || token > '9') && token != '.') {
                        return true;
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    // Ignore checks out of the engine.
                }
            }
        }
        return false;
    }

    private Position[] surroundings(final int r, final int c) {
        return new Position[] {
            new Position(r - 1, c - 1),
            new Position(r - 1, c),
            new Position(r - 1, c + 1),
            new Position(r, c - 1),
            new Position(r, c + 1),
            new Position(r + 1, c - 1),
            new Position(r + 1, c),
            new Position(r + 1, c + 1),
        };
    }

    public boolean isAdjacent(final int r, final int c) {
        for (Position p: positions) {
            for (Position s: this.surroundings(p.getRow(), p.getColumn())) {
                if (s.getRow() == r && s.getColumn() == c) {
                    return true;
                }
            }
        }
        return false;
    }
}
