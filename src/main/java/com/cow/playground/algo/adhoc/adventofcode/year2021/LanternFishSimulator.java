package com.cow.playground.algo.adhoc.adventofcode.year2021;

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

public class LanternFishSimulator {

  private static final Logger LOGGER = LoggerFactory.getLogger(LanternFishSimulator.class);

  private static final String INPUT_FILENAME = "day6.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int MAX_INTERNAL_CLOCK = 8;
  private List<Integer> startingFishes;

  public LanternFishSimulator() throws IOException {
    readInputs();
  }

  public long speculateSchoolSize(int days) {

    long[] count = new long[MAX_INTERNAL_CLOCK + 1];
    for (int fish : startingFishes) {
      count[fish]++;
    }

    for (int i = 1; i <= days; i++) {
      long temp = count[0];
      for (int j = 1; j <= MAX_INTERNAL_CLOCK; j++) {
        count[j - 1] = count[j];
      }
      count[8] = temp;
      count[6] += temp;
      LOGGER.debug("after day {}: {}", i, Arrays.toString(count));
    }

    long res = 0;
    for (int i = 0; i <= MAX_INTERNAL_CLOCK; i++) {
      res += count[i];
    }
    LOGGER.info("day 6: 360610 after 80 days and 1631629590423 after 256 days");
    LOGGER.info("lantern fish school size after {} days: {}", days, res);

    return res;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      startingFishes = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        for (String fish : line.split(",")) {
          startingFishes.add(Integer.parseInt(fish));
        }
      }
    }
  }
}
