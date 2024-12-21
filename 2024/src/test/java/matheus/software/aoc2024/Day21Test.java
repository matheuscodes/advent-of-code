package matheus.software.aoc2024;

import lombok.SneakyThrows;
import matheus.software.aoc2024.day21.RobotRecursion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day21Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private RobotRecursion underTest;

    @Test
    void firstPart() {
        assertEquals(68 * 29, underTest.solveComplexity("029A", 2));
        assertEquals(68 * 179, underTest.solveComplexity("179A", 2));
        assertEquals(126384, underTest.solveComplexity(helpers.readSample("day21"), 2));
        assertEquals(184718, underTest.solveComplexity(helpers.readInput("day21"), 2));
    }

    @Test
    @SneakyThrows
    void secondPart() throws Exception {
        long result = underTest.solveComplexity(helpers.readInput("day21"), 25);

        assertEquals(228800606998554L, result);
        //Too high (totals calculated wrong in subseqs)
        assertTrue(result < Long.MAX_VALUE);
        //Too high (totals calculated wrong at top)
        assertTrue(result < 1952085985163563L);
        //Wrong (using 27 and broken)
        assertTrue(result != 1404857290083847L);
        //Wrong (using 26 and broken)
        assertTrue(result != 564738229044279L);
        //Wrong (using 25 and broken)
        assertTrue(result != 227018983652693L);
        //Too low (using 24 and broken)
        assertTrue(result > 91259308868686L);
    }


}
