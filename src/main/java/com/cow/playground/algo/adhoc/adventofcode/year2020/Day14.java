package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2020/day/14
 */
public class Day14 {

  private static final String inputFilename = "day14.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day14().solve();
  }

  public void solve() throws IOException {
    firstPart();
    secondPart();
  }

  private void firstPart() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line = null;
      String mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";
      Map<BigInteger, BigInteger> mem = new HashMap<>();
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("mask")) {
          mask = line.substring(7);
        } else {
          Pattern p = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");
          Matcher m = p.matcher(line);
          m.find();
          BigInteger loc = new BigInteger(m.group(1));
          BigInteger value = new BigInteger(m.group(2));
          char[] digits = String.format("%36s", value.toString(2)).replace(" ", "0").toCharArray();
          for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) != 'X') {
              digits[i] = mask.charAt(i);
            }
          }
          value = new BigInteger(new String(digits), 2);
          mem.put(loc, value);
        }
      }
      BigInteger sum = BigInteger.ZERO;
      for (BigInteger value : mem.values()) {
        sum = sum.add(value);
      }
      System.out.println(sum.toString());
    }
  }
  private void secondPart() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line = null;
      String mask = "000000000000000000000000000000000000";
      Map<BigInteger, BigInteger> mem = new HashMap<>();
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("mask")) {
          mask = line.substring(7);
        } else {
          Pattern p = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");
          Matcher m = p.matcher(line);
          m.find();
          BigInteger originalLoc = new BigInteger(m.group(1));
          BigInteger value = new BigInteger(m.group(2));
          char[] digits = String.format("%36s", originalLoc.toString(2)).replace(" ", "0").toCharArray();
          List<BigInteger> locs = find(mask, digits, 0);
          for (BigInteger loc : locs) {
            mem.put(loc, value);
          }

        }
      }
      BigInteger sum = BigInteger.ZERO;
      for (BigInteger value : mem.values()) {
        sum = sum.add(value);
      }
      System.out.println(sum.toString());
    }
  }

  private List<BigInteger> find(String mask, char[] digits, int step) {
    List<BigInteger> res = new ArrayList<>();
    if (step == mask.length()) {
      res.add(new BigInteger(new String(digits), 2));
      return res;
    }
    char ch = mask.charAt(step);
    if (ch == '0') {
      return find(mask, digits, step+1);
    }
    if (ch == '1') {
      digits[step] = '1';
      return find(mask, digits, step+1);
    }
    digits[step] = '1';
    res.addAll(find(mask, digits, step+1));
    digits[step] = '0';
    res.addAll(find(mask, digits, step+1));
    return res;
  }
}