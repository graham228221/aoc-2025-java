package aoc25.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ArrayHelper {

    public static ArrayList<Integer> StringToIntegerArray(String input, String seperator) {
        ArrayList<Integer> returnList = new ArrayList<Integer>();

        int[] arr = Arrays.stream(seperator != null ? input.split(seperator) : input.split(""))
                .mapToInt(Integer::parseInt)
                .toArray();

        for (int i = 0; i < arr.length; i++) {
            returnList.add(arr[i]);
        }

        return returnList;
    }
}
