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
        // aoc/src/main/resources/day1.txt
    }

}