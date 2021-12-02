package com.cow.playground.algo.adhoc.adventofcode.year2021;

import java.io.IOException;

public class Aoc2021Story {

  public static void main(String[] args) throws IOException {
    SonarSweep ss = new SonarSweep();
    ss.countDrops();
    ss.countSize3WindowDrops();

    Submarine submarine = new Submarine();
    submarine.findProdDepthHorizontal();
  }
}
