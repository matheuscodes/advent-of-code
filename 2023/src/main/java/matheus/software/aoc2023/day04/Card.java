package matheus.software.aoc2023.day04;

import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Data
public final class Card {

    private String name;

    private int id;

    private long copies = 1;

    private Set<Integer> winning;

    private Set<Integer> numbers;

    public Card(final String cardName) {
        this.name = cardName;
        this.id = parseInt(cardName.replaceAll("Card", "").trim());
    }

    public long getWorth() {
        int winners = getWinners();
        if (winners > 0) {
            return (long) Math.pow(2, winners - 1);
        }
        return 0;
    }

    public int getWinners() {
        return numbers.stream()
                .filter(i -> winning.contains(i))
                .collect(Collectors.toSet())
                .size();
    }

    public void addCopies(final long moreCopies) {
        this.copies += moreCopies;
    }
}
