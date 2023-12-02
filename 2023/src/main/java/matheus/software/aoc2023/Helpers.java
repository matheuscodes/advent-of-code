package matheus.software.aoc2023;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;

@Service
public final class Helpers {


    public String readInput(final String name) {
        return readFileAsString(name + ".input");
    }

    public String readSample(final String name) {
        return readFileAsString(name + ".sample");
    }

    public String readFileAsString(final String filename) {
        try {
            File file = ResourceUtils.getFile("classpath:" + filename);

            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder  stringBuilder = new StringBuilder();

            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                    stringBuilder.append('\n');
                }
            } finally {
                reader.close();
            }

            return stringBuilder.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
