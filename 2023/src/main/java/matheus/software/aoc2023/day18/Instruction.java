package matheus.software.aoc2023.day18;

import lombok.Data;

import static java.lang.Long.parseLong;

@Data
public class Instruction {

    private String color;

    private Direction direction;
    private long amount;


    public Instruction(final String line) {
        String[] split = line.split("\\s");
        this.color = split[2];
        this.direction = this.directionFromString(split[0]);
        this.amount = parseLong(split[1]);
    }

    public record Direction(int i, int j) {

    }

    private Direction directionFromString(final String from) {
        return switch (from) {
            case "U" -> new Direction(-1, 0);
            case "D" -> new Direction(1, 0);
            case "L" -> new Direction(0, -1);
            case "R" -> new Direction(0, 1);
            default -> throw new RuntimeException("Impossible");
        };
    }
}
