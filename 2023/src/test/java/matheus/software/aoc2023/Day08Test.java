package matheus.software.aoc2023;

import matheus.software.aoc2023.day08.DesertNetwork;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day08Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private DesertNetwork underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day08.first");

        assertEquals(2, underTest.stepsRequired(raw));

        raw = helpers.readSample("day08.second");

        assertEquals(6, underTest.stepsRequired(raw));

        String rawInput = helpers.readInput("day08");

        assertEquals(12361, underTest.stepsRequired(rawInput));
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day08.third");

        assertEquals(6, underTest.ghostParanoia(raw));

        String rawInput = helpers.readInput("day08");

        assertEquals(18215611419223L, underTest.ghostParanoia(rawInput));
    }
}
