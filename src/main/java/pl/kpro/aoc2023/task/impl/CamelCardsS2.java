package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class CamelCardsS2 implements AdventTask {
    private static final String NAME = "CamelCardsS2";
    private static final int NUMBER = 14;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getNumber() {
        return NUMBER;
    }

    @Override
    public String run(String input) {
        Hand[] hands = Arrays.stream(input.split("\n")).map(Hand::new).sorted(Comparator.naturalOrder()).toArray(Hand[]::new);
        Long rank, sum;
        rank = 1L;
        sum = 0L;
        for (int i = 0; i < hands.length; i++) {
            Hand hand = hands[i];
            sum += rank * hand.bid;
            rank++;
        }
        return sum.toString();
    }

    enum Card {
        J, N2, N3, N4, N5, N6, N7, N8, N9, T ,Q ,K ,A;
    }

    class Hand implements Comparable<Hand> {
        final Card[] cards;
        final String definition;
        final Long bid;
        final int type;

        Hand(String cardsAndValue) {
            definition = cardsAndValue;
            Card[] possibleCards = Card.values();
            Card[] cards = Arrays.stream(cardsAndValue.split(" ")[0].split("")).map((s)->{
                for(Card c: possibleCards) {
                    if(c.name().contains(s))
                        return c;
                }
                throw new RuntimeException("Unknown card: "+s);
            }).toArray(Card[]::new);
            Long value = Long.valueOf(cardsAndValue.split(" ")[1].trim());
            this.type = this.type(cards);
            this.cards = cards;
            this.bid = value;
        }

        private int type(Card[] cards) {
            Map<Integer, Long> collected = Arrays.stream(cards).collect(Collectors.groupingBy(Card::ordinal, Collectors.counting()));
            int jokers = collected.get(Card.J.ordinal()) == null ? 0 : collected.get(Card.J.ordinal()).intValue();
            int[] cardCounts = collected.entrySet().stream().filter(e -> e.getKey() != Card.J.ordinal()).map(Map.Entry::getValue).mapToInt(Long::intValue).boxed().sorted(Comparator.reverseOrder()).mapToInt(Integer::intValue).toArray();

            if(jokers == 5 || jokers + cardCounts[0] == 5)
                return 6;
            if( jokers + cardCounts[0] == 4)
                return 5; // Four of kind
            if(jokers + cardCounts[0] == 3) {
                if (cardCounts[1] == 2)
                    return 4;
                return 3;
            }
            if(jokers + cardCounts[0] == 2) {
                if(cardCounts[1] == 2)
                    return 2;
                return 1;
            }
            if(jokers == 0 && cardCounts[0] == 1)
                return 0;
            throw new RuntimeException("Cards " + Arrays.stream(cards).map(Card::name).collect(Collectors.joining(",")));
        }
        @Override
        public int compareTo(Hand other) {
            if(this.type > other.type)
                return 1;
            else if (this.type < other.type)
                return -1;
            for (int i = 0; i < 5; i++) {
                if(this.cards[i].ordinal() > other.cards[i].ordinal())
                    return 1;
                else if (this.cards[i].ordinal() < other.cards[i].ordinal())
                    return -1;
            }
            return 0;
        }
    }
}
