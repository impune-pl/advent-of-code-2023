package pl.kpro.aoc2023.tasks.impl;

import pl.kpro.aoc2023.tasks.AdventTask;

import java.util.Arrays;

public class TrebuchetS1 implements AdventTask {
    private static final String NAME = "TrebuchetS1";
    private static final int NUMBER = 1;


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
        return Integer.toString(Arrays.stream(input.split("\n")).map(this::findNumber).mapToInt(Integer::parseInt).sum());
    }

    protected String findNumber(String line) {
        System.out.println(line);
        char[] characters = line.toCharArray();
        return new String(new char[]{this.findFirstDigitIn(characters), this.findLastDigit(characters)}).trim();
    }

    protected char findFirstDigitIn(char[] line) {
        for (char c : line) {
            if ('0' < c && c <= '9') {
                return c;
            }
        }
        return ' ';
    }

    protected char findLastDigit(char[] line) {
        for (int i = 1; i <= line.length; i++) {
            if ('0' <= line[line.length - i] && line[line.length - i] <= '9') {
                return line[line.length - i];
            }
        }
        return ' ';
    }
}