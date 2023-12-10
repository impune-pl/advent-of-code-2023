package pl.kpro.aoc2023.task.impl;

import org.apache.commons.lang3.ArrayUtils;
import pl.kpro.aoc2023.task.AbstractAdventTask;

import java.util.Arrays;

public class PipeMazeS1 extends AbstractAdventTask {
    public PipeMazeS1() {
        super(19, "PipeMazeS1");
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
        int[][] previousPoints = {start, start};
        int[][] currentPoints = {{-1, -1}, {-1, -1}};
        int counter = 1;

        // TODO: initialize first point of both paths
        for (int[] point : surroundings) {
            int[] checkedPoint = new int[]{point[0] + start[0], point[1] + start[1]};
            if (checkedPoint[0] < 0 || checkedPoint[1] < 0 || map[checkedPoint[0]][checkedPoint[1]] == '.')
                continue;
            PipeSegments ps = PipeSegments.get(map[checkedPoint[0]][checkedPoint[1]]);
            if (ps.isAmongConnectionPoints(- point[0],  - point[1])) {
                if (currentPoints[0][0] == -1) {
                    currentPoints[0][0] = point[0] + start[0];
                    currentPoints[0][1] = point[1] + start[1];
                } else {
                    currentPoints[1][0] = point[0] + start[0];
                    currentPoints[1][1] = point[1] + start[1];
                }
            }
        }

        while (currentPoints[0][0] != currentPoints[1][0] || currentPoints[0][1] != currentPoints[1][1]) {
            int[] tmp = ArrayUtils.clone(currentPoints[0]);
            currentPoints[0] = this.step(previousPoints[0], currentPoints[0], map);
            previousPoints[0] = tmp;
            tmp = ArrayUtils.clone(currentPoints[1]);
            currentPoints[1] = this.step(previousPoints[1], currentPoints[1], map);
            previousPoints[1] = tmp;
            System.out.printf("Left path: %s | Right path: %s | Counter: %s %n", Arrays.toString(currentPoints[0]), Arrays.toString(currentPoints[1]), counter);
            counter++;
        }
        return Integer.valueOf(counter).toString();
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