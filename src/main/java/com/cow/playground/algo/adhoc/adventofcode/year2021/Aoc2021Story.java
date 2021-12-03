package com.cow.playground.algo.adhoc.adventofcode.year2021;

import java.io.IOException;

public class Aoc2021Story {

  public static void main(String[] args) throws IOException {
    SonarScanner ss = new SonarScanner();
    ss.countDrops();
    ss.countSize3WindowDrops();

    Submarine submarine = new Submarine();
    submarine.setExplorationStrategy(new SimpleExplorationStrategy());
    submarine.explore();
    submarine.setExplorationStrategy(new ComplexExplorationStrategy());
    submarine.explore();

    SubmarineHealthCheck healthCheck = new SubmarineHealthCheck();
    healthCheck.getPowerConsumption();
    healthCheck.getLifeSupportRating();
  }
}
