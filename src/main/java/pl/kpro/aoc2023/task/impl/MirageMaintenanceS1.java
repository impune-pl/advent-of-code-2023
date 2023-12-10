package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AbstractAdventTask;

import java.util.Arrays;

public class MirageMaintenanceS1 extends AbstractAdventTask {

    public MirageMaintenanceS1() {
        super(17, "MirageMaintenanceS1");
    }

    public String run(String input) {
        return Integer.valueOf(Arrays.stream(input.split("\n")).map(s -> Arrays.stream(s.split(" +")).map(Integer::valueOf).toArray(Integer[]::new)).mapToInt(this::calculateNextStep).sum()).toString();
    }

    public int calculateNextStep(Integer[] values) {
        int n = values.length;
        int x = n + 1;
        double px = 0;
        for (int j = 1; j <= n; j++) {
            double pj = 1;
            for (int k = 1; k <= n; k++)
                if (k != j)
                    pj *= ((double) (x - k) / (j - k));
            px += pj * values[j - 1];
        }
        return Long.valueOf(Math.round(px)).intValue();
    }
}