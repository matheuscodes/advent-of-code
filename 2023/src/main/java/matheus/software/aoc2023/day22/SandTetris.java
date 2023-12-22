package matheus.software.aoc2023.day22;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static matheus.software.aoc2023.day22.Brick.parsePoint;

@Component
public final class SandTetris {

    public long chooseDesintegration(final String raw) {
        Brick[][][] space = new Brick[1000][10][10];
        var bricks = processAndStabilizeBricks(raw, space);
        var floor = space[0][0][0];

        HashMap<Brick, Set<Brick>> support = new HashMap<>();
        for (Brick brick: bricks) {
            support.computeIfAbsent(brick, k -> new HashSet<>());
        }
        for (Brick brick: bricks) {
            if (brick != floor) {
                brick.getSupport(space).forEach(b -> support.get(brick).add(b));
            }
        }
        HashSet<Brick> uniqueSupport = new HashSet<>();
        for (Set<Brick> supports: support.values()) {
            if (supports.size() == 1) {
                uniqueSupport.addAll(supports);
            }
        }
        return bricks.size() - uniqueSupport.size();
    }

    public long chainReaction(final String raw) {
        Brick[][][] space = new Brick[1000][10][10];
        var bricks = processAndStabilizeBricks(raw, space);
        var floor = space[0][0][0];

        long total = 0;
        bricks.remove(floor);
        bricks.sort(
                Comparator.comparingInt(
                        b -> b.getCubes().stream().findAny().orElseThrow().z()
                )
        );
        for (Brick fallenBrick : bricks) {
            Set<Brick> fallenBricks = new HashSet<>();
            fallenBricks.add(fallenBrick);
            for (Brick brick : bricks) {
                List<Brick> support = brick.getSupport(space);
                HashSet<Brick> stoodStill = new HashSet<>(support);
                stoodStill.removeAll(fallenBricks);
                if (support.size() > 0 && stoodStill.size() == 0) {
                    fallenBricks.add(brick);
                }
            }
            total += fallenBricks.size() - 1;
        }

        return total;
    }

    private List<Brick> processAndStabilizeBricks(
            final String raw,
            final Brick[][][] space
    ) {
        List<Brick> bricks = Arrays.stream(raw.split("\\n"))
                .map(i -> i.split("~"))
                .map(i -> new Brick(parsePoint(i[0]), parsePoint(i[1])))
                .collect(Collectors.toCollection(LinkedList::new));

        var floor = new Brick(
                new Brick.Point(0, 0, 0),
                new Brick.Point(9, 9, 0)
        );
        bricks.add(floor);
        bricks
            .forEach(b -> b.getCubes()
            .forEach(c -> space[c.z()][c.y()][c.x()] = b));
        boolean moved = true;
        while (moved) {
            moved = bricks.stream().anyMatch(b -> b != floor && b.fall(space));
        }
        return bricks;
    }


}
