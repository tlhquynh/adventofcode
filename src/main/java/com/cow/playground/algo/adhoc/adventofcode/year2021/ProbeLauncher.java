package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ProbeLauncher {
  private static final Logger LOGGER = LoggerFactory.getLogger(ProbeLauncher.class);

  private static final String INPUT_FILENAME = "day17.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private int xMin;
  private int xMax;
  private int yMin;
  private int yMax;

  public ProbeLauncher() throws IOException {
    readInputs();
  }

  /**
   *
   * @return highest point and count
   */
  public int[] fireToPosXNegYTarget() {
    int best = 0;
    int count = 0;
    for (int vx = 0; vx <= xMax; vx++) {
      for (int vy = yMin; vy <= -yMin-1; vy++) {
        int x = 0;
        int y = 0;
        int tx = vx;
        int ty = vy;
        int highest = 0;
        boolean found = false;
        while (x <= xMax && y >= yMin) {
          x += tx;
          y += ty;
          highest = Math.max(highest, y);
          if (x >= xMin && x <= xMax && y >= yMin && y <= yMax) {
            best = Math.max(best, highest);
            found = true;
          }
          if (tx > 0) {
            tx--;
          }
          ty--;
        }
        if (found) {
          count++;
        }
      }
    }

    LOGGER.info("day 17 task 1 / 2: 4186 / 2709");
    LOGGER.info("fire in style to highest point={}", best);
    LOGGER.info("number of ways to fire in style={}", count);

    return new int[] {best, count};
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split("[=\\.,]");
        xMin = Integer.parseInt(tokens[1]);
        xMax = Integer.parseInt(tokens[3]);
        yMin = Integer.parseInt(tokens[5]);
        yMax = Integer.parseInt(tokens[7]);
      }
    }
  }
}
