package matheus.software.aoc2024;

import matheus.software.aoc2024.day07.RopeBridge;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day07Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private RopeBridge underTest;

    @Test
    void firstPart() {
        assertEquals(3749, underTest.totalCalibrationResult(helpers.readSample("day07"), false));
        assertEquals(2501605301465L, underTest.totalCalibrationResult(helpers.readInput("day07"), false));
    }

    @Test
    void secondPart() {
        assertEquals(11387, underTest.totalCalibrationResult(helpers.readSample("day07"), true));
        assertEquals(44841372855953L, underTest.totalCalibrationResult(helpers.readInput("day07"), true));
    }
}
