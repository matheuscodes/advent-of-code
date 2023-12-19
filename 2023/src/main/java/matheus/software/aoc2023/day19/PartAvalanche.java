package matheus.software.aoc2023.day19;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class PartAvalanche {
    public long sumAcceptedRatings(final String raw) {
        String[] split = raw.split("\\n\\n");
        var parts = Arrays.stream(split[1].split("\\n"))
                .map(MachinePart::new).toList();
        var workflows = Arrays.stream(split[0].split("\\n"))
                .map(Workflow::new)
                .collect(Collectors.toMap(Workflow::getName, i -> i));
        LinkedList<MachinePart> accepted = new LinkedList<>();

        var queues = new HashMap<String, LinkedList<MachinePart>>();
        for (String key: workflows.keySet()) {
            queues.put(key, new LinkedList<>());
            if ("in".equals(key)) {
                queues.get(key).addAll(parts);
            }
        }

        while (queues.values().stream()
                        .map(List::size)
                        .reduce(0, Integer::sum) > 0) {
            for (var queue: queues.entrySet()) {
                while (!queue.getValue().isEmpty()) {
                    var first = queue.getValue().removeFirst();
                    var destination = workflows.get(queue.getKey()).run(first);
                    switch (destination) {
                        case "A":
                            accepted.add(first);
                            break;
                        case "R":
                            // Rejected...
                            break;
                        default:
                            queues.get(destination).add(first);
                    }
                }
            }
        }



        return accepted.stream()
                .map(MachinePart::finalRating)
                .reduce(0L, Long::sum);
    }

    public long possibleDistinct(final String raw, final String start) {
        String[] split = raw.split("\\n\\n");
        var workflows = Arrays.stream(split[0].split("\\n"))
                .map(Workflow::new)
                .collect(Collectors.toMap(Workflow::getName, i -> i));

        var partRange = new HashMap<String, Range>();
        partRange.put("x", new Range(1, 4000));
        partRange.put("m", new Range(1, 4000));
        partRange.put("a", new Range(1, 4000));
        partRange.put("s", new Range(1, 4000));

        LinkedList<HashMap<String, Range>> accepted = new LinkedList<>();

        var toDo = new HashMap<String, HashMap<String, PartAvalanche.Range>>();
        toDo.put(start, partRange);
        while (!toDo.keySet().isEmpty()) {
            String key = toDo.keySet().stream().findFirst().orElseThrow();
            var thisPartRange = toDo.get(key);
            toDo.remove(key);
            var workflow = workflows.get(key);
            var calculated = workflow.getDestinationRanges(thisPartRange);
            for (var destination: calculated.entrySet()) {
                if (toDo.get(destination.getKey()) != null) {
                    throw new UnsupportedOperationException("No circles.");
                } else if ("A".equals(destination.getKey())) {
                    accepted.addAll(destination.getValue());
                } else if (!"R".equals(destination.getKey())) {
                    if (destination.getValue().size() > 1) {
                        throw new UnsupportedOperationException("Damn.");
                    }
                    toDo.put(
                            destination.getKey(),
                            destination.getValue().get(0)
                    );
                }
            }
        }

        return accepted.stream()
                .map(m -> m.values().stream()
                        .map(Range::possibilities)
                        .reduce(1L, (acc, i) -> i * acc))
                .reduce(0L, Long::sum);
    }

    public record Range(int from, int to) {
        public long possibilities() {
            return to - from + 1;
        }
    }
}
