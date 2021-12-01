package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

/**
 * https://adventofcode.com/2020/day/10
 */
public class Day10 {

  private static final String inputFilename = "day10.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day10().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line;
      final List<Integer> nums = new ArrayList<>();
      nums.add(0);
      while ((line = reader.readLine()) != null) {
        nums.add(Integer.valueOf(line));
      }
      Collections.sort(nums);
      nums.add(nums.get(nums.size()-1) + 3);
      System.out.println(firstPart(nums));
      System.out.println(secondPart(nums).toString());
    }
  }

  private int firstPart(List<Integer> nums) {
    int[] diffCount = new int[4];
    for (int i = 1; i < nums.size(); i++) {
      diffCount[nums.get(i)-nums.get(i-1)]++;
    }
    return diffCount[1] * diffCount[3];
  }

  private BigInteger secondPart(List<Integer> nums) {
    BigInteger[] dp = new BigInteger[nums.size()];
    dp[0] = BigInteger.ONE;
    for (int i = 1; i < dp.length; i++) {
      dp[i] = BigInteger.ZERO.add(i - 1 >= 0 && nums.get(i-1) + 3 >= nums.get(i) ? dp[i-1] : BigInteger.ZERO);
      dp[i] = dp[i].add(i - 2 >= 0 && nums.get(i-2) + 3 >= nums.get(i) ? dp[i-2] : BigInteger.ZERO);
      dp[i] = dp[i].add(i - 3 >= 0 && nums.get(i-3) + 3 >= nums.get(i) ? dp[i-3] : BigInteger.ZERO);
    }
    return dp[dp.length-1];
  }
}