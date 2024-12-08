package matheus.software.aoc2024;

import matheus.software.aoc2024.day08.RoofAntennas;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day08Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private RoofAntennas underTest;

    @Test
    void firstPart() {
        assertEquals(2, underTest.findAntinodes(helpers.readSample("day08.1")));
        assertEquals(4, underTest.findAntinodes(helpers.readSample("day08.2")));
        assertEquals(4, underTest.findAntinodes(helpers.readSample("day08.3")));
        assertEquals(14, underTest.findAntinodes(helpers.readSample("day08.4")));
        assertEquals(247, underTest.findAntinodes(helpers.readInput("day08")));
    }

    @Test
    void secondPart() {
        assertEquals(9, underTest.findAllAntinodes(helpers.readSample("day08.5")));
        assertEquals(34, underTest.findAllAntinodes(helpers.readSample("day08.4")));
        assertEquals(861, underTest.findAllAntinodes(helpers.readInput("day08")));
    }
}
