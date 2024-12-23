package matheus.software.aoc2024.day23;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public final class LANParty {
    public long findLANs(final String raw) {
        String[] lines = raw.split("\\n");
        HashMap<String, HashSet<String>> computers = new HashMap<>();
        for (String line: lines) {
            String[] machines = line.split("-");
            for (String machine: machines) {
                if (!computers.containsKey(machine)) {
                    computers.put(machine, new HashSet<>());
                    computers.get(machine).add(machine);
                }
            }
            computers.get(machines[0]).add(machines[1]);
            computers.get(machines[1]).add(machines[0]);
        }

        HashSet<String> uniques = new HashSet<>();
        for (String i: computers.keySet()) {
            for (String j: computers.keySet()) {
                for (String k: computers.keySet()) {
                    if (!i.equals(j) && !i.equals(k) && !j.equals(k)) {
                        if (
                                computers.get(i).containsAll(List.of(j, k))
                                && computers.get(j).containsAll(List.of(i, k))
                                && computers.get(k).containsAll(List.of(i, j))
                        ) {
                            if (i.startsWith("t") || j.startsWith("t") || k.startsWith("t")) {
                                uniques.add(Stream.of(i, j, k).sorted().collect(Collectors.joining(",")));
                            }
                        }
                    }
                }
            }
        }
        return uniques.size();
    }

    public String findLargestLAN(final String raw) {
        String[] lines = raw.split("\\n");
        HashMap<String, HashSet<String>> computers = new HashMap<>();
        for (String line: lines) {
            String[] machines = line.split("-");
            for (String machine: machines) {
                if (!computers.containsKey(machine)) {
                    computers.put(machine, new HashSet<>());
                    computers.get(machine).add(machine);
                }
            }
            computers.get(machines[0]).add(machines[1]);
            computers.get(machines[1]).add(machines[0]);
        }

        HashSet<String> uniques = new HashSet<>(computers.keySet());
        recurse(uniques, computers);
        return uniques.stream().max(Comparator.comparingInt(String::length)).orElseThrow();
    }

    private void recurse(final HashSet<String> uniques, final HashMap<String, HashSet<String>> connections) {
        String largest = uniques.stream().max(Comparator.comparingInt(String::length)).orElseThrow();
        uniques.removeIf(a -> a.length() < largest.length());
        long size = uniques.size();
        for (String computer: connections.keySet()) {
            HashSet<String> newones = new HashSet<>();
            uniques.forEach(unique -> {
                if (unique.contains(computer)) {
                    return;
                }
                HashSet<String> set = new HashSet<>(List.of(unique.split(",")));
                set.add(computer);
                boolean interconnected = true;
                for (String i: set) {
                    for (String j: set) {
                        if (!connections.get(i).contains(j)) {
                            interconnected = false;
                        }
                    }
                }
                if (interconnected) {
                    newones.add(set.stream().sorted().collect(Collectors.joining(",")));
                }
            });
            uniques.addAll(newones);
        }
        if (uniques.size() > size) {
            // New items...
            recurse(uniques, connections);
        }
    }
}
