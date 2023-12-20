package matheus.software.aoc2023;

import matheus.software.aoc2023.day20.CableNetwork;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day20Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private CableNetwork underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day20.first");

        assertEquals(32, underTest.countPulses(raw, 1));
        assertEquals(32000000, underTest.countPulses(raw, 1000));

        String raw2 = helpers.readSample("day20.second");
        assertEquals(187, underTest.countPulses(raw2, 4));
        assertEquals(11687500, underTest.countPulses(raw2, 1000));

        String rawInput = helpers.readInput("day20");
        // 574063932 too low (missed output (rx) counts)
        // 612354314 too low (order of pulse execution was incorrect)
        // 713693112 too low (had to rewrite using message queue)
        assertEquals(777666211, underTest.countPulses(rawInput, 1000));

    }

    @Test
    void secondPart() {
        String rawInput = helpers.readInput("day20");
        assertEquals(243081086866483L, underTest.singleLowEnd(rawInput));
    }
}
