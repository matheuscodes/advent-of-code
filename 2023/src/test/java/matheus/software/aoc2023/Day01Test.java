package matheus.software.aoc2023;

import matheus.software.aoc2023.day01.CalibrationParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Day01Test {

    @Autowired
    CalibrationParser underTest;

    @Autowired
    Helpers helpers;

    @Test
    void firstPart() {
        assertEquals(142, underTest.sumCalibrations(helpers.readSample("day01.first")));
        assertEquals(53651, underTest.sumCalibrations(helpers.readInput("day01")));
    }

    @Test
    void secondPart() {
        assertEquals(281, underTest.sumSpelledCalibrations(helpers.readSample("day01.second")));

        assertTrue(53896 > underTest.sumSpelledCalibrations(helpers.readInput("day01"))); // Too high
        assertEquals(53894, underTest.sumSpelledCalibrations(helpers.readInput("day01")));
    }
}