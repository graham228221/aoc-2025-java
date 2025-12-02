package aoc25;

import java.util.ArrayList;
import java.util.List;

import aoc25.utils.InputFile;

public class Day1 {

    static Day1 day1 = new Day1();
    static final String FILENAME = "day1";
    static final Boolean TEST_MODE = false;

    static Integer partOne = 0;
    static Integer partTwo = 0;

    public static void main(String[] args) throws Exception {

        ArrayList<Instruction> instructions = getInstructions();

        System.out.println("number of instructions: " + instructions.size());

        Dial dial = new Dial();

        for (int i = 0; i < instructions.size(); i++) {
            Instruction instruction = instructions.get(i);
            dial.moveDial(instruction);
        }

        System.out.println("part 1 result: " + partOne);
        System.out.println("part 2 result: " + partTwo);

    }

    public static class Dial {
        Integer place;

        Dial() {
            this.place = 50;
        }

        void moveDial(Instruction instruction) throws Exception {
            int startPlace = this.place;
            int fullTurns = instruction.steps / 100;
            partTwo += fullTurns;

            int remainder = instruction.steps % 100; // mod

            if (instruction.direction.equals("L")) {
                this.place -= remainder;
                if (this.place < 0) {
                    this.place = 100 + this.place;
                    if (startPlace != 0 && this.place != 0) {
                        partTwo++;
                    }
                }
            } else if (instruction.direction.equals("R")) {
                this.place += remainder;
                if (this.place > 99) {
                    this.place = this.place - 100;
                    if (startPlace != 0 && this.place != 0) {
                        partTwo++;
                    }
                }
            } else {
                throw new Exception("--- UNKNOWN DIRECTION ---");
            }

            if (this.place == 0) {
                partOne++;
                partTwo++;
            }
        }

    }

    public static class Instruction {
        String input;
        String direction; // L or R
        Integer steps;

        Instruction(String inputValue) {
            this.input = inputValue;
            this.direction = inputValue.substring(0, 1);
            this.steps = Integer.parseInt(inputValue.substring(1, inputValue.length()));
        }
    }

    private static ArrayList<Instruction> getInstructions() {
        List<String> lines = TEST_MODE ? new InputFile(FILENAME).testLines : new InputFile(FILENAME).inputLines;

        ArrayList<Instruction> instructions = new ArrayList<Instruction>();

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            instructions.add(new Instruction(line));
        }

        return instructions;
    }

}
