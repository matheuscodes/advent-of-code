package matheus.software.aoc2023.day15;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;

import static java.lang.Long.parseLong;

@Component
public final class LensesOperation {
    public long sumHashes(final String raw) {
        String[] sequences = raw.replaceAll("\\n", "").split(",");

        long sum = 0;
        for (String sequence: sequences) {
            int hash = 0;
            char[] ascii = sequence.toCharArray();
            for (char c: ascii) {
                hash += c;
                hash = hash * 17;
                hash = hash % 256;
            }
            sum += hash;
        }
        return sum;
    }

    public long focusingPower(final String raw) {
        String[] sequences = raw.replaceAll("\\n", "").split(",");

        HashMap<Integer, LinkedList<Lens>> boxes = new HashMap<>();
        for (String sequence: sequences) {
            String[] split;
            if (sequence.contains("=")) {
                split = sequence.split("=");
                Lens lens = new Lens(split[0]);
                lens.setFocalLength(parseLong(split[1]));
                boxes.computeIfAbsent(lens.hash(), k -> new LinkedList<>());
                if (boxes.get(lens.hash()).contains(lens)) {
                    Lens existing = boxes.get(lens.hash())
                            .get(boxes.get(lens.hash()).indexOf(lens));
                    existing.setFocalLength(lens.getFocalLength());
                } else {
                    boxes.get(lens.hash()).add(lens);
                }
            } else {
                split = sequence.split("-");
                Lens lens = new Lens(split[0]);
                boxes.computeIfAbsent(lens.hash(), k -> new LinkedList<>());
                boxes.get(lens.hash()).remove(lens);
            }
        }

        return boxes.entrySet().stream().map(box -> {
            long power = 0;
            for (int slot = 0; slot < box.getValue().size(); slot++) {
                power += (box.getKey() + 1)
                        * (slot + 1)
                        * box.getValue().get(slot).getFocalLength();
            }
            return power;
        }).reduce(Long::sum).orElseThrow();
    }
}
