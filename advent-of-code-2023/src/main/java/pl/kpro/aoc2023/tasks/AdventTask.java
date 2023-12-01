package pl.kpro.aoc2023.tasks;

public interface AdventTask {
    String getName();

    int getNumber();

    String run(String input);
}