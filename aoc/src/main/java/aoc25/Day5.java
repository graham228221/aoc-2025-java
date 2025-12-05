package aoc25;

import java.util.ArrayList;
import java.util.List;

import aoc25.utils.InputFile;
import aoc25.utils.TupleLong;

public class Day5 {
    static Day5 day5 = new Day5();
    static final String FILENAME = "day5";
    static final Boolean TEST_MODE = false;
    static final InputFile INPUT_FILE = new InputFile(FILENAME);

    public static void main(String[] args) throws Exception {
        List<String> inputLines = TEST_MODE ? INPUT_FILE.testLines : INPUT_FILE.inputLines;
        List<String> inputRanges = inputLines.stream().filter(x -> x.contains("-")).toList();
        List<Long> inputIds = inputLines.stream().filter(x -> !x.isEmpty() && !x.contains("-"))
                .map(x -> Long.parseLong(x)).toList();
        List<TupleLong> ranges = getRanges(inputRanges);

        System.out.println("Number of ranges: " + inputRanges.size());
        System.out.println("Number of Ids: " + inputIds.size());

        Integer partOneResult = 0;

        for (Long id : inputIds) {
            for (TupleLong range : ranges) {
                if (range.x <= id && id <= range.y) {
                    partOneResult++;
                    break;
                }
            }
        }

        System.out.println("Part 1: " + partOneResult);

        List<TupleLong> sortedRanges = ranges.stream().sorted((p1, p2) -> p1.x.compareTo(p2.x)).toList();

        ArrayList<TupleLong> combinedRanges = new ArrayList<TupleLong>();

        for (TupleLong range : sortedRanges) {
            if (ranges.contains(range)) {
                Long rangeStart = range.x;
                ranges.remove(range);

                Long rangeEnd = range.y;

                for (TupleLong otherRange : sortedRanges.stream().filter(x -> x != range && x.x >= range.x).toList()) {
                    if (otherRange.x <= rangeEnd + 1) {
                        rangeEnd = Math.max(otherRange.y, rangeEnd);
                        ranges.remove(otherRange);
                    }
                }

                combinedRanges.add(new TupleLong(rangeStart, rangeEnd));
            }

        }

        Long partTwoResult = 0L;

        for (TupleLong range : combinedRanges) {
            // System.out.println("Adding result for combined range: " + range.x + "-" +
            // range.y);
            // System.out.println((range.y - range.x + 1) + "\n");
            partTwoResult += (range.y - range.x + 1);
        }

        System.out.println("Part 2: " + partTwoResult);

    }

    static ArrayList<TupleLong> getRanges(List<String> stringRanges) {
        ArrayList<TupleLong> ranges = new ArrayList<TupleLong>();

        for (String stringRange : stringRanges) {
            String[] splitRange = stringRange.split("-");
            ranges.add(
                    new TupleLong(Long.parseLong(splitRange[0]), Long.parseLong(splitRange[1])));
        }

        return ranges;
    }

}
