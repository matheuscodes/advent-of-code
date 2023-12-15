package matheus.software.aoc2023;

import matheus.software.aoc2023.day15.LensesOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day15Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private LensesOperation underTest;

    @Test
    void firstPart() {
        assertEquals(52, underTest.sumHashes("HASH"));

        String raw = helpers.readSample("day15");
        assertEquals(1320, underTest.sumHashes(raw));

        String rawInput = helpers.readInput("day15");
        assertEquals(517015, underTest.sumHashes(rawInput));
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day15");
        assertEquals(145, underTest.focusingPower(raw));

        String rawInput = helpers.readInput("day15");
        assertEquals(12, underTest.focusingPower("pc=1,ot=1,ab=1,ot-"));
        assertEquals(36, underTest.focusingPower("pc=1,ot=1,ab=1,ot-,ot=2"));
        assertEquals(1372, underTest.focusingPower("hgjhv=3,bzk=2,pc=1,bzk=3"));
        long power = underTest.focusingPower(rawInput);
        // 282029 - too low (bogus search existing in list)
        assertTrue(282029 < power);
        assertEquals(286104, power);
    }


}
