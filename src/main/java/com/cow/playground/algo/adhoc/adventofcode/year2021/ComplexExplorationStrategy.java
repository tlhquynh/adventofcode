package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ComplexExplorationStrategy implements SubmarineExplorationStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComplexExplorationStrategy.class);

  private List<String> ops;
  private List<Integer> vals;

  @Override
  public long explore(long startDepth, long startHorizontal) {
    long aim = 0;
    for (int i = 0; i < ops.size(); i++) {
      switch (ops.get(i)) {
        case "forward":
          startHorizontal += vals.get(i);
          startDepth += aim * vals.get(i);
          break;
        case "down":
          aim += vals.get(i);
          break;
        case "up":
          aim -= vals.get(i);
          break;
      }
    }
    long prod = startDepth * startHorizontal;
    LOGGER.info("day 2 task 2: 1971095320");
    LOGGER.info("Complex path location depth={} horizontal={} prod={}", startDepth, startHorizontal, prod);
    return prod;
  }

  @Override
  public void setOps(List<String> ops) {
    this.ops = ops;
  }

  @Override
  public void setVals(List<Integer> vals) {
    this.vals = vals;
  }
}
