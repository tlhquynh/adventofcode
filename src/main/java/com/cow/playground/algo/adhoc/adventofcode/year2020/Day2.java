package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2020/day/2
 */
public class Day2 {

  private static final String inputFilename = "day2.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day2().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {

      TextValidator firstValidator = getFirstValidator();
      TextValidator secondValidator = getSecondValidator();
      int firstValid = 0;
      int secondValid = 0;
      String line;
      while ((line = reader.readLine()) != null) {
        if (firstValidator.validate(line)) {
          firstValid++;
        }
        if (secondValidator.validate(line)) {
          secondValid++;
        }
      }
      System.out.println(firstValid);
      System.out.println(secondValid);
    }
  }

  private TextValidator getSecondValidator() {
    return text -> {
      Pattern p = Pattern.compile("(\\d+)(-)(\\d+)(\\s)([a-z])(:\\s)([a-z]+)");
      Matcher m = p.matcher(text);
      m.find();
      int first = Integer.parseInt(m.group(1)) - 1;
      int second = Integer.parseInt(m.group(3)) - 1;
      char letter = m.group(5).charAt(0);
      String password = m.group(7);
      if ((password.charAt(first) == letter ? 1 : 0) + (password.charAt(second) == letter ? 1 : 0) == 1) {
        return true;
      }
      return false;
    };
  }

  private TextValidator getFirstValidator() {
    return text -> {
      Pattern p = Pattern.compile("(\\d+)(-)(\\d+)(\\s)([a-z])(:\\s)([a-z]+)");
      Matcher m = p.matcher(text);
      m.find();
      int min = Integer.parseInt(m.group(1));
      int max = Integer.parseInt(m.group(3));
      char letter = m.group(5).charAt(0);
      int count = 0;
      for (char ch : m.group(7).toCharArray()) {
        if (ch == letter) {
          count++;
        }
      }
      if (min <= count && count <= max) {
        return true;
      }
      return false;
    };
  }
}