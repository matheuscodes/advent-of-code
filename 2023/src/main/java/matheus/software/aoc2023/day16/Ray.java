package matheus.software.aoc2023.day16;

import lombok.Data;

import java.util.List;

@Data
public final class Ray {

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT,
    }

    private int[] position;
    private Ray.Direction direction;

    public Ray(final int[] thisPosition, final Ray.Direction thisDirection) {
        this.position = thisPosition;
        this.direction = thisDirection;
    }

    public void move() {
        this.position = switch (direction) {
            case UP -> new int[]{this.position[0] - 1, this.position[1]};
            case DOWN -> new int[]{this.position[0] + 1, this.position[1]};
            case LEFT -> new int[]{this.position[0], this.position[1] - 1};
            case RIGHT -> new int[]{this.position[0], this.position[1] + 1};
        };
    }

    public List<Ray> split(final String tile) {
        return switch (tile) {
            case "\\" -> switch (this.direction) {
                case UP -> List.of(new Ray(this.position, Direction.LEFT));
                case DOWN -> List.of(new Ray(this.position, Direction.RIGHT));
                case LEFT -> List.of(new Ray(this.position, Direction.UP));
                case RIGHT -> List.of(new Ray(this.position, Direction.DOWN));
            };
            case "/" -> switch (this.direction) {
                case UP -> List.of(new Ray(this.position, Direction.RIGHT));
                case DOWN -> List.of(new Ray(this.position, Direction.LEFT));
                case LEFT -> List.of(new Ray(this.position, Direction.DOWN));
                case RIGHT -> List.of(new Ray(this.position, Direction.UP));
            };
            case "|" -> switch (this.direction) {
                case UP, DOWN ->
                        List.of(new Ray(this.position, this.direction));
                case LEFT, RIGHT -> List.of(
                        new Ray(this.position, Direction.UP),
                        new Ray(this.position, Direction.DOWN)
                );
            };
            case "-" -> switch (this.direction) {
                case LEFT, RIGHT ->
                        List.of(new Ray(this.position, this.direction));
                case UP, DOWN -> List.of(
                        new Ray(this.position, Direction.LEFT),
                        new Ray(this.position, Direction.RIGHT)
                );
            };
            default -> throw new RuntimeException("Impossible.");
        };
    }


}
