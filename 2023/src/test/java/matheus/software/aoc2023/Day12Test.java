package matheus.software.aoc2023;

import matheus.software.aoc2023.day12.DamageReport;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day12Test {


    @Autowired
    private Helpers helpers;

    @Autowired
    private DamageReport underTest;


    @Test
    void firstPart() {
        assertEquals(1, underTest.sumReconstructions("???.### 1,1,3"));
        assertEquals(4, underTest.sumReconstructions(".??..??...?##. 1,1,3"));
        assertEquals(1, underTest.sumReconstructions("?#?#?#?#?#?#?#? 1,3,1,6"));
        assertEquals(1, underTest.sumReconstructions("????.#...#... 4,1,1"));
        assertEquals(4, underTest.sumReconstructions("????.######..#####. 1,6,5"));
        assertEquals(10, underTest.sumReconstructions("?###???????? 3,2,1"));

        String raw = helpers.readSample("day12");
        assertEquals(21, underTest.sumReconstructions(raw));

        String rawInput = helpers.readInput("day12");
        assertEquals(7286, underTest.sumReconstructions(rawInput));
    }

    @Test
    void secondPart() {
        assertEquals(1, underTest.sumUnfoldedReconstructions("???.### 1,1,3"));
        assertEquals(16384, underTest.sumUnfoldedReconstructions(".??..??...?##. 1,1,3"));
        assertEquals(1, underTest.sumUnfoldedReconstructions("?#?#?#?#?#?#?#? 1,3,1,6"));
        assertEquals(16, underTest.sumUnfoldedReconstructions("????.#...#... 4,1,1"));
        assertEquals(2500, underTest.sumUnfoldedReconstructions("????.######..#####. 1,6,5"));
        assertEquals(506250, underTest.sumUnfoldedReconstructions("?###???????? 3,2,1"));

        String raw = helpers.readSample("day12");
        assertEquals(525152, underTest.sumUnfoldedReconstructions(raw));

        assertEquals(1728845, underTest.sumUnfoldedReconstructions("?????????#? 1,4,1"));

        String rawInput = helpers.readInput("day12");
        assertEquals(25470469710341L, underTest.sumUnfoldedReconstructions(rawInput));

    }
}
