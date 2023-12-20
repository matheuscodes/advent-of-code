package matheus.software.aoc2023.day20;

import java.util.Queue;

public class Output extends Module {
    public Output(final String thisName) {
        super(thisName);
    }

    @Override
    public void proceed(final Queue<Message> pulses) {
        this.consumeFirstPulse();
    }
}
