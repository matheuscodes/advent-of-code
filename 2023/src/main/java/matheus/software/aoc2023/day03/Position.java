package matheus.software.aoc2023.day03;

import lombok.Data;

@Data
public final class Position {
    private int row;
    private int column;

    public Position(final int r, final int c) {
        this.row = r;
        this.column = c;
    }
}
