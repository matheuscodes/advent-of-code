package matheus.software.aoc2024;

import matheus.software.aoc2024.day23.LANParty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class Day23Test {

    @Autowired
    private Helpers helpers;

    @Autowired
    private LANParty underTest;

    @Test
    void firstPart() {
        assertEquals(7, underTest.findLANs(helpers.readSample("day23")));
        assertEquals(1330, underTest.findLANs(helpers.readInput("day23")));
    }

    @Test
    void secondPart() {
        assertEquals("co,de,ka,ta", underTest.findLargestLAN(helpers.readSample("day23")));
        assertEquals("hl,io,ku,pk,ps,qq,sh,tx,ty,wq,xi,xj,yp", underTest.findLargestLAN(helpers.readInput("day23")));
    }
}
