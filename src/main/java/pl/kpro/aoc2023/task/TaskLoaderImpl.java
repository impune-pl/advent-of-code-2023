package pl.kpro.aoc2023.task;

import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TaskLoaderImpl implements TaskLoader {

    @Override
    public Set<AdventTask> getTasks() {
        Reflections reflections = new Reflections("pl.kpro.aoc2023.task");
        Set<Class<? extends AdventTask>> taskImplementations = reflections.getSubTypesOf(AdventTask.class);
        return taskImplementations.stream().filter(c -> !Modifier.isAbstract(c.getModifiers())).map(this::createInstance).sorted(Comparator.comparing(AdventTask::getNumber)).collect(Collectors.toCollection(LinkedHashSet<AdventTask>::new));
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
