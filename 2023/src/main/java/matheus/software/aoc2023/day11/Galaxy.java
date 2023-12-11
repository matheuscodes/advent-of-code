package matheus.software.aoc2023.day11;

import lombok.Data;

import java.util.List;

@Data
public final class Galaxy {

    private long id;
    private long x;
    private long y;

    public Galaxy(final long intId, final long intX, final long intY) {
        this.x = intX;
        this.y = intY;
        this.id = intId;
    }

    public static final class GalaxyPair {
        private final List<Galaxy> gs;

        public GalaxyPair(final Galaxy g1, final Galaxy g2) {
            if (g1.id < g2.id) {
                this.gs = List.of(g1, g2);
            } else {
                this.gs = List.of(g2, g1);
            }
        }

        public long getLength() {
            return Math.abs(gs.get(0).x - gs.get(1).x)
                    + Math.abs(gs.get(0).y - gs.get(1).y);
        }
    }
}
