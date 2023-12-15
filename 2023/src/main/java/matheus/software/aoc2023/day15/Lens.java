package matheus.software.aoc2023.day15;

import lombok.Data;

@Data
public final class Lens {

    private String label;
    private long focalLength;

    public Lens(final String thisLabel) {
        this.label = thisLabel;
    }

    public int hash() {
        return this.getLabel().chars()
                .reduce(0, (acc, c) -> ((acc + c) * 17) % 256);
    }

    @Override
    public boolean equals(final Object o) {
        if (o instanceof Lens) {
            return this.getLabel().equals(((Lens) o).getLabel());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.hash();
    }

    @Override
    public String toString() {
        return "[" + this.getLabel() + " " + this.getFocalLength() + "]";
    }

}
