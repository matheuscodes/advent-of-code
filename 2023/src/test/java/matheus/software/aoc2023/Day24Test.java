package matheus.software.aoc2023;

import matheus.software.aoc2023.day24.HailStorm;
import matheus.software.aoc2023.day24.HailStorm.Hail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day24Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private HailStorm underTest;

    @Test
    void firstPart() {

        String single = "19, 13, 30 @ -2,  1, -2\n18, 19, 22 @ -1, -1, -2";
        assertEquals(1, underTest.estimateCollisions(single, 7, 27));

        String parallel = "18, 19, 22 @ -1, -1, -2\n20, 25, 34 @ -2, -2, -4";
        assertEquals(0, underTest.estimateCollisions(parallel, 7, 27));

        String pastA = "19, 13, 30 @ -2,  1, -2\n20, 19, 15 @  1, -5, -3";
        assertEquals(0, underTest.estimateCollisions(pastA, 7, 27));

        String pastB = "20, 25, 34 @ -2, -2, -4\n20, 19, 15 @  1, -5, -3";
        assertEquals(0, underTest.estimateCollisions(pastB, 7, 27));

        String pastBoth = "18, 19, 22 @ -1, -1, -2\n20, 19, 15 @  1, -5, -3";
        assertEquals(0, underTest.estimateCollisions(pastBoth, 7, 27));

        String raw = helpers.readSample("day24");
        assertEquals(2, underTest.estimateCollisions(raw, 7, 27));

        String rawInput = helpers.readInput("day24");
        // 0 - wrong (ran on sample)
        assertEquals(
                13965,
                underTest.estimateCollisions(
                        rawInput,
                        200000000000000d,
                        400000000000000d
                )
        );
    }

    @Test
    void secondPart() {

        Hail rock = Hail.parseHail("24, 13, 10 @ -3, 1, 2");
        var test = Hail.parseHail("20, 19, 15 @ 1, -5, -3");
        assertEquals(1, underTest.collisionXYZ(rock, test));
        test = Hail.parseHail("18, 19, 22 @ -1, -1, -2");
        assertEquals(3, underTest.collisionXYZ(rock, test));
        test = Hail.parseHail("20, 25, 34 @ -2, -2, -4");
        assertEquals(4, underTest.collisionXYZ(rock, test));
        test = Hail.parseHail("19, 13, 30 @ -2, 1, -2");
        assertEquals(5, underTest.collisionXYZ(rock, test));
        test = Hail.parseHail("12, 31, 28 @ -1, -2, -1");
        assertEquals(6, underTest.collisionXYZ(rock, test));

        String raw = helpers.readSample("day24");
        assertEquals(47, underTest.rockObliteration(raw));

        String rawInput = helpers.readInput("day24");
        assertEquals(578177720733043L, underTest.rockObliteration(rawInput));
    }
}
