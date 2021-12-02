package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SimpleExplorationStrategy implements SubmarineExplorationStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleExplorationStrategy.class);

  private List<String> ops;
  private List<Integer> vals;

  @Override
  public long explore(long startDepth, long startHorizontal) {
    for (int i = 0; i < ops.size(); i++) {
      switch (ops.get(i)) {
        case "forward":
          startHorizontal += vals.get(i);
          break;
        case "down":
          startDepth += vals.get(i);
          break;
        case "up":
          startDepth -= vals.get(i);
          break;
      }
    }
    long prod = startDepth * startHorizontal;
    LOGGER.info("day 2 task 1: 1804520");
    LOGGER.info("Simple path location depth={} horizontal={} prod={}", startDepth, startHorizontal, prod);
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
