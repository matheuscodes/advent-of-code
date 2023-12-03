package matheus.software.aoc2023.day03;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public final class GearSearcher {

    @Autowired
    private PartNumberSearcher partNumberSearcher;

    public List<Gear> findGears(final String raw) {
        List<Gear> gears = new ArrayList<>();
        List<PartNumber> numbers = partNumberSearcher.getAllNumbers(raw);
        char[][] engine = partNumberSearcher.extractEngine(raw);
        for (int i = 0; i < engine.length; i++) {
            for (int j = 0; j < engine[i].length; j++) {
                if (engine[i][j] == '*') {
                    int r = i;
                    int c = j;
                    List<PartNumber> adjacent = numbers.stream()
                            .filter(n -> n.isAdjacent(r, c))
                            .toList();
                    if (adjacent.size() == 2) {
                        gears.add(new Gear(r, c, adjacent));
                    }
                }
            }
        }
        return gears;
    }

    public long sumGearRatios(final String raw) {
        List<Gear> gears = findGears(raw);
        return gears.stream()
                .map(Gear::getGearRatio)
                .reduce(Long::sum)
                .orElse(0L);
    }
}

