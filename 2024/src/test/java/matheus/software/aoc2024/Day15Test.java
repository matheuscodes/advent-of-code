package matheus.software.aoc2024;

import matheus.software.aoc2024.day15.Warehouse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day15Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private Warehouse underTest;

    @Test
    void firstPart() {
        assertEquals(2028, underTest.gpsCoordinates(helpers.readSample("day15.1")));
        assertEquals(10092, underTest.gpsCoordinates(helpers.readSample("day15.2")));
        assertEquals(1516281L, underTest.gpsCoordinates(helpers.readInput("day15")));
    }

    @Test
    void secondPart() {
        assertEquals(104, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd01")));
        assertEquals(203, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd02")));
        assertEquals(205, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd03")));
        assertEquals(304, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd04")));
        assertEquals(415, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd05")));
        assertEquals(821, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd06")));
        assertEquals(205, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd07")));
        assertEquals(617, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd08")));
        assertEquals(4809, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd09")));
        assertEquals(218, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd10")));
        assertEquals(1023, underTest.expandedGpsCoordinates(helpers.readSample("day15.tdd11")));
        assertEquals(2028, underTest.gpsCoordinates(helpers.readSample("day15.1")));
        assertEquals(9021, underTest.expandedGpsCoordinates(helpers.readSample("day15.2")));
        assertEquals(618, underTest.expandedGpsCoordinates(helpers.readSample("day15.3")));
        long value = underTest.expandedGpsCoordinates(helpers.readInput("day15"));
        //1505160 - too low (didn't detect free slot partially to north on isAbleToMove)
        assertTrue(1505160L < value);
        assertEquals(1527969L, value);
    }
}
