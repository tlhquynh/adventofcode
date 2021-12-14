package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Polymerizer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Polymerizer.class);

  private static final String INPUT_FILENAME = "day14.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private String template;
  Map<String, String> rules;

  public Polymerizer() throws IOException {
    readInputs();
  }

  public int transform(int rounds) {
    StringBuilder sb = new StringBuilder(template);
    for (int i = 0; i < rounds; i++) {
      for (int j = sb.length() - 2; j >= 0; j--) {
        String rule = rules.get(sb.substring(j, j + 2));
        if (rule != null) {
          sb.insert(j + 1, rule);
        }
      }
    }

    int[] count = new int['Z' - 'A' + 1];
    for (int i = 0; i < sb.length(); i++) {
      count[sb.charAt(i) - 'A']++;
    }
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for (int i = 0; i < count.length; i++) {
      if (count[i] > 0) {
        min = Math.min(min, count[i]);
        max = Math.max(max, count[i]);
      }
    }

    LOGGER.info("day 14 task 1: 2621");
    LOGGER.info("difference of max and min count: {}", max - min);

    return max - min;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      template = null;
      rules = new HashMap<>();
      String line;
      while ((line = reader.readLine()) != null) {
        if (template == null) {
          template = line;
        } else if (line.indexOf("->") >= 0) {
          String[] tokens = line.split("->");
          rules.put(tokens[0].trim(), tokens[1].trim());
        }
      }
    }
  }

}
