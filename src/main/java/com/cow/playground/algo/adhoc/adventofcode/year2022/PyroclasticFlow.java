package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PyroclasticFlow {
    private static final Logger LOGGER = LoggerFactory.getLogger(PyroclasticFlow.class);
    private static final String INPUT_FILENAME = "day17.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private static final byte[][][] ROCKS = {
            {{1, 1, 1, 1}},
            {{0, 1, 0},
                    {1, 1, 1},
                    {0, 1, 0}},
            {{0, 0, 1},
                    {0, 0, 1},
                    {1, 1, 1}},
            {{1},
                    {1},
                    {1},
                    {1}},
            {{1, 1},
                    {1, 1}}};
    private String wind;

    public PyroclasticFlow() throws IOException {
        readInputs();
    }

    public void task1() {
        int rockCount = 2022;
        int[] results = dropRocks(rockCount);
        assert results[0] == 3159;
        LOGGER.info("the height of the tower after {} rocks is {}", rockCount, results[0]);
        LOGGER.debug("wind count is {}", results[1]);
    }

    public void task2() {
        int magic = ROCKS.length * wind.length();  // they are primes
        // loop ???
    }

    private int[] dropRocks(int rockCount) {
        byte[][] tower = new byte[rockCount * 4 + 3][7];
        int highest = -1;
        int windCount = 0;
        for (int i = 0, rockId = 0, windId = 0; i < rockCount; i++, rockId++, windId++) {
            if (rockId >= ROCKS.length) {
                rockId = 0;
            }

            byte[][] rock = ROCKS[rockId];
            int left = 2;
            int top = highest + 3 + rock.length;
            do {
                windCount++;
                int deltaColumn = wind.charAt(windId) == '<' ? -1 : 1;
                boolean canMove = true;
                for (int u = 0; u < rock.length; u++) {
                    for (int v = 0; v < rock[u].length; v++) {
                        if (left + v + deltaColumn < 0 || left + v + deltaColumn >= 7
                                || tower[top - u][left + v + deltaColumn] + rock[u][v] > 1) {
                            canMove = false;
                            break;
                        }
                    }
                }
                if (canMove) {
                    left += deltaColumn;
                }
                int deltaRow = -1;
                boolean willRest = false;
                for (int u = 0; u < rock.length; u++) {
                    for (int v = 0; v < rock[u].length; v++) {
                        if (top - u + deltaRow < 0
                                || tower[top - u + deltaRow][left + v] + rock[u][v] > 1) {
                            willRest = true;
                            break;
                        }
                    }
                }
                if (willRest) {
                    if ((windCount + 1) % 10091 == 0) {
                        LOGGER.debug("Rock #{} rests at the right time", i + 1);
                    }
                    highest = Math.max(highest, top);
                    for (int u = 0; u < rock.length; u++) {
                        for (int v = 0; v < rock[u].length; v++) {
                            tower[top - u][left + v] += rock[u][v];
                        }
                    }
                    break;
                }
                top += deltaRow;
                if (++windId >= wind.length()) {
                    windId = 0;
                }
            } while (true);
        }
        return new int[]{highest + 1, windCount};
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            while ((line = reader.readLine()) != null) {
                wind = line;
            }
        }
    }
}