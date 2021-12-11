package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HeightMapAnalyzer {

  private static final Logger LOGGER = LoggerFactory.getLogger(HeightMapAnalyzer.class);

  private static final String INPUT_FILENAME = "day9.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int Y = 0;
  private static final int X = 1;
  private static final int[] DY = {0, 0, -1, 1};
  private static final int[] DX = {-1, 1, 0, 0};

  List<int[]> map;

  public HeightMapAnalyzer() throws IOException {
    readInputs();
  }

  public List<int[]> detectLowPoints() {
    List<int[]> res = new ArrayList<>();
    int risk = 0;
    int nRow = map.size();
    int nCol = map.get(0).length;
    for (int i = 0; i < nRow; i++) {
      for (int j = 0; j < nCol; j++) {
        int height = map.get(i)[j];
        boolean low = true;
        for (int k = 0; k < DY.length; k++) {
          int nextY = i + DY[k];
          int nextX = j + DX[k];
          if (nextY >= 0 && nextY < nRow && nextX >= 0 && nextX < nCol && map.get(nextY)[nextX] <= height) {
            low = false;
          }
        }
        if (low) {
          res.add(new int[]{i, j});
          risk += 1 + height;
        }
      }
    }
    LOGGER.info("day 9 task 1: 594");
    LOGGER.info("smoke risk = {}", risk);
    return res;
  }

  public List<Integer> detectBasinAreas() {
    int nRow = map.size();
    int nCol = map.get(0).length;
    boolean[][] v = new boolean[nRow][nCol];
    List<Integer> sizes = new ArrayList<>();
    for (int[] point : detectLowPoints()) {
      Deque<int[]> q = new ArrayDeque<>();
      q.addLast(point);
      v[point[Y]][point[X]] = true;
      int size = 1;
      while (!q.isEmpty()) {
        int[] cur = q.removeFirst();
        int height = map.get(cur[Y])[cur[X]];
        for (int k = 0; k < DY.length; k++) {
          int nextY = cur[Y] + DY[k];
          int nextX = cur[X] + DX[k];
          if (nextY >= 0 && nextY < nRow && nextX >= 0 && nextX < nCol && !v[nextY][nextX] && map.get(nextY)[nextX] > height && map.get(nextY)[nextX] < 9) {
            q.addLast(new int[]{nextY, nextX});
            v[nextY][nextX] = true;
            size++;
          }
        }
      }
      sizes.add(size);
    }
    Collections.sort(sizes, Collections.reverseOrder());
    LOGGER.info("day 9 task 2: 858494");
    LOGGER.info("production of largest basin areas: {}", sizes.get(0) * sizes.get(1) * sizes.get(2));
    return sizes;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      map = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        int[] row = new int[line.length()];
        for (int i = 0; i < line.length(); i++) {
          row[i] = line.charAt(i) - '0';
        }
        map.add(row);
      }
    }
  }
}
