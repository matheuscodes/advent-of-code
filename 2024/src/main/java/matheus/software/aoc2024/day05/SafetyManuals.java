package matheus.software.aoc2024.day05;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
public final class SafetyManuals {

    public long sumCorrectMidPages(final String raw) {
        String rules = raw.split("\\n\\n")[0];
        String manuals = raw.split("\\n\\n")[1];
        HashMap<String, LinkedList<String>> larger = new HashMap<>();
        for (String rule: rules.split("\\n")) {
            String[] split = rule.split("\\|");
            if (!larger.containsKey(split[0])) {
                larger.put(split[0], new LinkedList<>());
            }
            larger.get(split[0]).add(split[1]);
        }
        long sum = 0;
        for (String manual: manuals.split("\n")) {
            String[] pages = manual.split(",");
            if (correctOrder(pages, larger)) {
                sum += Long.parseLong(pages[pages.length / 2]);
            }
        }
        return sum;
    }

    public long sumIncorrectMidPages(final String raw) {
        String rules = raw.split("\\n\\n")[0];
        String manuals = raw.split("\\n\\n")[1];
        HashMap<String, LinkedList<String>> larger = new HashMap<>();
        HashMap<String, LinkedList<String>> smaller = new HashMap<>();
        for (String rule: rules.split("\\n")) {
            String[] split = rule.split("\\|");
            if (!larger.containsKey(split[0])) {
                larger.put(split[0], new LinkedList<>());
            }
            larger.get(split[0]).add(split[1]);
            if (!smaller.containsKey(split[1])) {
                smaller.put(split[1], new LinkedList<>());
            }
            smaller.get(split[1]).add(split[0]);
        }
        long sum = 0;
        for (String manual: manuals.split("\n")) {
            String[] pages = manual.split(",");
            if (!correctOrder(pages, larger)) {
                List<String> newPages = Arrays.stream(pages).sorted((a, b) -> {
                    if (larger.containsKey(a) && larger.get(a).contains(b)) {
                        return -1;
                    }
                    if (smaller.containsKey(b) && smaller.get(b).contains(a)) {
                        return 1;
                    }
                    return 0;
                }).toList();
                sum += Long.parseLong(newPages.get(pages.length / 2));
            }
        }
        return sum;
    }

    private boolean correctOrder(final String[] pages, final HashMap<String, LinkedList<String>> larger) {
        for (int i = 0; i < pages.length; i += 1) {
            for (int j = 0; j < pages.length; j += 1) {
                if (j < i) {
                    if (larger.containsKey(pages[i])
                        && larger.get(pages[i]).contains(pages[j])) {
                        return false;
                    }
                } else {
                    if (larger.containsKey(pages[j])
                        && larger.get(pages[j]).contains(pages[i])) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
