package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CampCleanup {

    private static final Logger LOGGER = LoggerFactory.getLogger(CampCleanup.class);
    private static final String INPUT_FILENAME = "day4.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;

    private List<List<Assignment>> elfPairs;

    static class Assignment {
        int start, end;

        Assignment(int start, int end) {
            this.start = start;
            this.end = end;
        }

        boolean contains(Assignment other) {
            return this.start <= other.start && other.end <= this.end;
        }

        boolean overlaps(Assignment other) {
            return !(this.end < other.start || other.end < this.start);
        }
    }

    public CampCleanup() throws IOException {
        readInputs();
    }

    public void findEnclosingPairs() {
        int count = 0;
        for (List<Assignment> elfPair : elfPairs) {
            Assignment first = elfPair.get(0);
            Assignment second = elfPair.get(1);
            count += first.contains(second) || second.contains(first) ? 1 : 0;
        }
        assert count == 487;
        LOGGER.info("The number of enclosing elf pairs is {}", count);
    }

    public void findOverlappingPairs() {
        int count = 0;
        for (List<Assignment> elfPair : elfPairs) {
            Assignment first = elfPair.get(0);
            Assignment second = elfPair.get(1);
            count += first.overlaps(second) ? 1 : 0;
        }
        assert count == 849;
        LOGGER.info("The number of overlapping elf pairs is {}", count);
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            elfPairs = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("[-,]");
                elfPairs.add(Arrays.asList(
                        new Assignment(Integer.parseInt(tokens[0]), Integer.parseInt(tokens[1])),
                        new Assignment(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]))));
            }
        }
    }
}


