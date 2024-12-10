package matheus.software.aoc2024;

import matheus.software.aoc2024.day10.TopographicMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day10Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private TopographicMap underTest;

    @Test
    void firstPart() {
        assertEquals(1, underTest.countHikingTrails(helpers.readSample("day10.1")));
        assertEquals(2, underTest.countHikingTrails(helpers.readSample("day10.2")));
        assertEquals(4, underTest.countHikingTrails(helpers.readSample("day10.3")));
        assertEquals(3, underTest.countHikingTrails(helpers.readSample("day10.4")));
        assertEquals(36, underTest.countHikingTrails(helpers.readSample("day10.5")));
        assertEquals(535, underTest.countHikingTrails(helpers.readInput("day10")));
    }

    @Test
    void secondPart() {
        assertEquals(16, underTest.ratingHikingTrails(helpers.readSample("day10.1")));
        assertEquals(2, underTest.ratingHikingTrails(helpers.readSample("day10.2")));
        assertEquals(13, underTest.ratingHikingTrails(helpers.readSample("day10.3")));
        assertEquals(81, underTest.ratingHikingTrails(helpers.readSample("day10.5")));
        assertEquals(3, underTest.ratingHikingTrails(helpers.readSample("day10.6")));
        assertEquals(227, underTest.ratingHikingTrails(helpers.readSample("day10.7")));
        assertEquals(1186, underTest.ratingHikingTrails(helpers.readInput("day10")));
    }
}
