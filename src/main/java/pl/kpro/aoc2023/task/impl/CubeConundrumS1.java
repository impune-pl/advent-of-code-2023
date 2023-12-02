package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class CubeConundrumS1 implements AdventTask {
    private static final String NAME = "CubeConundrumS1";
    private static final int NUMBER = 3;

    private static final Map<String, Integer> CUBE_TYPES_LIMITS;

    static {
        CUBE_TYPES_LIMITS = new HashMap<>();
        CUBE_TYPES_LIMITS.put("red", 12);
        CUBE_TYPES_LIMITS.put("green", 13);
        CUBE_TYPES_LIMITS.put("blue", 14);
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
    public String run(String input) {
        return Integer.valueOf(Arrays.stream(input.split("\n")).map(this::isGamePossible).filter(Map.Entry::getValue).mapToInt(Map.Entry::getKey).sum()).toString();
    }

    private Map.Entry<Integer, Boolean> isGamePossible(String gameDescriptor) {
        Integer gameId = this.getGameId(gameDescriptor);
        Boolean isGameValid = this.validateGame(gameDescriptor);
        return new AbstractMap.SimpleEntry<>(gameId, isGameValid);
    }

    private Boolean validateGame(String gameDescriptor) {
        Boolean isValid = true;
        String[] roundDescriptors = gameDescriptor.substring(gameDescriptor.indexOf(':') + 1).split(";");
        for (String roundDescriptor : roundDescriptors) {
            isValid = isValid && Arrays.stream(roundDescriptor.split(",")).map(String::trim).map(this::toCubeTypeAndNumber).noneMatch(this::isNumberInvalidForType);
        }
        return isValid;
    }

    private Map.Entry<String, Integer> toCubeTypeAndNumber(String descriptor) {
        return new AbstractMap.SimpleEntry<>(descriptor.split(" ")[1], Integer.valueOf(descriptor.split(" ")[0]));
    }

    private Boolean isNumberInvalidForType(Map.Entry<String, Integer> typeAndNumber) {
        return !(CUBE_TYPES_LIMITS.get(typeAndNumber.getKey()) >= typeAndNumber.getValue());
    }

    private Integer getGameId(String gameDescriptor) {
        return Integer.valueOf(gameDescriptor.substring(5, gameDescriptor.indexOf(':')));
    }
}