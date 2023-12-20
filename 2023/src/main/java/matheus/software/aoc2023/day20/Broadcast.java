package matheus.software.aoc2023.day20;

import java.util.Queue;

public final class Broadcast extends Module {
    public Broadcast(final String name) {
        super(name.trim());
    }

    @Override
    public void proceed(final Message message, final Queue<Message> pulses) {
        var pulse = message.getPulse();
        if (pulse != null) {
            this.getOutputs().forEach(i -> {
                this.getSentPulses().add(pulse);
                pulses.add(new Message(pulse, i.getName(), this.getName()));
            });
        }
    }
}
