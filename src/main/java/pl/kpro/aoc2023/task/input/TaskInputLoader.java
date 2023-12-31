package pl.kpro.aoc2023.task.input;

import java.nio.file.Path;
import java.util.List;

public interface TaskInputLoader {
    List<Path> getPossibleInputsForTask(String taskName);

    public String loadInput(Path path);
}