package matheus.software.aoc2024;

import matheus.software.aoc2024.day02.NuclearReports;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day02Test {

    @Autowired
    private NuclearReports underTest;

    @Autowired
    private Helpers helpers;

    @Test
    void firstPart() {
        assertEquals(2, underTest.countSafe(helpers.readSample("day02"), false));
        assertEquals(686, underTest.countSafe(helpers.readInput("day02"), false));
    }

    @Test
    void secondPart() {
        assertEquals(4, underTest.countSafe(helpers.readSample("day02"), true));
        assertEquals(8, underTest.countSafe(helpers.readSample("day02.extra"), true));
        assertTrue(706 < underTest.countSafe(helpers.readInput("day02"), true)); // Too low
        assertTrue(713 < underTest.countSafe(helpers.readInput("day02"), true)); // Too low
        assertTrue(715 < underTest.countSafe(helpers.readInput("day02"), true)); // Too low
        assertEquals(717, underTest.countSafe(helpers.readInput("day02"), true));
    }
}
