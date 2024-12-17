package matheus.software.aoc2024;

import matheus.software.aoc2024.day17.HandheldComputer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day17Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private HandheldComputer underTest;

    @Test
    void firstPart() {
        assertEquals("4,6,3,5,6,3,5,2,1,0", underTest.interpretProgram(helpers.readSample("day17.1")));
        assertEquals("2,1,0,1,7,2,5,0,3", underTest.interpretProgram(helpers.readInput("day17")));
    }

    @Test
    void secondPart() {
        assertEquals(117440, underTest.findProgramRegister(helpers.readSample("day17.2")));
        assertEquals(267265166222235L, underTest.findProgramRegister(helpers.readInput("day17")));
    }
}
