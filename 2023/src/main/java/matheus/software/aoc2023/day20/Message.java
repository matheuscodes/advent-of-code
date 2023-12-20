package matheus.software.aoc2023.day20;

import lombok.Data;

@Data
public class Message {
    private String pulse;
    private String destination;
    private String source;

    public Message(
            final String thisPulse,
            final String thisDestination,
            final String thisSource
    ) {
        this.pulse = thisPulse;
        this.destination = thisDestination;
        this.source = thisSource;
    }
}
