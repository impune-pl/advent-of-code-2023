package pl.kpro.aoc2023.tasks;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskLoaderImpl implements TaskLoader {

    @Override
    public Set<AdventTask> getTasks() {
        Reflections reflections = new Reflections("pl.kpro.aoc2023.tasks");
        Set<Class<? extends AdventTask>> taskImplementations = reflections.getSubTypesOf(AdventTask.class);
        return taskImplementations.stream().map(this::createInstance).sorted(Comparator.comparing(AdventTask::getNumber)).collect(Collectors.toCollection(LinkedHashSet<AdventTask>::new));
    }

    private AdventTask createInstance(Class<? extends AdventTask> taskImpl) {
        try {
            return taskImpl.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException ignored) {
            throw new RuntimeException("Unable to instantiate class %s for some stupid reason.".formatted(taskImpl.getCanonicalName()));
        }
    }
}
