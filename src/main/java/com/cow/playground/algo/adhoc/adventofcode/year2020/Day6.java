package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

/**
 * https://adventofcode.com/2020/day/6
 */
public class Day6 {

  private static final String inputFilename = "day6.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  private Map<String, TextValidator> requirements;

  public static void main(String[] args) throws IOException {
    new Day6().solve();
  }

  public void solve() throws IOException {

    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line;
      int[] counts = new int['z' - 'a' + 1];
      int yesesAtLeastOnce = 0;
      int yesesAll = 0;
      int memberCount = 0;
      while ((line = reader.readLine()) != null) {
        if (line.isBlank()) {
          yesesAtLeastOnce += Arrays.stream(counts).filter(c -> c > 0).count();
          int finalMemberCount = memberCount;
          yesesAll += Arrays.stream(counts).filter(c -> c == finalMemberCount).count();
          Arrays.fill(counts, 0);
          memberCount = 0;
        } else {
          memberCount++;
          for (char ch : line.toCharArray()) {
            counts[ch - 'a']++;
          }
        }
      }
      yesesAtLeastOnce += Arrays.stream(counts).filter(c -> c > 0).count();
      int finalMemberCount = memberCount;
      yesesAll += Arrays.stream(counts).filter(c -> c == finalMemberCount).count();
      System.out.println(yesesAtLeastOnce);
      System.out.println(yesesAll);
    }
  }
}