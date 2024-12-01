package matheus.software.aoc2024;

import matheus.software.aoc2024.day01.LocationLists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day01Test {

    @Autowired
    private LocationLists underTest;

    @Autowired
    private Helpers helpers;

    @Test
    void firstPart() {
        assertEquals(11, underTest.listDifference(helpers.readSample("day01")));
        assertTrue(56258 < underTest.listDifference(helpers.readInput("day01"))); // Too low - forgot to abs()
        assertEquals(765748, underTest.listDifference(helpers.readInput("day01")));
    }

    @Test
    void secondPart() {
        assertEquals(31, underTest.similarityScore(helpers.readSample("day01")));

        assertEquals(27732508, underTest.similarityScore(helpers.readInput("day01")));
    }
}
