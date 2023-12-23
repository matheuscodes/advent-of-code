package matheus.software.aoc2023;

import matheus.software.aoc2023.day17.CityMap;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static java.lang.String.join;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day17Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private CityMap underTest;

    @Test
    void firstPart() {
        String simple = "111\n991\n991";
        assertEquals(4, underTest.leastHeatLoss(simple, 1, 3));

        String longer = "1111\n9991\n9991\n9991";
        assertEquals(6, underTest.leastHeatLoss(longer, 1, 3));

        String evenlonger = "11111\n99991\n99991\n99991\n99991";
        assertEquals(16, underTest.leastHeatLoss(evenlonger, 1, 3));

        String muchlonger = "111111\n999991\n999991\n999991\n999991";
        assertEquals(25, underTest.leastHeatLoss(muchlonger, 1, 3));

        String raw = helpers.readSample("day17");
        assertEquals(102, underTest.leastHeatLoss(raw, 1, 3));

        String rawInput = helpers.readInput("day17");
        long test = underTest.leastHeatLoss(rawInput, 1, 3);
        // 1383 too high (trying to bruteforce...)
        assertTrue(1383 > test);
        // 1150 too high (trying to bruteforce...)
        assertTrue(1150 > test);
        // 1148 too high (trying to bruteforce...)
        assertTrue(1148 > test);
        // 992 too high (trying to bruteforce...)
        assertTrue(992 > test);
        // 972 too high (solution was close but not quite there)
        assertTrue(972 > test);
        // Looked up a solution...
        assertEquals(963, test);
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day17");
        assertEquals(94, underTest.leastHeatLoss(raw, 4, 10));

        String muchlonger = join("\n", new String[] {
                "111111111111",
                "999999999991",
                "999999999991",
                "999999999991",
                "999999999991"
        });
        assertEquals(71, underTest.leastHeatLoss(muchlonger, 4, 10));

        String rawInput = helpers.readInput("day17");
        long test = underTest.leastHeatLoss(rawInput, 4, 10);
        assertEquals(1178, test);
    }
}
