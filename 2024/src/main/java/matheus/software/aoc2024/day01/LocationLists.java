package matheus.software.aoc2024.day01;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;

import static java.lang.Math.abs;

@Component
public final class LocationLists {
    public Long listDifference(final String raw) {
        LinkedList<Integer> first = new LinkedList<>();
        LinkedList<Integer> second = new LinkedList<>();
        for (String line: raw.split("\\n")) {
            String[] locations = line.split("\\s\\s\\s");
            first.add(Integer.parseInt(locations[0]));
            second.add(Integer.parseInt(locations[1]));
        }
        Collections.sort(first);
        Collections.sort(second);
        long sum = 0;
        for (int i = 0; i < first.size(); i += 1) {
            System.out.println(second.get(i) + "\t" + first.get(i));
            sum += abs(second.get(i) - first.get(i));
        }

        return sum;
    }

    public Long similarityScore(final String raw) {
        LinkedList<Integer> first = new LinkedList<>();
        LinkedList<Integer> second = new LinkedList<>();
        for (String line: raw.split("\\n")) {
            String[] locations = line.split("\\s\\s\\s");
            first.add(Integer.parseInt(locations[0]));
            second.add(Integer.parseInt(locations[1]));
        }
        long similarity = 0;
        for (final int current : first) {
            long count = second.stream().filter(number -> number == current).count();
            similarity += current * count;
        }

        return similarity;
    }
}
