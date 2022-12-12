package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class HillClimbing {
    private static final Logger LOGGER = LoggerFactory.getLogger(HillClimbing.class);
    private static final String INPUT_FILENAME = "day12.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    List<char[]> map;
    Location start, end;

    public HillClimbing() throws IOException {
        readInputs();
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            map = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                map.add(line.toCharArray());
            }
            for (int i = 0; i < map.size(); i++) {
                char[] row = map.get(i);
                for (int j = 0; j < row.length; j++) {
                    if (row[j] == 'S') {
                        start = new Location(i, j);
                        row[j] = 'a';
                    } else if (row[j] == 'E') {
                        end = new Location(i, j);
                        row[j] = 'z';
                    }
                }
            }
        }
    }

    public void task1() {
        int res = floodfill(Collections.<Location>singletonList(start), end);
        assert res == 449;
        LOGGER.info("S -> E in {} steps", res);
    }

    public void task2() {
        List<Location> starts = new ArrayList<>();
        for (int i = 0; i < map.size(); i++) {
            char[] row = map.get(i);
            for (int j = 0; j < row.length; j++) {
                if (row[j] == 'a') {
                    starts.add(new Location(i, j));
                }
            }
        }
        int res = floodfill(starts, end);
        assert res == 443;
        LOGGER.info("a -> E in {} steps", res);
    }

    private int floodfill(List<Location> starts, Location end) {
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        int nRow = map.size();
        int nCol = map.get(0).length;
        int[][] v = new int[nRow][nCol];
        Deque<Location> queue = new ArrayDeque<>();
        for (Location start : starts) {
            queue.addLast(start);
            v[start.r][start.c] = 1;
        }
        while (!queue.isEmpty()) {
            Location current = queue.removeFirst();
            for (int i = 0; i < dr.length; i++) {
                int nr = current.r + dr[i];
                int nc = current.c + dc[i];
                if (0 <= nr && nr < nRow && 0 <= nc && nc < nCol && v[nr][nc] == 0
                        && map.get(nr)[nc] - map.get(current.r)[current.c] <= 1) {
                    v[nr][nc] = v[current.r][current.c] + 1;
                    Location next = new Location(nr, nc);
                    queue.addLast(next);
                    if (nr == end.r && nc == end.c) {
                        return v[nr][nc] - 1;
                    }
                }
            }
        }
        return -1;
    }

    static class Location {
        int r, c;

        Location(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }
}