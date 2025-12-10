package aoc25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import aoc25.utils.InputFile;

public class Day8 {
    static Day8 day8 = new Day8();
    static final String FILENAME = "day8";
    static final Boolean TEST_MODE = true;
    static final Boolean PART_ONE = true;
    static final int iterations = TEST_MODE ? 10 : 1000;

    static ArrayList<Box> boxes;
    static ArrayList<ArrayList<Box>> circuits;
    static HashMap<String, ArrayList<Box>> circuitMap;
    static List<Pair> sortedPairs;

    public static void main(String[] args) throws Exception {
        getJunctionBoxes();

        System.out
                .println("Expected pairs from " + boxes.size() + " boxes: " + (boxes.size() * (boxes.size() - 1) / 2));

        ArrayList<Pair> pairs = new ArrayList<Pair>();

        for (int i = 0; i < boxes.size(); i++) {
            Box box = boxes.get(i);
            for (int j = i + 1; j < boxes.size(); j++) {
                Box otherBox = boxes.get(j);

                System.out.println("Comparing box " + i + ":" + box.input + " with box " + j + " : " + otherBox.input);

                Boolean alreadyPaired = pairs.stream().anyMatch(
                        p -> p.ref.equals(otherBox.input + "-" + box.input));

                if (!alreadyPaired) {

                    pairs.add(new Pair(box, otherBox));
                }
            }
        }

        System.out
                .println("Expected pairs from " + boxes.size() + " boxes: " + (boxes.size() * (boxes.size() - 1) / 2));
        System.out.println("Number of pairs: " + pairs.size());
        if (pairs.size() != (boxes.size() * (boxes.size() - 1) / 2)) {
            throw new Exception("--- UNEXPECTED NUMBER OF PAIRS CALCULATED ---");
        }

        sortedPairs = pairs.stream().sorted((p1, p2) -> p1.distance.compareTo(p2.distance)).toList();

        if (PART_ONE) {

            for (int i = 0; i < iterations; i++) {
                joinNextPair(i);
            }

            System.out.println("Number of circuits: " + circuitMap.size());

            List<ArrayList<Box>> circuitsInOrderOfSize = circuitMap.values().stream()
                    .sorted((list1, list2) -> Integer.compare(list2.size(), list1.size())).toList();
            // should be descending order

            Long partOneResult = 1L;

            for (int i = 0; i < 3; i++) {
                ArrayList<Box> circuit = circuitsInOrderOfSize.get(i);
                System.out.println(i + " biggest circuit size: " + circuit.size());
                partOneResult *= circuit.size();
            }

            System.out.println("Part 1 result: " + partOneResult);

        } else {
            // Part two: keep joining circuits until only one remains
            System.out.println("Starting with " + circuitMap.size() + " circuits");

            Long partTwoResult = 0L;
            int i = 0;

            while (circuitMap.size() > 1) {
                partTwoResult = joinNextPair(i);
                System.out.println("Circuits remaining: " + circuitMap.size());
                i++;
            }

            System.out.println("Part two result: " + partTwoResult);
        }

    }

    static class Pair {
        String ref;
        Double distance;
        Box boxOne;
        Box boxTwo;

        Pair(Box boxOne, Box boxTwo) {
            this.ref = boxOne.input + "-" + boxTwo.input;
            this.distance = getDistance(boxOne, boxTwo);
            this.boxOne = boxOne;
            this.boxTwo = boxTwo;
        }
    }

    static class Box {
        String input;
        int x;
        int y;
        int z;

        HashMap<Double, Box> otherBoxesByDistance;

        Box(String input) {
            this.input = input;
            String[] split = input.split(",");
            this.x = Integer.parseInt(split[0]);
            this.y = Integer.parseInt(split[1]);
            this.z = Integer.parseInt(split[2]);
        }

    }

    static double getDistance(Box boxOne, Box boxTwo) {
        // double distance = Math.sqrt(
        // Math.pow(boxOne.x - boxTwo.x, 2) + Math.pow(boxOne.y - boxTwo.y, 2) +
        // Math.pow(boxOne.z - boxTwo.z, 2));

        // quicker without using sqrt? don't need this as we don't need actual distance
        double distance = Math.pow(boxOne.x - boxTwo.x, 2) + Math.pow(boxOne.y - boxTwo.y, 2)
                + Math.pow(boxOne.z - boxTwo.z, 2);
        return distance;
    }

    static Long joinNextPair(int i) {
        System.out.println("Iteration " + i);
        Pair nextClosestPair = sortedPairs.get(i);
        System.out.println("Closest pair is: " + nextClosestPair.ref + " at distance" + nextClosestPair.distance);

        Boolean boxOneIsInACircuit = circuitMap.keySet().stream()
                .anyMatch(k -> k.contains(nextClosestPair.boxOne.input));
        Boolean boxTwoIsInACircuit = circuitMap.keySet().stream()
                .anyMatch(k -> k.contains(nextClosestPair.boxTwo.input));

        if (boxOneIsInACircuit && boxTwoIsInACircuit) { // both boxes are already in circuits which we need to merge

            String circuitRefOne = circuitMap.keySet().stream()
                    .filter(k -> k.contains(nextClosestPair.boxOne.input))
                    .toList().get(0);
            String circuitRefTwo = circuitMap.keySet().stream()
                    .filter(k -> k.contains(nextClosestPair.boxTwo.input))
                    .toList().get(0);

            if (circuitRefOne.equals(circuitRefTwo)) {
                // Boxes are already in the same circuit
            } else {
                // Need to merge two circuits
                ArrayList<Box> circuitOne = circuitMap.get(circuitRefOne);
                ArrayList<Box> circuitTwo = circuitMap.get(circuitRefTwo);
                circuitOne.addAll(circuitTwo);
                circuitMap.remove(circuitRefOne);
                circuitMap.remove(circuitRefTwo);
                circuitMap.put(circuitRefOne + "-" + circuitRefTwo, circuitOne);
            }

        }

        return Long.valueOf(nextClosestPair.boxOne.x) * Long.valueOf(nextClosestPair.boxTwo.x);
    }

    static ArrayList<Box> getJunctionBoxes() {
        boxes = new ArrayList<Box>();
        circuitMap = new HashMap<String, ArrayList<Box>>();

        List<String> lines = TEST_MODE ? new InputFile(FILENAME).testLines : new InputFile(FILENAME).inputLines;

        for (String line : lines) {
            Box box = new Box(line);
            boxes.add(box);

            // add each box to its own individual circuit:
            ArrayList<Box> newCircuit = new ArrayList<Box>();
            newCircuit.add(box);
            circuitMap.put(box.input, newCircuit);
        }

        return boxes;

    }
}
