package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class EscapePlan {

  private static final Logger LOGGER = LoggerFactory.getLogger(EscapePlan.class);

  private static final String INPUT_FILENAME = "day7.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private ArrayList<Integer> startingCrabs;

  public EscapePlan() throws IOException {
    readInputs();
  }

  public long bestSimpleCrabAlignment() {
    return new SimpleEscapeStrategy().execute(startingCrabs);
  }

  public long bestComplexCrabAlignment() {
    return new ComplexEscapeStrategy().execute(startingCrabs);
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      startingCrabs = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        for (String fish : line.split(",")) {
          startingCrabs.add(Integer.parseInt(fish));
        }
      }
    }
  }
}
