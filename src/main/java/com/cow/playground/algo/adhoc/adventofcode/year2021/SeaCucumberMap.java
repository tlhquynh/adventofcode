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

public class SeaCucumberMap {

  private static final Logger LOGGER = LoggerFactory.getLogger(SeaCucumberMap.class);

  private static final String INPUT_FILENAME = "day25.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int SOUTH = 1;
  private static final int EAST = 2;
  private static final int EMPTY = 0;

  private List<int[]> board;
  private List<SeaCucumber> southies;
  private List<SeaCucumber> easties;

  public SeaCucumberMap() throws IOException {
    readInputs();
  }

  public int stepsToFroze() {
    int nRow = board.size();
    int nCol = board.get(0).length;
    int steps = 0;
    do {
      boolean movable = false;
      List<SeaCucumber> moved = new ArrayList<>();
      for (SeaCucumber seaCucumber : easties) {
        if (board.get(seaCucumber.y)[(seaCucumber.x + 1) % nCol] == EMPTY) {
          moved.add(seaCucumber);
        }
      }
      for (SeaCucumber seaCucumber : moved) {
        board.get(seaCucumber.y)[(seaCucumber.x + 1) % nCol] = EAST;
        board.get(seaCucumber.y)[seaCucumber.x] = EMPTY;
        seaCucumber.x = (seaCucumber.x + 1) % nCol;
        movable = true;
      }
      moved = new ArrayList<>();
      for (SeaCucumber seaCucumber : southies) {
        if (board.get((seaCucumber.y + 1) % nRow)[seaCucumber.x] == EMPTY) {
          moved.add(seaCucumber);
        }
      }
      for (SeaCucumber seaCucumber : moved) {
        board.get((seaCucumber.y + 1) % nRow)[seaCucumber.x] = SOUTH;
        board.get(seaCucumber.y)[seaCucumber.x] = EMPTY;
        seaCucumber.y = (seaCucumber.y + 1) % nRow;
        movable = true;
      }
      steps++;
      LOGGER.debug("step={}", steps);
      for (int[] row : board) {
        LOGGER.debug(Arrays.toString(row));
      }
      if (!movable) {
        break;
      }
    } while (true);

    LOGGER.info("day 25 task 1: 456");
    LOGGER.info("steps before froze = {}", steps);

    return steps;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      board = new ArrayList<>();
      easties = new ArrayList<>();
      southies = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        int[] row = new int[line.length()];
        int i = 0;
        for (char ch : line.toCharArray()) {
          if (ch == 'v') {
            row[i] = SOUTH;
            southies.add(new SeaCucumber(board.size(), i, SOUTH));
          } else if (ch == '>') {
            row[i] = EAST;
            easties.add(new SeaCucumber(board.size(), i, EAST));
          } else {
            row[i] = EMPTY;
          }
          i++;
        }
        board.add(row);
      }
    }
  }

  private class SeaCucumber {
    int y;
    int x;
    int dir;

    private SeaCucumber(int y, int x, int dir) {
      this.x = x;
      this.y = y;
      this.dir = dir;
    }
  }
}
