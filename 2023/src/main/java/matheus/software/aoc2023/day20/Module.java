package matheus.software.aoc2023.day20;

import lombok.Data;

import java.util.LinkedList;
import java.util.Queue;

@Data
public abstract class Module {

    private String name;
    private LinkedList<Module> inputs = new LinkedList<>();
    private LinkedList<Module> outputs = new LinkedList<>();

    private LinkedList<String> sentPulses = new LinkedList<>();

    public Module(final String thisName) {
        this.name = thisName;
    }

    /**
     * Links modules who give input.
     * @param m - origin module
     */
    public void addInput(final Module m) {
        inputs.add(m);
    }

    public final void addOutput(final Module m) {
        outputs.add(m);
        m.addInput(this);
    }

    public abstract void proceed(Message message, Queue<Message> pulses);
}
