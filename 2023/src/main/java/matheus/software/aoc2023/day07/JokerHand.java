package matheus.software.aoc2023.day07;

import java.util.Map;

public final class JokerHand extends Hand {

    public JokerHand(
            final String cardsString,
            final String bidString,
            final Map<Character, Long> cardStrength
    ) {
        super(cardsString, bidString, cardStrength);
        this.setStrength(this.typeBonus());
    }

    @Override
    public long selection(final Map<Character, Integer> unique) {
        if (unique.containsKey('J')) {
            return switch (unique.size()) {
                case 1, 2 -> FIVE_OF_A_KIND;
                case 3 -> switch (unique.get('J')) {
                    case 1 -> unique.containsValue(3)
                            ? FOUR_OF_A_KIND : FULL_HOUSE;
                    case 2, 3 -> FOUR_OF_A_KIND;
                    default -> throw new RuntimeException("Impossible");
                };
                case 4 -> switch (unique.get('J')) {
                    case 1, 2 -> THREE_OF_A_KIND;
                    default -> throw new RuntimeException("Impossible");
                };
                case 5 -> ONE_PAIR;
                default -> throw new RuntimeException("Impossible");
            };
        } else {
            return switch (unique.size()) {
                case 1 -> FIVE_OF_A_KIND;
                case 2 -> unique.containsValue(1) ? FOUR_OF_A_KIND : FULL_HOUSE;
                case 3 -> unique.containsValue(3) ? THREE_OF_A_KIND : TWO_PAIR;
                case 4 -> ONE_PAIR;
                default -> 0L;
            };
        }
    }
}
