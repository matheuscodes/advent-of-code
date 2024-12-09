package matheus.software.aoc2024;

import matheus.software.aoc2024.day09.DiskMemory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day09Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private DiskMemory underTest;

    @Test
    void firstPart() {
        assertEquals(0, underTest.optimize("1", true));
        assertEquals(1, underTest.optimize("101", true));
        assertEquals(3, underTest.optimize("102", true));
        assertEquals(3, underTest.optimize("122", true));
        assertEquals(1928, underTest.optimize(helpers.readSample("day09"), true));
        assertEquals(6242766523059L, underTest.optimize(helpers.readInput("day09"), true));
    }

    @Test
    void secondPart() {
        assertEquals(0, underTest.optimize("1", false));
        assertEquals(1, underTest.optimize("101", false));
        assertEquals(3, underTest.optimize("102", false));
        assertEquals(3, underTest.optimize("122", false));
        assertEquals(5, underTest.optimize("112", false));
        assertEquals(2858, underTest.optimize(helpers.readSample("day09"), false));
        assertEquals(6272188244509L, underTest.optimize(helpers.readInput("day09"), false));
    }
}
