package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.HashMap;
import java.util.Map;

public class HauntedWastelandS1 implements AdventTask {
    @Override
    public String getName() {
        return "HauntedWastelandS1";
    }

    @Override
    public int getNumber() {
        return 15;
    }

    @Override
    public String run(String input) {
        String[] lines = input.split("\n");
        char[] instructions = lines[0].toCharArray();

        Map<String, String[]> waypoints = new HashMap<>();
        for (int i = 2; i < lines.length; i++) {
            String index = lines[i].split(" = ")[0];
            String[] targets = lines[i].split(" = ")[1].substring(1,9).split(", ");
            waypoints.put(index, targets);
        }
        Long stepCount = 0L;
        String location = "AAA";
        while(!location.equals("ZZZ")) {
            if(instructions[stepCount.intValue() % instructions.length] == 'R') {
                location = waypoints.get(location)[1];
            } else {
                location = waypoints.get(location)[0];
            }
            stepCount++;
        }
        return stepCount.toString();
    }
}
