package pl.kpro.aoc2023;

import pl.kpro.aoc2023.taskInputs.TaskInputLoader;
import pl.kpro.aoc2023.taskInputs.TaskInputLoaderImpl;
import pl.kpro.aoc2023.tasks.AdventTask;
import pl.kpro.aoc2023.tasks.TaskLoader;
import pl.kpro.aoc2023.tasks.TaskLoaderImpl;

import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

public class Launcher {
    private final String[] args;
    private final TaskLoader taskLoader;
    private final TaskInputLoader taskInputLoader;
    private final Set<AdventTask> tasks;

    public Launcher(String[] args) {
        this.args = args;
        this.taskLoader = new TaskLoaderImpl();
        this.taskInputLoader = new TaskInputLoaderImpl();
        this.tasks = taskLoader.getTasks();
    }

    public void start() {
        boolean shouldEnd;
        do {
            this.interaction();
            System.out.println("Quit Y/N?");
            Scanner scanner = new Scanner(System.in);
            String quit = scanner.nextLine();
            shouldEnd = quit.toLowerCase().contains("y");
        } while (!shouldEnd);
    }

    private void interaction() {
        System.out.println("Select task number to run task. Available tasks:");
        for (AdventTask task : this.tasks) {
            System.out.printf("    - %d: %s%n", task.getNumber(), task.getName());
        }
        Scanner scanner = new Scanner(System.in);
        int selectedTaskNumber = scanner.nextInt();
        AdventTask selectedTask = this.getSelectedTask(selectedTaskNumber);
        String taskInput = this.getSelectedTaskInput(selectedTask.getName());
        System.out.println(selectedTask.run(taskInput));
    }

    private AdventTask getSelectedTask(int taskNumber) {
        return this.tasks.stream()
                .filter(task -> task.getNumber() == taskNumber)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Task with number " + taskNumber + " not found."));
    }


    private String getSelectedTaskInput(String selectedTaskName) {
        System.out.println("Select input file for task. Available files:");
        List<Path> inputFiles = this.taskInputLoader.getPossibleInputsForTask(selectedTaskName);
        for (int i = 0; i < inputFiles.size(); i++) {
            System.out.printf("    - %d: %s%n", i, inputFiles.get(i).getFileName());
        }
        Scanner scanner = new Scanner(System.in);
        int selectedFileIndex = scanner.nextInt();
        Path selectedFile = inputFiles.get(selectedFileIndex);
        return this.taskInputLoader.loadInput(selectedFile);
    }
}