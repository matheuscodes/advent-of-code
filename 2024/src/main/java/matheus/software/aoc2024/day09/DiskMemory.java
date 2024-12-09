package matheus.software.aoc2024.day09;

import org.javatuples.Pair;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

@Component
public final class DiskMemory {

    public long optimize(final String raw, final boolean fragment) {
        String[] tokens = raw.replaceAll("\\n", "").split("");
        int totalSize =  Arrays.stream(tokens).map(Integer::parseInt).reduce(0, Integer::sum);
        short[] blocks = new short[totalSize];
        Arrays.fill(blocks, (short) -1);

        Integer[] files = Arrays.stream(tokens).map(Integer::parseInt).toArray(Integer[]::new);
        short fileID = 0;
        int filePointer = 0;
        HashMap<Integer, Integer> freeMemory = new HashMap<>();
        HashMap<Short, Pair<Integer, Integer>> usedMemory = new HashMap<>();
        for (int i = 0; i < files.length; i += 1) {
            if (i % 2 == 0) {
                // File
                for (int j = filePointer; j < filePointer + files[i]; j += 1) {
                    blocks[j] = fileID;
                }
                usedMemory.put(fileID, new Pair<>(files[i], filePointer));
                fileID += 1;
            } else {
                freeMemory.put(filePointer, files[i]);
            }
            filePointer = filePointer + files[i];
        }

        if (fragment) {
            fragment(blocks);
        } else {
            shuffle(blocks, freeMemory, usedMemory);
        }
        long sum = 0;
        for (int i = 0; i < blocks.length; i += 1) {
            if (blocks[i] >= 0) {
                sum += (long) i * blocks[i];
            }
        }
        return sum;
    }

    private void fragment(final short[] blocks) {
        int left = 0;
        int right = blocks.length - 1;
        while (left < right) {
            if (blocks[left] >= 0) {
                left += 1;
            } else if (blocks[right] < 0) {
                right -= 1;
            } else {
                blocks[left] = blocks[right];
                blocks[right] = -1;
                left += 1;
                right -= 1;
            }
        }
    }

    private void shuffle(final short[] blocks,
                         final HashMap<Integer, Integer> freeMemory,
                         final HashMap<Short, Pair<Integer, Integer>> usedMemory) {
        for (Short file: usedMemory.keySet().stream().sorted(Comparator.comparingInt(a -> -a)).toList()) {
            Integer neededSpace = usedMemory.get(file).getValue0();
            Integer position = usedMemory.get(file).getValue1();
            for (Integer freePosition: freeMemory.keySet().stream().sorted().toList()) {
                int freeSpace = freeMemory.get(freePosition);
                if (freeSpace >= neededSpace && position > freePosition) {
                    for (int i = 0; i < neededSpace; i += 1) {
                        blocks[position + i] = -1;
                        blocks[freePosition + i] = file;
                    }
                    freeMemory.remove(freePosition);
                    if (freeSpace > neededSpace) {
                        freeMemory.put(freePosition + neededSpace, freeSpace - neededSpace);
                    }
                    break;
                }
            }
        }
    }
}
