package matheus.software.aoc2023.day20;

import java.util.HashMap;
import java.util.Queue;

public final class Conjunction extends Module {

    private final HashMap<String, String> memory = new HashMap<>();

    public Conjunction(final String name) {
        super(name.trim().replaceAll("&", ""));
    }


//    @Override
//    public void receive(final String pulse, final Module whoAmI) {
//        newMemory.computeIfAbsent(whoAmI.getName(), k -> new LinkedList<>());
//        newMemory.get(whoAmI.getName()).add(pulse);
//    }

    @Override
    public void proceed(final Message message, final Queue<Message> pulses) {
//        boolean received = false;
//        for (var entry: newMemory.entrySet()) {
//            if (entry.getValue().size() > 0) {
//                var item = entry.getValue().removeFirst();
//                if (item != null) {
//                    memory.put(entry.getKey(), item);
//                }
//                received = true;
//            }
//        }
//        if (!received) {
//            return;
//        }
        memory.put(message.getSource(), message.getPulse());
        if (memory.values().stream().allMatch("high"::equals)) {
            this.getOutputs().forEach(i -> {
                this.getSentPulses().add("low");
                pulses.add(new Message("low", i.getName(), this.getName()));
            });
        } else {
            this.getOutputs().forEach(i -> {
                this.getSentPulses().add("high");
                pulses.add(new Message("high", i.getName(), this.getName()));
            });
        }
    }

    @Override
    public void addInput(final Module m) {
        super.addInput(m);
        memory.put(m.getName(), "low");
    }
}
