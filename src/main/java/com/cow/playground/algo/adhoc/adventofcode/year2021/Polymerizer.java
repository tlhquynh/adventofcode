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
  private Map<String, Character> rules;

  public Polymerizer() throws IOException {
    readInputs();
  }

  public long[] transform(int rounds) {
    // count[u][v][i][j]: String uv after i rounds generates how many characters from A to Z
    long[][][][] count = new long['Z' - 'A' + 1]['Z' - 'A' + 1][41][];
    long[] res = new long['Z' - 'A' + 1];
    for (int i = 0; i < template.length() - 1; i++) {
      countChars(template.charAt(i), template.charAt(i + 1), rounds, count);
      for (int j = 0; j < res.length; j++) {
        res[j] += count[template.charAt(i) - 'A'][template.charAt(i + 1) - 'A'][rounds][j];
      }
      res[template.charAt(i) - 'A']--;
    }
    // it was unnecessarily deducted above
    res[template.charAt(0) - 'A']++;

    long min = Long.MAX_VALUE;
    long max = Long.MIN_VALUE;
    for (int i = 0; i < res.length; i++) {
      if (res[i] > 0) {
        min = Math.min(min, res[i]);
        max = Math.max(max, res[i]);
      }
    }

    LOGGER.info("day 14 task 1 / 2: 2621 / 2843834241366");
    LOGGER.info("difference of max and min counts after {} rounds: {}", rounds, max - min);

    return res;
  }

  private void countChars(char c1, char c2, int roundsLeft, long[][][][] count) {
    if (count[c1 - 'A'][c2 - 'A'][roundsLeft] != null) {
      return;
    }

    count[c1 - 'A'][c2 - 'A'][roundsLeft] = new long['Z' - 'A' + 1];
    Character rule = rules.get(c1 + "" + c2);
    if (roundsLeft == 0 || rule == null) {
      count[c1 - 'A'][c2 - 'A'][roundsLeft][c1 - 'A']++;
      count[c1 - 'A'][c2 - 'A'][roundsLeft][c2 - 'A']++;
      return;
    }

    countChars(c1, rule, roundsLeft - 1, count);
    countChars(rule, c2, roundsLeft - 1, count);
    for (int i = 0; i < 'Z' - 'A' + 1; i++) {
      count[c1 - 'A'][c2 - 'A'][roundsLeft][i] += count[c1 - 'A'][rule - 'A'][roundsLeft - 1][i];
      count[c1 - 'A'][c2 - 'A'][roundsLeft][i] += count[rule - 'A'][c2 - 'A'][roundsLeft - 1][i];
    }
    count[c1 - 'A'][c2 - 'A'][roundsLeft][rule - 'A']--;
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
          rules.put(tokens[0].trim(), tokens[1].trim().charAt(0));
        }
      }
    }
  }

}
