package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PowerConsumptionAnalyzer implements HealthReportAnalyzer {

  private static final Logger LOGGER = LoggerFactory.getLogger(PowerConsumptionAnalyzer.class);

  @Override
  public int analyze(List<String> report) {
    int[] ones = new int[report.get(0).length()];
    for (String val : report) {
      for (int i = 0; i < ones.length; i++) {
        if (val.charAt(i) == '1') {
          ones[i]++;
        }
      }
    }
    int gamma = 0;
    int epsilon = 0;
    int n = report.size();
    for (int i = ones.length - 1, pow = 1; i >= 0; i--, pow *= 2) {
      if (ones[i] + ones[i] > n) {
        gamma += pow;
      } else {
        epsilon += pow;
      }
    }
    int res = gamma * epsilon;

    LOGGER.info("day 3 task 1: 4147524");
    LOGGER.info("gamma={} epsilon={} power={}", gamma, epsilon, res);

    return res;
  }
}
