package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class DiracDiceGame {

  private static final Logger LOGGER = LoggerFactory.getLogger(DiracDiceGame.class);

  private static final String INPUT_FILENAME = "day21.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final int ROLLS_EACH_TURN = 3;
  private static final int GAME_TRACK_SIZE = 10;
  private static final int DETERMINISTIC_DICE_WINNING_SCORE = 1000;
  private static final int DIRAC_DICE_WINNING_SCORE = 21;
  /**
   * pre-calculated DS and DU where
   * <ul>
   *   <li>DS: all possible sums after 3 rolls of a dirac dice</li>
   *   <li>DU: the occurrences of the corresponding DS</li>
   * </ul>
   */
  private static final int[] DS = {3, 4, 5, 6, 7, 8, 9};
  private static final int[] DU = {1, 3, 6, 7, 6, 3, 1};

  private int[] locations;

  public DiracDiceGame() throws IOException {
    readInputs();
  }

  /**
   * @param faces the number of faces of the deterministic dice
   * @return int[] the points when one of the player wins
   */
  public int[] simulateDeterministicDice(int faces) {
    int[] points = new int[locations.length];
    int[] locs = locations.clone();
    for (int turn = 0, face = faces, rolls = 0; ; turn = 1 - turn) {
      for (int i = 0; i < ROLLS_EACH_TURN; i++) {
        face = rollDeterministicDice(faces, face);
        locs[turn] = (locs[turn] + face) % GAME_TRACK_SIZE;
        rolls++;
      }
      points[turn] += locs[turn] + 1;
      if (points[turn] >= DETERMINISTIC_DICE_WINNING_SCORE) {
        LOGGER.info("day 21 task 1: 893700");
        LOGGER.debug(Arrays.toString(points));
        LOGGER.debug("rolls done: {}", rolls);
        LOGGER.info("losing score * rolls done = {}", points[1 - turn] * rolls);
        break;
      }
    }
    return points;
  }

  public long[] simulateDiracDice() {
    // dp[u][v][i][j][k]: the number of universes created at turn k and scores u/v for player 1/2 and locations i/j for players 1/2
    long[][][][][] dp = new long[DIRAC_DICE_WINNING_SCORE + 1][DIRAC_DICE_WINNING_SCORE + 1][GAME_TRACK_SIZE][GAME_TRACK_SIZE][2];
    dp[0][0][locations[0]][locations[1]][1] = 1;
    for (int u = 0; u < DIRAC_DICE_WINNING_SCORE; u++) {
      for (int v = 0; v < DIRAC_DICE_WINNING_SCORE; v++) {
        for (int i = 0; i < GAME_TRACK_SIZE; i++) {
          for (int j = 0; j < GAME_TRACK_SIZE; j++) {
            for (int k = 0; k < 2; k++) {
              if (dp[u][v][i][j][k] > 0) {
                for (int w = 0; w < DS.length; w++) {
                  if (k == 0) {
                    int newLoc = (j + DS[w]) % GAME_TRACK_SIZE;
                    dp[u][Math.min(DIRAC_DICE_WINNING_SCORE, v + newLoc + 1)][i][newLoc][1 - k] += dp[u][v][i][j][k] * DU[w];
                  } else {
                    int newLoc = (i + DS[w]) % GAME_TRACK_SIZE;
                    dp[Math.min(DIRAC_DICE_WINNING_SCORE, u + newLoc + 1)][v][newLoc][j][1 - k] += dp[u][v][i][j][k] * DU[w];
                  }
                }
              }
            }
          }
        }
      }
    }
    long[] res = new long[2];
    for (int v = 0; v < DIRAC_DICE_WINNING_SCORE; v++) {
      for (int i = 0; i < GAME_TRACK_SIZE; i++) {
        for (int j = 0; j < GAME_TRACK_SIZE; j++) {
          res[0] += dp[DIRAC_DICE_WINNING_SCORE][v][i][j][0];
        }
      }
    }
    for (int u = 0; u < DIRAC_DICE_WINNING_SCORE; u++) {
      for (int i = 0; i < GAME_TRACK_SIZE; i++) {
        for (int j = 0; j < GAME_TRACK_SIZE; j++) {
          res[1] += dp[u][DIRAC_DICE_WINNING_SCORE][i][j][1];
        }
      }
    }
    LOGGER.info("day 21 task 2: 568867175661958");
    LOGGER.info("most universes won = {}", Math.max(res[0], res[1]));
    return res;
  }

  /**
   * @param faces the number of faces of the dice
   * @param cur   current value of the dice
   * @return the next value
   */
  private int rollDeterministicDice(int faces, int cur) {
    return cur % faces + 1;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      String line;
      locations = new int[2];
      int player = 0;
      while ((line = reader.readLine()) != null) {
        String[] tokens = line.split(" ");
        LOGGER.debug(Arrays.toString(tokens));
        // shift to 0-based
        locations[player++] = Integer.parseInt(tokens[4]) - 1;
      }
    }
  }
}