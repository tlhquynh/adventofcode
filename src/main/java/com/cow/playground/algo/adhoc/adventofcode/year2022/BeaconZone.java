package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BeaconZone {
    private static final Logger LOGGER = LoggerFactory.getLogger(BeaconZone.class);
    private static final String INPUT_FILENAME = "day15.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    List<Point> sensors;
    List<Point> beacons;

    public BeaconZone() throws IOException {
        readInputs();
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            sensors = new ArrayList<>();
            beacons = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                // Sensor at x=..., y=...: closest beacon is at x=..., y=...
                String[] tokens = line.split(" ");
                sensors.add(new Point(
                        Integer.parseInt(tokens[3].substring(2, tokens[3].length() - 1)),
                        Integer.parseInt(tokens[2].substring(2, tokens[2].length() - 1))
                ));
                beacons.add(new Point(
                        Integer.parseInt(tokens[9].substring(2)),
                        Integer.parseInt(tokens[8].substring(2, tokens[8].length() - 1))
                ));
            }
        }
    }

    public void task1() {
        int targetRow = 2_000_000;
        long cover = findCoverage(targetRow, 0, targetRow, 0, new ArrayList<>());
        assert cover == 5125700;
        LOGGER.info("Beacon cannot be present in {} locations", cover);
    }

    public void task2() {
        List<Point> availableSpots = new ArrayList<>();
        long cover = findCoverage(0, 0, 4_000_000, 4_000_000, availableSpots);
        assert availableSpots.size() == 1;
        long frequency = availableSpots.get(0).c * 4_000_000L + availableSpots.get(0).r;
        assert frequency == 11379394658764L;
        LOGGER.info("Distress beacon's tuning frequency is {}", frequency);
    }

    /**
     * (up, down, left, right) is the rectangle of interest to find the distress beacon
     *
     * @param up
     * @param left
     * @param down
     * @param right
     * @param availableSpots will be updated with possible locations for the distress beacon.
     *                       In this problem we are expecting only 1 spot.
     * @return the count of impossible locations for the distress beacon
     */
    private long findCoverage(int up, int left, int down, int right, List<Point> availableSpots) {
        long cover = 0;
        // 1. go row by row of the rectangle
        for (int targetRow = up; targetRow <= down; targetRow++) {
            List<Extreme> extremes = new ArrayList<>();
            // 2. for each row, find all parts covered by sensors
            for (int i = 0; i < sensors.size(); i++) {
                Point sensor = sensors.get(i);
                Point beacon = beacons.get(i);
                int d = manhattan(sensor, beacon);
                if (sensor.r >= targetRow) {
                    if (sensor.r - d <= targetRow) {
                        int dc = d - (sensor.r - targetRow);
                        extremes.add(new Extreme(new Point(targetRow, sensor.c - dc), true));
                        extremes.add(new Extreme(new Point(targetRow, sensor.c + dc), false));
                    }
                } else {
                    if (sensor.r + d >= targetRow) {
                        int dc = d - (targetRow - sensor.r);
                        extremes.add(new Extreme(new Point(targetRow, sensor.c - dc), true));
                        extremes.add(new Extreme(new Point(targetRow, sensor.c + dc), false));
                    }
                }
            }
            // 3. identify locations that are already taken by sensors or beacons
            Set<Integer> takenCols = new HashSet<>();
            for (Point sensor : sensors) {
                if (sensor.r == targetRow) {
                    takenCols.add(sensor.c);
                }
            }
            for (Point beacon : beacons) {
                if (beacon.r == targetRow) {
                    takenCols.add(beacon.c);
                }
            }
            // 4. as the parts found in (2) can overlap each other
            // 4a. sort the parts found in (2) by column (or x)
            extremes.sort((u, v) -> u.p.c < v.p.c ? -1 : u.p.c > v.p.c ? 1 : -Boolean.compare(u.isOpen, v.isOpen));
            // 4b. go from left to right to count coverage
            int openCount = 0;
            int prev = 0;  // this is the start of a covered part
            int farthestAvailable = left;  // this is the start of an available part
            for (Extreme e : extremes) {
                int prevCount = openCount;
                openCount += e.isOpen ? 1 : -1;
                if (openCount == 1) {
                    if (prevCount == 0) {
                        prev = e.p.c;
                    }
                } else if (openCount == 0) {
                    // exclude the locations of sensors/beacons
                    for (int takenCol : takenCols) {
                        if (prev <= takenCol && takenCol <= e.p.c) {
                            cover--;
                        }
                    }
                    // now record the available part
                    if (farthestAvailable < prev) {
                        for (int j = farthestAvailable; j < prev; j++) {
                            availableSpots.add(new Point(targetRow, j));
                        }
                    }
                    farthestAvailable = e.p.c + 1;
                    // now record the covered part
                    cover += e.p.c - prev + 1;
                }
            }
            for (int j = farthestAvailable; j < right; j++) {
                availableSpots.add(new Point(targetRow, j));
            }
        }
        return cover;
    }

    private int manhattan(Point p, Point q) {
        return Math.abs(p.r - q.r) + Math.abs(p.c - q.c);
    }

    static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    static class Extreme {
        Point p;
        boolean isOpen;

        Extreme(Point p, boolean isOpen) {
            this.p = p;
            this.isOpen = isOpen;
        }
    }
}