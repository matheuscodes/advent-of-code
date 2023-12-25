package matheus.software.aoc2023.day25;

import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


@Component
public final class Apparatus {

    public long cutWires(final String raw) {
        var lines = raw.split("\\n");
        var graph = new HashMap<String, HashSet<String>>();
        for (var line: lines) {
            var split = line.split(": ");
            var name = split[0];
            var connections = split[1].split("\\s");
            for (var connection: connections) {
                graph.computeIfAbsent(
                        name, (k) -> new HashSet<>()).add(connection);
                graph.computeIfAbsent(
                        connection, (k) -> new HashSet<>()).add(name);
            }
        }
        var edgeFrequency = new HashMap<HashSet<String>, Integer>();
        var nodes = new LinkedList<>(graph.keySet());
        for (int i = 0; i < nodes.size(); i++) {
            var start = nodes.get(i);
            for (int j = i + 1; j < nodes.size(); j++) {
                var target = nodes.get(j);
                markEdges(start, target, graph, edgeFrequency);
            }
        }

        edgeFrequency.entrySet().stream()
                .sorted(Comparator.comparingInt(e -> -e.getValue()))
                .limit(3)
                .forEach(e -> cutEdge(graph, e.getKey()));

        var first = graph.keySet().iterator().next();
        long partialSize = calculateGraphSize(first, graph);
        long rest = graph.size() - partialSize;

        return partialSize * rest;
    }

    private static void cutEdge(
            final HashMap<String, HashSet<String>> graph,
            final HashSet<String> edge) {
        var it = edge.iterator();
        String a = it.next();
        String b = it.next();
        graph.get(a).remove(b);
        graph.get(b).remove(a);
    }

    private static void markEdges(
            final String start,
            final String target,
            final HashMap<String, HashSet<String>> graph,
            final HashMap<HashSet<String>, Integer> edgeFrequency
    ) {
        Queue<Step> queue = new LinkedList<>();
        var visited = new HashSet<>();
        queue.add(new Step(start, List.of()));
        visited.add(start);
        while (!queue.isEmpty()) {
            Step current = queue.poll();
            if (target.equals(current.node)) {
                current.edges.forEach(edge -> {
                    int frequency = edgeFrequency.getOrDefault(edge, 0);
                    edgeFrequency.put(edge, frequency + 1);
                });
                return;
            }
            graph.get(current.node).stream()
                    .filter(n -> !visited.contains(n))
                    .forEach(n -> {
                        var nextEdges = new LinkedList<>(current.edges());
                        nextEdges.add(new HashSet<>(List.of(current.node, n)));
                        Step nextStep = new Step(n, nextEdges);
                        queue.add(nextStep);
                        visited.add(n);
                    });
        }
    }

    private static long calculateGraphSize(
            final String start,
            final HashMap<String, HashSet<String>> graph
    ) {
        Queue<String> queue = new LinkedList<>();
        var visited = new HashSet<String>();
        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            String current = queue.poll();
            graph.get(current).stream()
                    .filter(n -> !visited.contains(n))
                    .forEach(n -> {
                        queue.add(n);
                        visited.add(n);
                    });
        }
        return visited.size();
    }

    record Step(String node, List<HashSet<String>> edges) {

    }
}
