package matheus.software.aoc2023;

import matheus.software.aoc2023.day18.DigPlan;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day18Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private DigPlan underTest;

    @Test
    void firstPart() {
        //TODO: this originally reused solution from Day 10
        // What about using shoelace + Prick's Theorem to solve it as well?
        String raw = helpers.readSample("day18");
        assertEquals(62, underTest.interiorVolume(raw));

        String rawInput = helpers.readInput("day18");
        // 137960 too high (solution didn't account for many loops in a line)
        assertTrue(137960 > underTest.interiorVolume(rawInput));
        assertEquals(108909, underTest.interiorVolume(rawInput));
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day18");
        assertEquals(952408144115L, underTest.fullInteriorVolume(raw));

        String rawInput = helpers.readInput("day18");


        // Too low (lacking Pick's Theorem)
        assertTrue(133125624027704L < underTest.fullInteriorVolume(rawInput));
        assertEquals(133125706867777L, underTest.fullInteriorVolume(rawInput));
    }
}
