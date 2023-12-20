package matheus.software.aoc2023.day20;

import java.util.HashMap;
import java.util.Queue;

public final class Conjunction extends Module {

    private final HashMap<String, String> memory = new HashMap<>();

    public Conjunction(final String name) {
        super(name.trim().replaceAll("&", ""));
    }

    @Override
    public void proceed(final Message message, final Queue<Message> pulses) {
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
