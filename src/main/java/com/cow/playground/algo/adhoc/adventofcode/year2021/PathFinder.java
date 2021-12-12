package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class PathFinder {

  private static final Logger LOGGER = LoggerFactory.getLogger(PathFinder.class);

  private static final String INPUT_FILENAME = "day12.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final String START = "start";
  private static final String END = "end";

  private boolean[][] a;
  private int n;
  private Map<String, Integer> idOf;

  private boolean[] bigCave;

  public PathFinder() throws IOException {
    readInputs();
  }

  public int countUniquePaths() {
    int start = idOf.get(START);
    int end = idOf.get(END);
    int res = countUniquePaths(start, end, a, new boolean[n]);
    LOGGER.info("day 12 task 1: 4411");
    LOGGER.info("unique path count={}", res);
    return res;
  }

  public int countMoreUniquePaths() {
    int uniquePathCounts = countUniquePaths();
    int start = idOf.get(START);
    int end = idOf.get(END);
    int res = uniquePathCounts;
    for (int i = 0; i < n; i++) {
      if (!bigCave[i] && i != start && i != end) {
        res += countUniquePaths(start, end, i, a, new int[n]) - uniquePathCounts;
      }
    }
    LOGGER.info("day 12 task 2: 136767");
    LOGGER.info("more unique path count={}", res);
    return res;
  }

  private int countUniquePaths(int cur, int end, boolean[][] a, boolean[] visited) {
    if (cur == end) {
      return 1;
    }
    visited[cur] = true;
    int res = 0;
    for (int i = 0; i < n; i++) {
      if ((!visited[i] || bigCave[i]) && a[cur][i]) {
        res += countUniquePaths(i, end, a, visited);
      }
    }
    visited[cur] = false;
    return res;
  }

  private int countUniquePaths(int cur, int end, int mediumCave, boolean[][] a, int[] visited) {
    if (cur == end) {
      return 1;
    }
    visited[cur]++;
    int res = 0;
    for (int i = 0; i < n; i++) {
      if ((visited[i] == 0 || bigCave[i] || (mediumCave == i && visited[i] < 2)) && a[cur][i]) {
        res += countUniquePaths(i, end, mediumCave, a, visited);
      }
    }
    visited[cur]--;
    return res;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      idOf = new HashMap<>();
      List<int[]> edges = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split("-");
        int u = idOf.getOrDefault(tokens[0], n);
        if (u == n) {
          n++;
        }
        int v = idOf.getOrDefault(tokens[1], n);
        if (v == n) {
          n++;
        }
        idOf.put(tokens[0], u);
        idOf.put(tokens[1], v);
        edges.add(new int[] {u, v});
      }

      a = new boolean[n][n];
      for (int[] edge : edges) {
        a[edge[0]][edge[1]] = true;
        a[edge[1]][edge[0]] = true;
      }
      bigCave = new boolean[n];
      for (Map.Entry<String, Integer> entry : idOf.entrySet()) {
        bigCave[entry.getValue()] = Character.isUpperCase(entry.getKey().charAt(0));
      }
    }
  }
}
