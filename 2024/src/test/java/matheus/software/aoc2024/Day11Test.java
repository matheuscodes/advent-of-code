package matheus.software.aoc2024;

import matheus.software.aoc2024.day11.StoneAutomata;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day11Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private StoneAutomata underTest;

    @Test
    void firstPart() {
        assertEquals(1, underTest.watchAndBlink("0", 1));
        assertEquals(1, underTest.watchAndBlink("1", 1));
        assertEquals(2, underTest.watchAndBlink("10", 1));
        assertEquals(2, underTest.watchAndBlink("2024", 1));
        assertEquals(7, underTest.watchAndBlink(helpers.readSample("day11.1"), 1));
        assertEquals(4, underTest.watchAndBlink("2024", 2));
        assertEquals(3, underTest.watchAndBlink(helpers.readSample("day11.2"), 1));
        assertEquals(4, underTest.watchAndBlink(helpers.readSample("day11.2"), 2));
        assertEquals(5, underTest.watchAndBlink(helpers.readSample("day11.2"), 3));
        assertEquals(9, underTest.watchAndBlink(helpers.readSample("day11.2"), 4));
        assertEquals(13, underTest.watchAndBlink(helpers.readSample("day11.2"), 5));
        assertEquals(22, underTest.watchAndBlink(helpers.readSample("day11.2"), 6));
        assertEquals(55312, underTest.watchAndBlink(helpers.readSample("day11.2"), 25));
        assertEquals(198075, underTest.watchAndBlink(helpers.readInput("day11"), 25));
    }

    @Test
    void secondPart() {
        assertEquals(235571309320764L, underTest.watchAndBlink(helpers.readInput("day11"), 75));
    }
}
