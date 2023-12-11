package pl.kpro.aoc2023.task.impl;

import org.apache.commons.lang3.ArrayUtils;
import pl.kpro.aoc2023.task.AbstractAdventTask;

import java.util.*;
import java.util.stream.Stream;

public class CosmicExpansionS2 extends AbstractAdventTask {

    public CosmicExpansionS2() {
        super(22, "CosmicExpansionS2");
    }

    @Override
    public String run(String input) {
        // expand rows
        char[][] map = Arrays.stream(input.split("\n")).map(String::toCharArray).toArray(char[][]::new);
        List<Integer> horizontalExpansions = new ArrayList<>();
        List<Integer> verticalExpansions = new ArrayList<>();
        for (int i = 0; i < map.length; i++) {
            boolean isHExpansion = true;
            boolean isVExpansion = true;
            for (int j = 0; j < map[i].length; j++) {
                if(map[i][j]!='.')
                    isHExpansion= false;
                if(map[j][i]!='.')
                    isVExpansion= false;
            }
            if(isHExpansion) horizontalExpansions.add(i);
            if(isVExpansion) verticalExpansions.add(i);
        }

        Map<Integer, long[]> galaxies = new HashMap<>();
        int galaxyIndex = 1;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                int finalI = i;
                long h = horizontalExpansions.stream().filter(he -> finalI > he).count();
                int finalJ = j;
                long v = verticalExpansions.stream().filter(he -> finalJ > he).count();
                if(map[i][j] == '#') {
                    galaxies.put(galaxyIndex, new long[]{i + 999999 * h,j + 999999 * v});
                    galaxyIndex ++;
                }
            }
        }
        // vertical and horizontal loop over galaxy map, looking for gaps. When gap encountered, galaxy coordinate modifier + 1 000 000

        long[] pathsSum = galaxies.entrySet().stream().mapToLong(e->this.sumPathsForGalaxy(e,galaxies)).toArray();
        return Long.toString(Arrays.stream(pathsSum).sum());
    }

    private long sumPathsForGalaxy(Map.Entry<Integer, long[]> galaxy, Map<Integer, long[]> galaxies) {
        return galaxies.entrySet().stream().filter(e -> e.getKey() > galaxy.getKey()).mapToLong(e-> this.getPathLength(e, galaxy)).sum();
    }

    private long getPathLength(Map.Entry<Integer, long[]> galaxy1e, Map.Entry<Integer, long[]> galaxy2e) {
        long[] galaxy1 = galaxy1e.getValue();
        long[] galaxy2 = galaxy2e.getValue();
        return Math.abs(galaxy1[1]-galaxy2[1])+Math.abs(galaxy1[0]-galaxy2[0]);
    }
}
