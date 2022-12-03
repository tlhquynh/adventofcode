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

public class RucksackReorganization {

    private static final Logger LOGGER = LoggerFactory.getLogger(RucksackReorganization.class);
    private static final String INPUT_FILENAME = "day3.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;

    private List<Rucksack> rucksacks;

    class Rucksack {

        char[] items;

        List<char[]> compartments = new ArrayList<>();

        Rucksack(char[] items) {
            this.items = items;
            compartments.add(Arrays.copyOfRange(items, 0, items.length / 2));
            compartments.add(Arrays.copyOfRange(items, items.length / 2, items.length));
        }

        char findTheOnlySharedItemInTwoCompartments() {
            return getTheOnlySharedItem(compartments.get(0), compartments.get(1));
        }

        char[] getItems() {
            return items;
        }
    }

    public RucksackReorganization() throws IOException {
        readInputs();
    }

    public void task1() {
        int sum = 0;
        for (Rucksack s : rucksacks) {
            sum += getItemPriority(s.findTheOnlySharedItemInTwoCompartments());
        }
        assert sum == 8349;
        LOGGER.info("day 3 task 1: {}", sum);
    }

    public void task2() {
        int sum = 0;
        for (int i = 0; i < rucksacks.size(); i += 3) {
            sum += getItemPriority(getTheOnlySharedItem(
                    rucksacks.get(i).getItems(),
                    rucksacks.get(i + 1).getItems(),
                    rucksacks.get(i + 2).getItems()));
        }
        assert sum == 2681;
        LOGGER.info("day 3 task 2: {}", sum);
    }

    private char getTheOnlySharedItem(char[]... itemsArrays) {
        Map<Character, Integer> seenCount = new HashMap<>();
        int currentCount = 0;
        for (char[] items : itemsArrays) {
            for (char item : items) {
                int count = seenCount.getOrDefault(item, 0);
                if (count >= currentCount) {
                    seenCount.put(item, currentCount + 1);
                } else {
                    seenCount.remove(item);
                }
            }
            currentCount++;
        }
        for (Map.Entry<Character, Integer> count : seenCount.entrySet()) {
            if (count.getValue() == currentCount) {
                return count.getKey();
            }
        }
        throw new InvalidParameterException("Input compartments have no shared item");
    }

    private int getItemPriority(char ch) {
        if (ch >= 'a' && ch <= 'z')
            return ch - 'a' + 1;
        if (ch >= 'A' && ch <= 'Z')
            return ch - 'A' + 27;
        throw new InvalidParameterException("Input contains invalid rucksack item " + ch);
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            rucksacks = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                Rucksack r = new Rucksack(line.toCharArray());
                rucksacks.add(r);
            }
        }
    }
}
