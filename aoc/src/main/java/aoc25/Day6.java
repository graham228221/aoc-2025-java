package aoc25;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import aoc25.utils.InputFile;

public class Day6 {
    static Day6 day6 = new Day6();
    static final String FILENAME = "day6";
    static final Boolean TEST_MODE = false;

    static Integer height;
    static Integer width;

    static String[][] map;
    static String[][] splitMap;

    public static void main(String[] args) throws Exception {

        buildColumns();

        Long partOneResult = 0L;
        Long partTwoResult = 0L;

        for (int x = 0; x < width; x++) {
            String operation = splitMap[height - 1][x].trim();
            Long partOneColumnResult = operation.equals("+") ? 0L : 1L;
            Long partTwoColumnResult = operation.equals("+") ? 0L : 1L;

            HashMap<Integer, ArrayList<String>> columnMap = new HashMap<Integer, ArrayList<String>>();

            for (int y = 0; y < height - 1; y++) {
                String item = splitMap[y][x];
                if (operation.equals("+")) {
                    partOneColumnResult += Long.parseLong(item.trim());
                } else {
                    partOneColumnResult *= Long.parseLong(item.trim());
                }

                partOneResult += partOneColumnResult;

                // Part two:
                String[] split = item.split("");
                for (int i = 0; i < split.length; i++) {
                    if (!split[i].equals(" ")) {
                        if (columnMap.containsKey(i)) {
                            columnMap.get(i).add(split[i]);
                        } else {
                            columnMap.put(i, new ArrayList<>(Arrays.asList(split[i])));
                        }
                    }
                }
            }

            for (Integer i : columnMap.keySet()) {
                Integer newNumber = Integer.parseInt(String.join("", columnMap.get(i)).trim());
                if (operation.equals("+")) {
                    partTwoColumnResult += newNumber;
                } else {
                    partTwoColumnResult *= newNumber;
                }
            }

            partTwoResult += partTwoColumnResult;

        }

        System.out.println("Part 1 result: " + partOneResult);
        System.out.println("Part 2 result: " + partTwoResult);

    }

    static void buildColumns() {
        InputFile inputFile = new InputFile(FILENAME);

        List<String> inputLines = TEST_MODE ? inputFile.testLines : inputFile.inputLines;

        height = inputLines.size();
        width = inputLines.get(0).split("[ ]{1,}").length; // one or more spaces

        String opLine = inputLines.get(height - 1);
        String[] splitOpLine = opLine.split("");

        ArrayList<Integer> indices = new ArrayList<Integer>();

        for (int i = 0; i < splitOpLine.length; i++) {
            if (!splitOpLine[i].equals(" ")) {
                indices.add(i);
            }
        }

        splitMap = new String[height][width];

        for (int y = 0; y < inputLines.size(); y++) {

            String row = inputLines.get(y);

            for (Integer x = 0; x < indices.size(); x++) {
                String item = x == indices.size() - 1 ? row.substring(indices.get(x))
                        : row.substring(indices.get(x), indices.get(x + 1));
                splitMap[y][x] = item;
            }
        }

    }

}
