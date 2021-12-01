package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/3
 */
public class Day3 {

  private static final String inputFilename = "day3.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day3().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line;
      List<String> map = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        map.add(line);
      }
      System.out.println(getTrees(map, 1, 3));
      System.out.println(1L * getTrees(map, 1, 1) * getTrees(map, 1, 3) * getTrees(map, 1, 5) * getTrees(map, 1, 7) * getTrees(map, 2, 1));
    }
  }

  private static int getTrees(List<String> map, int dr, int dc) {
    int trees = 0;
    int cols = map.get(0).length();
    for (int r = 0, c = 0; r < map.size(); r += dr, c += dc) {
      trees += map.get(r).charAt(c % cols) == '#' ? 1 : 0;
    }
    return trees;
  }
}