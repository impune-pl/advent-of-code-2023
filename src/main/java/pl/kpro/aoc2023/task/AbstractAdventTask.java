package pl.kpro.aoc2023.task;

public abstract class AbstractAdventTask implements AdventTask {
    private final String name;
    private final int number;

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getNumber() {
        return this.number;
    }

    public AbstractAdventTask(int number, String name) {
        this.name = name;
        this.number = number;
    }
}