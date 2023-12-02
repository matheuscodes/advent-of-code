package matheus.software.aoc2023.day02;

import lombok.Data;

import static java.lang.Integer.parseInt;

@Data
public class Game {
    private int id;

    private int maxRed = 0;
    private int maxGreen = 0;
    private int maxBlue = 0;

    public Game(int id) {
        this.id = id;
    }

    public void add(String[] round) {
        for(String cubes: round) {
            int parsed = parseCubeValue(cubes);
            if(cubes.contains("red") && parsed > maxRed) {
                maxRed = parsed;
            }
            if(cubes.contains("green") && parsed > maxGreen) {
                maxGreen = parsed;
            }
            if(cubes.contains("blue") && parsed > maxBlue) {
                maxBlue = parsed;
            }
        }
    }

    private int parseCubeValue(String cubes) {
        return parseInt(cubes
                .replaceAll("red", "")
                .replaceAll("green", "")
                .replaceAll("blue", "")
                .trim());
    }

    public boolean isPossible(int red, int green, int blue) {
        return red >= maxRed && green >= maxGreen && blue >= maxBlue;
    }

    public long getPower() {
        return (long) maxRed * maxGreen * maxBlue;
    }
}
