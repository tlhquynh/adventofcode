package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://adventofcode.com/2020/day/9
 */
public class Day9 {

  private static final String inputFilename = "day9.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day9().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line;
      final List<BigInteger> nums = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        nums.add(new BigInteger(line));
      }

      BigInteger weakness = firstPart(nums, 25);
      System.out.println(weakness.toString());
      System.out.println(secondPart(nums, weakness).toString());
    }
  }

  private BigInteger firstPart(List<BigInteger> nums, int windowSize) {
    Map<BigInteger, Integer> sums = new HashMap<>();
    for (int i = 1; i < windowSize; i++) {
      for (int j = 0; j < i; j++) {
        BigInteger sum = nums.get(i).add(nums.get(j));
        Integer count = sums.get(sum);
        if (count == null) {
          sums.put(sum, 1);
        } else {
          sums.put(sum, count + 1);
        }
      }
    }
    BigInteger res = null;
    for (int i = windowSize; i < nums.size(); i++) {
      if (!sums.containsKey(nums.get(i))) {
        res = nums.get(i);
      }
      for (int j = i - windowSize + 1; j < i; j++) {
        BigInteger sum = nums.get(i).add(nums.get(j));
        Integer count = sums.get(sum);
        if (count == null) {
          sums.put(sum, 1);
        } else {
          sums.put(sum, count + 1);
        }

        sum = nums.get(i - windowSize).add(nums.get(j));
        count = sums.get(sum);
        if (count == 1) {
          sums.remove(sum);
        } else {
          sums.put(sum, count - 1);
        }
      }
    }
    return res;
  }

  private BigInteger secondPart(List<BigInteger> nums, BigInteger target) {
    BigInteger sum = nums.get(0);
    for (int left = 0, right = 0; right < nums.size(); ) {
      int comp = sum.compareTo(target);
      if (comp < 0) {
        sum = sum.add(nums.get(++right));
      }
      while (comp > 0) {
        sum = sum.subtract(nums.get(left++));
        comp = sum.compareTo(target);
      }
      if (comp == 0) {
        BigInteger min = nums.get(left);
        BigInteger max = nums.get(right);
        for (int i = left; i <= right; i++) {
          min = min.compareTo(nums.get(i)) > 0 ? nums.get(i) : min;
          max = max.compareTo(nums.get(i)) < 0 ? nums.get(i) : max;
        }
        return min.add(max);
      }
    }
    return null;
  }
}