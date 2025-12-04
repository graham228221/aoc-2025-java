package aoc25;

import java.util.Arrays;
import java.util.List;

import aoc25.utils.InputFile;

public class Day4 {
    static Day4 day4 = new Day4();
    static final String FILENAME = "day4";
    static final Boolean TEST_MODE = false;

    static String[][] map;

    public static void main(String[] args) throws Exception {

        map = getMap();

        // printMap();

        // Part one:
        Integer totalThingsRemoved = removeThingsFromMap();
        System.out.println("Part One Result: " + totalThingsRemoved);

        // Part two;
        // printMap();

        Integer thingsRemovedThisTime = totalThingsRemoved;

        while (thingsRemovedThisTime > 0) {
            thingsRemovedThisTime = removeThingsFromMap();
            totalThingsRemoved += thingsRemovedThisTime;
            // System.out.println("Things removed this time: " + thingsRemovedThisTime);
        }
        System.out.println("Part Two Result: " + totalThingsRemoved);

    }

    private static Integer removeThingsFromMap() {

        // deep clone
        String[][] tempMap = new String[map.length][map[0].length];
        for (int i = 0; i < tempMap.length; i++) {
            tempMap[i] = Arrays.copyOf(map[i], map[i].length);
        }

        Integer thingsRemoved = 0;

        for (int y = 0; y < map.length; y++) {
            // for (int y = 0; y < 1; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x].equals("@")) {
                    // System.out.println("Space " + x + "," + y + " has a thingy in it");

                    Integer surroundingThings = 0;

                    // check surroundings:
                    for (int j = y - 1; j <= y + 1; j++) {
                        for (int i = x - 1; i <= x + 1; i++) {
                            // System.out.println("Checking: " + i + "," + j);
                            if (i < 0 || i >= map[0].length) {
                                // System.out.println(i + "," + j + " is outside horizontal bounds");
                            } else if (j < 0 || j >= map.length) {
                                // System.out.println(i + "," + j + " is outside vertical bounds");
                            } else if (i == x && j == y) {
                                // System.out.println(i + "," + j + " is the same thingy");
                            } else if (map[j][i].equals("@")) {
                                // System.out.println("Surrounding space " + i + "," + j + " has a thingy in
                                // it");
                                surroundingThings++;
                            } else {
                                // System.out.println("Surrounding space " + i + "," + j + " is empty");
                            }
                        }
                    }

                    // System.out.println("surrounding things: " + surroundingThings);

                    if (surroundingThings < 4) {
                        // System.out.println(x + "," + y + " is removable\n");
                        thingsRemoved++;
                        tempMap[y][x] = ".";
                    } else {
                        // System.out.println(x + "," + y + " is not removable\n");
                    }

                } else {
                    // System.out.println("Space " + x + "," + y + " is empty\n");
                }

                // printMap();
            }
        }

        // System.out.println("Things removed: " + thingsRemoved);

        map = tempMap;

        return thingsRemoved;
    }

    private static String[][] getMap() {
        InputFile inputFile = new InputFile(FILENAME);

        List<String> lines = TEST_MODE ? inputFile.testLines : inputFile.inputLines;

        return InputFile.getStringMap(lines);
    }

    static void printMap() {

        String strMap = "";
        for (String[] row : map) {
            strMap += String.join("", Arrays.asList(row)) + "\n";
        }

        System.out.println(strMap);
    }

}
