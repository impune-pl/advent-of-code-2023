package pl.kpro.aoc2023.task.impl;

import pl.kpro.aoc2023.task.AdventTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WaitForItS2 implements AdventTask {
    private static final String NAME = "WaitForItS2";
    private static final int NUMBER = 12;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getNumber() {
        return NUMBER;
    }

    @Override
    public String run(String input) {
        return Long.toString(this.parseRaces(input).stream().map(this::getNumberOfWaysToBeatRace).reduce((a, b) -> a * b).orElse(0L));
    }

    private List<Race> parseRaces(String input) {
        String[][] data = Arrays.stream(input.split("\n")).map(s -> s.substring(s.indexOf(':') + 1)).map(s -> s.trim().split(" +")).toArray(String[][]::new);
        List<Race> raceList = new ArrayList<>();
        for (int i = 0; i < data[0].length; i++) {
            raceList.add(new Race(Long.parseLong(data[0][i]), Long.parseLong(data[1][i])));
        }
        return raceList;
    }

    private Long getNumberOfWaysToBeatRace(Race race) {
        /*
        known:
        t = total time
        d = total distance
        to find:
        x = button down time = speed
        y = button up time = travel time
        y = t - x
        ad = y * x = actual distance
        find out how many natural x's such that ad > d
        
        ad = (t - x)*x = t*x - x^2;
        0 = -x^2 + tx - d
        0 = x^2 - tx + d
        t > x; x > 0;
        solution: find min and max x's to determine bounds, then calculate difference!
         */

        double delta, root1, root2;
        delta = Math.pow(race.time, 2) - 4 * race.distance;
        if (delta <= 0) return 0L;
        root1 = (-1 * race.time + Math.sqrt(delta)) / -2d;
        root2 = (-1 * race.time - Math.sqrt(delta)) / -2d;
        long r1, r2;
        r1 = Double.valueOf(root1).longValue();
        r2 = Double.valueOf(root2).longValue();
        System.out.println(r1 - r2);
        return Math.abs(r1 - r2);
    }

    class Race {
        final Long time, distance;

        Race(Long time, Long distance) {
            this.time = time;
            this.distance = distance;
        }
    }
}