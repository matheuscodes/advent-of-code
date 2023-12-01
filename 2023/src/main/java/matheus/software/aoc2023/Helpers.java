package matheus.software.aoc2023;

import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.*;

@Service
public class Helpers {


    public String readInput(String name) {

        return readFileAsString(name + ".input");
    }

    public String readSample(String name) {
        return readFileAsString(name + ".sample");
    }

    public String readFileAsString(String filename) {
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
