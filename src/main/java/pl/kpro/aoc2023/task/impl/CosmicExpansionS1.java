package pl.kpro.aoc2023.task.impl;

import org.apache.commons.lang3.ArrayUtils;
import pl.kpro.aoc2023.task.AbstractAdventTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Stream;

public class CosmicExpansionS1 extends AbstractAdventTask {

    public CosmicExpansionS1() {
        super(21, "CosmicExpansionS1");
    }

    @Override
    public String run(String input) {
        // expand rows
        char[][] inputMap = Arrays.stream(input.split("\n")).flatMap(s-> s.matches("^\\.+$") ? Stream.of(s,s) : Stream.of(s)).map(String::toCharArray).toArray(char[][]::new);
        LinkedList<StringBuilder> mapBuilders = new LinkedList<>();
        // columns
        for (int i = 0; i < inputMap[1].length; i++) {
            boolean shouldAddColumn = true;
            // rows
            for (int j = 0; j < inputMap.length; j++) {
                if(i==0){
                    mapBuilders.addLast(new StringBuilder(280));
                }
                // if row contains anything other than ., it should not get expanded
                if(inputMap[j][i] != '.')
                    shouldAddColumn = false;
            }
            if(shouldAddColumn) {
               mapBuilders.forEach(b -> b.append(".."));
            } else {
                for (int j = 0; j < inputMap.length; j++) {
                    mapBuilders.get(j).append(inputMap[j][i]);
                }
            }
        }
        char[][] map = mapBuilders.stream().map(b -> b.toString().toCharArray()).toArray(char[][]::new);
        Map<Integer, int[]> galaxies = new HashMap<>();
        int galaxyIndex = 1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j] == '#') {
                    galaxies.put(galaxyIndex, new int[]{i,j});
                    galaxyIndex ++;
                }
            }
        }
        int[] pathsSum = galaxies.entrySet().stream().mapToInt(e->this.sumPathsForGalaxy(e,galaxies)).toArray();
        return Integer.toString(Arrays.stream(pathsSum).sum());
    }

    private int sumPathsForGalaxy(Map.Entry<Integer, int[]> galaxy, Map<Integer, int[]> galaxies) {
        return galaxies.entrySet().stream().filter(e -> e.getKey() > galaxy.getKey()).mapToInt(e-> this.getPathLength(e, galaxy)).sum();
    }

    private int getPathLength(Map.Entry<Integer, int[]> galaxy1e, Map.Entry<Integer, int[]> galaxy2e) {
        int[] galaxy1 = galaxy1e.getValue();
        int[] galaxy2 = galaxy2e.getValue();
        int[] currentPosition = ArrayUtils.clone(galaxy1);
        int steps = 0;
        while(currentPosition[0] != galaxy2[0] || currentPosition[1] != galaxy2[1]) {
            if(currentPosition[0] > galaxy2[0]) {
                currentPosition[0]--;
                steps++;
            } else if(currentPosition[0] < galaxy2[0]) {
                currentPosition[0]++;
                steps++;
            }

            if(currentPosition[1] > galaxy2[1]) {
                currentPosition[1]--;
                steps++;
            } else if (currentPosition[1] < galaxy2[1]) {
                currentPosition[1]++;
                steps++;
            }
            System.out.println("step: "+steps+" position: "+ Arrays.toString(currentPosition));
        }
        System.out.printf("Galaxy %s %s to galaxy %s %s, %s steps %n", galaxy1e.getKey().toString(), Arrays.toString(galaxy1e.getValue()), galaxy2e.getKey().toString(), Arrays.toString(galaxy2e.getValue()), steps);
        return steps;
    }
}
