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

public class Reactor {
  private static final Logger LOGGER = LoggerFactory.getLogger(Reactor.class);

  private static final String INPUT_FILENAME = "day22.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private List<Cuboid> cuboids;

  public Reactor() throws IOException {
    readInputs();
  }

  public int reboot(int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
    int[][][] states = new int[101][101][101];
    for (int x = xMin; x <= xMax; x++) {
      for (int y = yMin; y <= yMax; y++) {
        for (int z = zMin; z <= zMax; z++) {
          for (Cuboid cuboid : cuboids) {
            if (isInside(x, y, z, cuboid)) {
              states[x - xMin][y - yMin][z - zMin] = cuboid.state;
            }
          }
        }
      }
    }
    int res = 0;
    for (int x = 0; x <= xMax - xMin; x++) {
      for (int y = 0; y <= yMax - yMin; y++) {
        for (int z = 0; z <= zMax - zMin; z++) {
          res += states[x][y][z];
        }
      }
    }
    LOGGER.info("day 22 task 1: 648681");
    LOGGER.info("switched-on cube count={}", res);
    return res;
  }

  public long reboot() {
    List<Cuboid> cur = new ArrayList<>();
    cur.add(cuboids.get(0));
    for (int i = 1; i < cuboids.size(); i++) {
      int size = cur.size();
      cur.add(cuboids.get(i));
      for (int j = 0; j < size; j++) {
        // split cuboids[i] and cur[j] into smaller cuboids based on their intersection
        //      top
        //       z   back
        //       |  y
        //       | /
        //  left |/___x right
        // front bottom
        // IMPORTANT improvement: this if condition is to check that cuboids.get(i) and cur.get(j) overlap.
        if (((cuboids.get(i).xRange[0] <= cur.get(j).xRange[0] && cur.get(j).xRange[0] <= cuboids.get(i).xRange[1]) || (cur.get(j).xRange[0] <= cuboids.get(i).xRange[0] && cuboids.get(i).xRange[0] <= cur.get(j).xRange[1])) &&
                ((cuboids.get(i).yRange[0] <= cur.get(j).yRange[0] && cur.get(j).yRange[0] <= cuboids.get(i).yRange[1]) || (cur.get(j).yRange[0] <= cuboids.get(i).yRange[0] && cuboids.get(i).yRange[0] <= cur.get(j).yRange[1])) &&
                ((cuboids.get(i).zRange[0] <= cur.get(j).zRange[0] && cur.get(j).zRange[0] <= cuboids.get(i).zRange[1]) || (cur.get(j).zRange[0] <= cuboids.get(i).zRange[0] && cuboids.get(i).zRange[0] <= cur.get(j).zRange[1]))) {
          // 1. on x axis: left
          if (cur.get(j).xRange[1] >= cuboids.get(i).xRange[0] && cuboids.get(i).xRange[0] >= cur.get(j).xRange[0]) {
            Cuboid temp = new Cuboid(cur.get(j).state, cur.get(j).xRange[0], cuboids.get(i).xRange[0] - 1, cur.get(j).yRange[0], cur.get(j).yRange[1], cur.get(j).zRange[0], cur.get(j).zRange[1]);
            if (temp.isValid()) {
              cur.add(temp);
              cur.get(j).xRange[0] = cuboids.get(i).xRange[0];
            }
          }
          // 2. on x axis: right
          if (cur.get(j).xRange[0] <= cuboids.get(i).xRange[1] && cuboids.get(i).xRange[1] <= cur.get(j).xRange[1]) {
            Cuboid temp = new Cuboid(cur.get(j).state, cuboids.get(i).xRange[1] + 1, cur.get(j).xRange[1], cur.get(j).yRange[0], cur.get(j).yRange[1], cur.get(j).zRange[0], cur.get(j).zRange[1]);
            if (temp.isValid()) {
              cur.add(temp);
              cur.get(j).xRange[1] = cuboids.get(i).xRange[1];
            }
          }
          // 3. on y axis: back
          if (cur.get(j).yRange[0] <= cuboids.get(i).yRange[1] && cuboids.get(i).yRange[1] <= cur.get(j).yRange[1]) {
            Cuboid temp = new Cuboid(cur.get(j).state, cur.get(j).xRange[0], cur.get(j).xRange[1], cuboids.get(i).yRange[1] + 1, cur.get(j).yRange[1], cur.get(j).zRange[0], cur.get(j).zRange[1]);
            if (temp.isValid()) {
              cur.add(temp);
              cur.get(j).yRange[1] = cuboids.get(i).yRange[1];
            }
          }
          // 4. on y axis: front
          if (cur.get(j).yRange[1] >= cuboids.get(i).yRange[0] && cuboids.get(i).yRange[0] >= cur.get(j).yRange[0]) {
            Cuboid temp = new Cuboid(cur.get(j).state, cur.get(j).xRange[0], cur.get(j).xRange[1], cur.get(j).yRange[0], cuboids.get(i).yRange[0] - 1, cur.get(j).zRange[0], cur.get(j).zRange[1]);
            if (temp.isValid()) {
              cur.add(temp);
              cur.get(j).yRange[0] = cuboids.get(i).yRange[0];
            }
          }
          // 5. on z axis: top
          if (cur.get(j).zRange[0] <= cuboids.get(i).zRange[1] && cuboids.get(i).zRange[1] <= cur.get(j).zRange[1]) {
            Cuboid temp = new Cuboid(cur.get(j).state, cur.get(j).xRange[0], cur.get(j).xRange[1], cur.get(j).yRange[0], cur.get(j).yRange[1], cuboids.get(i).zRange[1] + 1, cur.get(j).zRange[1]);
            if (temp.isValid()) {
              cur.add(temp);
              cur.get(j).zRange[1] = cuboids.get(i).zRange[1];
            }
          }
          // 6. on z axis: bottom
          if (cur.get(j).zRange[1] >= cuboids.get(i).zRange[0] && cuboids.get(i).zRange[0] >= cur.get(j).zRange[0]) {
            Cuboid temp = new Cuboid(cur.get(j).state, cur.get(j).xRange[0], cur.get(j).xRange[1], cur.get(j).yRange[0], cur.get(j).yRange[1], cur.get(j).zRange[0], cuboids.get(i).zRange[0] - 1);
            if (temp.isValid()) {
              cur.add(temp);
              cur.get(j).zRange[0] = cuboids.get(i).zRange[0];
            }
          }
        }
        if (!isInside(cur.get(j).xRange[0], cur.get(j).yRange[0], cur.get(j).zRange[0], cuboids.get(i))
                || !isInside(cur.get(j).xRange[0], cur.get(j).yRange[0], cur.get(j).zRange[1], cuboids.get(i))
                || !isInside(cur.get(j).xRange[0], cur.get(j).yRange[1], cur.get(j).zRange[0], cuboids.get(i))
                || !isInside(cur.get(j).xRange[0], cur.get(j).yRange[1], cur.get(j).zRange[1], cuboids.get(i))
                || !isInside(cur.get(j).xRange[1], cur.get(j).yRange[0], cur.get(j).zRange[0], cuboids.get(i))
                || !isInside(cur.get(j).xRange[1], cur.get(j).yRange[0], cur.get(j).zRange[1], cuboids.get(i))
                || !isInside(cur.get(j).xRange[1], cur.get(j).yRange[1], cur.get(j).zRange[0], cuboids.get(i))
                || !isInside(cur.get(j).xRange[1], cur.get(j).yRange[1], cur.get(j).zRange[1], cuboids.get(i))) {
          cur.add(cur.get(j));
        }
      }
      cur = cur.subList(size, cur.size());
    }
    long res = 0;
    for (Cuboid cuboid : cur) {
      if (cuboid.state == 1) {
        res += cuboid.size();
        LOGGER.debug("{}: {} ==> {}", cuboid, cuboid.size(), res);
      }
    }
    LOGGER.info("day 23 task 2: 1302784472088899");
    LOGGER.info("switched-on cube count={}", res);

    return res;
  }

  private boolean isInside(int x, int y, int z, Cuboid cuboid) {
    return x >= cuboid.xRange[0] && x <= cuboid.xRange[1] && y >= cuboid.yRange[0] && y <= cuboid.yRange[1] && z >= cuboid.zRange[0] && z <= cuboid.zRange[1];
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      cuboids = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split("[\\s=\\.,]");
        cuboids.add(new Cuboid("on".equals(tokens[0]) ? 1 : 0,
                Integer.parseInt(tokens[2]), Integer.parseInt(tokens[4]),
                Integer.parseInt(tokens[6]), Integer.parseInt(tokens[8]),
                Integer.parseInt(tokens[10]), Integer.parseInt(tokens[12])));
      }
    }
  }

  private class Cuboid {
    private int[] xRange;
    private int[] yRange;
    private int[] zRange;
    private int state;

    private Cuboid(int state, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
      xRange = new int[]{xMin, xMax};
      yRange = new int[]{yMin, yMax};
      zRange = new int[]{zMin, zMax};
      this.state = state;
    }

    private long size() {
      return 1L * (xRange[1] - xRange[0] + 1) * (yRange[1] - yRange[0] + 1) * (zRange[1] - zRange[0] + 1);
    }

    private boolean isValid() {
      return xRange[0] <= xRange[1] && yRange[0] <= yRange[1] && zRange[0] <= zRange[1];
    }

    public String toString() {
      return "[" + xRange[0] + ".." + xRange[1] + "," + yRange[0] + ".." + yRange[1] + "," + zRange[0] + ".." + zRange[1] + "," + state + "]";
    }
  }
}
