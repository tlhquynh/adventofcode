package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RockPaperScissors {
    private static final Logger LOGGER = LoggerFactory.getLogger(RockPaperScissors.class);
    private static final String INPUT_FILENAME = "day2.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;

    private static final String ROCK = "A";
    private static final String PAPER = "B";
    private static final String SCISSORS = "C";

    private List<String[]> rounds;
    private final Map<String, Map<String, Integer>> points;

    public RockPaperScissors() throws IOException {
        readInputs();
        points = new HashMap<>();
        points.put("X", new HashMap<>());
        points.put("Y", new HashMap<>());
        points.put("Z", new HashMap<>());
        for (String[] round : rounds) {
            Map<String, Integer> map = points.get(round[1]);
            map.merge(ROCK, pointsForB(round[0], ROCK), Integer::sum);
            map.merge(PAPER, pointsForB(round[0], PAPER), Integer::sum);
            map.merge(SCISSORS, pointsForB(round[0], SCISSORS), Integer::sum);
            points.put(round[1], map);
        }
    }

    public int getTotal(String x, String y, String z) {
        return points.get("X").get(x) + points.get("Y").get(y) + points.get("Z").get(z);
    }

    private int pointsForB(String a, String b) {
        if (ROCK.equals(b)) {
            return 1 + (ROCK.equals(a) ? 3 : PAPER.equals(a) ? 0 : 6);
        }
        if (PAPER.equals(b)) {
            return 2 + (ROCK.equals(a) ? 6 : PAPER.equals(a) ? 3 : 0);
        }
        if (SCISSORS.equals(b)) {
            return 3 + (ROCK.equals(a) ? 0 : PAPER.equals(a) ? 6 : 3);
        }
        return 0;
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            rounds = new ArrayList<>();
            String line;

            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                rounds.add(tokens);
                LOGGER.debug("rounds {}", Arrays.toString(tokens));
            }
        }
    }

    public void task1() {
        LOGGER.info("day 2 task 1: 13809");
        LOGGER.debug("x=rock, y=paper, z=scissors: {}", getTotal(ROCK, PAPER, SCISSORS));
    }

    public void task2() {
        int total = 0;
        for (String[] round : rounds) {
            if (ROCK.equals(round[0])) {
                total += "X".equals(round[1]) ? pointsForB(ROCK, SCISSORS) : "Y".equals(round[1]) ? pointsForB(ROCK, ROCK) : pointsForB(ROCK, PAPER);
            } else if (PAPER.equals(round[0])) {
                total += "X".equals(round[1]) ? pointsForB(PAPER, ROCK) : "Y".equals(round[1]) ? pointsForB(PAPER, PAPER) : pointsForB(PAPER, SCISSORS);
            } else {
                total += "X".equals(round[1]) ? pointsForB(SCISSORS, PAPER) : "Y".equals(round[1]) ? pointsForB(SCISSORS, SCISSORS) : pointsForB(SCISSORS, ROCK);
            }
        }
        LOGGER.info("day 2 task 2: 12316");
        LOGGER.debug("points for new strategy: {}", total);
    }
}
