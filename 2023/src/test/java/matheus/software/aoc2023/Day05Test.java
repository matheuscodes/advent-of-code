package matheus.software.aoc2023;

import matheus.software.aoc2023.day05.LocationMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day05Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private LocationMapper underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day05");
        assertEquals(35, underTest.findMinIndividualLocation(raw));

        String inputRaw = helpers.readInput("day05");
        assertEquals(806029445, underTest.findMinIndividualLocation(inputRaw));
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day05");
        assertEquals(46, underTest.findMinRangeLocation(raw, true));

        String inputRaw = helpers.readInput("day05");
        Long min = underTest.findMinRangeLocation(inputRaw, false);
        assertTrue(1339903174 > min); // Too high
        assertEquals(59370572, min);
    }
}
