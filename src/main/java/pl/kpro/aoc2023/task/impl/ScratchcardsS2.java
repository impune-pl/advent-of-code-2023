package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScratchcardsS2 implements AdventTask {
    private static final String NAME = "ScratchcardsS2";
    private static final int NUMBER = 8;


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
        Map<Integer, Card> cards = Arrays.stream(input.split("\n")).map(Card::new).collect(Collectors.toMap(Card::getIndex, card -> card));

        int cardsCount = cards.keySet().stream().mapToInt(cardIndex -> this.countCardsWonBy(cardIndex, cards)).sum();

        return Integer.toString(cardsCount);
    }

    int countCardsWonBy(Integer cardIndex, Map<Integer, Card> cards) {
        if (cards.get(cardIndex).winsCardsWithIndexes.isEmpty()) {
            return 1;
        }
        return 1 + cards.get(cardIndex).winsCardsWithIndexes.stream()
                .mapToInt(index -> this.countCardsWonBy(index, cards)).sum();
    }


    class Card {
        final Integer index;
        final List<Integer> winningNumbers, yourNumbers, winsCardsWithIndexes;
        final Integer matchesCount;

        Card(String card) {
            this.index = Integer.parseInt(card.substring(5, card.indexOf(':')).trim());
            this.winningNumbers = Arrays.stream(card.substring(card.indexOf(':') + 1).split("\\|")[0].trim().split(" +")).map(Integer::parseInt).toList();
            this.yourNumbers = Arrays.stream(card.substring(card.indexOf(':') + 1).split("\\|")[1].trim().split(" +")).map(Integer::parseInt).toList();
            int matches = 0;
            for (Integer number : yourNumbers) {
                if (winningNumbers.contains(number))
                    matches++;
            }
            this.matchesCount = matches;
            if (matches != 0)
                this.winsCardsWithIndexes = IntStream.range(1, matches + 1).map(val -> this.index + val).boxed().toList();
            else
                this.winsCardsWithIndexes = Collections.emptyList();
        }

        Integer getIndex() {
            return this.index;
        }
    }
}