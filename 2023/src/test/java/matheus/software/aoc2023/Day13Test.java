package matheus.software.aoc2023;

import matheus.software.aoc2023.day13.ReflectionFinder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class Day13Test {
    @Autowired
    private Helpers helpers;

    @Autowired
    private ReflectionFinder underTest;

    @Test
    void firstPart() {
        String raw = helpers.readSample("day13");
        assertEquals(405, underTest.summarizeSmugeless(raw, false));

        String rawInput = helpers.readInput("day13");
        assertEquals(27300, underTest.summarizeSmugeless(rawInput, false));

    }

    @Test
    void secondPart() {
        String test1 =
                "#.##..##.\n" +
                "..#.##.#.\n" +
                "##......#\n" +
                "##......#\n" +
                "..#.##.#.\n" +
                "..##..##.\n" +
                "#.#.##.#.";
        assertEquals(300, underTest.summarizeSmugeless(test1, true));

        String test2 =
                "#...##..#\n" +
                "#....#..#\n" +
                "..##..###\n" +
                "#####.##.\n" +
                "#####.##.\n" +
                "..##..###\n" +
                "#....#..#";
        assertEquals(100, underTest.summarizeSmugeless(test2, true));

        String raw = helpers.readSample("day13");
        assertEquals(400, underTest.summarizeSmugeless(raw, true));

        String testExactlyOneSmudge =
                ".##.#....####..\n" +
                "###....###...##\n" +
                ".....###..#####\n" +
                ".....###..#####\n" +
                "###....###...##\n" +
                ".##.#....####..\n" +
                "#.#..#.#...#.##\n" +
                "##..#.##.#.##..\n" +
                ".#.#.#.#..#.###\n" +
                ".#.###.#..#..##\n" +
                ".#..#.....#.###\n" +
                "..###.###..##..\n" +
                "...#.#..#..####\n" +
                "#.####.##..#.##\n" +
                "..##.##.###.###\n" +
                "#.#.##.#..#.###\n" +
                "#..###...###.#.";
        long tested = underTest.summarizeSmugeless(testExactlyOneSmudge, true);
        assertEquals(14, tested);

        String testUnrestrictedPotential =
                "..###.#..####\n" +
                "..###.###..##\n" +
                "..###.###.###\n" +
                "..#.#..#....#\n" +
                "..#.#..##...#\n" +
                "..###.###.###\n" +
                "..###.###..##\n" +
                "..###.#..####\n" +
                "###..#...#.##\n" +
                "###....#.#.#.\n" +
                "...#.###..##.\n" +
                "######.###..#\n" +
                "##......#..#.";
        tested = underTest.summarizeSmugeless(testUnrestrictedPotential, true);
        assertEquals(400, tested);

        String rawInput = helpers.readInput("day13");
        tested = underTest.summarizeSmugeless(rawInput, true);
        // too high (forgot "exactly one" smudge)
        assertTrue(30518 > tested);
        // too low (should not restrict clean on potential)
        assertTrue(28821 < tested);
        assertEquals(29276, tested);
    }
}
