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

public class MonkeyMath {
    private static final Logger LOGGER = LoggerFactory.getLogger(MonkeyMath.class);
    private static final String INPUT_FILENAME = "day21.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    Map<String, Variable> nameToVar;

    public MonkeyMath() throws IOException {
        readInputs();
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            nameToVar = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                //brtf: vltp + qcqj
                String[] tokens = line.split(" ");
                String varName = tokens[0].substring(0, tokens[0].length()-1);
                if (tokens.length == 2) {
                    Variable var = nameToVar.getOrDefault(varName, new Variable(varName));
                    var.setValue(Integer.parseInt(tokens[1]));
                    nameToVar.put(varName, var);
                } else {
                    Variable var = nameToVar.getOrDefault(varName, new Variable(varName));
                    var.setFormula(
                            Character.isDigit(tokens[1].charAt(0)) ? addVariable(Integer.parseInt(tokens[1])) : addVariable(tokens[1]),
                            Character.isDigit(tokens[3].charAt(0)) ? addVariable(Integer.parseInt(tokens[3])) : addVariable(tokens[3]),
                            Operator.getOp(tokens[2].charAt(0))
                            );
                    nameToVar.put(varName, var);
                }
            }
        }
    }
    public void task1() {
        long res = solve(nameToVar.get("root"));
        assert res == 80326079210554L;
        LOGGER.info("Monkey root yells: {}", res);
    }
    public void task2() throws IOException {
        // a few exploration runs point out that
        // - ptvl is not calculated from humn
        // - jsrw gets smaller when humn gets bigger
        // we will do a binary search from the approx range 0 to Long.MAX_VALUE / 1_000
        long lower = 0;
        long upper = Long.MAX_VALUE / 1_000;
        while (lower <= upper) {
            long mid = lower + (upper - lower) / 2;
            readInputs();
            nameToVar.get("humn").value = mid;
            long ptvlValue = solve(nameToVar.get("ptvl"));
            long jsrwValue = solve(nameToVar.get("jsrw"));
            if (jsrwValue == ptvlValue) {
                assert mid == 3617613952378L;
                LOGGER.info("humn should yell {}", mid);
                return;
            }
            if (jsrwValue > ptvlValue) {
                lower = mid + 1;
            } else {
                upper = mid - 1;
            }
        }
    }
    private long solve(Variable var) {
        if (var.isFinal) {
            return var.value;
        }
        switch (var.op) {
            case ADD: var.setValue(solve(var.left) + solve(var.right)); break;
            case SUB: var.setValue(solve(var.left) - solve(var.right)); break;
            case MUL: var.setValue(solve(var.left) * solve(var.right)); break;
            case DIV: var.setValue(solve(var.left) / solve(var.right)); break;
        }
        return var.value;
    }

    private Variable addVariable(String name) {
        Variable var = nameToVar.getOrDefault(name, new Variable(name));
        nameToVar.put(name, var);
        return var;
    }

    private Variable addVariable(int value) {
        String name = String.valueOf(value);
        Variable var = nameToVar.getOrDefault(name, new Variable(name));
        var.setValue(value);
        nameToVar.put(name, var);
        return var;
    }
    enum Operator {
        MUL('*'), SUB('-'), ADD('+'), DIV('/');
        char op;
        Operator(char op) {
            this.op = op;
        }
        static public Operator getOp(char op) {
            for (Operator operator : values()) {
                if (operator.op == op) {
                    return operator;
                }
            }
            throw new InvalidParameterException("Invalid operator: " + op);
        }
    }
    static class Variable {
        String name;
        long value;
        Variable left, right;
        boolean isFinal;
        Operator op;
        Variable(String name) {
            this.name = name;
            this.isFinal = false;
        }
        public void setValue(long value) {
            this.value = value;
            this.isFinal = true;
        }
        public void setFormula(Variable left, Variable right, Operator op) {
            this.isFinal = false;
            this.left = left;
            this.right = right;
            this.op = op;
        }
    }
}