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

public class BingoGame {

  private static final Logger LOGGER = LoggerFactory.getLogger(BingoGame.class);

  private static final String INPUT_FILENAME = "day4.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int MAX = 1000;

  private List<Integer> drawnNumbers;
  private List<int[][]> boards;

  public BingoGame() throws IOException {
    readInputs();
  }

  public int start() {
    int score = -1;
    outer:
    for (int num : drawnNumbers) {
      for (int[][] board : boards) {
        for (int i = 0; i < 5; i++) {
          for (int j = 0; j < 5; j++) {
            if (board[i][j] == num) {
              board[i][j] = board[i][j] + MAX;
              boolean bingo = true;
              for (int k = 0; k < 5; k++) {
                if (board[i][k] < MAX) {
                  bingo = false;
                }
              }
              if (bingo) {
                score = getScore(board, num);
                break outer;
              }
              bingo = true;
              for (int k = 0; k < 5; k++) {
                if (board[k][j] < MAX) {
                  bingo = false;
                }
              }
              if (bingo) {
                score = getScore(board, num);
                break outer;
              }
            }
          }
        }
      }
    }
    LOGGER.info("day 4 task 1: 63424");
    LOGGER.info("bingo score = {}", score);
    return -1;
  }

  private int getScore(int[][] board, int lastDrawn) {
    int sum = 0;
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        if (board[i][j] < 1000) {
          sum += board[i][j];
        }
      }
    }
    return sum * lastDrawn;
  }

  private void readInputs() throws IOException {

    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      drawnNumbers = new ArrayList<>();
      String[] tokens = reader.readLine().split(",");
      for (String token : tokens) {
        drawnNumbers.add(Integer.parseInt(token));
      }
      LOGGER.debug(drawnNumbers.toString());
      boards = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        int[][] board = new int[5][5];
        for (int i = 0; i < 5; i++) {
          tokens = reader.readLine().trim().split("\\s+");
          for (int j = 0; j < 5; j++) {
            board[i][j] = Integer.parseInt(tokens[j]);
          }
          boards.add(board);
          LOGGER.info(Arrays.toString(board[i]));
        }
      }
    }
  }
}
