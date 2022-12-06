package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class TuningTrouble {
    private static final Logger LOGGER = LoggerFactory.getLogger(TuningTrouble.class);
    private static final String INPUT_FILENAME = "day6.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private static final int PACKET_MARKER_LENGTH = 4;
    private static final int MESSAGE_MARKER_LENGTH = 14;

    private char[] data;
    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                data = line.concat(" ").toCharArray();
            }
        }
    }
    public TuningTrouble() throws IOException {
        readInputs();
    }
    public void task1() {
        int res = getStartMarker(PACKET_MARKER_LENGTH);
        assert res == 1953;
        LOGGER.info("Packet marker location is at {}", res);
    }
    public void task2() {
        int res = getStartMarker(MESSAGE_MARKER_LENGTH);
        assert res == 2301;
        LOGGER.info("Message marker location is at {}", res);
    }
    private int getStartMarker(int markerLength) {
        int res = -1;
        for (int i = 0, j = 1; j < data.length; j++) {
            if (j - i >= markerLength) {
                res = j;
                break;
            }
            for (int k = j-1; k >= i; k--) {
                if (data[j] == data[k]) {
                    i = k + 1;
                    break;
                }
            }
        }
        return res;
    }
}
