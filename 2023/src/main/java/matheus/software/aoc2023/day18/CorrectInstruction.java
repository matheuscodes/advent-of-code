package matheus.software.aoc2023.day18;

import java.math.BigInteger;

public final class CorrectInstruction extends Instruction {

    public CorrectInstruction(final String line) {
        super(line);
        String[] split = line.split("\\s");
        String hex = split[2].replaceAll("\\(", "")
                            .replaceAll("\\)", "")
                            .replaceAll("#", "");
        this.setDirection(this.directionFromString(hex.substring(5)));
        this.setAmount(new BigInteger(hex.substring(0, 5), 16).longValue());
    }

    private Direction directionFromString(final String from) {
        return switch (from) {
            case "3" -> new Direction(-1, 0);
            case "1" -> new Direction(1, 0);
            case "2" -> new Direction(0, -1);
            case "0" -> new Direction(0, 1);
            default -> throw new RuntimeException("Impossible");
        };
    }
}
