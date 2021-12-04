package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OxygenGeneratorAnalyzer implements HealthReportAnalyzer {

  private static final Logger LOGGER = LoggerFactory.getLogger(OxygenGeneratorAnalyzer.class);

  @Override
  public int analyze(List<String> report) {
    Set<String> start = new HashSet<>(report);
    for (int bit = 0; start.size() > 1; bit++) {
      Set<String> next = new HashSet<>();
      int ones = 0;
      for (String val : start) {
        if (val.charAt(bit) == '1') {
          ones++;
        }
      }
      if (ones + ones >= start.size()) {
        for (String val : start) {
          if (val.charAt(bit) == '1') {
            next.add(val);
          }
        }
      } else {
        for (String val : start) {
          if (val.charAt(bit) == '0') {
            next.add(val);
          }
        }
      }
      start = next;
    }

    int res = Integer.parseInt(start.iterator().next(), 2);

    LOGGER.trace("oxygen rating = {}", res);

    return res;
  }
}
