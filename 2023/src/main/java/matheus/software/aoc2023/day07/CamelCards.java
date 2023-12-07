package matheus.software.aoc2023.day07;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public final class CamelCards {

    private static final Map<Character, Long> CARD_STRENGTH;
    static {
        CARD_STRENGTH = Map.ofEntries(
                Map.entry('A', 12L),
                Map.entry('K', 11L),
                Map.entry('Q', 10L),
                Map.entry('J', 9L),
                Map.entry('T', 8L),
                Map.entry('9', 7L),
                Map.entry('8', 6L),
                Map.entry('7', 5L),
                Map.entry('6', 4L),
                Map.entry('5', 3L),
                Map.entry('4', 2L),
                Map.entry('3', 1L),
                Map.entry('2', 0L)
        );
    }

    private static final Map<Character, Long> JOKER_CARD_STRENGTH;
    static {
        JOKER_CARD_STRENGTH = Map.ofEntries(
                Map.entry('A', 12L),
                Map.entry('K', 11L),
                Map.entry('Q', 10L),
                Map.entry('T', 9L),
                Map.entry('9', 8L),
                Map.entry('8', 7L),
                Map.entry('7', 6L),
                Map.entry('6', 5L),
                Map.entry('5', 4L),
                Map.entry('4', 3L),
                Map.entry('3', 2L),
                Map.entry('2', 1L),
                Map.entry('J', 0L)
        );
    }
    private List<Hand> parseHands(final String raw) {
        String[] hands = raw.split("\\n");
        return Arrays.stream(hands).map(i -> i.split("\\s"))
                .map(i -> new Hand(i[0], i[1], CARD_STRENGTH))
                .sorted()
                .toList();
    }

    private List<JokerHand> parseJokerHands(final String raw) {
        String[] hands = raw.split("\\n");
        return Arrays.stream(hands).map(i -> i.split("\\s"))
                .map(i -> new JokerHand(i[0], i[1], JOKER_CARD_STRENGTH))
                .sorted()
                .toList();
    }

    public long totalWinnings(final String raw) {
        List<Hand> hands = parseHands(raw);
        long winnings = 0L;
        for (int i = 0; i < hands.size(); i++) {
            winnings += hands.get(i).getBid() * (i + 1);
        }
        return winnings;
    }

    public long totalJokerWinnings(final String raw) {
        List<JokerHand> hands = parseJokerHands(raw);
        long winnings = 0L;
        for (int i = 0; i < hands.size(); i++) {
            winnings += hands.get(i).getBid() * (i + 1);
        }
        return winnings;
    }
}
