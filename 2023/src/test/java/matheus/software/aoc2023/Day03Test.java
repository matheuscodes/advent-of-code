package matheus.software.aoc2023;

import matheus.software.aoc2023.day03.GearSearcher;
import matheus.software.aoc2023.day03.PartNumber;
import matheus.software.aoc2023.day03.PartNumberSearcher;
import matheus.software.aoc2023.day03.Position;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class Day03Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private PartNumberSearcher partNumberSearcher;

    @Autowired
    private GearSearcher gearSearcher;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day03.first");
        List<PartNumber> found = partNumberSearcher.getAllNumbers(raw);
        assertEquals(10, found.size());

        PartNumber first = found.get(0);
        assertEquals(467, first.getNumber());
        assertEquals(new Position(0, 0), first.getPositions().get(0));
        assertEquals(new Position(0, 1), first.getPositions().get(1));
        assertEquals(new Position(0, 2), first.getPositions().get(2));

        PartNumber second = found.get(1);
        assertEquals(114, second.getNumber());
        assertEquals(new Position(0, 5), second.getPositions().get(0));
        assertEquals(new Position(0, 6), second.getPositions().get(1));
        assertEquals(new Position(0, 7), second.getPositions().get(2));

        PartNumber eighth = found.get(7);
        assertEquals(755, eighth.getNumber());
        assertEquals(new Position(7, 6), eighth.getPositions().get(0));
        assertEquals(new Position(7, 7), eighth.getPositions().get(1));
        assertEquals(new Position(7, 8), eighth.getPositions().get(2));

        char[][] engine = partNumberSearcher.extractEngine(raw);
        assertTrue(first.isRealPartNumber(engine));
        assertFalse(second.isRealPartNumber(engine));

        assertEquals(8, partNumberSearcher.findPartNumbers(raw).size());
        assertEquals(4361, partNumberSearcher.partNumbersSum(raw));


        assertEquals(540131, partNumberSearcher.partNumbersSum(
                helpers.readInput("day03")
        ));
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day03.first");
        assertEquals(2, gearSearcher.findGears(raw).size());
        assertEquals(467835, gearSearcher.sumGearRatios(raw));
        assertEquals(86879020, gearSearcher.sumGearRatios(
                helpers.readInput("day03"))
        );
    }
}
