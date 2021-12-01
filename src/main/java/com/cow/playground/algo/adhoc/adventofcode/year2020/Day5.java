package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeSet;

/**
 * https://adventofcode.com/2020/day/5
 */
public class Day5 {

  private static final String inputFilename = "day5.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  private static Map<String, TextValidator> requirements;

  public static void main(String[] args) throws IOException {
    new Day5().solve();
  }

  private static int guess(String s, char c0) {
    int n = s.length();
    int min = 0;
    int max = (1 << n) - 1;
    for (int i = 0; i < n; i++) {
      if (s.charAt(i) == c0) {
        max = min + (max - min) / 2;
      } else {
        min = min + (max - min) / 2 + 1;
      }
    }
    return min;
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line;
      TreeSet<Integer> seats = new TreeSet<>();
      while ((line = reader.readLine()) != null) {
        int seat = guess(line.substring(0, 7), 'F') * 8 + guess(line.substring(7), 'L');
        seats.add(seat);
      }
      System.out.println(seats.last());
      for (Integer seat : seats) {
        if (!seats.contains(seat + 1) && seats.contains(seat + 2)) {
          System.out.println(seat + 1);
          return;
        }
      }
    }
  }
}