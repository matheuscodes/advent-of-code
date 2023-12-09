package matheus.software.aoc2023.day08;

import lombok.Data;

@Data
public final class BranchPoint {

    private String name;

    private BranchPoint left;
    private BranchPoint right;

    public BranchPoint(final String nameString) {
        this.name = nameString;
    }

    public void setBranch(
            final BranchPoint leftPoint,
            final BranchPoint rightPoint
    ) {
        this.left = leftPoint;
        this.right = rightPoint;
    }

    @Override
    public String toString() {
        return name + " (" + left.getName() + "," + right.getName() + ")";
    }
}
