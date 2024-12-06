package matheus.software.aoc2024;

import matheus.software.aoc2024.day06.GuardsRound;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day06Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private GuardsRound underTest;

    @Test
    void firstPart() {
        assertEquals(2, underTest.followGuard(helpers.readSample("day06.tdd1"), 1, 1));
        assertEquals(3, underTest.followGuard(helpers.readSample("day06.tdd2"), 1, 1));
        assertEquals(4, underTest.followGuard(helpers.readSample("day06.tdd3"), 1, 1));
        assertEquals(41, underTest.followGuard(helpers.readSample("day06"), 6, 4));
        assertEquals(4752, underTest.followGuard(helpers.readInput("day06"), 55, 86));
    }

    @Test
    void secondPart() {
        assertEquals(6, underTest.loopGuard(helpers.readSample("day06"), 6, 4));
        //1720 - Too high
        assertEquals(1719, underTest.loopGuard(helpers.readInput("day06"), 55, 86));
    }
}
