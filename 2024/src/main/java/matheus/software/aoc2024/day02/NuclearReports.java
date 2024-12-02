package matheus.software.aoc2024.day02;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@Component
public final class NuclearReports {
    public long countSafe(final String raw, final boolean tolerate) {
        long safeCount = 0;
        Stream<String> lines = Arrays.stream(raw.split("\\n"));
        List<List<Long>> reports = lines.map(
                line -> Arrays.stream(line.split("\\s"))
                              .map(Long::parseLong)
                              .toList()
        ).toList();
        for (List<Long> report: reports) {
            int position = findUnsafeSpot(report);
            if (position >= 0) {
                // Unsafe
                if (tolerate) {
                    ArrayList<Long> badDiff1 = new ArrayList<>(report);
                    ArrayList<Long> badDiff2 = new ArrayList<>(report);
                    ArrayList<Long> first = new ArrayList<>(report);
                    ArrayList<Long> last = new ArrayList<>(report);
                    badDiff1.remove(position);
                    badDiff2.remove(position - 1);
                    first.remove(0);
                    last.remove(report.size() - 1);
                    if (findUnsafeSpot(badDiff1) < 0
                        || findUnsafeSpot(badDiff2) < 0
                        || findUnsafeSpot(first) < 0
                        || findUnsafeSpot(last) < 0) {
                        safeCount += 1;
                    }
                }
            } else {
                safeCount += 1;
            }
        }

        return safeCount;
    }

    private int findUnsafeSpot(final List<Long> report) {
        long firstDiff = report.get(1) - report.get(0);
        if (firstDiff != 0) {
            for (int i = 1; i < report.size(); i += 1) {
                long diff = report.get(i) - report.get(i - 1);
                if ((Math.abs(diff) > 3)
                    || (firstDiff < 0 && diff >= 0)
                    || (firstDiff > 0 && diff <= 0)) {
                    return i;
                }
            }
        } else {

            return 1;
        }
        return -1;
    }
}
