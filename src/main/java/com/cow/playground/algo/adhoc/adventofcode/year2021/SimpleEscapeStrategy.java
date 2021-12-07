package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class SimpleEscapeStrategy implements EscapeStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEscapeStrategy.class);

  @Override
  public long execute(List<Integer> startingCrabs) {
    Collections.sort(startingCrabs);
    long fuels = 0;
    for (int i = 1; i < startingCrabs.size(); i++) {
      fuels += startingCrabs.get(i) - startingCrabs.get(0);
    }
    long best = fuels;
    for (int i = 1; i < startingCrabs.size(); i++) {
      fuels -= (startingCrabs.size() - i) * (startingCrabs.get(i) - startingCrabs.get(i - 1));
      fuels += i * (startingCrabs.get(i) - startingCrabs.get(i - 1));
      best = Math.min(best, fuels);
    }
    LOGGER.info("day 7 task 1: 348664");
    LOGGER.info("min fuel for simple escape strategy: {}", best);
    return best;
  }
}
