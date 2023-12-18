package matheus.software.aoc2023;

import matheus.software.aoc2023.day17.CityMap;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static matheus.software.aoc2023.day16.Ray.Direction.RIGHT;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day17Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private CityMap underTest;

    @Disabled
    @Test
    void firstPart() {
        String simple = "111\n991\n991";
        assertEquals(4, underTest.leastHeatLoss4(simple));

        String longer = "1111\n9991\n9991\n9991";
        assertEquals(14, underTest.leastHeatLoss4(longer));

        String evenlonger = "11111\n99991\n99991\n99991\n99991";
        assertEquals(32, underTest.leastHeatLoss4(evenlonger));


        String raw = helpers.readSample("day17");
//        assertEquals(102, underTest.leastHeatLoss4(raw));

        String rawInput = helpers.readInput("day17");
//        long test = ;
        // 1383 too high ?
        // 1150 too high
        // 1148
        // 992 too high ?!?!
        // 972 too high...
//        assertEquals(-1, underTest.leastHeatLoss4(rawInput));
    }

//    @Test
//    void firstPart2() {
//        String simple = "111\n991\n991";
//        assertEquals(4, underTest2.leastHeatLoss(simple));
//
//        String longer = "1111\n9991\n9991\n9991";
////        assertEquals(14, underTest.leastHeatLoss3(longer));
//
//        String evenlonger = "11111\n99991\n99991\n99991\n99991";
////        assertEquals(32, underTest.leastHeatLoss3(evenlonger));
//
//
//        String raw = helpers.readSample("day17");
////        assertEquals(102, underTest.leastHeatLoss3(raw));
//
//        String rawInput = helpers.readInput("day17");
////        long test = underTest.leastHeatLoss3(rawInput);
//        // 1383 too high ?
//        // 1150 too high
//        // 1148
////        assertEquals(-1, test);
//    }
}
