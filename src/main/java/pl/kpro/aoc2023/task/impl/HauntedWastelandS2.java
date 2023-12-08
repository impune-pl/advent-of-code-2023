package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class HauntedWastelandS2 implements AdventTask {
    @Override
    public String getName() {
        return "HauntedWastelandS2";
    }

    @Override
    public int getNumber() {
        return 16;
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

        String[] locations = waypoints.keySet().stream().filter(k->k.endsWith("A")).toArray(String[]::new);
        int[] stepCounts = Arrays.stream(locations).mapToInt((s)->this.stepsStartingFromLocation(s, instructions, waypoints)).toArray();

        return this.calculateLeastCommonStepCount(stepCounts);
    }

    private String calculateLeastCommonStepCount(int[] stepCounts) {
        long lcm = 1;
        int divisor = 2;
        while (true) {
            int counter = 0;
            boolean divisible = false;

            for (int i = 0; i < stepCounts.length; i++) {
                if (stepCounts[i] == 1) {
                    counter++;
                }

                if (stepCounts[i] % divisor == 0) {
                    divisible = true;
                    stepCounts[i] = stepCounts[i] / divisor;
                }
            }

            if (divisible) {
                lcm = lcm * divisor;
            }
            else {
                divisor++;
            }
            if (counter == stepCounts.length) {
                return Long.toString(lcm);
            }
        }
    }

    private int stepsStartingFromLocation(String start, char[] instructions, Map<String, String[]> waypoints) {
        int stepCount = 0;
        String location = start;
        while(!location.endsWith("Z")) {
            if(instructions[stepCount % instructions.length] == 'R') {
                location = waypoints.get(location)[1];
            } else {
                location = waypoints.get(location)[0];
            }
            stepCount++;
        }
        return stepCount;
    }
}
