package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2020/day/15
 */
public class Day15 {

  private static final String inputFilename = "day15.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day15().solve();
  }

  public void solve() throws IOException {
    firstPart();
//    secondPart();
  }

  private void firstPart() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      List<Integer> nums = new ArrayList<>();
      Map<Integer, List<Integer>> occurs = new HashMap<>();
      int next = -1;
      for (String token : reader.readLine().split(",")) {
        int num = Integer.parseInt(token);
        nums.add(num);
        next = processOccurrence(occurs, nums, num);
      }
      for (int i = nums.size(); i < 30000000; i++) { // first star: 2020
        nums.add(next);
        next = processOccurrence(occurs, nums, next);
      }
      System.out.println(nums.get(nums.size()-1));
    }
  }

  private int processOccurrence(Map<Integer, List<Integer>> occurs, List<Integer> nums, int num) {
    int currLoc = nums.size();
    if (!occurs.containsKey(num)) {
      List<Integer> locs = new ArrayList<>();
      locs.add(currLoc);
      occurs.put(num, locs);
      return 0;
    }
    List<Integer> locs = occurs.get(num);
    int prevLoc = locs.get(locs.size()-1);
    locs.add(currLoc);
    occurs.put(num, locs);
    return currLoc - prevLoc;
  }
}