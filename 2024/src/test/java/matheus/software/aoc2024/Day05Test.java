package matheus.software.aoc2024;

import matheus.software.aoc2024.day05.SafetyManuals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day05Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private SafetyManuals underTest;

    @Test
    void firstPart() {
        assertEquals(3, underTest.sumCorrectMidPages("1|2\n\n1,3,2"));
        assertEquals(0, underTest.sumCorrectMidPages("2|1\n\n1,3,2"));
        assertEquals(143, underTest.sumCorrectMidPages(helpers.readSample("day05")));
        assertEquals(5747, underTest.sumCorrectMidPages(helpers.readInput("day05")));
    }

    @Test
    void secondPart() {
        assertEquals(0, underTest.sumIncorrectMidPages("1|2\n\n1,2,3"));
        assertEquals(2, underTest.sumIncorrectMidPages("1|2\n\n2,1,3"));
        assertEquals(3, underTest.sumIncorrectMidPages("3|1\n\n2,1,3"));
        assertEquals(123, underTest.sumIncorrectMidPages(helpers.readSample("day05")));
        assertEquals(5502, underTest.sumIncorrectMidPages(helpers.readInput("day05")));
    }
}
