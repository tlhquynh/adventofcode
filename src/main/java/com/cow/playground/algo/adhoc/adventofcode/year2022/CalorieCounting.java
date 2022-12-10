package com.cow.playground.algo.adhoc.adventofcode.year2022;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CalorieCounting {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalorieCounting.class);
    private static final String INPUT_FILENAME = "day1.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private List<List<Integer>> elfInventory;

    public CalorieCounting() throws IOException {
        readInputs();
    }

    public void findTops() {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        for (List<Integer> calorieCount : elfInventory) {
            int sum = 0;
            for (int calories : calorieCount) {
                sum += calories;
            }
            pq.add(sum);
        }

        int sum = pq.remove();
        assert sum == 67450;
        LOGGER.info("day 1 task 1: {}", sum);

        sum += pq.remove() + pq.remove();
        assert sum == 199357;
        LOGGER.info("day 1 task 2: {}}", sum);
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            this.elfInventory = new ArrayList<>();
            List<Integer> calorieCounts = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.isBlank()) {
                    this.elfInventory.add(calorieCounts);
                    calorieCounts = new ArrayList<>();
                } else {
                    int calories = Integer.parseInt(line);
                    calorieCounts.add(calories);
                    LOGGER.debug("New calories {}", calories);
                }
            }

            if (!calorieCounts.isEmpty()) {
                this.elfInventory.add(calorieCounts);
            }
        }
    }
}
