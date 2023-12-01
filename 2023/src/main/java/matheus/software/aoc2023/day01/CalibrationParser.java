package matheus.software.aoc2023.day01;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.stream.Stream;

@Component
public class CalibrationParser {

    public Long sumCalibrations(String raw) {
        Stream<String> lines = Arrays.stream(raw.split("\\n"));
        return lines.map(CalibrationParser::extractDigits).reduce(Long::sum).orElse(0L);
    }

    public Long sumSpelledCalibrations(String raw) {
        Stream<String> lines = Arrays.stream(raw.split("\\n"));
        return lines.map(CalibrationParser::replaceDigits).map(CalibrationParser::extractDigits).reduce(Long::sum).orElse(0L);
    }

    private static long extractDigits(String line) {
        int first = -1;
        int second = -1;

        for(char i: line.toCharArray()) {
            if(i >= '0' && i <= '9') {
                if(first == -1) {
                    first = i - '0';
                }
                second = i - '0';
            }
        }
        return ((first * 10L) + second);
    }



    private static String replaceDigits(String line) {
        return line.replaceAll("one", "o1e")
                .replaceAll("two", "t2o")
                .replaceAll("three", "t3e")
                .replaceAll("four", "f4r")
                .replaceAll("five", "f5i")
                .replaceAll("six", "s6x")
                .replaceAll("seven", "s7n")
                .replaceAll("eight", "e8t")
                .replaceAll("nine", "n9e")
                .replaceAll("zero", "z0o");
    }
}
