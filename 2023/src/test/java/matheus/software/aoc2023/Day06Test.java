package matheus.software.aoc2023;

import matheus.software.aoc2023.day06.Speedometer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day06Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private Speedometer underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day06");
        assertEquals(288, underTest.errorMargin(raw));

        String rawInput = helpers.readInput("day06");
        assertEquals(4403592, underTest.errorMargin(rawInput));
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day06");
        assertEquals(71503, underTest.singleErrorMargin(raw));

        String rawInput = helpers.readInput("day06");
        assertEquals(38017587, underTest.singleErrorMargin(rawInput));
    }
}
