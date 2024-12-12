package matheus.software.aoc2024;

import matheus.software.aoc2024.day12.GardenPlot;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day12Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private GardenPlot underTest;

    @Test
    void firstPart() {
        assertEquals(32, underTest.fencingPrice("AA\nAA"));
        assertEquals(140, underTest.fencingPrice(helpers.readSample("day12.1")));
        assertEquals(772, underTest.fencingPrice(helpers.readSample("day12.2")));
        assertEquals(1930, underTest.fencingPrice(helpers.readSample("day12.3")));
        assertEquals(1489582L, underTest.fencingPrice(helpers.readInput("day12")));
    }

    @Test
    void secondPart() {
        assertEquals(16, underTest.fencingBulkPrice("AA\nAA"));
        assertEquals(80, underTest.fencingBulkPrice(helpers.readSample("day12.1")));
        assertEquals(436, underTest.fencingBulkPrice(helpers.readSample("day12.2")));
        assertEquals(1206, underTest.fencingBulkPrice(helpers.readSample("day12.3")));
        assertEquals(236, underTest.fencingBulkPrice(helpers.readSample("day12.4")));
        assertEquals(368, underTest.fencingBulkPrice(helpers.readSample("day12.5")));
        assertEquals(914966L, underTest.fencingBulkPrice(helpers.readInput("day12")));
    }
}
