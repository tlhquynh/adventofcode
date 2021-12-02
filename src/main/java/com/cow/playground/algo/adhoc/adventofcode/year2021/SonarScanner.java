package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SonarScanner {

  private static final Logger LOGGER = LoggerFactory.getLogger(SonarScanner.class);

  private static final String INPUT_FILENAME = "day1.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private List<Integer> depths;

  public SonarScanner() throws IOException {
    readInputs();
  }

  public int countDrops() {
    LOGGER.info("day 1 task 1: 1602");
    return countWindowDrops(1);
  }

  public int countSize3WindowDrops() {
    LOGGER.info("day 1 task 2: 1633");
    return countWindowDrops(3);
  }

  private int countWindowDrops(int windowSize) {
    int res = 0;
    for (int i = 0; i < depths.size() - windowSize; i++) {
      if (depths.get(i) < depths.get(i + windowSize)) {
        res++;
      }
    }
    LOGGER.info("size-{} window drop count: {}", windowSize, res);
    return res;
  }

  private void readInputs() throws IOException {

    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      depths = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        int d = Integer.parseInt(line);
        depths.add(d);
      }
    }
  }
}
