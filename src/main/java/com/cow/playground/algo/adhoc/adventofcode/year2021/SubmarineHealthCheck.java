package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SubmarineHealthCheck {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubmarineHealthCheck.class);

  private static final String INPUT_FILENAME = "day3.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private List<String> reportVals;

  public SubmarineHealthCheck() throws IOException {
    readInputs();
  }

  public int getPowerConsumption() {
    int[] ones = new int[reportVals.get(0).length()];
    for (String val : reportVals) {
      for (int i = 0; i < ones.length; i++) {
        if (val.charAt(i) == '1') {
          ones[i]++;
        }
      }
    }
    int gamma = 0;
    int epsilon = 0;
    int n = reportVals.size();
    for (int i = ones.length - 1, pow = 1; i >= 0; i--, pow *= 2) {
      if (ones[i] + ones[i] > n) {
        gamma += pow;
      } else {
        epsilon += pow;
      }
    }
    int res = gamma * epsilon;
    LOGGER.info("day 3 task 1: 4147524");
    LOGGER.info("gamma={} epsilon={} power={}", gamma, epsilon, res);
    return res;
  }

  public int getLifeSupportRating() {
    int res = getOxygenGeneratorRating() * getCo2ScrubberRating();
    LOGGER.info("day 3 task 2: 3570354");
    LOGGER.info("life support rating={}", res);
    return res;
  }

  private int getOxygenGeneratorRating() {
    Set<String> start = new HashSet<>(reportVals);
    for (int bit = 0; start.size() > 1; bit++) {
      Set<String> next = new HashSet<>();
      int ones = 0;
      for (String val : start) {
        if (val.charAt(bit) == '1') {
          ones++;
        }
      }
      if (ones + ones >= start.size()) {
        for (String val : start) {
          if (val.charAt(bit) == '1') {
            next.add(val);
          }
        }
      } else {
        for (String val : start) {
          if (val.charAt(bit) == '0') {
            next.add(val);
          }
        }
      }
      start = next;
    }
    return Integer.parseInt(start.iterator().next(), 2);
  }

  private int getCo2ScrubberRating() {
    Set<String> start = new HashSet<>(reportVals);
    for (int bit = 0; start.size() > 1; bit++) {
      Set<String> next = new HashSet<>();
      int zeroes = 0;
      for (String val : start) {
        if (val.charAt(bit) == '0') {
          zeroes++;
        }
      }
      if (zeroes + zeroes <= start.size()) {
        for (String val : start) {
          if (val.charAt(bit) == '0') {
            next.add(val);
          }
        }
      } else {
        for (String val : start) {
          if (val.charAt(bit) == '1') {
            next.add(val);
          }
        }
      }
      start = next;
    }
    return Integer.parseInt(start.iterator().next(), 2);
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      reportVals = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        reportVals.add(line);
      }
    }
  }
}
