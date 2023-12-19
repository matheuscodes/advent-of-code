package matheus.software.aoc2023.day19;

import lombok.Data;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.parseInt;

@Data
public final class Workflow {

    private String name;

    private List<Operation> operations;

    public Workflow(final String line) {
        var split = line.replaceAll("}", "").split("\\{");
        this.name = split[0];
        this.operations = Arrays.stream(split[1].split(","))
                .map(Operation::new).toList();
    }

    public String run(final MachinePart part) {
        for (Operation o: this.operations) {
            if (o.operate(part) != null) {
                return o.operate(part);
            }
        }
        throw new RuntimeException("Impossible!");
    }

    public HashMap<
            String,
            LinkedList<
                    HashMap<String, PartAvalanche.Range>
                    >
            > getDestinationRanges(
                    final HashMap<String, PartAvalanche.Range> partRange
    ) {
        var result = new HashMap<
                String,
                LinkedList<
                        HashMap<String, PartAvalanche.Range>
                        >
                >();
        var rest = partRange;
        for (Operation o: operations) {
            var cut = o.cutRange(rest);
            result.computeIfAbsent(o.destination, k -> new LinkedList<>());
            result.get(o.destination).add(cut[0]);
            rest = cut[1];
        }
        return result;
    }

    private static class Operation {

        private final String destination;
        private final String operation;
        private String key;
        private int value;

        Operation(final String asString) {
            if (!asString.contains(":")) {
                operation = "-";
                destination = asString;
            } else {
                var split = asString.split(":");
                this.destination = split[1];
                this.operation = split[0].contains("<") ? "<" : ">";
                split = split[0].split(this.operation);
                this.key = split[0];
                this.value = parseInt(split[1]);
            }
        }

        String operate(final MachinePart part) {
            return switch (operation) {
                case "<" -> part.getRating(key) < value ? destination : null;
                case ">" -> part.getRating(key) > value ? destination : null;
                default -> destination;
            };
        }

        public HashMap<String, PartAvalanche.Range>[] cutRange(
                final HashMap<String, PartAvalanche.Range> partRange
        ) {
            var part = partRange.get(key);
            var newPartRanges = new HashMap<>(partRange);
            var restPartRanges = new HashMap<>(partRange);
            if (key != null) {
                newPartRanges.put(key, switch (operation) {
                    case "<" -> part.to() < value ? part
                            : new PartAvalanche.Range(part.from(), value - 1);
                    case ">" -> part.from() > value ? part
                            : new PartAvalanche.Range(value + 1, part.to());
                    default -> part;
                });

                restPartRanges.put(key, switch (operation) {
                    case "<" -> part.from() > value ? part
                            : new PartAvalanche.Range(value, part.to());
                    case ">" -> part.to() < value ? part
                            : new PartAvalanche.Range(part.from(), value);
                    default -> part;
                });
            }
            return new HashMap[]{newPartRanges, restPartRanges};
        }
    }
}
