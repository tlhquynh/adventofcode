package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.*;

public class MonkeyBusiness {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonkeyBusiness.class);
    private static final String INPUT_FILENAME = "day11.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private List<Monkey> monkeys;
    private List<Integer> selectedDivisors;  // TODO assuming all divisors are different
    private Map<Integer, Integer> divisorToIndex;

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            monkeys = new ArrayList<>();
            Monkey monkey = null;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Monkey")) {
                    monkey = new Monkey();
                } else if (line.contains("Starting")) {
                    String[] tokens = line.trim().split("[ ,]");
                    for (int i = 2; i < tokens.length; i++) {
                        if (!tokens[i].isEmpty()) {
                            monkey.originalWorryItems.add(Integer.parseInt(tokens[i]));
                        }
                    }
                } else if (line.contains("Operation")) {
                    int colonSpaceIndex = line.indexOf(": ");
                    monkey.specialWorryChanger = new SpecialModuloWorryChanger(line.substring(colonSpaceIndex + 2));
                    monkey.originalWorryChanger = new OriginalWorryChanger(line.substring(colonSpaceIndex + 2));
                } else if (line.contains("Test")) {
                    String[] tokens = line.trim().split(" ");
                    monkey.testDivisor = Integer.parseInt(tokens[tokens.length - 1]);
                } else if (line.contains("true")) {
                    String[] tokens = line.trim().split(" ");
                    monkey.divisorPassedMonkey = Integer.parseInt(tokens[tokens.length - 1]);
                } else if (line.contains(("false"))) {
                    String[] tokens = line.trim().split(" ");
                    monkey.divisorFailedMonkey = Integer.parseInt(tokens[tokens.length - 1]);
                    monkeys.add(monkey);
                    monkey = null;
                }
            }
        }

        // For task 2, prepare the list of selected divisors for SpecialModulo
        // SpecialModulo stores the remainders of those divisors
        selectedDivisors = new ArrayList<>();
        for (Monkey monkey : monkeys) {
            selectedDivisors.add(monkey.testDivisor);
        }
        // prepare a mapping from divisor to index for looking up remainders later
        divisorToIndex = new HashMap<>();
        for (int i = 0; i < selectedDivisors.size(); i++) {
            divisorToIndex.put(selectedDivisors.get(i), i);
        }
        // for each item, convert it to SpecialModulo
        for (Monkey monkey : monkeys) {
            for (int originalWorryItem : monkey.originalWorryItems) {
                monkey.specialWorryItems.addLast(new SpecialModulo(originalWorryItem));
            }
        }
    }

    public void task1() throws IOException {
        readInputs();
        for (int round = 0; round < 20; round++) {
            for (Monkey monkey : monkeys) {
                while (!monkey.originalWorryItems.isEmpty()) {
                    monkey.busyLevel++;
                    int oldWorry = monkey.originalWorryItems.removeFirst();
                    int newWorry = monkey.originalWorryChanger.change(oldWorry);
                    newWorry /= 3;
                    if (newWorry % monkey.testDivisor == 0) {
                        monkeys.get(monkey.divisorPassedMonkey).originalWorryItems.addLast(newWorry);
                    } else {
                        monkeys.get(monkey.divisorFailedMonkey).originalWorryItems.addLast(newWorry);
                    }
                }
            }
        }
        int[] busyLevels = new int[monkeys.size()];
        for (int i = 0; i < busyLevels.length; i++) {
            busyLevels[i] = monkeys.get(i).busyLevel;
        }
        Arrays.sort(busyLevels);
        int monkeyBusinessLevel = busyLevels[busyLevels.length - 1] * busyLevels[busyLevels.length - 2];
        assert monkeyBusinessLevel == 58056;
        LOGGER.info("Task 1 monkey business level is {}", monkeyBusinessLevel);
    }

    public void task2() throws IOException {
        readInputs();
        for (int round = 0; round < 10000; round++) {
            for (Monkey monkey : monkeys) {
                while (!monkey.specialWorryItems.isEmpty()) {
                    monkey.busyLevel++;
                    SpecialModulo oldWorry = monkey.specialWorryItems.removeFirst();
                    SpecialModulo newWorry = monkey.specialWorryChanger.change(oldWorry);
                    if (newWorry.remainders[divisorToIndex.get(monkey.testDivisor)] == 0) {
                        monkeys.get(monkey.divisorPassedMonkey).specialWorryItems.addLast(newWorry);
                    } else {
                        monkeys.get(monkey.divisorFailedMonkey).specialWorryItems.addLast(newWorry);
                    }
                }
            }
        }
        int[] busyLevels = new int[monkeys.size()];
        for (int i = 0; i < busyLevels.length; i++) {
            busyLevels[i] = monkeys.get(i).busyLevel;
        }
        Arrays.sort(busyLevels);
        long monkeyBusinessLevel = (long) busyLevels[busyLevels.length - 1] * busyLevels[busyLevels.length - 2];
        assert monkeyBusinessLevel == 15048718170L;
        LOGGER.info("Task 2 monkey business level is {}", monkeyBusinessLevel);
    }

    private class SpecialModuloWorryChanger {
        static final String WORRY_LABEL = "old";
        Object[] formula;

        public SpecialModuloWorryChanger(String formula) {
            String[] tokens = formula.split(" ");
            this.formula = new Object[tokens.length - 2];
            for (int i = 2; i < tokens.length; i++) {
                this.formula[i - 2] = tokens[i];
            }
        }

        public SpecialModulo change(SpecialModulo worry) {
            return change(worry, 0, formula.length - 1);
        }

        public SpecialModulo change(SpecialModulo worry, int formulaBeginIndex, int formulaEndIndex) {
            if (formulaBeginIndex == formulaEndIndex) {
                if (WORRY_LABEL.equals(formula[formulaBeginIndex])) {
                    return worry;
                } else if (formula[formulaBeginIndex] instanceof String) {
                    return new SpecialModulo(Integer.parseInt((String) formula[formulaBeginIndex]));
                }
                return (SpecialModulo) formula[formulaBeginIndex];
            }
            if ("*".equals(formula[formulaBeginIndex + 1])) {
                Object saved = formula[formulaBeginIndex + 2];
                formula[formulaBeginIndex + 2] = change(worry, formulaBeginIndex, formulaBeginIndex)
                        .multiply(change(worry, formulaBeginIndex + 2, formulaBeginIndex + 2));
                SpecialModulo res = change(worry, formulaBeginIndex + 2, formulaEndIndex);
                formula[formulaBeginIndex + 2] = saved;
                return res;
            } else if ("+".equals(formula[formulaBeginIndex + 1])) {
                return change(worry, formulaBeginIndex, formulaBeginIndex)
                        .add(change(worry, formulaBeginIndex + 2, formulaEndIndex));
            } else {
                throw new InvalidParameterException("Operator not supported");
            }
        }
    }

    static class OriginalWorryChanger {
        static final String WORRY_LABEL = "old";
        String[] formula;

        public OriginalWorryChanger(String formula) {
            String[] tokens = formula.split(" ");
            this.formula = Arrays.copyOfRange(tokens, 2, tokens.length);
        }

        public int change(int worry) {
            return change(worry, 0, formula.length - 1);
        }

        public int change(int worry, int formulaBeginIndex, int formulaEndIndex) {
            if (formulaBeginIndex == formulaEndIndex) {
                if (WORRY_LABEL.equals(formula[formulaBeginIndex])) {
                    return worry;
                }
                return Integer.parseInt(formula[formulaBeginIndex]);
            }
            if ("*".equals(formula[formulaBeginIndex + 1])) {
                String saved = formula[formulaBeginIndex + 2];
                formula[formulaBeginIndex + 2] = Integer.toString(change(worry, formulaBeginIndex, formulaBeginIndex) * change(worry, formulaBeginIndex + 2, formulaBeginIndex + 2));
                int res = change(worry, formulaBeginIndex + 2, formulaEndIndex);
                formula[formulaBeginIndex + 2] = saved;
                return res;
            } else if ("+".equals(formula[formulaBeginIndex + 1])) {
                return change(worry, formulaBeginIndex, formulaBeginIndex) + change(worry, formulaBeginIndex + 2, formulaEndIndex);
            } else {
                throw new InvalidParameterException("Operator not supported");
            }
        }
    }

    static class Monkey {
        Deque<SpecialModulo> specialWorryItems = new ArrayDeque<>();
        Deque<Integer> originalWorryItems = new ArrayDeque<>();  // normal integer calculation
        SpecialModuloWorryChanger specialWorryChanger;
        OriginalWorryChanger originalWorryChanger;  // worry changer that does normal integer calculation
        int testDivisor;
        int divisorPassedMonkey;
        int divisorFailedMonkey;
        int busyLevel = 0;
    }

    /**
     * This class takes in a worryItem and then stores only the remainders of a selected set of divisors.
     * These divisors are the test divisors that monkeys use for testing.
     * TODO the class should NOT simply assume that selectedDivisors was initialized in advance !!!
     */
    private class SpecialModulo {
        int[] remainders;

        SpecialModulo(int worryItem) {
            remainders = new int[selectedDivisors.size()];
            for (int i = 0; i < remainders.length; i++) {
                remainders[i] = worryItem % selectedDivisors.get(i);
            }
        }

        SpecialModulo() {
            remainders = new int[selectedDivisors.size()];
        }

        public SpecialModulo multiply(SpecialModulo other) {
            SpecialModulo res = new SpecialModulo();
            for (int i = 0; i < selectedDivisors.size(); i++) {
                res.remainders[i] = (remainders[i] * other.remainders[i]) % selectedDivisors.get(i);
            }
            return res;
        }

        public SpecialModulo add(SpecialModulo other) {
            SpecialModulo res = new SpecialModulo();
            for (int i = 0; i < selectedDivisors.size(); i++) {
                res.remainders[i] = (remainders[i] + other.remainders[i]) % selectedDivisors.get(i);
            }
            return res;
        }
    }
}