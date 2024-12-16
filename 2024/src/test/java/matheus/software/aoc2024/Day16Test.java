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
//        assertEquals(-1, underTest.findPathScore(helpers.readInput("day16")));
    }
}
