package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChitonBoard {

  private static final Logger LOGGER = LoggerFactory.getLogger(ChitonBoard.class);

  private static final String INPUT_FILENAME = "day15.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int[] DY = {-1, 1, 0, 0};
  private static final int[] DX = {0, 0, 1, -1};
  private static final int Y = 0;
  private static final int X = 1;

  private List<int[]> board;

  public ChitonBoard() throws IOException {
    readInputs();
  }

  public int safestPathToTarget() {
    int nRow = board.size();
    int nCol = board.get(0).length;
    int[][] dp = new int[2][nCol];
    Arrays.fill(dp[0], Integer.MAX_VALUE);
    dp[0][0] = 0;
    for (int i = 0; i < nRow; i++) {
      dp[1][0] = dp[0][0] + board.get(i)[0];
      for (int j = 1; j < nCol; j++) {
        dp[1][j] = Math.min(dp[1][j - 1], dp[0][j]) + board.get(i)[j];
      }
      dp[0] = dp[1];
      dp[1] = new int[nCol];
    }

    int res = dp[0][nCol - 1] - board.get(0)[0];

    LOGGER.info("day 15 task 1: 769");
    LOGGER.info("lowest risk path={}", res);

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
