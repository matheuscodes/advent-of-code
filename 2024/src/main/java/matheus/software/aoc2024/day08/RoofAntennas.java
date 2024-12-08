package matheus.software.aoc2024.day08;

import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.stream.IntStream;

@Component
public final class RoofAntennas {

    public long findAntinodes(final String raw) {
        return findConditionalAntinodes(raw, false);
    }
    public long findAllAntinodes(final String raw) {
        return findConditionalAntinodes(raw, true);
    }

    public long findConditionalAntinodes(final String raw, final boolean resonantHarmonics) {
        HashMap<String, LinkedList<Pair<Integer, Integer>>> antennas = new HashMap<>();
        String[] lines = raw.split("\\n");
        int columns = 0;
        for (int i = 0; i < lines.length; i += 1) {
            String[] line = lines[i].split("");
            columns = Math.max(columns, line.length);
            for (int j = 0; j < line.length; j += 1) {
                if (!".".equals(line[j])) {
                    antennas.putIfAbsent(line[j], new LinkedList<>());
                    antennas.get(line[j]).add(new Pair<>(i, j));
                }
            }
        }
        final int maxRows = lines.length;
        final int maxColumns = columns;

        HashSet<Pair<Integer, Integer>> antinodes = new HashSet<>();
        for (LinkedList<Pair<Integer, Integer>> antenna: antennas.values()) {
            for (int i = 0; i < antenna.size(); i += 1) {
                for (int j = i + 1; j < antenna.size(); j += 1) {
                    int[] multiples = new int[] {1};
                    if (resonantHarmonics) {
                        int minDistance = Math.min(
                                Math.abs(antenna.get(i).getValue0() - antenna.get(j).getValue0()),
                                Math.abs(antenna.get(i).getValue1() - antenna.get(j).getValue1())
                        );
                        int multiple = Math.max(maxRows / minDistance, maxColumns / minDistance);
                        multiples = IntStream.rangeClosed(-multiple, multiple).toArray();
                    }
                    for (int m: multiples) {
                        antinodes.add(new Pair<>(
                                antenna.get(i).getValue0() + m * (antenna.get(i).getValue0() - antenna.get(j).getValue0()),
                                antenna.get(i).getValue1() + m * (antenna.get(i).getValue1() - antenna.get(j).getValue1())
                        ));
                        antinodes.add(new Pair<>(
                                antenna.get(j).getValue0() + m * (antenna.get(j).getValue0() - antenna.get(i).getValue0()),
                                antenna.get(j).getValue1() + m * (antenna.get(j).getValue1() - antenna.get(i).getValue1())
                        ));
                    }
                }
            }
        }

        return antinodes.stream()
                .filter(c -> c.getValue0() >= 0 && c.getValue0() < maxRows)
                .filter(c -> c.getValue1() >= 0 && c.getValue1() < maxColumns)
                .count();
    }
}
