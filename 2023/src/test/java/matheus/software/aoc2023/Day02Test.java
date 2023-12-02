package matheus.software.aoc2023;

import matheus.software.aoc2023.day02.RandomCubeGame;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day02Test {

    @Autowired
    RandomCubeGame underTest;

    @Autowired
    Helpers helpers;

    @Test
    void firstPart() {
        assertEquals(8, underTest.possibleGameSum(helpers.readSample("day02.first"), 12, 13, 14));
        assertTrue( 3029 > underTest.possibleGameSum(helpers.readInput("day02"), 12, 13, 14)); // Too high
        assertEquals(2679, underTest.possibleGameSum(helpers.readInput("day02"), 12, 13, 14));
    }

    @Test
    void secondPart() {
        assertEquals(2286, underTest.gamePowerSum(helpers.readSample("day02.first")));
        assertEquals(77607, underTest.gamePowerSum(helpers.readInput("day02")));
    }
}
