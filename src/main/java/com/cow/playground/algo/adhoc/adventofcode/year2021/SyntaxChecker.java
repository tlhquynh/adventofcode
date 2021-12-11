package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SyntaxChecker {

  private static final Logger LOGGER = LoggerFactory.getLogger(SyntaxChecker.class);

  private static final String INPUT_FILENAME = "day10.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final String OPEN = "([{<";
  private static final String CLOSED = ")]}>";
  private static final int[] SCORE = {3, 57, 1197, 25137};

  List<String> inputs;

  public SyntaxChecker() throws IOException {
    readInputs();
  }

  public List<Long> findErrorScores() {
    List<Long> res = new ArrayList<>();
    List<Long> incomplete = new ArrayList<>();
    long errorScores = 0;
    int i = 0;
    for (String input : inputs) {
      long score = findErrorScore(input);
      if (score > 0) {
        errorScores += score;
      } else if (score < 0) {
        LOGGER.info("{} score={}", ++i, -score);
        incomplete.add(-score);
      }
      res.add(score);
    }
    Collections.sort(incomplete);
    LOGGER.info("day 10 task 1: 1503747205");
    LOGGER.info("total error scores = {}", errorScores);
    LOGGER.info("day 10 task 2: 2292863731");
    LOGGER.info("total error fix scores = {}", incomplete.get(incomplete.size() / 2));
    return res;
  }

  private long findErrorScore(String input) {
    Deque<Integer> deque = new ArrayDeque<>();
    for (int i = 0; i < input.length(); i++) {
      char ch = input.charAt(i);
      int charIndex = getCharIndex(ch);
      if (charIndex < 0) {
        if (deque.size() == 0) {
          return SCORE[-charIndex - 1];
        }
        if (deque.peekFirst() != -charIndex) {
          return SCORE[-charIndex - 1];
        }
        deque.removeFirst();
      } else {
        deque.addFirst(charIndex);
      }
    }
    if (deque.size() == 0) {
      return 0;
    }
    long res = 0;
    while (deque.size() > 0) {
      res = res * 5 + deque.removeFirst();
    }
    return -res;
  }

  private int getCharIndex(char ch) {
    int res = OPEN.indexOf(ch) + 1;
    if (res <= 0) {
      res = -(CLOSED.indexOf(ch) + 1);
    }
    return res;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      inputs = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        inputs.add(line);
      }
    }
  }
}
