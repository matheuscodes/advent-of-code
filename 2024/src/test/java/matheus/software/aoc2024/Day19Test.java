package matheus.software.aoc2024;

import matheus.software.aoc2024.day19.OnsenTowels;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day19Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private OnsenTowels underTest;

    @Test
    void firstPart() {
        assertEquals(6L, underTest.possibleCombinedTowels(helpers.readSample("day19")));
        assertEquals(350L, underTest.possibleCombinedTowels(helpers.readInput("day19")));
    }

    @Test
    void secondPart() {
        assertEquals(16L, underTest.allTowelCombinations(helpers.readSample("day19")));
        assertEquals(769668867512623L, underTest.allTowelCombinations(helpers.readInput("day19")));
    }
}
