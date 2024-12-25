package matheus.software.aoc2024.day25;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;

@Component
public final class LockedDoors {
    public long findKeyPairs(final String raw) {
        String[] blocks = raw.split("\\n\\n");
        LinkedList<Integer[]> keys = new LinkedList<>();
        LinkedList<Integer[]> locks = new LinkedList<>();

        for (String block: blocks) {
            String[] rows = block.split("\\n");
            Integer[] key = new Integer[] {0, 0, 0, 0, 0};
            Integer[] lock = new Integer[] {5, 5, 5, 5, 5};
            for (int i = 1; i < rows.length - 1; i += 1) {
                char[] pins = rows[i].toCharArray();
                for (int j = 0; j < pins.length; j += 1) {
                    key[j] += pins[j] == '#' ? 1 : 0;
                    lock[j] -= pins[j] == '.' ? 1 : 0;
                }
            }
            if ("#####".equals(rows[0])) {
                //Lock
                locks.add(lock);
            } else {
                //Key
                keys.add(key);
            }
        }
        HashSet<String> pairs = new HashSet<>();
        for (Integer[] lock: locks) {
            for (Integer[] key: keys) {
                boolean matches = true;
                for (int i = 0; i < 5; i += 1) {
                    if (key[i] + lock[i] > 5) {
                        matches = false;
                        break;
                    }
                }
                if (matches) {
                    pairs.add(Arrays.toString(lock) + Arrays.toString(key));
                }
            }
        }
        return pairs.size();
    }
}
