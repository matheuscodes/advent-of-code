package matheus.software.aoc2024;

import matheus.software.aoc2024.day04.WordPuzzle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day04Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private WordPuzzle underTest;

    @Test
    void firstPart() {
        assertEquals(20, underTest.findXMAS(helpers.readSample("day04.tdd1")));
        assertEquals(18, underTest.findXMAS(helpers.readSample("day04")));
        assertEquals(2557, underTest.findXMAS(helpers.readInput("day04")));
    }

    @Test
    void secondPart() {
        assertEquals(4, underTest.find2MAS(helpers.readSample("day04.tdd2")));
        assertEquals(9, underTest.find2MAS(helpers.readSample("day04")));
        assertEquals(1854, underTest.find2MAS(helpers.readInput("day04")));
    }
}
