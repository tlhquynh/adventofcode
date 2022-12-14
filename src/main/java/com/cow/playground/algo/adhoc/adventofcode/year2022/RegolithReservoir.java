package com.cow.playground.algo.adhoc.adventofcode.year2022;

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

public class RegolithReservoir {
    private static final Logger LOGGER = LoggerFactory.getLogger(RegolithReservoir.class);
    private static final String INPUT_FILENAME = "day14.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    List<List<Point>> paths;
    int maxR, maxC;
    Element[][] cave;

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            paths = new ArrayList<>();
            maxR = Integer.MIN_VALUE;
            maxC = Integer.MIN_VALUE;
            String line;
            while ((line = reader.readLine()) != null) {
                List<Point> path = new ArrayList<>();
                String[] tokens = line.split(" -> ");
                for (String token : tokens) {
                    String[] cr = token.split(",");
                    Point point = new Point(Integer.parseInt(cr[1]), Integer.parseInt(cr[0]));
                    path.add(point);
                    maxR = Math.max(maxR, point.r);
                    maxC = Math.max(maxC, point.c);
                }
                paths.add(path);
            }
            maxC += maxR;
            cave = new Element[maxR + 3][maxC + 3];
            for (Element[] row : cave) {
                Arrays.fill(row, Element.AIR);
            }
            for (List<Point> path : paths) {
                Point u = path.get(0);
                for (int k = 1; k < path.size(); k++) {
                    Point v = path.get(k);
                    if (u.r == v.r) {
                        // horizontal
                        for (int j = Math.min(u.c, v.c); j <= Math.max(u.c, v.c); j++) {
                            cave[u.r][j] = Element.ROCK;
                        }
                    } else {
                        // vertical
                        for (int i = Math.min(u.r, v.r); i <= Math.max(u.r, v.r); i++) {
                            cave[i][u.c] = Element.ROCK;
                        }
                    }
                    u = v;
                }
            }
            // add bottom floor
            for (int j = 0; j < cave[0].length; j++) {
                cave[maxR+2][j] = Element.ROCK;
            }
        }
    }
    public void task1() throws IOException {
        readInputs();
        int count = 0;
        while (dropSand(0, 500, maxR + 1)) {
            count++;
        }
        assert count == 757;
        LOGGER.info("Number of sand units is {}", count);
    }

    public void task2() throws IOException {
        readInputs();
        int count = 0;
        while (dropSand(0, 500, Integer.MAX_VALUE)) {
            count++;
            if (cave[0][500] == Element.SAND) {
                assert count == 24943;
                LOGGER.info("Number of overflowing sand units is {}", count);
                return;
            }
        }
    }

    /**
     * Drop sand until rest or void
     * @param r
     * @param c
     * @return true for rest and false for void
     */
    private boolean dropSand(int r, int c, int voidRow) {
        while (r < voidRow) {
            if (cave[r+1][c] == Element.AIR) {
                r++;
            } else if (cave[r+1][c-1] == Element.AIR) {
                r++;
                c--;
            } else if (cave[r+1][c+1] == Element.AIR) {
                r++;
                c++;
            } else {
                cave[r][c] = Element.SAND;
                return true;
            }
        }
        return false;
    }
    static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    private enum Element {
        ROCK, AIR, SAND
    }
}