package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class SupplyStacks {
    private static final Logger LOGGER = LoggerFactory.getLogger(SupplyStacks.class);
    private static final String INPUT_FILENAME = "day5.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private List<Deque<Character>> stacks;
    private List<Instruction> instructions;
    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            stacks = new ArrayList<>();
            instructions = new ArrayList<>();

            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("[")) {
                    // line describes the stacks
                    int lengthOfStacks = (line.length() + 1) / 4;
                    while (stacks.size() < lengthOfStacks) {
                        stacks.add(new ArrayDeque<>());
                    }
                    for (int i = 1; i < line.length(); i += 4) {
                        if (line.charAt(i) != ' ') {
                            stacks.get(i / 4).addLast(line.charAt(i));
                        }
                    }
                } else if (line.contains("move")) {
                    // line describes an instruction
                    String[] tokens = line.split(" ");
                    instructions.add(new Instruction(
                            Integer.parseInt(tokens[3]),
                            Integer.parseInt(tokens[5]),
                            Integer.parseInt(tokens[1])));
                }
            }
        }
    }

    public void rearrangeByCrateMover9000() throws IOException {
        readInputs();
        for (Instruction instruction : instructions) {
            Deque<Character> fromStack = stacks.get(instruction.from - 1);
            Deque<Character> toStack = stacks.get(instruction.to - 1);
            for (int i = 0; i < instruction.count; i++) {
                toStack.addFirst(fromStack.removeFirst());
            }
        }
        String tops = stackTops();
        assert "SPFMVDTZT".equals(tops);
        LOGGER.info("By CrateMover 9000 Top crates are {}", stackTops());
    }

    public void rearrangeByCrateMover9001() throws IOException {
        readInputs();
        for (Instruction instruction : instructions) {
            Deque<Character> fromStack = stacks.get(instruction.from - 1);
            Deque<Character> toStack = stacks.get(instruction.to - 1);
            Deque<Character> tempStack = new ArrayDeque<>();
            for (int i = 0; i < instruction.count; i++) {
                tempStack.addFirst(fromStack.removeFirst());
            }
            for (int i = 0; i < instruction.count; i++) {
                toStack.addFirst(tempStack.removeFirst());
            }
        }
        String tops = stackTops();
        assert "ZFSJBPRFP".equals(tops);
        LOGGER.info("By CrateMover 9001 Top crates are {}", stackTops());
    }

    private String stackTops() {
        StringBuilder sb = new StringBuilder();
        for (Deque<Character> stack : stacks) {
//            if (stack.size() == 0) {
//                throw new InvalidParameterException("Inputs resulted in an empty stack");
//            }
            sb.append(stack.peekFirst());
        }
        return sb.toString();
    }

    static class Instruction {
        int from, to, count;
        Instruction(int from, int to, int count) {
            this.from = from;
            this.to = to;
            this.count = count;
        }
    }
}
