package matheus.software.aoc2023.day04;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public final class CardPile {

    public List<Card> parseCards(final String raw) {
        String[] rawCards = raw.split("\\n");
        List<Card> cards = new ArrayList<>();
        for (String rawCard: rawCards) {
            String[] firstSplit = rawCard.split(":");
            String[] secondSplit = firstSplit[1].split("\\|");
            Card card = new Card(firstSplit[0]);
            card.setWinning(
                    Arrays.stream(secondSplit[0].split("\\s"))
                            .filter(i -> i.length() > 0)
                            .map(Integer::parseInt)
                            .collect(Collectors.toSet())
            );
            card.setNumbers(
                    Arrays.stream(secondSplit[1].split("\\s"))
                            .filter(i -> i.length() > 0)
                            .map(Integer::parseInt)
                            .collect(Collectors.toSet())
            );
            cards.add(card);
        }
        return cards;
    }

    public long cardsWorth(final String raw) {
        List<Card> cards = parseCards(raw);
        return cards.stream().map(Card::getWorth).reduce(Long::sum).orElse(0L);
    }

    public long multiplyCards(final String raw) {
        List<Card> cards = parseCards(raw);
        for (Card card: cards) {
            int winners = card.getWinners();
            for (int i = 0; i < winners; i++) {
                cards.get(card.getId() + i).addCopies(card.getCopies());
            }
        }
        return cards.stream().map(Card::getCopies).reduce(Long::sum).orElse(0L);
    }
}
