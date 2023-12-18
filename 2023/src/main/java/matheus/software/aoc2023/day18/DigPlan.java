package matheus.software.aoc2023.day18;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public final class DigPlan {
    public long fullInteriorVolume(final String raw) {
        var lines = Arrays.stream(raw.split("\\n"))
                .map(CorrectInstruction::new).toList();

        return calculateArea(lines.stream().map(i -> (Instruction) i).toList());
    }

    public long interiorVolume(final String raw) {
        var lines = Arrays.stream(raw.split("\\n"))
                .map(Instruction::new).toList();

        return calculateArea(lines);
    }

    private long calculateArea(final List<Instruction> lines) {
        LinkedList<Long[]> points = new LinkedList<>();

        var digger = new Long[]{0L, 0L};
        points.add(new Long[]{0L, 0L});
        long perimeter = 0L;
        for (Instruction instruction: lines) {
            digger[0] += instruction.getDirection().i()
                    * instruction.getAmount();
            digger[1] += instruction.getDirection().j()
                    * instruction.getAmount();
            perimeter += Math.abs(
                    (instruction.getDirection().i()
                        + instruction.getDirection().j())
                            * instruction.getAmount()
            );
            points.add(new Long[]{digger[0], digger[1]});
        }
        Long[][] ints = new Long[points.size()][];
        for (int i = 0; i < points.size(); i++) {
            ints[i] = points.get(i);
        }
        return shoelaceFormula(ints) + (perimeter / 2) + 1;
    }

    public long shoelaceFormula(final Long[][] arr) {
        int n = arr.length;

        arr[n - 1][0] = arr[0][0];
        arr[n - 1][1] = arr[0][1];

        long det = 0L;

        for (int i = 0; i < n - 1; i++) {
            det += (arr[i][0] * arr[i + 1][1]);
        }

        for (int i = 0; i < n - 1; i++) {
            det -= (arr[i][1] * arr[i + 1][0]);
        }

        return Math.abs(det) / 2;
    }
}
