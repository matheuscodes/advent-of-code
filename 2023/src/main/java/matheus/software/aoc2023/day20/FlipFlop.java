package matheus.software.aoc2023.day20;

import java.util.Queue;

public class FlipFlop extends Module {

    private boolean turnedOn = false;

    public FlipFlop(final String name) {
        super(name.trim().replaceAll("%", ""));
    }

    @Override
    public void proceed(final Queue<Message> pulses) {
        var pulse = this.consumeFirstPulse();
        if ("low".equals(pulse)) {
            if (turnedOn) {
                turnedOn = false;
                this.getOutputs().forEach(i -> {
                    this.getSentPulses().add("low");
                    pulses.add(new Message("low", i.getName(), this.getName()));
                });
            } else {
                turnedOn = true;
                this.getOutputs().forEach(i -> {
                    this.getSentPulses().add("high");
                    pulses.add(
                            new Message("high", i.getName(), this.getName())
                    );
                });
            }
        }
    }
}
