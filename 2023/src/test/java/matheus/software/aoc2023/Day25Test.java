package matheus.software.aoc2023;

import matheus.software.aoc2023.day25.Apparatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day25Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private Apparatus underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day25");
        assertEquals(54, underTest.cutWires(raw));


        String rawInput = helpers.readInput("day25");
        assertEquals(552695, underTest.cutWires(rawInput));
    }

}
