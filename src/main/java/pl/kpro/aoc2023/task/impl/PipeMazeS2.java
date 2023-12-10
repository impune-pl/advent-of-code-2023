package pl.kpro.aoc2023.task.impl;

import com.google.common.collect.ArrayListMultimap;
import org.apache.commons.lang3.ArrayUtils;
import pl.kpro.aoc2023.task.AbstractAdventTask;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PipeMazeS2 extends AbstractAdventTask {
    public PipeMazeS2() {
        super(20, "PipeMazeS2");
    }

    private static final int[][] surroundings = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

    public String run(String input) {
        /* algo:
            data structure centric:
                - find S[x,y]
                - counter = 0
                - walk around S to find first steps R1[x,y], L1[x,y]
                    - walk around R1 to find next step R2
                    - walk around L1 to find next step L2
                    - counter ++
                    - repeat until LN = RN
                return counter
        */
        char[][] map = Arrays.stream(input.split("\n")).map(String::toCharArray).toArray(char[][]::new);
        int[] start = new int[2];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[y].length; x++) {
                if ('S' == map[y][x]) {
                    start[0] = y;
                    start[1] = x;
                }
            }
        }
        int pathRange = 1;
        int[] currentPoint = new int[2];
        int[] previousPoint = new int[]{start[0], start[1]};
        int totalLength = 6867 * 2;
        int[][] pointsInPath = new int[totalLength][2];

        // TODO: initialize first point
        for (int[] point : surroundings) {
            int[] checkedPoint = new int[]{point[0] + start[0], point[1] + start[1]};
            if (checkedPoint[0] < 0 || checkedPoint[1] < 0 || map[checkedPoint[0]][checkedPoint[1]] == '.')
                continue;
            PipeSegments ps = PipeSegments.get(map[checkedPoint[0]][checkedPoint[1]]);
            if (ps.isAmongConnectionPoints(- point[0],  - point[1])) {
                currentPoint[0] = point[0] + start[0];
                currentPoint[1] = point[1] + start[1];
                break;
            }
        }
        pointsInPath[0][0] = start[0];
        pointsInPath[0][1] = start[1];
        pointsInPath[1][0] = currentPoint[0];
        pointsInPath[1][1] = currentPoint[1];

        while (currentPoint[0] != start[0] || currentPoint[1] != start[1]) {
            int[] tmp = ArrayUtils.clone(currentPoint);
            currentPoint = this.step(previousPoint, currentPoint, map);
            pointsInPath[pathRange][0] = currentPoint[0];
            pointsInPath[pathRange][1] = currentPoint[1];
            previousPoint = tmp;
            pathRange++;
        }

        //shoelace - pathRange + 1
        Long area = Math.round(this.area(pointsInPath));
        return Long.toString(area + 1 - pathRange / 2);
    }

    private double area(int[][] arr)
    {
        int n = arr.length;
        arr[n - 1][0] = arr[0][0];
        arr[n - 1][1] = arr[0][1];

        double det = 0.0;
        for (int i = 0; i < n - 1; i++)
            det += (double)(arr[i][0] * arr[i + 1][1]);
        for (int i = 0; i < n - 1; i++)
            det -= (double)(arr[i][1] * arr[i + 1][0]);

        det = Math.abs(det);
        det /= 2;
        return det;
    }

    private int[] step(int[] previous, int[] current, char[][] map) {
        int[] newVector = PipeSegments.get(map[current[0]][current[1]]).getConnectionPointOtherThan(previous[0]-current[0], previous[1]-current[1]);
        return new int[]{current[0] + newVector[0], current[1] + newVector[1]};
    }

    private enum PipeSegments {
        VERTICAL('|', new int[]{-1, 0}, new int[]{1, 0}),
        HORIZONTAL('-', new int[]{0, -1}, new int[]{0, 1}),
        UP_TO_RIGHT('L', new int[]{-1, 0}, new int[]{0, 1}),
        UP_TO_LEFT('J', new int[]{-1, 0}, new int[]{0, -1}),
        DOWN_TO_RIGHT('F', new int[]{1, 0}, new int[]{0, 1}),
        DOWN_TO_LEFT('7', new int[]{1, 0}, new int[]{0, -1});

        final char character;
        final int[][] connectionPoints;

        PipeSegments(char c, int[]... connectionPoints) {
            this.character = c;
            this.connectionPoints = connectionPoints;
        }

        public int[] getConnectionPointOtherThan(int... point) {
            return connectionPoints[0][0] == point[0] && connectionPoints[0][1] == point[1] ? connectionPoints[1] : connectionPoints[0];
        }

        public boolean isAmongConnectionPoints(int... point) {
            return connectionPoints[0][0] == point[0] && connectionPoints[0][1] == point[1] || connectionPoints[1][0] == point[0] && connectionPoints[1][1] == point[1];
        }

        public static PipeSegments get(char c) {
            return switch (c) {
                case '|' -> VERTICAL;
                case '-' -> HORIZONTAL;
                case 'L' -> UP_TO_RIGHT;
                case 'J' -> UP_TO_LEFT;
                case 'F' -> DOWN_TO_RIGHT;
                case '7' -> DOWN_TO_LEFT;
                default -> throw new RuntimeException("Unknown pipe segment type: " + c);
            };
        }
    }
}