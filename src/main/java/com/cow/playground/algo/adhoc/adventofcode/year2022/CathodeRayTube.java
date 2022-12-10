package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CathodeRayTube {
    private static final Logger LOGGER = LoggerFactory.getLogger(CathodeRayTube.class);
    private static final String INPUT_FILENAME = "day10.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private static List<Instruction> instructions;

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            instructions = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens.length == 1) {
                    instructions.add(new Instruction(Op.get(tokens[0])));
                } else if (tokens.length == 2) {
                    instructions.add(new Instruction(
                            Op.get(tokens[0]),
                            Integer.parseInt(tokens[1])));
                }
            }
        }
    }

    public void task1() {
        int x = 1;
        int startOfCycle = 1;
        int signalStrength = 0;
        List<InterestedSchedule> schedules = new ArrayList<>();
        schedules.add(new InterestedSchedule(20, InterestedSchedule.FOREVER, 40));
        for (Instruction instruction : instructions) {
            for (InterestedSchedule schedule : schedules) {
                if (instruction.op == Op.NOOP) {
                    List<Integer> interestingCycles = schedule.getInterestingCycles(startOfCycle, startOfCycle);
                    LOGGER.debug("interesting cycles {}", interestingCycles);
                    for (int interestingCycle : interestingCycles) {
                        signalStrength += interestingCycle * x;
                    }
                } else if (instruction.op == Op.ADDX) {
                    List<Integer> interestingCycles = schedule.getInterestingCycles(startOfCycle, startOfCycle + instruction.op.processTime - 1);
                    LOGGER.debug("interesting cycles {}", interestingCycles);
                    for (int interestingCycle : interestingCycles) {
                        signalStrength += interestingCycle * x;
                    }
                    x += instruction.param;
                }
                startOfCycle += instruction.op.processTime;
            }
        }
        LOGGER.info("Signal strength is {}", signalStrength);
    }

    public void task2() {
        int nRow = 6;
        int nCol = 40;
        boolean[][] screen = new boolean[nRow][nCol];
        int midSpriteLocation = 1;
        int currentCycle = 1;
        for (Instruction instruction : instructions) {
            for (int i = 0; i < instruction.op.processTime; i++, currentCycle++) {
                int row = (currentCycle - 1) / nCol;
                int col = (currentCycle - 1) % nCol;
                if (col >= midSpriteLocation - 1 && col <= midSpriteLocation + 1) {
                    screen[row][col] = true;
                }
            }
            if (instruction.op == Op.ADDX) {
                midSpriteLocation += instruction.param;
            }
        }
        for (int i = 0; i < nRow; i++) {
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < nCol; j++) {
                sb.append(screen[i][j] ? "#" : ".");
            }
            LOGGER.info("{}", sb);
        }
        LOGGER.info("RFKZCPEF");
    }

    public CathodeRayTube() throws IOException {
        readInputs();
    }

    enum Op {
        ADDX("addx", 2),
        NOOP("noop", 1);
        final String name;
        final int processTime;

        Op(String name, int processTime) {
            this.name = name;
            this.processTime = processTime;
        }

        public static Op get(String name) {
            for (Op op : values()) {
                if (op.name.equals(name)) {
                    return op;
                }
            }
            throw new InvalidParameterException("Invalid instruction " + name);
        }
    }

    static class Instruction {
        Op op;
        int param;

        Instruction(Op op, int param) {
            this.op = op;
            this.param = param;
        }

        Instruction(Op op) {
            this.op = op;
        }
    }

    static class InterestedSchedule {
        static final int FOREVER = 0;
        int startCycle;
        int repetitions;
        int frequency;

        InterestedSchedule(int startCycle, int repetitions, int frequency) {
            this.startCycle = startCycle;
            this.repetitions = repetitions;
            this.frequency = frequency;
        }

        List<Integer> getInterestingCycles(int firstCycle, int lastCycle) {
            if (lastCycle < startCycle) {
                return Collections.emptyList();
            }
            List<Integer> res = new ArrayList<>();
            int endCycle = repetitions == FOREVER ? lastCycle : Math.min(lastCycle, startCycle + frequency * repetitions);
            int mod = (firstCycle - startCycle) % frequency;
            int beginCycle = firstCycle <= startCycle ?
                    startCycle :
                    mod == 0 ? firstCycle : firstCycle + frequency - mod;
            for (int cycle = beginCycle; cycle <= endCycle; cycle += frequency) {
                res.add(cycle);
            }
            return res;
        }
    }
}