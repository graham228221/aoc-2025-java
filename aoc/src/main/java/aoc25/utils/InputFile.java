package aoc25.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class InputFile {

    public List<String> inputLines;
    public List<String> testLines;

    public InputFile(String fileName) {
        inputLines = getLines(fileName);
        testLines = getTestLines(fileName);
    }

    private static List<String> getTestLines(String fileName) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("aoc/src/main/resources/" + fileName + "_example.txt"))) {
            return br.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    private static List<String> getLines(String fileName) {
        try (BufferedReader br = new BufferedReader(
                new FileReader("aoc/src/main/resources/" + fileName + ".txt"))) {
            return br.lines().toList();
        } catch (IOException e) {
            e.printStackTrace();
            throw new IllegalStateException(e);
        }
    }

    public static String[][] getStringMap(List<String> inputLines) {

        if (inputLines.isEmpty()) {
            System.out.println("INPUT IS EMPTY!");
            return null;
        }

        int height = inputLines.size();
        int width = inputLines.get(0).length(); // assumes all rows are the same!

        String[][] map = new String[height][width];

        for (int y = 0; y < inputLines.size(); y++) {

            char[] row = inputLines.get(y).toCharArray();

            for (int x = 0; x < width; x++) {
                map[y][x] = String.valueOf(row[x]);
            }
        }

        return map;
    }

}