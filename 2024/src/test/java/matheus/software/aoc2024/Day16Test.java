package matheus.software.aoc2024;

import matheus.software.aoc2024.day16.ReindeerMaze;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public final class Day16Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private ReindeerMaze underTest;

    @Test
    void firstPart() {
        assertEquals(7036, underTest.findPathScore(helpers.readSample("day16.1")));
        assertEquals(11048, underTest.findPathScore(helpers.readSample("day16.2")));
        // 96444 - Too high (wrong turn calculation)
        assertEquals(94444, underTest.findPathScore(helpers.readInput("day16")));
    }

    @Test
    void secondPart() {
        assertEquals(45, underTest.countSpots(helpers.readSample("day16.1")));
        assertEquals(64, underTest.countSpots(helpers.readSample("day16.2")));
        // 96444 - Too high
        assertEquals(502, underTest.countSpots(helpers.readInput("day16")));
    }
}
