package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.*;
import java.util.stream.Collectors;

public class CubeConundrumS2 implements AdventTask {
    private static final String NAME = "CubeConundrumS2";
    private static final int NUMBER = 4;

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
        return Long.valueOf(Arrays.stream(input.split("\n")).mapToLong(this::getGamePower).sum()).toString();
    }

    private Long getGamePower(String line) {
        line = line.substring(line.indexOf(':') + 1);
        Map<String, Optional<Map.Entry<String, Integer>>> map = Arrays.stream(line.split("[;,]")).map(String::trim).map(this::toCubeTypeAndNumber).collect(Collectors.groupingBy(Map.Entry::getKey, Collectors.maxBy(Comparator.comparingInt(Map.Entry::getValue))));
        return map.values().stream().filter(Optional::isPresent).mapToLong(stringIntegerEntry -> Long.valueOf(stringIntegerEntry.get().getValue())).reduce((a, b) -> a * b).orElseGet(() -> 0L);
    }

    private Map.Entry<String, Integer> toCubeTypeAndNumber(String descriptor) {
        return new AbstractMap.SimpleEntry<>(descriptor.split(" ")[1], Integer.valueOf(descriptor.split(" ")[0]));
    }
}