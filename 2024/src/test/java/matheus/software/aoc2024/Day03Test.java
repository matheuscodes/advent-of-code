package matheus.software.aoc2024;

import matheus.software.aoc2024.day03.CorruptedProgram;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day03Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private CorruptedProgram underTest;

    @Test
    void firstPart() {
        assertEquals(161, underTest.extractMultiplications(helpers.readSample("day03.first")));
        assertEquals(184122457, underTest.extractMultiplications(helpers.readInput("day03")));
    }

    @Test
    void secondPart() {
        assertEquals(48, underTest.runWithConditions(helpers.readSample("day03.second")));
        assertEquals(107862689, underTest.runWithConditions(helpers.readInput("day03")));
    }
}
