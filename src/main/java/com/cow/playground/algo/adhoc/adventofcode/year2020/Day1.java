package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * https://adventofcode.com/2020/day/1
 */
public class Day1 {

  private static final String inputFilename = "day1.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day1().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      boolean[] v = new boolean[2021];
      String line;
      while ((line = reader.readLine()) != null) {
        int num = Integer.parseInt(line);
        v[num] = true;
      }
      System.out.println(find(0, v, 2, 2020, 1, -1));
      System.out.println(find(0, v, 3, 2020, 1, -1));
    }
  }

  private long find(int step, boolean[] v, int numCount, int sumLeft, long productSoFar, int last) {
    if (step + 2 == numCount) {
      for (int j = last + 1; j < sumLeft - j; j++) {
        if (v[j] && v[sumLeft - j]) {
          return productSoFar * j * (sumLeft - j);
        }
      }
      return -1;
    }
    for (int i = last + 1; i * (numCount - step) < sumLeft; i++) {
      if (v[i]) {
        long res = find(step + 1, v, numCount, sumLeft - i, productSoFar * i, i);
        if (res > -1) {
          return res;
        }
      }
    }
    return -1;
  }
}