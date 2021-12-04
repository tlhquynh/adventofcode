package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class SubmarineHealthCheck {

  private static final Logger LOGGER = LoggerFactory.getLogger(SubmarineHealthCheck.class);

  private static final String INPUT_FILENAME = "day3.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private List<String> report;

  public SubmarineHealthCheck() throws IOException {
    readInputs();
  }

  public int getPowerConsumption() {
    return new PowerConsumptionAnalyzer().analyze(report);
  }

  public int getLifeSupportRating() {
    int res = new OxygenGeneratorAnalyzer().analyze(report) * new CarbonDioxideScrubberAnalyzer().analyze(report);
    LOGGER.info("day 3 task 2: 3570354");
    LOGGER.info("life support rating={}", res);
    return res;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      report = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        report.add(line);
      }
    }
  }
}
