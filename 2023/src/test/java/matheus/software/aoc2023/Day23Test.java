package matheus.software.aoc2023;

import matheus.software.aoc2023.day23.HikingTrail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day23Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private HikingTrail underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day23");
        assertEquals(94, underTest.bruteForce(raw, true));
        assertEquals(94, underTest.countLongestPathSteps(raw, true));


        String rawInput = helpers.readInput("day23");
        assertEquals(2354, underTest.bruteForce(rawInput, true));
        assertEquals(2354, underTest.countLongestPathSteps(rawInput, true));
    }

    @Test
    void secondPart() {
        System.out.println("Part 2");
        String raw = helpers.readSample("day23");
        assertEquals(154, underTest.bruteForce(raw, false));
        assertEquals(154, underTest.countLongestPathSteps(raw, false));


        String rawInput = helpers.readInput("day23");
        var test = underTest.countLongestPathSteps(rawInput, false);
        // Too low (only finds first match)
        assertTrue(4978 < test);
        // Too low (still calculating...)
        assertTrue(6062 < test);
        // Too low (still calculating...)
        assertTrue(6162 < test);
        // Too low (still calculating...)
        assertTrue(6322 < test);
        // Too low (still calculating...)
        assertTrue(6358 < test);
        // Too low (still calculating...)
        assertTrue(6622 < test);
        // Too high (guessing)
        assertTrue(7001 > test);
        assertEquals(6686, test);
        // 9411 is the max
        // Bruteforce takes about 8 hours but works...
        // assertEquals(6686, underTest.bruteForce(rawInput, true));
    }
}
