package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

public class Echo implements AdventTask {
    private static final String NAME = "Echo";
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
        return input;
    }
}