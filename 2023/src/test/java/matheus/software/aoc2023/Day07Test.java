package matheus.software.aoc2023;

import matheus.software.aoc2023.day07.CamelCards;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day07Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private CamelCards underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day07");
        assertEquals(6440, underTest.totalWinnings(raw));


        String inputRaw = helpers.readInput("day07");
        assertEquals(250474325, underTest.totalWinnings(inputRaw));
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day07");
        assertEquals(5905, underTest.totalJokerWinnings(raw));


        String inputRaw = helpers.readInput("day07");
        long winnings = underTest.totalJokerWinnings(inputRaw);
        assertTrue(249551233 > winnings); // Too high
        assertTrue(248975635 > winnings); // Too high
        assertEquals(248909434, winnings);
    }
}
