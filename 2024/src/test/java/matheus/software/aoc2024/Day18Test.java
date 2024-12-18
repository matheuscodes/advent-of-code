package matheus.software.aoc2024;

import matheus.software.aoc2024.day18.ComputerMemory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day18Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private ComputerMemory underTest;

    @Test
    void firstPart() {
        assertEquals(22, underTest.safePath(helpers.readSample("day18"), 7, 12));
        assertEquals(270, underTest.safePath(helpers.readInput("day18"), 71, 1024));
    }

    @Test
    void secondPart() {
        assertEquals("6,1", underTest.findCutOff(helpers.readSample("day18"), 7));
        assertEquals("51,40", underTest.findCutOff(helpers.readInput("day18"), 71));
    }
}
