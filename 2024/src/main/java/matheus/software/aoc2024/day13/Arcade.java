package matheus.software.aoc2024.day13;

import org.springframework.stereotype.Component;

@Component
public final class Arcade {

    public long playABButtons(final String raw) {
        return playABButtonsWithCorrection(raw, 0);
    }

    public long playABButtonsWithCorrection(final String raw, final long correction) {
        String[] groups = raw.split("\\n\\n");
        long sum = 0;
        for (String group: groups) {
            String[] lines = group.split("\\n");
            String[] first = lines[0].split(",");
            int x1 = Integer.parseInt(first[0].replaceAll("Button A: X+", ""));
            int y1 = Integer.parseInt(first[1].replaceAll(" Y+", ""));
            String[] second = lines[1].split(",");
            int x2 = Integer.parseInt(second[0].replaceAll("Button B: X+", ""));
            int y2 = Integer.parseInt(second[1].replaceAll(" Y+", ""));
            String[] third = lines[2].split(",");
            long px = Integer.parseInt(third[0].replaceAll("Prize: X=", "")) + correction;
            long py = Integer.parseInt(third[1].replaceAll(" Y=", "")) + correction;

            long b = calculateBs(x1, y1, x2, y2, px, py);
            if (b >= 0) {
                long a = calculateAs(y1, y2, py, b);
                if (a >= 0) {
                    sum += a * 3 + b;
                }
            }
        }
        return sum;
    }

    private long calculateBs(final int x1, final int y1, final int x2, final int y2, final long px, final long py) {
        double up = px * y1 - py * x1;
        double down = x2 * y1 - y2 * x1;
        double result = up / down;
        if (Math.floor(result) == result) {
            return (long) result;
        }
        return -1L; // Cannot half/partial press it.
    }

    private long calculateAs(final int y1, final int y2, final long py, final long b) {
        double up = py - b * y2;
        double result = up / y1;
        if (Math.floor(result) == result) {
            return (long) result;
        }
        return -1L; // Cannot half/partial press it.
    }
}
