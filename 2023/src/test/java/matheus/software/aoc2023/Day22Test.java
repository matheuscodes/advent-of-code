package matheus.software.aoc2023;

import matheus.software.aoc2023.day22.SandTetris;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day22Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private SandTetris underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day22");
        assertEquals(5, underTest.chooseDesintegration(raw));
        String rawInput = helpers.readInput("day22");
        var test = underTest.chooseDesintegration(rawInput);
        // Too high (counting same support multiple times)
        assertTrue(744 > test);
        assertEquals(515, test);
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day22");
        assertEquals(7, underTest.chainReaction(raw));
        String rawInput = helpers.readInput("day22");
        var test = underTest.chainReaction(rawInput);
        // Too high (counting same falls multiple times)
        assertTrue(114230 > test);
        assertEquals(101541, test);
    }
}
