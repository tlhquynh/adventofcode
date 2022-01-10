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
import java.util.Map;

public class AmphipodSorter {

  private static final Logger LOGGER = LoggerFactory.getLogger(AmphipodSorter.class);

  private static final String INPUT_FILENAME = "day23.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private static final int[] DY = {0, 0, -1, 1};
  private static final int[] DX = {-1, 1, 0, 0};
  private static final int[] ENERGY = {1, 10, 100, 1000};
  private static final int[] COLUMN = {3, 5, 7, 9};
  private static final int BIG_VALUE = 1_000_000;


  private List<char[]> map;


  public AmphipodSorter() throws IOException {
    readInputs();
  }

//  public int sort() {
//    int res = dfs(0, 4, 0, 4, 0);
//  }

//  private int dfs(int cost, int prevY, int prevX,int lastY, int lastX) {
//      for (int i = 0; i < DY.length; i++) {
//        int y = lastY + DY[i];
//        int x = lastX + DX[i];
//        if ((y != prevY || x != prevX) && map.get(y)[x] == '.') {
//          swap(y, x, lastY, lastX);
//          dfs(cost+ENERGY[map.get(y)[x]-'A'],lastY,lastX, y,x);
//          swap(y, x, lastY, lastX);
//        }
//      }
//      if (lastX != 3 && lastX != 5 && lastX != 7 && lastX != 9) {
//        // try DIRECTION_MOVING_IN
//        char[] row = map.get(1);
//        for (int i = 0; i < row.length; i++) {
//          if (row[i] >= 'A' && row[i] <= 'D') {
//            int[] homeCost = goHome(1, i);
//            if (homeCost[0] < Integer.MAX_VALUE) {
//              dfs(cost+homeCost[0], lastY,lastX,homeCost[1],homeCost[2]);
//              swap(homeCost[1],homeCost[2],lastY,lastX);
//            }
//          }
//        }
//        if (lastY != 2 && lastY != 3) {
//          // try DIRECTION_MOVING_OUT
//          for (int y = 2; y <= 3; y++) {
//            for (int x = 3; x <= 9; x+= 2) {
//            }
//            if (row[i] >= 'A' && row[i] <= 'D') {
//              int[] homeCost = goHome(1, i);
//              if (homeCost[0] < Integer.MAX_VALUE) {
//                dfs(cost+homeCost[0], lastY,lastX,homeCost[1],homeCost[2]);
//                swap(homeCost[1],homeCost[2],lastY,lastX);
//              }
//            }
//          }
//        }
//      }
//    }
//  }

  private int[] goHome(int lastY, int lastX) {
    int col = COLUMN[map.get(lastY)[lastX]-'A'];
    if (map.get(2)[col] != '.') {
      return new int[] {Integer.MAX_VALUE, -1, -1};
    }
    if (map.get(3)[col] != '.' && map.get(3)[col] != map.get(lastY)[lastX]) {
      return new int[] {Integer.MAX_VALUE, -1, -1};
    }
    int tempY = lastY;
    int tempX = lastX;
    int cost = 0;
    while (tempY == 1 && tempX != COLUMN[map.get(lastY)[lastX]]) {
      tempX += tempX < COLUMN[map.get(lastY)[lastX]] ? 1 : -1;
      cost += ENERGY[map.get(lastY)[lastX]];
      if (map.get(tempY)[tempX] != '.') {
        return new int[] {Integer.MAX_VALUE, -1, -1};
      }
    }
    if (map.get(2)[col] != '.') {
      cost += ENERGY[map.get(lastY)[lastX]];
      swap(lastY, lastX, 2, COLUMN[map.get(lastY)[lastX]]);
    }
    if (map.get(3)[col] != '.') {
      cost += ENERGY[map.get(lastY)[lastX]];
      swap(3,COLUMN[map.get(lastY)[lastX]], 2, COLUMN[map.get(lastY)[lastX]]);
      return new int[] {cost, 3, col};
    }
    return new int[] {cost, 2, col};
  }

  private void swap(int y1, int x1, int y2, int x2) {
    char temp = map.get(y1)[x1];
    map.get(y1)[x1] = map.get(y2)[x2];
    map.get(y2)[x2] = temp;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      map = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        map.add(line.toCharArray());
      }
    }
  }
}
