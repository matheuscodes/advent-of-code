package matheus.software.aoc2024;

import matheus.software.aoc2024.day14.Robots;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day14Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private Robots underTest;

    @Test
    void firstPart() {
        assertEquals(12, underTest.predictMovement(helpers.readSample("day14"), 11, 7, 100L));
        assertEquals(230172768L, underTest.predictMovement(helpers.readInput("day14"), 101, 103, 100L));
    }
    @Test
    void secondPart() {
        assertEquals(8087L, underTest.findTree(helpers.readInput("day14")));
    }
}
