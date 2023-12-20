package matheus.software.aoc2023.day20;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

@Component
public final class CableNetwork {
    public long countPulses(final String raw, final int pushes) {
        var lines = raw.split("\\n");
        var modules = new HashMap<String, Module>();
        for (String line: lines) {
            var created = switch (line.charAt(0)) {
                case 'b' -> new Broadcast(line.replaceAll("->.*", ""));
                case '%' -> new FlipFlop(line.replaceAll("->.*", ""));
                case '&' -> new Conjunction(line.replaceAll("->.*", ""));
                default -> throw new RuntimeException("Impossible");
            };
            modules.put(created.getName(), created);
        }

//        modules.put("output", new Output("output"));

        for (String line: lines) {
            var split = line.split(" -> ");
            var moduleName = split[0].replaceAll("[%&]", "");
            var module = modules.get(moduleName);
            for (String connection: split[1].split(",")) {
                if (modules.get(connection.trim()) == null) {
                    modules.put(connection, new Output(connection));
                }
                module.addOutput(modules.get(connection.trim()));
            }
        }
        var pulses = new LinkedList<String>();
        Queue<Message> messages = new LinkedList<>();
        for (int i = 0; i < pushes; i++) {
            messages.add(new Message("low", "broadcaster", "start"));
            while (!messages.isEmpty()) {
                var message = messages.poll();
                var module = modules.get(message.getDestination());
                module.process(message, messages, modules);
            }
        }

        modules.values().forEach(m -> pulses.addAll(m.getSentPulses()));

        long highs = pulses.stream().filter("high"::equals).count();
        long lows = pulses.stream().filter("low"::equals).count() + pushes;
        return highs * lows;
    }

    public long singleLowEnd(String raw) {
        var lines = raw.split("\\n");
        var modules = new HashMap<String, Module>();
        for (String line: lines) {
            var created = switch (line.charAt(0)) {
                case 'b' -> new Broadcast(line.replaceAll("->.*", ""));
                case '%' -> new FlipFlop(line.replaceAll("->.*", ""));
                case '&' -> new Conjunction(line.replaceAll("->.*", ""));
                default -> throw new RuntimeException("Impossible");
            };
            modules.put(created.getName(), created);
        }

//        modules.put("output", new Output("output"));

        for (String line: lines) {
            var split = line.split(" -> ");
            var moduleName = split[0].replaceAll("[%&]", "");
            var module = modules.get(moduleName);
            for (String connection: split[1].split(",")) {
                if (modules.get(connection.trim()) == null) {
                    modules.put(connection, new Output(connection));
                }
                module.addOutput(modules.get(connection.trim()));
            }
        }
        // Hint: Looked in the input for the required inputs for a low pulse.
        // rx <- zh <- (sx, jt, kb, ks)
        Long sx = null;
        Long jt = null;
        Long kb = null;
        Long ks = null;
        long counter = 0;
        Queue<Message> messages = new LinkedList<>();
        while (sx == null || jt == null || kb == null || ks == null) {
            messages.add(new Message("low", "broadcaster", "start"));
            while (!messages.isEmpty()) {
                var message = messages.poll();
                if ("low".equals(message.getPulse())) {
                    if ("sx".equals(message.getDestination()) && sx == null) {
                        sx = 0L;
                    }
                    if ("jt".equals(message.getDestination()) && jt == null) {
                        jt = 0L;
                    }
                    if ("kb".equals(message.getDestination()) && kb == null) {
                        kb = 0L;
                    }
                    if ("ks".equals(message.getDestination()) && ks == null) {
                        ks = 0L;
                    }
                }
                var module = modules.get(message.getDestination());
                module.process(message, messages, modules);
            }
            counter += 1;
            if (sx != null && sx == 0L) {
                sx = counter;
            }
            if (jt != null && jt == 0L) {
                jt = counter;
            }
            if (kb != null && kb == 0L) {
                kb = counter;
            }
            if (ks != null && ks == 0L) {
                ks = counter;
            }
        }

        return lcm(sx, lcm(jt, lcm(kb, ks)));
    }

    // https://www.baeldung.com/java-least-common-multiple
    private long lcm(final long number1, final long number2) {
        if (number1 == 0 || number2 == 0) {
            return 0;
        }
        long absNumber1 = Math.abs(number1);
        long absNumber2 = Math.abs(number2);
        long absHigherNumber = Math.max(absNumber1, absNumber2);
        long absLowerNumber = Math.min(absNumber1, absNumber2);
        long lcm = absHigherNumber;
        while (lcm % absLowerNumber != 0) {
            lcm += absHigherNumber;
        }
        return lcm;
    }
}
