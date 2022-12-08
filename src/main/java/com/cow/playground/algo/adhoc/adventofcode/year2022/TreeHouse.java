package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TreeHouse {
    private static final Logger LOGGER = LoggerFactory.getLogger(TreeHouse.class);
    private static final String INPUT_FILENAME = "day8.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private static List<int[]> map;

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            map = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                char[] chars = line.toCharArray();
                int[] row = new int[chars.length];
                for (int i = 0; i < row.length; i++) {
                    row[i] = chars[i] - '0';
                }
                map.add(row);
            }
        }
    }

    public TreeHouse() throws IOException {
        readInputs();
    }

    public void task1() {
        int nRow = map.size();
        int nCol = map.get(0).length;
        boolean[][] visible = new boolean[nRow][nCol];
        for (int i = 0; i < nRow; i++) {
            int[] row = map.get(i);
            visible[i][0] = true;
            int max = row[0];
            for (int j = 1; j < nCol; j++) {
                if (row[j] > max) {
                    visible[i][j] = true;
                    max = row[j];
                }
            }
            visible[i][nCol - 1] = true;
            max = row[nCol - 1];
            for (int j = nCol - 1; j >= 0; j--) {
                if (row[j] > max) {
                    visible[i][j] = true;
                    max = row[j];
                }
            }
        }
        for (int j = 0; j < nCol; j++) {
            visible[0][j] = true;
            int max = map.get(0)[j];
            for (int i = 1; i < nRow; i++) {
                if (map.get(i)[j] > max) {
                    visible[i][j] = true;
                    max = map.get(i)[j];
                }
            }
            visible[nRow - 1][j] = true;
            max = map.get(nRow - 1)[j];
            for (int i = nRow - 1; i >= 0; i--) {
                if (map.get(i)[j] > max) {
                    visible[i][j] = true;
                    max = map.get(i)[j];
                }
            }
        }
        int res = 0;
        for (boolean[] row : visible) {
            for (boolean b : row) {
                res += b ? 1 : 0;
            }
        }
        assert res == 1700;
        LOGGER.info("Visible tree count is {}", res);
    }

    public void task2() {
        int nRow = map.size();
        int nCol = map.get(0).length;
        int bestScore = 0;
        for (int i = 1; i < nRow - 1; i++) {
            for (int j = 1; j < nCol - 1; j++) {
                int currentHeight = map.get(i)[j];
                int left = 0;
                int jj = j - 1;
                for (; jj >= 0 && map.get(i)[jj] < currentHeight; jj--, left++) ;
                left += jj >= 0 ? 1 : 0;
                int right = 0;
                jj = j + 1;
                for (; jj < nCol && map.get(i)[jj] < currentHeight; jj++, right++) ;
                right += jj < nCol ? 1 : 0;
                int top = 0;
                int ii = i - 1;
                for (; ii >= 0 && map.get(ii)[j] < currentHeight; ii--, top++) ;
                top += ii >= 0 ? 1 : 0;
                int bottom = 0;
                ii = i + 1;
                for (; ii < nRow && map.get(ii)[j] < currentHeight; ii++, bottom++) ;
                bottom += ii < nRow ? 1 : 0;
                int score = left * top * right * bottom;
                if (score > bestScore) {
                    bestScore = score;
                    LOGGER.debug("Best at ({}, {}): {}", i, j, bestScore);
                }
            }
        }
        assert bestScore == 470596;
        LOGGER.info("Best scenic spot score is {}", bestScore);
    }
}
