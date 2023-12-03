package matheus.software.aoc2023.day03;

import lombok.Data;

import java.util.List;

@Data
public final class Gear {

    private Position position;
    private List<PartNumber> partNumbers;

    public Gear(final int r, final int c, final List<PartNumber> adjacent) {
        this.position = new Position(r, c);
        this.partNumbers = adjacent;
    }

    public long getGearRatio() {
        return partNumbers.stream()
                .map(PartNumber::getNumber)
                .reduce(1L, (a, b) -> a * b);
    }
}
