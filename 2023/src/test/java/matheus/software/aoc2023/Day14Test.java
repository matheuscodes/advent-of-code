package matheus.software.aoc2023;

import matheus.software.aoc2023.day14.ReflectorDish;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day14Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private ReflectorDish underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day14");
        assertEquals(136, underTest.totalLoad(raw));

        String rawInput = helpers.readInput("day14");
        assertEquals(110565, underTest.totalLoad(rawInput));

    }

    @Test
    void secondPart() {
        long cycles = 1000000000;
        String raw = helpers.readSample("day14");
        assertEquals(64, underTest.totalLoadAfterCycles(raw, cycles));

        String rawInput = helpers.readInput("day14");
        assertEquals(89845, underTest.totalLoadAfterCycles(rawInput, cycles));

    }
}
