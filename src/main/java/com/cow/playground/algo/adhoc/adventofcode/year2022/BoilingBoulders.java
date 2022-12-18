package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class BoilingBoulders {
    private static final Logger LOGGER = LoggerFactory.getLogger(BoilingBoulders.class);
    private static final String INPUT_FILENAME = "day18.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private List<Coordinator> unitCubes;
    int minX, minY, minZ, maxX, maxY, maxZ;

    public BoilingBoulders() throws IOException {
        readInputs();
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {
            String line;
            unitCubes = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                unitCubes.add(new Coordinator(
                        Integer.parseInt(tokens[0]),
                        Integer.parseInt(tokens[1]),
                        Integer.parseInt(tokens[2])));
            }
            minX = Integer.MAX_VALUE;
            minY = Integer.MAX_VALUE;
            minZ = Integer.MAX_VALUE;
            maxX = Integer.MIN_VALUE;
            maxY = Integer.MIN_VALUE;
            maxZ = Integer.MIN_VALUE;
            for (Coordinator c : unitCubes) {
                minX = Math.min(minX, c.x);
                minY = Math.min(minY, c.y);
                minZ = Math.min(minZ, c.z);
                maxX = Math.max(maxX, c.x);
                maxY = Math.max(maxY, c.y);
                maxZ = Math.max(maxZ, c.z);
            }
        }
    }

    public void task1() {
        int total = unitCubes.size() * 6;
        // brute force
        for (int i = 0; i < unitCubes.size(); i++) {
            Coordinator ci = unitCubes.get(i);
            for (int j = i + 1; j < unitCubes.size(); j++) {
                Coordinator cj = unitCubes.get(j);
                boolean isAdjacent = (Math.abs(ci.x - cj.x) == 1 && ci.y == cj.y && ci.z == cj.z)
                        || (ci.x == cj.x && Math.abs(ci.y - cj.y) == 1 && ci.z == cj.z)
                        || (ci.x == cj.x && ci.y == cj.y && Math.abs(ci.z - cj.z) == 1);
                if (isAdjacent) {
                    total -= 2;
                }
            }
        }
        assert total == 4628;
        LOGGER.info("Total exposed faces is {}", total);
    }

    public void task2() {
        int[] DX = {-1, 1, 0, 0, 0, 0};
        int[] DY = {0, 0, -1, 1, 0, 0};
        int[] DZ = {0, 0, 0, 0, -1, 1};
        Set<Coordinator> stopCubes = new HashSet<>();
        Set<Coordinator> airCubes = new HashSet<>();
        Set<Coordinator> surfaceCubes = new HashSet<>();
        stopCubes.addAll(unitCubes);
        minX--;
        minY--;
        minZ--;
        maxX++;
        maxY++;
        maxZ++;
        Deque<Coordinator> q = new ArrayDeque<>();
        Set<Coordinator> visited = new HashSet<>();
        q.addLast(new Coordinator(minX, minY, minZ));
        visited.add(new Coordinator(minX, minY, minZ));
        while (!q.isEmpty()) {
            Coordinator c = q.removeFirst();
            for (int i = 0; i < DX.length; i++) {
                int nx = c.x + DX[i];
                int ny = c.y + DY[i];
                int nz = c.z + DZ[i];
                if (nx >= minX && nx <= maxX && ny >= minY && ny <= maxY && nz >= minZ && nz <= maxZ) {
                    Coordinator next = new Coordinator(nx, ny, nz);
                    if (!visited.contains(next)) {
                        if (stopCubes.contains(next)) {
                            airCubes.add(c);
                            surfaceCubes.add(next);
                        } else {
                            q.addLast(next);
                            visited.add(next);
                        }
                    }
                }
            }
        }
        int surfaces = 0;
        for (Coordinator c : surfaceCubes) {
            for (int i = 0; i < DX.length; i++) {
                int nx = c.x + DX[i];
                int ny = c.y + DY[i];
                int nz = c.z + DZ[i];
                if (airCubes.contains(new Coordinator(nx, ny, nz))) {
                    surfaces++;
                }
            }
        }
        LOGGER.info("Surface size is {}", surfaces);
    }

    static class Coordinator {
        int x, y, z;

        Coordinator(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Coordinator that = (Coordinator) o;
            return x == that.x && y == that.y && z == that.z;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, z);
        }
    }
}