package com.cow.playground.algo.adhoc.adventofcode.year2020;

@FunctionalInterface
interface TextValidator {

  boolean validate(String text);

  default boolean isNumericInRange(String s, int min, int max) {
    if (s.length() < String.valueOf(min).length() || s.length() > String.valueOf(max).length()) {
      return false;
    }
    int value = 0;
    for (char ch : s.toCharArray()) {
      value = value * 10 + (ch - '0');
    }
    return value >= min && value <= max;
  }
}