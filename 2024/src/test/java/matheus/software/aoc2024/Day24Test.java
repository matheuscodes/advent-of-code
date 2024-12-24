package matheus.software.aoc2024;

import matheus.software.aoc2024.day24.GroveGates;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day24Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private GroveGates underTest;

    @Test
    void firstPart() {
        assertEquals(4, underTest.numberFromBooleans(helpers.readSample("day24.1")));
        assertEquals(2024, underTest.numberFromBooleans(helpers.readSample("day24.2")));
        assertEquals(55544677167336L, underTest.numberFromBooleans(helpers.readInput("day24")));
    }

    @Test
    void secondPart() {
        assertEquals("gsd,kth,qnf,tbt,vpm,z12,z26,z32", underTest.findCrossedWires(helpers.readInput("day24")));
    }
}
