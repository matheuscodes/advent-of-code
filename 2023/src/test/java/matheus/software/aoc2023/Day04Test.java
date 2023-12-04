package matheus.software.aoc2023;

import matheus.software.aoc2023.day04.CardPile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day04Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private CardPile underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day04");
        assertEquals(13, underTest.cardsWorth(raw));


        String rawInput = helpers.readInput("day04");
        assertEquals(18653, underTest.cardsWorth(rawInput));
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day04");
        assertEquals(30, underTest.multiplyCards(raw));


        String rawInput = helpers.readInput("day04");
        assertEquals(5921508, underTest.multiplyCards(rawInput));
    }
}
