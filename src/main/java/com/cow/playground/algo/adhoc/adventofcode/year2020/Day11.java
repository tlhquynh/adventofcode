package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/11
 */
public class Day11 {

  private static final String inputFilename = "day11.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;
  private static final int CHAR_DOT = 0;
  private static final int CHAR_L = 1;
  private static final int CHAR_HASH = 10;
  private static final int[] DR = {-1, -1, -1, 0, 0, 1, 1, 1};
  private static final int[] DC = {-1, 0, 1, -1, 1, -1, 0, 1};

  public static void main(String[] args) throws IOException {
    new Day11().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line;
      final List<char[]> floor = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        floor.add(line.toCharArray());
      }
      System.out.println(transform(floor, new CellTransformer() {
        @Override
        public int next(int[][] a, int r, int c) {
          return _next(a, r, c, 1, 4);
        }
      }));
      System.out.println(transform(floor, new CellTransformer() {
        @Override
        public int next(int[][] a, int r, int c) {
          return _next(a, r, c, 100, 5);
        }
      }));
    }
  }

  private int transform(List<char[]> floor, CellTransformer transformer) {

    int rows = floor.size();
    int cols = floor.get(0).length;
    int[][] a = new int[rows+2][cols+2];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        a[i+1][j+1] = floor.get(i)[j] == 'L' ? CHAR_L : floor.get(i)[j] == '#' ? CHAR_HASH : CHAR_DOT;
      }
    }

    int[][] b = new int[rows+2][cols+2];
    do {
      boolean ok = false;
      for (int i = 1; i <= rows; i++) {
        for (int j = 1; j <= cols; j++) {
          b[i][j] = transformer.next(a, i, j);
          ok |= (a[i][j] != b[i][j]);
        }
      }
      if (!ok) {
        break;
      }
      for (int i = 1; i <= rows; i++) {
        a[i] = b[i].clone();
      }
    } while (true);
    int res = 0;
    for (int i = 1; i <= rows; i++) {
      for (int j = 1; j <= cols; j++) {
        res += a[i][j] == CHAR_HASH ? 1 : 0;
      }
    }
    return res;
  }

  private int _next(int[][] a, int r, int c, int max, int maxOccupiedToEmptySeat) {
    int rows = a.length;
    int cols = a[0].length;
    int count = 0;
    for (int i = 0; i < DR.length; i++) {
      for (int j = 0, nextR = r + DR[i], nextC = c + DC[i]; j < max && nextR >= 1 && nextC >= 1 && nextR < rows-1 && nextC < cols; j++, nextR += DR[i], nextC += DC[i]) {
        if (a[nextR][nextC] > CHAR_DOT) {
          count += a[nextR][nextC];
          break;
        }
      }
    }
    if (a[r][c] == CHAR_L && count / CHAR_HASH == 0) {
      return CHAR_HASH;
    }
    if (a[r][c] == CHAR_HASH && count / CHAR_HASH >= maxOccupiedToEmptySeat) {
      return CHAR_L;
    }
    return a[r][c];
  }

  @FunctionalInterface
  interface CellTransformer {
    int next(int[][] a, int r, int c);
  }
}