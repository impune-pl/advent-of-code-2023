package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class CamelCardsS1 implements AdventTask {
    private static final String NAME = "CamelCardsS1";
    private static final int NUMBER = 13;

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
        for (Hand hand : hands) {
            sum += rank * hand.bid;
            rank++;
        }
        return sum.toString();
    }

    enum Card {
        N2, N3, N4, N5, N6, N7, N8, N9, T ,J ,Q ,K ,A;
    }

    class Hand implements Comparable<Hand> {
        final Card[] cards;
        final Long bid;
        final int type;

        Hand(String cardsAndValue) {
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
            if(collected.containsValue(5L))
                return 6;
            if(collected.containsValue(4L))
                return 5; // Four of kind
            if(collected.containsValue(3L)) {
                if (collected.containsValue(2L))
                    return 4; // Full house
                // Not full house, and contains 3 of kind;
                return 3; // Three of kind
            }
            if(collected.containsValue(2L)) {
                // return 1 or 2
                return Math.toIntExact(collected.values().stream().filter(v -> v == 2L).count());
            }
            return 0;
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
