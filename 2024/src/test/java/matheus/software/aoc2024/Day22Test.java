package matheus.software.aoc2024;

import matheus.software.aoc2024.day22.MonkeyBusiness;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day22Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private MonkeyBusiness underTest;

    @Test
    void firstPart() {
        assertEquals(5908254, underTest.secretNumbers("123", 10));
        assertEquals(37327623, underTest.secretNumbers(helpers.readSample("day22.1"), 2000));
        assertEquals(21147129593L, underTest.secretNumbers(helpers.readInput("day22"), 2000));
    }

    @Test
    void secondPart() {
        assertEquals(6, underTest.bestPriceChange("123", 10));
        assertEquals(24, underTest.bestPriceChange(helpers.readSample("day22.1"), 2000));
        assertEquals(23, underTest.bestPriceChange(helpers.readSample("day22.2"), 2000));
        assertEquals(2445, underTest.bestPriceChange(helpers.readInput("day22"), 2000));
    }

}
