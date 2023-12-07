package matheus.software.aoc2023.day07;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;


@Data
public class Hand implements Comparable<Hand> {

    private long bid;

    private String cards;

    private long strength;

    public static final long FIVE_OF_A_KIND = 60000;
    public static final long FOUR_OF_A_KIND = 50000;
    public static final long FULL_HOUSE = 40000;
    public static final long THREE_OF_A_KIND = 30000;
    public static final long TWO_PAIR = 20000;
    public static final long ONE_PAIR = 10000;

    private Map<Character, Long> map;

    public Hand(
            final String cardsString,
            final String bidString,
            final Map<Character, Long> cardStrength
    ) {
        this.bid = Long.parseLong(bidString);
        this.cards = cardsString;
        this.strength = this.typeBonus();
        this.map = cardStrength;
    }

    final long typeBonus() {
        Map<Character, Integer> unique = new HashMap<>();
        for (Character c: this.getCards().toCharArray()) {
            Integer current = unique.get(c);
            if (current == null) {
                current = 0;
            }
            unique.put(c, current + 1);
        }
        return selection(unique);
    }

    /**
     * Can be overwritten.
     * @param unique - map of found cards and their count.
     * @return bonus amount.
     */
    public long selection(final Map<Character, Integer> unique) {
        return switch (unique.size()) {
            case 1 -> FIVE_OF_A_KIND;
            case 2 -> unique.containsValue(1) ? FOUR_OF_A_KIND : FULL_HOUSE;
            case 3 -> unique.containsValue(3) ? THREE_OF_A_KIND : TWO_PAIR;
            case 4 -> ONE_PAIR;
            default -> 0L;
        };
    }

    @Override
    public final int compareTo(final Hand hand) {
        if (hand.getStrength() != this.getStrength()) {
            return (int) (this.getStrength() - hand.getStrength());
        } else {
            char[] first = this.getCards().toCharArray();
            char[] second = hand.getCards().toCharArray();
            for (int i = 0; i < first.length; i++) {
                if (first[i] != second[i]) {
                    long dif = getMap().get(first[i]) - getMap().get(second[i]);
                    return (int) dif;
                }
            }
            return 0;
        }
    }
}
