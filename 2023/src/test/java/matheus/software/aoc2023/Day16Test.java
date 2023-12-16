package matheus.software.aoc2023;

import matheus.software.aoc2023.day16.Cavern;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static matheus.software.aoc2023.day16.Ray.Direction.DOWN;
import static matheus.software.aoc2023.day16.Ray.Direction.RIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day16Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private Cavern underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day16");
        assertEquals(46, underTest.tilesEnergized(raw, new int[]{0, 0}, RIGHT));

        String rawInput = helpers.readInput("day16");
        long test = underTest.tilesEnergized(rawInput, new int[]{0, 0}, RIGHT);
        // too low (lack of proper loop control)
        assertTrue(5141 < test);
        assertTrue(5792 < test);
        assertEquals(7307, test);
    }

    @Test
    void secondPart() {
        String raw = helpers.readSample("day16");
        long example = underTest.tilesEnergized(raw, new int[]{0, 3}, DOWN);
        assertEquals(51, example);
        assertEquals(example, underTest.maxEnergy(raw));

        String rawInput = helpers.readInput("day16");
        assertEquals(7635, underTest.maxEnergy(rawInput));
    }
}
