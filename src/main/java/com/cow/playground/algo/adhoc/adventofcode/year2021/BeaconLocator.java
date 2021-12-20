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

public class BeaconLocator {
  private static final Logger LOGGER = LoggerFactory.getLogger(BeaconLocator.class);

  private static final String INPUT_FILENAME = "day19.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  List<List<int[]>> readings;

  public BeaconLocator() throws IOException {
    readInputs();
  }

  public List<int[]> locateBeacons(int relativeToScanner) {
    Comparator<int[]> readingComp = (p, q) -> p[0] != q[0] ? p[0] - q[0] : (p[1] != q[1] ? p[1] - q[1] : (p[2] != q[2] ? p[2] - q[2] : 0));
    int n = readings.size();
    for (int i = 0; i < n; i++) {
      for (int j = i+1; j < n; j++) {
        List<int[]> u = cloneReadings(readings.get(i));
        Collections.sort(shiftToZeroes(u), readingComp);
        // choose
      }
    }
    return null;
  }

  private List<int[]> shiftToZeroes(List<int[]> u) {
    return null;
  }

  private List<int[]> cloneReadings(List<int[]> original) {
    List<int[]> res = new ArrayList<>();
    for (int[] ints : original) {
      res.add(ints.clone());
    }
    return res;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      readings = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.indexOf("scanner") >= 0) {
          readings.add(new ArrayList<>());
        } else if (line.indexOf(",") >= 0) {
          String[] tokens = line.split(",");
          int[] coordinates = new int[tokens.length];
          for (int i = 0; i < coordinates.length; i++) {
            coordinates[i] = Integer.parseInt(tokens[i]);
          }
          readings.get(readings.size()-1).add(coordinates);
        }
      }
    }
  }
}
