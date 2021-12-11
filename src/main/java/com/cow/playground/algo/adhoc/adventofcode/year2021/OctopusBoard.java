package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class OctopusBoard {

  private static final Logger LOGGER = LoggerFactory.getLogger(OctopusBoard.class);

  private static final String INPUT_FILENAME = "day11.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int[] DY = {-1, -1, -1, 0, 1, 1, 1, 0};
  private static final int[] DX = {-1, 0, 1, 1, 1, 0, -1, -1};
  private static final int Y = 0;
  private static final int X = 1;

  List<int[]> board;

  public OctopusBoard() throws IOException {
    readInputs();
  }

  public int simulate(int steps) {
    List<int[]> temp = new ArrayList<>();
    for (int[] row : board) {
      temp.add(row.clone());
    }
    int res = 0;
    for (int i = 0; i < steps; i++) {
      res += simulate(temp);
    }
    LOGGER.info("day 11 task 1: 1632");
    LOGGER.info("flash count={}", res);
    return res;
  }

  public int simulateNonstop() {
    List<int[]> temp = new ArrayList<>();
    for (int[] row : board) {
      temp.add(row.clone());
    }
    int stopTarget = temp.size() * temp.get(0).length;
    for (int step = 1; ; step++) {
      int flashes = simulate(temp);
      if (flashes == stopTarget) {
        LOGGER.info("day 11 task 2: 303");
        LOGGER.info("flash all at step={}", step);
        return step;
      }
    }
  }

  private int simulate(List<int[]> board) {
    int nRow = board.size();
    int nCol = board.get(0).length;
    Deque<int[]> queue = new ArrayDeque<>();
    for (int i = 0; i < nRow; i++) {
      int[] row = board.get(i);
      for (int j = 0; j < nCol; j++) {
        if (++row[j] > 9) {
          queue.addLast(new int[]{i, j});
        }
      }
    }
    while (!queue.isEmpty()) {
      int[] cur = queue.removeFirst();
      if (board.get(cur[Y])[cur[X]] > 9) {
        for (int i = 0; i < DY.length; i++) {
          int y = cur[Y] + DY[i];
          int x = cur[X] + DX[i];
          if (x >= 0 && y >= 0 && x < nCol && y < nRow && board.get(y)[x] <= 9) {
            if (++board.get(y)[x] > 9) {
              queue.addLast(new int[]{y, x});
            }
          }
        }
      }
    }
    int res = 0;
    for (int i = 0; i < nRow; i++) {
      int[] row = board.get(i);
      for (int j = 0; j < nCol; j++) {
        if (row[j] > 9) {
          row[j] = 0;
          res++;
        }
      }
    }
    return res;
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
