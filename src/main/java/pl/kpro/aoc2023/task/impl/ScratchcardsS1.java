package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScratchcardsS1 implements AdventTask {
    private static final String NAME = "ScratchcardsS1";
    private static final int NUMBER = 7;


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
        return Integer.toString(Arrays.stream(input.split("\n")).map(line -> line.substring(line.indexOf(':') + 1).trim()).mapToInt(this::countPoints).sum());
    }

    private Integer countPoints(String line) {
        List<Integer> winningNumbers = Arrays.stream(line.split("\\|")[0].trim().split(" +")).map(Integer::parseInt).toList();
        List<Integer> yourNumbers = Arrays.stream(line.split("\\|")[1].trim().split(" +")).map(Integer::parseInt).toList();
        int matches = 0;
        for (Integer number : yourNumbers) {
            if (winningNumbers.contains(number))
                matches++;
        }
        if (matches == 0)
            return 0;
        return 0x1 << matches - 1;
    }
}