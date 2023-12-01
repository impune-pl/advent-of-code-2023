package pl.kpro.aoc2023.taskInputs;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TaskInputLoaderImpl implements TaskInputLoader {

    @Override
    public List<Path> getPossibleInputsForTask(String taskName) {
        List<Path> inputPathList = new ArrayList<>();
        try {
            URL resourceUrl = getClass().getClassLoader().getResource("./tasks");
            if (resourceUrl != null) {
                Path resourcePath = Paths.get(resourceUrl.toURI());
                try (Stream<Path> paths = Files.walk(resourcePath, 1)) {
                    paths.filter(Files::isRegularFile)
                            .filter(filePath -> filePath.getFileName().toString().contains(taskName))
                            .forEach(inputPathList::add);
                }
            }
        } catch (URISyntaxException | IOException e) {
            throw new RuntimeException("Unable to read file; exception: %s".formatted(e.getMessage()));
        }
        return inputPathList;
    }

    @Override
    public String loadInput(Path path) {
        try {
            try (Stream<String> lines = Files.lines(path)) {
                return lines.collect(Collectors.joining("\n"));
            }
        } catch (NullPointerException | IOException e) {
            throw new RuntimeException("Unable to read file; exception: %s".formatted(e.getMessage()));
        }
    }
}
