package matheus.software.aoc2023;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class HelpersTest {

    @Autowired
    Helpers underTest;

    @Test
    void readInput() {
        assertEquals("this is my input\nthis should be full\n", underTest.readInput("test"));
    }

    @Test
    void readSample() {
        assertEquals("this is my sample\n", underTest.readSample("test"));
    }
}