package matheus.software.aoc2023;

import matheus.software.aoc2023.day21.Garden;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day21Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private Garden underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day21");
        assertEquals(2, underTest.countPlots(raw, 1));
        assertEquals(4, underTest.countPlots(raw, 2));
        assertEquals(16, underTest.countPlots(raw, 6));

        String rawInput = helpers.readInput("day21");
        var test = underTest.countPlots(rawInput, 64);
        assertTrue(2665 < test);
        assertEquals(3795, test);
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day21");
        assertEquals(16, underTest.countPlots(raw, 6));
        assertEquals(50, underTest.countPlots(raw, 10));
        assertEquals(1594, underTest.countPlots(raw, 50));
        assertEquals(6536, underTest.countPlots(raw, 100));
        assertEquals(167004, underTest.countPlots(raw, 500));
        assertEquals(668697, underTest.countPlots(raw, 1000));
        //TODO:
        // For whatever reason, this takes 5 minutes to run...
        // ... while 26501365 runs in 1 second. Find out why.
//        assertEquals(16733044, underTest.countPlots(raw, 5000));

        String rawInput = helpers.readInput("day21");
        // Too low was stupidly running on the example instead of input
        var test = underTest.countPlots(rawInput, 26501365);
        assertTrue(469553507348815L < test);
        assertEquals(630129824772393L, test);
    }
}
