package matheus.software.aoc2023;

import matheus.software.aoc2023.day09.InstabilitySensor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day09Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private InstabilitySensor underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day09");


        assertEquals(5, underTest.sumExtrapolations("1 2 3 4"));
        assertEquals(3, underTest.sumExtrapolations("3 3 3 3"));
        assertEquals(0, underTest.sumExtrapolations("0 0 0 0"));
        assertEquals(114, underTest.sumExtrapolations(raw));

        String cornerCase1 = "26 48 82 133 204 293 390 474 510 446 210 -293 -1184 -2613 -4762 -7848 -12126 -17892 -25486 -35295 -47756";
        assertEquals(-63359, underTest.sumExtrapolations(cornerCase1));

        String cornerCase2 = "4 23 56 99 156 248 421 755 1374 2467 4376 7935 15547 34154 82661 209211 529188 1304971 3106480 7122287 15741466";
        assertEquals(33605888, underTest.sumExtrapolations(cornerCase2));

        String rawInput = helpers.readInput("day09");

        Long sum = underTest.sumExtrapolations(rawInput);
        assertTrue(1672795430 > sum);
        assertTrue(1666172749 > sum);
        assertEquals(1666172641, sum);
    }

    @Test
    void secondPart() {
        assertEquals(5, underTest.sumPrepolations("10 13 16 21 30 45"));


        String raw = helpers.readSample("day09");
        assertEquals(2, underTest.sumPrepolations(raw));

        String rawInput = helpers.readInput("day09");

        Long sum = underTest.sumPrepolations(rawInput);
        assertEquals(933, sum);
    }
}
