package matheus.software.aoc2023;

import matheus.software.aoc2023.day11.Observatory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day11Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private Observatory underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day11");

        assertEquals(374, underTest.sumLengths(raw, 1));

        String rawInput = helpers.readInput("day11");

        long sum = underTest.sumLengths(rawInput, 1);
        assertTrue(1188455 < sum); // Too low (pair calculation problem)
        assertTrue(9509268 < sum); // Too low (hashes caused pair conflict)
        assertEquals(9509330, sum);
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day11");

        assertEquals(1030, underTest.sumLengths(raw, 9));
        assertEquals(8410, underTest.sumLengths(raw, 99));

        String rawInput = helpers.readInput("day11");

        long sum = underTest.sumLengths(rawInput, 1000000 - 1);
        assertTrue(177077874 < sum); // Too low (use of integer instead of long)
        assertEquals(635832237682L, sum);

    }
}
