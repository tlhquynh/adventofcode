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

public class SubmarineDisplay {
  private static final Logger LOGGER = LoggerFactory.getLogger(Submarine.class);

  private static final String INPUT_FILENAME = "day8.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private static final String[] ORIGINAL_DIGITS = {"abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"};
  private static final String[] SORTED_DIGITS = ORIGINAL_DIGITS.clone();
  static {
    Arrays.sort(SORTED_DIGITS);
  }

  private List<DisplayEntry> entryList;

  public SubmarineDisplay() throws IOException {
    readInputs();
  }

  public int count1478() {
    int res = 0;
    for (DisplayEntry entry : entryList) {
      for (String output : entry.outputs) {
        if (output.length() == 2 || output.length() == 3 || output.length() == 4 || output.length() == 7) {
          res++;
        }
      }
    }
    LOGGER.info("day 8 task 1: 301");
    LOGGER.info("the number of digits 1/4/7/8 is {}", res);
    return res;
  }

  public int findOutputSum() {
    int res = 0;
    for (DisplayEntry entry : entryList) {
      int val = read(entry, 0, new StringBuilder(), new boolean[7]);
      LOGGER.debug("output: {}", val);
      res += val;
    }
    LOGGER.info("day 8 task 2: 908067");
    LOGGER.info("output sum is {}", res);
    return res;
  }

  /**
   *
   * @param entry
   * @param step 0..6 to map wrong wires to right wires
   * @param map mapping from wrong wires to right wires
   * @param usedChar mark right wires already used in the mapping
   * @return
   */
  private int read(DisplayEntry entry, int step, StringBuilder map, boolean[] usedChar) {
    if (step >= 7) {
      // generate 10 digit strings based on wire mapping
      String[] solution = new String[10];
      for (int i = 0; i < entry.patterns.length; i++) {
        solution[i] = translate(entry.patterns[i], map);
      }
      Arrays.sort(solution);

      // check the generated 10 digit strings against the right 10 digit strings
      boolean isSolution = true;
      for (int i = 0; i < SORTED_DIGITS.length; i++) {
        if (!SORTED_DIGITS[i].equals(solution[i])) {
          isSolution = false;
        }
      }
      if (!isSolution) {
        return -1;
      }

      // build the output number only when mapping is correct
      int res = 0;
      for (int i = 0; i < entry.outputs.length; i++) {
        String digit = translate(entry.outputs[i], map);
        for (int j = 0; j < ORIGINAL_DIGITS.length; j++) {
          if (ORIGINAL_DIGITS[j].equals(digit)) {
            res = res * 10 + j;
            break;
          }
        }
      }
      return res;
    }

    // try all possible mappings
    for (int i = 0; i < usedChar.length; i++) {
      if (usedChar[i]) {
        continue;
      }
      usedChar[i] = true;
      char ch = (char)(i + 'a');
      int solution = read(entry, step+1, map.append(ch), usedChar);
      if (solution >= 0) {
        return solution;
      }
      map.deleteCharAt(map.length()-1);
      usedChar[i] = false;
    }
    return -1;
  }

  /**
   * Translate a reading of wrong wires to a correct reading based on the mapping
   * @param encode a reading of wrong wires
   * @param map mapping from wrong wires to right wires
   * @return
   */
  private static String translate(String encode, StringBuilder map) {
    StringBuilder sb = new StringBuilder();
    for (char ch : encode.toCharArray()) {
      sb.append(map.charAt(ch - 'a'));
    }
    return sb.toString().chars().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
  }

  private void readInputs() throws IOException {

    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      entryList = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        DisplayEntry entry = new DisplayEntry();
        String[] tokens = line.split("\\|");
        entry.patterns = tokens[0].trim().split(" ");
        entry.outputs = tokens[1].trim().split(" ");
        entryList.add(entry);
      }
    }
  }

  private class DisplayEntry {
    private String[] patterns;
    private String[] outputs ;
  }
}
