package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * https://adventofcode.com/2020/day/4
 */
public class Day4 {

  private static final String inputFilename = "day4.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  private Map<String, TextValidator> requirements;

  public static void main(String[] args) throws IOException {
    new Day4().buildRequirements().solve();
  }

  public Day4 buildRequirements() {
    requirements = new HashMap<>();
    requirements.put("byr", new TextValidator() {
      @Override
      public boolean validate(String text) {
        return isNumericInRange(text, 1920, 2002);
      }
    });
    requirements.put("iyr", new TextValidator() {
      @Override
      public boolean validate(String text) {
        return isNumericInRange(text, 2010, 2020);
      }
    });
    requirements.put("eyr", new TextValidator() {
      @Override
      public boolean validate(String text) {
        return isNumericInRange(text, 2020, 2030);
      }
    });
    requirements.put("hgt", new TextValidator() {
      @Override
      public boolean validate(String text) {
        return (text.matches("\\d+cm") && isNumericInRange(text.substring(0, text.length() - 2), 150, 193))
                || (text.matches("\\d+in") && isNumericInRange(text.substring(0, text.length() - 2), 59, 76));
      }
    });
    requirements.put("hcl", s -> s.matches("#[0-9a-f]{6}"));
    requirements.put("ecl", s -> s.matches("amb|blu|brn|gry|grn|hzl|oth"));
    requirements.put("pid", s -> s.matches("[0-9]{9}"));
    requirements.put("cid", s -> true);
    return this;
  }

  public void solve() throws IOException {

    int relaxedValid = 0;
    int strictValid = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line;
      StringBuilder sb = new StringBuilder();
      while ((line = reader.readLine()) != null) {
        if (line.isBlank()) {
          relaxedValid += validate(sb, requirements, false);
          strictValid += validate(sb, requirements, true);
          sb = new StringBuilder();
        } else {
          sb = sb.append(" ").append(line);
        }
      }
      relaxedValid += validate(sb, requirements, false);
      strictValid += validate(sb, requirements, true);
      System.out.println(relaxedValid);
      System.out.println(strictValid);
    }
  }

  private static int validate(StringBuilder sb, Map<String, TextValidator> requirements, boolean isStrict) {
    String[] tokens = sb.toString().split(" ");
    Set<String> attributes = new HashSet<>();
    for (String token : tokens) {
      int colon = token.indexOf(":");
      if (colon >= 0) {
        String attribute = token.substring(0, colon);
        String value = token.substring(colon + 1);
        if (requirements.containsKey(attribute)) {
          if (isStrict && !requirements.get(attribute).validate(value)) {
            return 0;
          }
          attributes.add(attribute);
        }
      }
    }
    Set<String> temp = new HashSet<>(requirements.keySet());
    temp.removeAll(attributes);
    return temp.isEmpty() || (temp.size() == 1 && temp.contains("cid")) ? 1 : 0;
  }
}