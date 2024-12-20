package matheus.software.aoc2024;

import matheus.software.aoc2024.day20.Racetrack;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day20Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private Racetrack underTest;

    @Test
    void firstPart() {
        assertEquals(44, underTest.countCheatSaves(helpers.readSample("day20"), 2, 2));
        assertEquals(30, underTest.countCheatSaves(helpers.readSample("day20"), 4, 2));
        assertEquals(16, underTest.countCheatSaves(helpers.readSample("day20"), 6, 2));
        assertEquals(14, underTest.countCheatSaves(helpers.readSample("day20"), 8, 2));
        assertEquals(10, underTest.countCheatSaves(helpers.readSample("day20"), 10, 2));
        assertEquals(8, underTest.countCheatSaves(helpers.readSample("day20"), 12, 2));
        assertEquals(5, underTest.countCheatSaves(helpers.readSample("day20"), 20, 2));
        assertEquals(4, underTest.countCheatSaves(helpers.readSample("day20"), 36, 2));
        assertEquals(3, underTest.countCheatSaves(helpers.readSample("day20"), 38, 2));
        assertEquals(2, underTest.countCheatSaves(helpers.readSample("day20"), 40, 2));
        assertEquals(1, underTest.countCheatSaves(helpers.readSample("day20"), 64, 2));
        long result = underTest.countCheatSaves(helpers.readInput("day20"), 100, 2);

        //24 - just wrong (count exact not at least)
        assertTrue(result != 24);
        //1408 - too low (start was not part of the path)
        assertTrue(result > 1408);
        //1415 - too high (silly attempt)
        assertTrue(result < 14015);

        assertEquals(1409, result);
    }

    @Test
    void secondPart() {
        assertEquals(285, underTest.countCheatSaves(helpers.readSample("day20"), 50, 20));
        long result = underTest.countCheatSaves(helpers.readInput("day20"), 100, 20);

        // 1012536 - too low (start was not part of the path)
        assertTrue(result > 1012536);
        // 1012537 - too low (silly attempt)
        assertTrue(result > 1012537);


        assertEquals(1012821, result);
    }
}
