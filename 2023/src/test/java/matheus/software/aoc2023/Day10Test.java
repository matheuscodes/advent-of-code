package matheus.software.aoc2023;

import matheus.software.aoc2023.day10.PipeCrawler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day10Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private PipeCrawler underTest;

    @Test
    void firstPart() {
        String raw1 = helpers.readSample("day10.first");
        assertEquals(4, underTest.farthestSteps(raw1).size() / 2);

        String raw2 = helpers.readSample("day10.second");
        assertEquals(4, underTest.farthestSteps(raw2).size() / 2);

        String raw3 = helpers.readSample("day10.third");
        assertEquals(8, underTest.farthestSteps(raw3).size() / 2);

        String rawInput = helpers.readInput("day10");
        assertEquals(6947, underTest.farthestSteps(rawInput).size() / 2);

    }

    @Test
    void secondPart() {
        String raw4 = helpers.readSample("day10.fourth");
        assertEquals(4, underTest.enclosedArea(raw4));

        String raw5 = helpers.readSample("day10.fifth");
        assertEquals(4, underTest.enclosedArea(raw5));

        String raw6 = helpers.readSample("day10.sixth");
        assertEquals(8, underTest.enclosedArea(raw6));

        String raw7 = helpers.readSample("day10.seventh");
        assertEquals(10, underTest.enclosedArea(raw7));

        String rawInput = helpers.readInput("day10");
        assertEquals(273, underTest.enclosedArea(rawInput));
    }

}
