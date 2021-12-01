package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/13
 */
public class Day13 {

  private static final String inputFilename = "day13.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day13().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      int start = Integer.parseInt(reader.readLine());
      String[] busTokens = reader.readLine().split(",");
      List<Integer> buses = new ArrayList<>();
      for (String token : busTokens) {
        if (!token.equals("x")) {
          buses.add(Integer.parseInt(token));
        }
      }
      System.out.println(firstPart(buses, start));
    }
  }

  private int firstPart(List<Integer> buses, int start) {
    int min = Integer.MAX_VALUE;
    int busId = -1;
    for (int bus : buses) {
      int temp = (start + bus - 1) / bus * bus;
      if (temp < min) {
        min = temp;
        busId = bus;
      }
    }

    return (min - start) * busId;
  }
}