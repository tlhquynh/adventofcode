package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class OrigamiSimulator {

  private static final Logger LOGGER = LoggerFactory.getLogger(OrigamiSimulator.class);

  private static final String INPUT_FILENAME = "day13.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int Y = 0;
  private static final int X = 1;
  private static final Comparator<int[]> sortY = (u, v) -> u[Y] == v[Y] ? u[X] - v[X] : u[Y] - v[Y];
  private static final Comparator<int[]> sortX = (u, v) -> u[X] == v[X] ? u[Y] - v[Y] : u[X] - v[X];
  private static final int VERY_SMALL = -10_000;

  private List<int[]> points;
  private List<int[]> folds;

  public OrigamiSimulator() throws IOException {
    readInputs();
  }

  public int foldOnce() {
    int res = fold(1);
    LOGGER.info("day 13 task 1: 802");
    LOGGER.info("points after 1st fold={}", res);
    return res;
  }

  public List<int[]> foldAll() {
    fold(folds.size());

    // find minX and minY to shift all points to the quarter where x and y are >= 0
    int minX = 0;
    int minY = 0;
    for (int[] point : points) {
      minX = Math.min(minX, point[X]);
      minY = Math.min(minY, point[Y]);
    }
    for (int[] point : points) {
      point[X] += minX;
      point[Y] += minY;
    }

    Collections.sort(points, sortY);

    StringBuilder sb = new StringBuilder("\n");
    int pointIndex = 0;
    int[] cur = points.get(pointIndex);
    for (int y = 0, x = 0; ; ) {
      if (y < cur[Y]) {
        sb.append("\n");
        y++;
        x = 0;
      } else {
        for (; x < cur[X]; x++) {
          sb.append(" ");
        }
        sb.append("*");
        x++;
        pointIndex++;
        if (pointIndex == points.size()) {
          break;
        }
        cur = points.get(pointIndex);
      }
    }
    LOGGER.info("day 13 task 2: RKHFZGUB");
    LOGGER.info(sb.toString());
    return points;
  }

  private int fold(int steps) {
    for (int i = 0; i < steps; i++) {
      int[] fold = folds.get(i);
      if (fold[0] == X) {
        Collections.sort(points, sortX);
        int startingIndex = -(Collections.binarySearch(points, new int[]{VERY_SMALL, fold[1] + 1}, sortX) + 1);
        List<int[]> newPoints = new ArrayList<>();
        for (int j = startingIndex; j < points.size(); j++) {
          int y = points.get(j)[Y];
          int x = fold[1] - (points.get(j)[X] - fold[1]);
          if (Collections.binarySearch(points, new int[]{y, x}, sortX) < 0) {
            newPoints.add(new int[]{y, x});
          }
        }
        points.removeIf(u -> u[X] > fold[1]);
        points.addAll(newPoints);
      } else {
        Collections.sort(points, sortY);
        int startingIndex = -(Collections.binarySearch(points, new int[]{fold[1] + 1, VERY_SMALL}, sortY) + 1);
        List<int[]> newPoints = new ArrayList<>();
        for (int j = startingIndex; j < points.size(); j++) {
          int y = fold[1] - (points.get(j)[Y] - fold[1]);
          int x = points.get(j)[X];
          if (Collections.binarySearch(points, new int[]{y, x}, sortY) < 0) {
            newPoints.add(new int[]{y, x});
          }
        }
        points.removeIf(u -> u[Y] > fold[1]);
        points.addAll(newPoints);
      }
    }
    return points.size();
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      points = new ArrayList<>();
      folds = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.indexOf(",") >= 0) {
          String[] tokens = line.split(",");
          points.add(new int[]{
                  Integer.parseInt(tokens[1]),
                  Integer.parseInt(tokens[0])});
        } else if (line.indexOf("=") >= 0) {
          int equalPos = line.indexOf("=");
          folds.add(new int[]{
                  line.charAt(equalPos - 1) == 'x' ? X : Y,
                  Integer.parseInt(line.substring(equalPos + 1))});
        }
      }
    }
  }

}
