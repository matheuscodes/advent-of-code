package matheus.software.aoc2024;

import matheus.software.aoc2024.day25.LockedDoors;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day25Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private LockedDoors underTest;

    @Test
    void firstPart() {
        assertEquals(3, underTest.findKeyPairs(helpers.readSample("day25")));
        assertEquals(3365, underTest.findKeyPairs(helpers.readInput("day25")));
    }


}
