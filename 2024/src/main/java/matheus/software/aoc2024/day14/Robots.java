package matheus.software.aoc2024.day14;

import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public final class Robots {
    public record Robot(int positionX, int positionY, int velocityX, int velocityY) {
        long[] positionAfter(final long seconds) {
            return new long[] {
                    positionX + velocityX * seconds,
                    positionY + velocityY * seconds,
            };
        }

        int[] finalPositionAfter(final int maxX, final int maxY, final long seconds) {
            long[] position = positionAfter(seconds);
            return new int[] {
                    (int) (position[0] < 0 ? maxX + (position[0] % maxX) : (position[0]))  % maxX,
                    (int) (position[1] < 0 ? maxY + (position[1] % maxY) : (position[1]))  % maxY,
            };
        }

        int quadrant(final int maxX, final int maxY, final long seconds) {
            int[] finalPosition = finalPositionAfter(maxX, maxY, seconds);
            if (finalPosition[0] < (maxX / 2)) {
                if (finalPosition[1] < (maxY / 2)) {
                    return 0;
                } else if (finalPosition[1] > (maxY / 2)) {
                    return 1;
                }
            } else if (finalPosition[0] > (maxX / 2)) {
                if (finalPosition[1] < (maxY / 2)) {
                    return 2;
                } else if (finalPosition[1] > (maxY / 2)) {
                    return 3;
                }
            }
            return 4; //Hidden middle
        }
    }
    public long predictMovement(final String raw, final int maxX, final int maxY, final long seconds) {
        LinkedList<Robot> robots = parseRobots(raw);

        int[] quadrants = new int[5];
        robots.forEach(robot -> quadrants[robot.quadrant(maxX, maxY, seconds)] += 1);

        return (long) quadrants[0] * quadrants[1] * quadrants[2] * quadrants[3];
    }

    private LinkedList<Robot> parseRobots(final String raw) {
        String[] lines = raw.split("\\n");
        LinkedList<Robot> robots = new LinkedList<>();
        for (String line: lines) {
            String[] split = line.split(" ");
            String[] position = split[0].replace("p=", "").split(",");
            String[] velocity = split[1].replace("v=", "").split(",");
            robots.add(new Robot(
                    Integer.parseInt(position[0]),
                    Integer.parseInt(position[1]),
                    Integer.parseInt(velocity[0]),
                    Integer.parseInt(velocity[1])
            ));
        }
        return robots;
    }

    public long findTree(final String raw) {
        final int maxX = 101;
        final int maxY = 103;
        LinkedList<Robot> robots = parseRobots(raw);

        for (int i = 0; i < 100000; i += 1) {
            int[][] board = new int[maxY][maxX];
            final long seconds = i;
            robots.forEach(robot -> {
                int[] position = robot.finalPositionAfter(maxX, maxY, seconds);
                board[position[1] % maxY][position[0] % maxX] += 1;
            });

            for (int[] j : board) {
                // A tree would need lines to be drawn...
                if (hasLine(j)) {
                    return seconds;
                }
            }
        }

        return -1; // Not found.
    }

    private boolean hasLine(final int[] values) {
        for (int i = 0; i < values.length; i += 1) {
            int j = i;
            int size = 0;
            while (j < values.length && values[j] > 0) {
                size += 1;
                j += 1;
            }
            if (size > 10) {
                return true;
            }
        }
        return false;
    }
}
