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

  private List<String> ops;
  private List<Integer> vals;

  private SubmarineExplorationStrategy explorationStrategy;

  public Submarine() throws IOException {
    readInputs();
    this.explorationStrategy = new SimpleExplorationStrategy();
    this.explorationStrategy.setOps(ops);
    this.explorationStrategy.setVals(vals);
  }

  public long explore() {
    return explorationStrategy.explore(0, 0);
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

  public void setExplorationStrategy(SubmarineExplorationStrategy explorationStrategy) {
    this.explorationStrategy = explorationStrategy;
    this.explorationStrategy.setOps(ops);
    this.explorationStrategy.setVals(vals);
  }
}
