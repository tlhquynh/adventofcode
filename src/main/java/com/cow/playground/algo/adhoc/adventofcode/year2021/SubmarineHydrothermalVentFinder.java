package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SubmarineHydrothermalVentFinder {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubmarineHealthChecker.class);

  private static final String INPUT_FILENAME = "day5.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int MAX_SIZE = 1000;

  private List<Vent> vents;

  public SubmarineHydrothermalVentFinder() throws IOException {
    readInputs();
  }

  public int countHorizontalVerticalDangerousSpots() {
    int[][] map = new int[MAX_SIZE][MAX_SIZE];
    addVerticalLines(map);
    addHorizontalLines(map);
    int res = 0;
    for (int i = 0; i < MAX_SIZE; i++) {
      for (int j = 0; j < MAX_SIZE; j++) {
        if (map[i][j] > 1) {
          res++;
        }
      }
    }
    LOGGER.info("day 5 task 1: 4993");
    LOGGER.info("Dangerous hydrothermal spots from vertical and horizontal vents = {}", res);
    return res;
  }

  public int countHorizontalVerticalDiagonalDangerousSpots() {
    int[][] map = new int[MAX_SIZE][MAX_SIZE];
    addVerticalLines(map);
    addHorizontalLines(map);
    addDiagonalLines(map);
    int res = 0;
    for (int i = 0; i < MAX_SIZE; i++) {
      for (int j = 0; j < MAX_SIZE; j++) {
        if (map[i][j] > 1) {
          res++;
        }
      }
    }
    LOGGER.info("day 5 task 2: 21101");
    LOGGER.info("Dangerous hydrothermal spots from vertical, horizontal and diagonal vents = {}", res);
    return res;
  }

  private void addVerticalLines(int[][] map) {
    for (Vent vent : vents) {
      if (vent.x1 == vent.x2) {
        for (int i = Math.min(vent.y1, vent.y2); i <= Math.max(vent.y1, vent.y2); i++) {
          map[i][vent.x1]++;
        }
      }
    }
  }

  private void addHorizontalLines(int[][] map) {
    for (Vent vent : vents) {
      if (vent.y1 == vent.y2) {
        for (int i = Math.min(vent.x1, vent.x2); i <= Math.max(vent.x1, vent.x2); i++) {
          map[vent.y1][i]++;
        }
      }
    }
  }

  private void addDiagonalLines(int[][] map) {
    for (Vent vent : vents) {
      if (Math.abs(vent.y1 - vent.y2) == Math.abs(vent.x1 - vent.x2)) {
        int n = Math.abs(vent.y1 - vent.y2);
        int dy = (vent.y1 - vent.y2) / n;
        int dx = (vent.x1 - vent.x2) / n;
        for (int i = 0, cx = vent.x2, cy = vent.y2; i <= n; i++, cx += dx, cy += dy) {
          map[cy][cx]++;
        }
      }
    }
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      vents = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(" -> ");
        Vent vent = new Vent();
        String[] xy = tokens[0].split(",");
        vent.x1 = Integer.parseInt(xy[0]);
        vent.y1 = Integer.parseInt(xy[1]);
        xy = tokens[1].split(",");
        vent.x2 = Integer.parseInt(xy[0]);
        vent.y2 = Integer.parseInt(xy[1]);
        vents.add(vent);
      }
    }
  }

  private class Vent {
    private int x1, y1, x2, y2;
  }
}
