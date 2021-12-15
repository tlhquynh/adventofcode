package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ChitonBoard {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChitonBoard.class);

  private static final String INPUT_FILENAME = "day15.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int[] DY = {0, 0, -1, 1};
  private static final int[] DX = {-1, 1, 0, 0};

  private List<int[]> board;

  public ChitonBoard() throws IOException {
    readInputs();
  }

  public int safestPathToTarget(int reps) {
    int nRow = board.size();
    int nCol = board.get(0).length;

    int[][] d = new int[nRow * reps][nCol * reps];
    dijkstra(0, 0, d);

    LOGGER.info("day 15 task 1 / 2: 769 / 2963");
    LOGGER.info("lowest risk path={}", d[d.length - 1][d[0].length - 1]);

    return d[d.length - 1][d[0].length - 1];
  }

  /**
   * Dijkstra when node is labeled as a coordinate (y, x)
   *
   * @param sy starting y
   * @param sx starting x
   * @param d  distance from (sy, sx)
   */
  private void dijkstra(int sy, int sx, int[][] d) {

    // nRow and nCol now are multiples of the original nRow and nCol
    int nRow = d.length;
    int nCol = d[0].length;

    for (int i = 0; i < nRow; i++) {
      for (int j = 0; j < nCol; j++) {
        d[i][j] = Integer.MAX_VALUE;
      }
    }

    d[sy][sx] = 0;
    PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt(u -> u[2]));
    q.add(new int[]{sy, sx, 0});
    while (!q.isEmpty()) {
      int[] cur = q.poll();
      int y = cur[0];
      int x = cur[1];
      int v = cur[2];
      if (v != d[y][x])
        continue;

      for (int i = 0; i < DY.length; i++) {
        int nextY = y + DY[i];
        int nextX = x + DX[i];
        if (nextY < 0 || nextX < 0 || nextY >= nRow || nextX >= nCol) {
          continue;
        }

        if (d[y][x] + getVal(nextY, nextX) < d[nextY][nextX]) {
          d[nextY][nextX] = d[y][x] + getVal(nextY, nextX);
          q.add(new int[]{nextY, nextX, d[nextY][nextX]});
        }
      }
    }
  }

  private int getVal(int i, int j) {
    int nRow = board.size();
    int nCol = board.get(0).length;
    return (board.get(i % nRow)[j % nCol] - 1 + i / nRow + j / nCol) % 9 + 1;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      board = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        int[] row = new int[line.length()];
        for (int i = 0; i < line.length(); i++) {
          row[i] = line.charAt(i) - '0';
        }
        board.add(row);
      }
    }
  }
}
