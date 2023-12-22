package matheus.software.aoc2023.day22;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import static java.lang.Integer.parseInt;

@Data
public final class Brick {

    private List<Point> cubes = new LinkedList<>();
    private String id;

    private static LinkedList<String> ids;

    static {
        ids = new LinkedList<>();
        ids.addAll(List.of("A", "B", "C", "D", "E", "F", "G", "H", "I"));
    }

    public Brick(final Point from, final Point to) {
//        this.id = Brick.ids.removeFirst();
        for (int z = from.z; z <= to.z; z++) {
            for (int y = from.y; y <= to.y; y++) {
                for (int x = from.x; x <= to.x; x++) {
                    cubes.add(new Point(x, y, z));
                }
            }
        }
    }

    public static Point parsePoint(final String toParse) {
        var split = toParse.split(",");
        return new Point(
                parseInt(split[0]),
                parseInt(split[1]),
                parseInt(split[2])
        );
    }

    public boolean fall(final Brick[][][] space) {
        // Assumes floor has been added, or falls out of bounds.
        var conflictBricks = getSupport(space);
        if (conflictBricks.isEmpty()) {
            this.getCubes().forEach(c -> space[c.z()][c.y()][c.x()] = null);
            this.setCubes(getNextPosition());
            this.getCubes().forEach(c -> space[c.z()][c.y()][c.x()] = this);
            return true;
        }
        return false;
    }

    public List<Brick> getSupport(final Brick[][][] space) {
        var newCubes = getNextPosition();
        return newCubes.stream()
                .map(c -> space[c.z()][c.y()][c.x()])
                .filter(Objects::nonNull)
                .filter(b -> !b.equals(this))
                .toList();
    }

    private List<Point> getNextPosition() {
        return cubes.stream()
                .map(c -> new Point(c.x(), c.y(), c.z() - 1))
                .toList();
    }

    public record Point(int x, int y, int z) {

    }
}
