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
    private RandomCubeGame underTest;

    @Autowired
    private Helpers helpers;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day02");
        assertEquals(8, underTest.possibleGameSum(raw, 12, 13, 14));
        String input = helpers.readInput("day02");
        assertTrue(3029 > underTest.possibleGameSum(input, 12, 13, 14));
        assertEquals(2679, underTest.possibleGameSum(input, 12, 13, 14));
    }

    @Test
    void secondPart() {
        assertEquals(2286, underTest.gamePowerSum(helpers.readSample("day02")));
        assertEquals(77607, underTest.gamePowerSum(helpers.readInput("day02")));
    }
}
