package matheus.software.aoc2024;

import matheus.software.aoc2024.day13.Arcade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day13Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private Arcade underTest;


    @Test
    void firstPart() {
        assertEquals(480, underTest.playABButtons(helpers.readSample("day13")));
        assertEquals(37686, underTest.playABButtons(helpers.readInput("day13")));
    }

    @Test
    void secondPart() {
        assertEquals(875318608908L, underTest.playABButtonsWithCorrection(helpers.readSample("day13"), 10000000000000L));
        assertEquals(77204516023437L, underTest.playABButtonsWithCorrection(helpers.readInput("day13"), 10000000000000L));
    }
}
