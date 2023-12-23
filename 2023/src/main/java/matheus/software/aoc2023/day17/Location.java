package matheus.software.aoc2023.day17;

import lombok.Data;

import java.util.Objects;

@Data
public final class Location implements Comparable<Location> {
    private int r;
    private int c;
    private Direction direction;
    private int bestDistance;

    public boolean reached(final int row, final int column) {
        return r == row && c == column;
    }

    public enum Direction {
        UP(-1, 0),
        DOWN(1, 0),
        LEFT(0, -1),
        RIGHT(0, 1);
        private final int r;
        private final int c;

        Direction(final int thisR, final int thisC) {
            this.r = thisR;
            this.c = thisC;
        }

        public int r() {
            return this.r;
        }

        public int c() {
            return this.c;
        }

        public Location next(
                final Integer[][] grid,
                final Location l,
                final int multiplier,
                final int distance
        ) {
            int row = l.r + multiplier * this.r();
            int column = l.c + multiplier * this.c();
            if ((row < 0 || column < 0)
                    || (row >= grid.length || column >= grid[0].length)) {
                return null;
            }
            return new Location(
                    row,
                    column,
                    this,
                    distance + grid[row][column]
            );
        }

        public boolean sameAxis(final Location thisC) {
            if (thisC.getDirection() == null) {
                return false;
            }
            return switch (thisC.getDirection()) {
                case UP, DOWN -> this.equals(DOWN) || this.equals(UP);
                case LEFT, RIGHT -> this.equals(RIGHT)  || this.equals(LEFT);
            };
        }
    }

    public Location(
            final int thisR,
            final int thisC,
            final Direction thisDirection,
            final int thisBestDistance
    ) {
        this.r = thisR;
        this.c = thisC;
        this.direction = thisDirection;
        this.bestDistance = thisBestDistance;
    }

    @Override
    public int compareTo(final Location o) {
        return bestDistance - o.bestDistance;
    }

    @Override
    public boolean equals(final Object other) {
        if (other instanceof Location o) {
            return o.r == r && o.c == c && o.direction == direction;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c, direction);
    }
}
