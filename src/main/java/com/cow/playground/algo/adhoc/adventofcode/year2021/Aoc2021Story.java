package com.cow.playground.algo.adhoc.adventofcode.year2021;

import java.io.IOException;

public class Aoc2021Story {

  public static void main(String[] args) throws IOException {
    // day 1 scanning the sea bed
    SonarScanner ss = new SonarScanner();
    ss.countDrops();
    ss.countSize3WindowDrops();

    // day 2 submarine starts to explore
    Submarine submarine = new Submarine();
    submarine.setExplorationStrategy(new SimpleExplorationStrategy());
    submarine.explore();
    submarine.setExplorationStrategy(new ComplexExplorationStrategy());
    submarine.explore();

    // day 3 submarine health check
    SubmarineHealthChecker healthCheck = new SubmarineHealthChecker();
    healthCheck.getPowerConsumption();
    healthCheck.getLifeSupportRating();

    // day 4 playing bingo with the giant squid
    BingoGame bingoGame = new BingoGame();
    bingoGame.start();

    // day 5 hypothermal vent danger
    SubmarineHydrothermalVentFinder finder = new SubmarineHydrothermalVentFinder();
    finder.countHorizontalVerticalDangerousSpots();
    finder.countHorizontalVerticalDiagonalDangerousSpots();
  }
}
