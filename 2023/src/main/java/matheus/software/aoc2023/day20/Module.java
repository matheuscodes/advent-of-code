package matheus.software.aoc2023.day20;

import lombok.Data;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

@Data
public abstract class Module {

    private String name;
    private LinkedList<Module> inputs = new LinkedList<>();
    private LinkedList<Module> outputs = new LinkedList<>();

    private LinkedList<String> sentPulses = new LinkedList<>();
    private LinkedList<String> pendingPulses = new LinkedList<>();

    public Module(final String thisName) {
        this.name = thisName;
    }

    public void addInput(Module m) {
        inputs.add(m);
    }

    public void addOutput(Module m) {
        outputs.add(m);
        m.addInput(this);
    }

    public abstract void proceed(Queue<Message> pulses);

    public void receive(final String pulse, final Module whoAmI) {
        this.getPendingPulses().add(pulse);
    }

    public final String consumeFirstPulse() {
        if (this.getPendingPulses().size() > 0) {
            return this.getPendingPulses().removeFirst();
        }
        return null;
    }

    public void process(
            final Message message,
            final Queue<Message> messages,
            final HashMap<String, Module> modules
    ) {
        this.receive(message.getPulse(), modules.get(message.getSource()));
        this.proceed(messages);
    }
}
