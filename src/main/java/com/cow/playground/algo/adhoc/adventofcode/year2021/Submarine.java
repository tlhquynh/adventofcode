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

public class Submarine {

  private static final Logger LOGGER = LoggerFactory.getLogger(Submarine.class);

  private static final String INPUT_FILENAME = "day2.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private long dep;
  private long hor;
  private List<String> ops;
  private List<Integer> vals;

  public Submarine() throws IOException {
    readInputs();
  }

  public void reset(long dep, long hor) {
    this.dep = dep;
    this.hor = hor;
  }

  public long findSimplePath() {
    reset(0, 0);
    for (int i = 0; i < ops.size(); i++) {
      switch (ops.get(i)) {
        case "forward": hor += vals.get(i); break;
        case "down": dep += vals.get(i); break;
        case "up": dep -= vals.get(i); break;
      }
    }
    long prod = dep * hor;
    LOGGER.info("day 2 task 1: 1804520");
    LOGGER.info("Simple path location depth={} horizontal={} prod={}", dep, hor, prod);
    return prod;
  }

  public long findComplexPath() {
    reset(0, 0);
    long aim = 0;
    for (int i = 0; i < ops.size(); i++) {
      switch (ops.get(i)) {
        case "forward": hor += vals.get(i); dep += aim * vals.get(i); break;
        case "down": aim += vals.get(i); break;
        case "up": aim -= vals.get(i); break;
      }
    }
    long prod = dep * hor;
    LOGGER.info("day 2 task 2: 1971095320");
    LOGGER.info("Complex path location depth={} horizontal={} prod={}", dep, hor, prod);
    return prod;
  }

  private void readInputs() throws IOException {

    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      ops = new ArrayList<>();
      vals = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(" ");
        ops.add(tokens[0]);
        vals.add(Integer.parseInt(tokens[1]));
      }
    }
  }
}
