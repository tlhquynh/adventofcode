package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

public class ComplexEscapeStrategy implements EscapeStrategy {

  private static final Logger LOGGER = LoggerFactory.getLogger(SimpleEscapeStrategy.class);

  @Override
  public long execute(List<Integer> startingCrabs) {
    Collections.sort(startingCrabs);
    int max = startingCrabs.stream().max(Integer::compare).get();
    long best = Long.MAX_VALUE;
    for (int i = 0; i < max; i++) {
      long fuels = 0;
      for (int j = 0; j < startingCrabs.size(); j++) {
        int diff = Math.abs(i - startingCrabs.get(j));
        fuels += (diff + 1) * diff / 2;
      }
      best = Math.min(best, fuels);
    }
    LOGGER.info("day 7 task 2: 100220525");
    LOGGER.info("min fuel for complex escape strategy: {}", best);
    return best;
  }
}
