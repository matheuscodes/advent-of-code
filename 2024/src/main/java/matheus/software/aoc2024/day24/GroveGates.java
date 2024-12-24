package matheus.software.aoc2024.day24;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@Component
public final class GroveGates {
    public long numberFromBooleans(final String raw) {
        HashMap<String, Boolean> gates = new HashMap<>();
        String[] split = raw.split("\\n\\n");
        for (String line: split[0].split("\\n")) {
            String[] state = line.split(": ");
            gates.put(state[0], "1".equals(state[1]));
        }
        LinkedList<String> later = new LinkedList<>(List.of(split[1].split("\\n")));
        while (!later.isEmpty()) {
            String line = later.poll();
            String[] operands = line.split(" (AND|XOR|OR|->) ");
            String command = line.replaceAll(".*\\s(AND|XOR|OR)\\s.*", "$1");
            if (gates.containsKey(operands[0]) && gates.containsKey(operands[1])) {
                switch (command) {
                    case "AND" -> gates.put(operands[2], gates.get(operands[0]) & gates.get(operands[1]));
                    case "OR" -> gates.put(operands[2], gates.get(operands[0]) | gates.get(operands[1]));
                    case "XOR" -> gates.put(operands[2], gates.get(operands[0]) ^ gates.get(operands[1]));
                    default -> throw new RuntimeException("Impossible.");
                }
            } else {
                later.add(line);
            }
        }

        List<String> result = gates.keySet().stream().filter(a -> a.startsWith("z")).sorted().toList();
        long value = 0;
        for (int i = 0; i < result.size(); i += 1) {
            if (gates.get(result.get(i))) {
                value += Math.pow(2, i);
            }
        }
        return value;
    }

    public String findCrossedWires(final String raw) {
        String[] split = raw.split("\\n\\n");

        List<String> faultyGates = findFaultyGates(split[1].split("\\n"));
        return String.join(",", faultyGates);
    }


    private List<String> findFaultyGates(final String[] lines) {
        final List<String> faultyGates = new ArrayList<>();
        /*
         * There are 4 cases in which is faulty:
         *
         * 1. If there is output to a z-wire, the operator should always be XOR (unless
         * it is the final bit). If not -> faulty.
         * 2. If the output is not a z-wire and the inputs are not x and y, the operator
         * should always be AND or OR. If not -> faulty.
         * 3. If the inputs are x and y (but not the first bit) and the operator is XOR,
         * the output wire should be the input of another XOR-gate. If not -> faulty.
         * 4. If the inputs are x and y (but not the first bit) and the operator is AND,
         * the output wire should be the input of an OR-gate. If not -> faulty.
         */
        for (final String line : lines) {
            String[] command = line.split(" -> ");
            String[] operation = command[0].split(" ");
            String outputWire = command[1];
            String operator = operation[1];
            String[] operands = new String[] {operation[0], operation[2]};
            if (outputWire.startsWith("z") && !outputWire.equals("z45")) {
                if (!operator.equals("XOR")) {
                    faultyGates.add(outputWire);
                }
            } else {
                boolean firstOperandIsInput = operands[0].startsWith("x") || operands[0].startsWith("y");
                boolean secondOperandIsInput = operands[1].startsWith("x") || operands[1].startsWith("y");
                if (!outputWire.startsWith("z")
                        && !firstOperandIsInput
                        && !secondOperandIsInput) {
                    if (operator.equals("XOR")) {
                        faultyGates.add(outputWire);
                    }
                } else {
                    boolean isFirstBit = !(operands[0].endsWith("00") && operands[1].endsWith("00"));
                    if (operator.equals("XOR")
                            && firstOperandIsInput
                            && secondOperandIsInput) {
                        if (isFirstBit) {
                            if (notFoundAnother(lines, line, outputWire, "XOR")) {
                                faultyGates.add(outputWire);
                            }
                        }
                    } else if (operator.equals("AND")
                            && firstOperandIsInput
                            && secondOperandIsInput) {
                        if (isFirstBit) {
                            if (notFoundAnother(lines, line, outputWire, "OR")) {
                                faultyGates.add(outputWire);
                            }
                        }
                    }
                }
            }
        }
        return faultyGates.stream().sorted().toList();
    }

    private boolean notFoundAnother(final String[] lines, final String currentLine, final String outputWire, final String operator) {
        for (final String line : lines) {
            if (!line.equals(currentLine)) {
                String[] command = line.split(" -> ");
                String[] operation = command[0].split(" ");
                if ((operation[0].equals(outputWire) || operation[2].equals(outputWire))
                        && operation[1].equals(operator)) {
                    return false;
                }
            }
        }
        return true;
    }
}
