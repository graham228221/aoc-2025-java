package aoc25;

import java.util.*;

import aoc25.utils.InputFile;
import aoc25.utils.ArrayHelper;

public class Day3 {
    static Day3 day3 = new Day3();
    static final String FILENAME = "day3";
    static final Boolean TEST_MODE = false;

    public static void main(String[] args) throws Exception {
        ArrayList<Bank> banks = getBatteryBanks();

        Integer partOneResult = 0;
        Long partTwoResult = 0L;

        for (Bank bank : banks) {
            System.out.println("bank size: " + bank.batteries.size());
            partOneResult += bank.getPart1Joltage();
            partTwoResult += bank.getPart2Joltage();
        }

        System.out.println(partOneResult);
        System.out.println(partTwoResult);
    }

    static class Bank {
        ArrayList<Battery> batteries = new ArrayList<Battery>();

        Bank(String input) {
            ArrayList<Integer> splitBank = ArrayHelper.StringToIntegerArray(input, null);
            for (int i = 0; i < splitBank.size(); i++) {
                this.batteries.add(new Battery(i, splitBank.get(i)));
            }
        }

        int getPart1Joltage() {

            Battery maxBatteryExceptLast = this.batteries.stream()
                    .filter(b -> b.index < this.batteries.size() - 1)
                    .max(Comparator.comparing(b -> b.voltage))
                    .get();

            Battery nextBestBattery = this.batteries.stream()
                    .filter(b -> b.index > maxBatteryExceptLast.index)
                    .max(Comparator.comparing(b -> b.voltage))
                    .get();

            return Integer
                    .parseInt(String.valueOf(maxBatteryExceptLast.voltage) + String.valueOf(nextBestBattery.voltage));
        }

        Long getPart2Joltage() {

            ArrayList<Battery> batteryArrangement = new ArrayList<Battery>();

            for (int i = 0; i < 12; i++) {

                int prevBatteryIndex = batteryArrangement.isEmpty() ? -1 : batteryArrangement.get(i - 1).index;
                int maxBatteryIndex = this.batteries.size() - 12 + batteryArrangement.size();

                Battery nextBattery = this.batteries.stream()
                        .filter(b -> b.index > prevBatteryIndex
                                && b.index <= maxBatteryIndex)
                        .max(Comparator.comparing(b -> b.voltage))
                        .get();

                batteryArrangement.add(nextBattery);
            }

            String joltageStr = "";

            for (Battery battery : batteryArrangement) {
                joltageStr += String.valueOf(battery.voltage);
            }

            return Long.parseLong(joltageStr);

        }
    }

    static class Battery {
        int index;
        int voltage;

        Battery(int index, int voltage) {
            this.index = index;
            this.voltage = voltage;
        }
    }

    private static ArrayList<Bank> getBatteryBanks() {
        List<String> lines = TEST_MODE ? new InputFile(FILENAME).testLines : new InputFile(FILENAME).inputLines;

        ArrayList<Bank> banks = new ArrayList<Bank>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            banks.add(new Bank(line));
        }

        return banks;
    }
}
