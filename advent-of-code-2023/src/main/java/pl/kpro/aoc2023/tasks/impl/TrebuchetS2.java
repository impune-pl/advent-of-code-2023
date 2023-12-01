package pl.kpro.aoc2023.tasks.impl;

import pl.kpro.aoc2023.tasks.AdventTask;

import java.util.*;
import java.util.stream.Collectors;

public class TrebuchetS2 extends TrebuchetS1 implements AdventTask {
    private static final String NAME = "TrebuchetS2";
    private static final int NUMBER = 2;

    private static final Map<String, Integer> DIGIT_WORDS_TO_DIGITS;
    private static final Map<String, Integer> REVERSE_DIGIT_WORDS_TO_DIGITS;

    static {
        DIGIT_WORDS_TO_DIGITS = new HashMap<String, Integer>();
        DIGIT_WORDS_TO_DIGITS.put("one", 1);
        DIGIT_WORDS_TO_DIGITS.put("two", 2);
        DIGIT_WORDS_TO_DIGITS.put("three", 3);
        DIGIT_WORDS_TO_DIGITS.put("four", 4);
        DIGIT_WORDS_TO_DIGITS.put("five", 5);
        DIGIT_WORDS_TO_DIGITS.put("six", 6);
        DIGIT_WORDS_TO_DIGITS.put("seven", 7);
        DIGIT_WORDS_TO_DIGITS.put("eight", 8);
        DIGIT_WORDS_TO_DIGITS.put("nine", 9);
        REVERSE_DIGIT_WORDS_TO_DIGITS = new HashMap<String, Integer>();
        REVERSE_DIGIT_WORDS_TO_DIGITS.put("eno", 1);
        REVERSE_DIGIT_WORDS_TO_DIGITS.put("owt", 2);
        REVERSE_DIGIT_WORDS_TO_DIGITS.put("eerht", 3);
        REVERSE_DIGIT_WORDS_TO_DIGITS.put("ruof", 4);
        REVERSE_DIGIT_WORDS_TO_DIGITS.put("evif", 5);
        REVERSE_DIGIT_WORDS_TO_DIGITS.put("xis", 6);
        REVERSE_DIGIT_WORDS_TO_DIGITS.put("neves", 7);
        REVERSE_DIGIT_WORDS_TO_DIGITS.put("thgie", 8);
        REVERSE_DIGIT_WORDS_TO_DIGITS.put("enin", 9);
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getNumber() {
        return NUMBER;
    }

    @Override
    protected String findNumber(String line) {
        char[] charactersWithReplacedFirst = this.replaceFirstToken(line, this.getDigitNames(), DIGIT_WORDS_TO_DIGITS).toCharArray();
        char[] charactersWithReplacedLast = this.reverse(this.replaceFirstToken(this.reverse(line), this.getReversedDigitNames(), REVERSE_DIGIT_WORDS_TO_DIGITS)).toCharArray();
        return new String(new char[]{this.findFirstDigitIn(charactersWithReplacedFirst), this.findLastDigit(charactersWithReplacedLast)}).trim();
    }

    private String reverse(String line) {
        return new StringBuilder(line).reverse().toString();
    }

    private Set<String> getDigitNames() {
        return DIGIT_WORDS_TO_DIGITS.keySet();
    }

    private Set<String> getReversedDigitNames() {
        return REVERSE_DIGIT_WORDS_TO_DIGITS.keySet();
    }

    private String replaceFirstToken(String line, Set<String> tokens, Map<String, Integer> tokenReplacements) {
        Map<String, Integer> tokenPositions = new HashMap<>();
        for (String token : tokens) {
            tokenPositions.put(token, line.indexOf(token));
        }
        Optional<Map.Entry<String, Integer>> first = tokenPositions.entrySet().stream().filter(entry -> entry.getValue() >= 0).min(Map.Entry.comparingByValue());
        return first.map(stringIntegerEntry -> line.replace(stringIntegerEntry.getKey(), tokenReplacements.get(stringIntegerEntry.getKey()).toString())).orElse(line);
    }
}