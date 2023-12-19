package matheus.software.aoc2023;

import matheus.software.aoc2023.day19.PartAvalanche;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day19Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private PartAvalanche underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day19");
        assertEquals(19114, underTest.sumAcceptedRatings(raw));
        String rawInput = helpers.readInput("day19");
        assertEquals(368523, underTest.sumAcceptedRatings(rawInput));
    }

    @Test
    void secondPart() {
        String simple1 = "pv{a>1716:R,A}\n\n";
        assertEquals((1716) * 4000L * 4000L * 4000L,
                underTest.possibleDistinct(simple1, "pv"));
        String simple2 = "pv{a<1716:R,A}\n\n";
        assertEquals((4000 - 1715) * 4000L * 4000L * 4000L,
                underTest.possibleDistinct(simple2, "pv"));
        String simple3 = "lnx{m>1548:A,A}\n\n";
        assertEquals(4000L * 4000L * 4000L * 4000L,
                underTest.possibleDistinct(simple3, "lnx"));
        String complex1 = """
                pv{a>1716:R,A}
                hdj{m>838:A,pv}

                """;
        assertEquals(((1716) * (838) * 4000L * 4000L)
                + (4000L * (4000L - 838) * 4000L * 4000L),
                underTest.possibleDistinct(complex1, "hdj"));
        String complex2 = """
                px{a<2006:qkq,m>2090:R,R}
                in{s<1351:px,R}
                qkq{x<1416:A,crn}
                crn{x>2662:A,R}

                """;
        assertEquals(29806731000000L,
                underTest.possibleDistinct(complex2, "in"));
        String raw = helpers.readSample("day19");
        assertEquals(167409079868000L,
                underTest.possibleDistinct(raw, "in"));

        String rawInput = helpers.readInput("day19");
        var test = underTest.possibleDistinct(rawInput, "in");
        // Too low (calculating edges of ranges wrong)
        assertTrue(79947733516856L < test);
        assertEquals(124167549767307L, test);
    }
}
