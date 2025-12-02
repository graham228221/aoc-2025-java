package aoc25;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import aoc25.utils.InputFile;

public class Day2 {

    static Day2 day2 = new Day2();
    static final String FILENAME = "day2";
    static final Boolean TEST_MODE = false;

    static Long partOneResult = 0L;
    static Long partTwoResult = 0L;

    public static void main(String[] args) throws Exception {
        ArrayList<Range> ranges = getRanges();

        System.out.println("number of ranges: " + ranges.size());

        for (Range range : ranges) {
            partOneResult += range.partOneTotal;
            partTwoResult += range.partTwoTotal;
        }

        System.out.println("part one total: " + partOneResult);
        System.out.println("part two total: " + partTwoResult);

    }

    private static ArrayList<Range> getRanges() {
        List<String> lines = TEST_MODE ? new InputFile(FILENAME).testLines : new InputFile(FILENAME).inputLines;

        ArrayList<Range> ranges = new ArrayList<Range>();

        String[] rangeInputs = lines.get(0).split(",");

        for (int i = 0; i < rangeInputs.length; i++) {
            String input = rangeInputs[i];
            ranges.add(new Range(input));
        }

        return ranges;
    }

    public static class Range {
        String input;
        Long min;
        Long max;

        Long partOneTotal;
        Long partTwoTotal;

        Range(String inputValue) {
            this.input = inputValue;
            String[] split = inputValue.split("-");
            this.min = Long.parseLong(split[0]);
            this.max = Long.parseLong(split[1]);

            checkRange();
        }

        void checkRange() {

            this.partOneTotal = 0L;
            this.partTwoTotal = 0L;

            for (Long i = this.min; i <= this.max; i++) {
                String number = String.valueOf(i);
                Boolean partTwoEligible = false;
                if (number.length() % 2 == 0) {
                    int midPoint = number.length() / 2;
                    String[] splitNumber = { number.substring(0, midPoint), number.substring(midPoint) };
                    if (arrayElementsAreEqual(splitNumber)) {
                        this.partOneTotal += i;
                        partTwoEligible = true;
                    }
                }
                if (!partTwoEligible && number.length() > 1) { // check if it's just repeated digits
                    String[] splitNumber = number.split("");
                    if (arrayElementsAreEqual(splitNumber)) {
                        partTwoEligible = true;
                    }
                }
                if (number.length() % 3 == 0 && !partTwoEligible) {
                    int splitPoint = number.length() / 3;
                    String[] splitNumber = {
                            number.substring(0, splitPoint),
                            number.substring(splitPoint, splitPoint * 2),
                            number.substring(splitPoint * 2)
                    };
                    if (arrayElementsAreEqual(splitNumber)) {
                        partTwoEligible = true;
                    }
                }
                if (number.length() % 4 == 0 && !partTwoEligible) {
                    int splitPoint = number.length() / 4;
                    String[] splitNumber = {
                            number.substring(0, splitPoint),
                            number.substring(splitPoint, splitPoint * 2),
                            number.substring(splitPoint * 2, splitPoint * 3),
                            number.substring(splitPoint * 3)
                    };
                    if (arrayElementsAreEqual(splitNumber)) {
                        partTwoEligible = true;
                    }
                }
                if (number.length() % 5 == 0 && !partTwoEligible) {
                    int splitPoint = number.length() / 5;
                    String[] splitNumber = {
                            number.substring(0, splitPoint),
                            number.substring(splitPoint, splitPoint * 2),
                            number.substring(splitPoint * 2, splitPoint * 3),
                            number.substring(splitPoint * 3, splitPoint * 4),
                            number.substring(splitPoint * 4)
                    };
                    if (arrayElementsAreEqual(splitNumber)) {
                        partTwoEligible = true;
                    }
                }

                if (partTwoEligible) {
                    this.partTwoTotal += i;
                }

            }
        }
    }

    static Boolean arrayElementsAreEqual(String[] strArray) {
        if (strArray == null || strArray.length == 0) {
            return false;
        }

        for (int i = 1; i < strArray.length; i++) {
            if (!Objects.equals(strArray[0], strArray[i])) {
                return false;
            }
        }
        return true;
    }
}
