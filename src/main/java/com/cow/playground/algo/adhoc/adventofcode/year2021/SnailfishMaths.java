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

public class SnailfishMaths {

  private static final Logger LOGGER = LoggerFactory.getLogger(SnailfishMaths.class);

  private static final String INPUT_FILENAME = "day18.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private List<SnailfishNumber> snailfishNumberList;

  public SnailfishMaths() throws IOException {
    readInputs();
  }

  public int sumMagnitude() {
    SnailfishNumber sum = snailfishNumberList.get(0).clone();
    for (int i = 1; i < snailfishNumberList.size(); i++) {
      LOGGER.debug(">>> new sum={}", sum);
      LOGGER.debug("plus {}", snailfishNumberList.get(i));
      sum = new SnailfishNumber(sum, snailfishNumberList.get(i).clone()).reduce();
    }
    LOGGER.debug(">>> new sum={}", sum);

    int res = sum.magnitude();

    LOGGER.info("day 18 task 1: 4176");
    LOGGER.info("magnitude of sum={}", res);

    return res;
  }

  public int largestMagnitudeOfPairs() {
    int res = 0;
    for (int i = 0; i < snailfishNumberList.size(); i++) {
      for (int j = 0; j < snailfishNumberList.size(); j++) {
        if (i != j) {
          res = Math.max(res, new SnailfishNumber(snailfishNumberList.get(i).clone(), snailfishNumberList.get(j).clone()).reduce().magnitude());
        }
      }
    }

    LOGGER.info("day 18 task 2: 4633");
    LOGGER.info("largest magnitude of all pairs={}", res);

    return res;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      snailfishNumberList = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        snailfishNumberList.add(new SnailfishNumber(line));
      }
    }
  }

  private class SnailfishNumber {
    private SnailfishNumber leftNumber;
    private SnailfishNumber rightNumber;
    boolean isRegular;
    int value;

    private SnailfishNumber(int value) {
      this.isRegular = true;
      this.value = value;
    }

    private SnailfishNumber(SnailfishNumber leftNumber, SnailfishNumber rightNumber) {
      this.leftNumber = leftNumber;
      this.rightNumber = rightNumber;
      isRegular = false;
    }

    private SnailfishNumber(String s) {
      if (s.indexOf(",") < 0) {
        isRegular = true;
        value = Integer.parseInt(s);
      } else {
        isRegular = false;
        for (int i = 0, braces = 0; i < s.length(); i++) {
          char ch = s.charAt(i);
          if (ch == '[') {
            braces++;
          } else if (ch == ']') {
            braces--;
          } else if (ch == ',' && braces == 1) {
            leftNumber = new SnailfishNumber(s.substring(1, i));
            rightNumber = new SnailfishNumber(s.substring(i + 1, s.length() - 1));
            break;
          }
        }
      }
    }

    private SnailfishNumber reduce() {
      do {
        // explode once if any
        SnailfishNumber pairToBeExploded = findPairToExplode(0);
        if (pairToBeExploded != null) {
          LOGGER.debug("exploding {}", pairToBeExploded);
          List<SnailfishNumber> flattened = flatten();
          SnailfishNumber last = null;
          for (SnailfishNumber cur : flattened) {
            if (cur == pairToBeExploded) {
              if (last != null) {
                if (last.rightNumber.isRegular) {
                  last.rightNumber.value += pairToBeExploded.leftNumber.value;
                } else {
                  last.leftNumber.value += pairToBeExploded.leftNumber.value;
                }
              }
              break;
            }
            last = cur;
          }
          last = null;
          for (int i = flattened.size() - 1; i >= 0; i--) {
            SnailfishNumber cur = flattened.get(i);
            if (cur == pairToBeExploded) {
              if (last != null) {
                if (last.leftNumber.isRegular) {
                  last.leftNumber.value += pairToBeExploded.rightNumber.value;
                } else {
                  last.rightNumber.value += pairToBeExploded.rightNumber.value;
                }
              }
              break;
            }
            last = cur;
          }
          explode(pairToBeExploded);
          LOGGER.debug("done exploding {}", this);
          continue;
        }
        // if none is exploded, try to split once
        if (splitSomePair()) {
          LOGGER.debug("done splitting {}", this);
          continue;
        }
        // if no explode/split was done, stop
        break;
      } while (true);
      return this;
    }

    private boolean splitSomePair() {
      if (isRegular) {
        return false;
      }
      if (leftNumber.value >= 10) {
        leftNumber = new SnailfishNumber(new SnailfishNumber(leftNumber.value / 2),
                new SnailfishNumber((leftNumber.value + 1) / 2));
        return true;
      }
      if (leftNumber.splitSomePair()) {
        return true;
      }
      if (rightNumber.value >= 10) {
        rightNumber = new SnailfishNumber(new SnailfishNumber(rightNumber.value / 2),
                new SnailfishNumber((rightNumber.value + 1) / 2));
        return true;
      }
      return rightNumber.splitSomePair();
    }

    private boolean explode(SnailfishNumber pairToBeExploded) {
      if (isRegular) {
        return false;
      }
      if (leftNumber == pairToBeExploded) {
        this.leftNumber = new SnailfishNumber(0);
        return true;
      }
      if (rightNumber == pairToBeExploded) {
        this.rightNumber = new SnailfishNumber(0);
        return true;
      }
      return this.leftNumber.explode(pairToBeExploded) || rightNumber.explode(pairToBeExploded);
    }

    private List<SnailfishNumber> flatten() {
      List<SnailfishNumber> res = new ArrayList<>();
      if (this.isRegular) {
        return res;
      }
      if (leftNumber.isRegular) {
        res.add(this);
      } else {
        res.addAll(leftNumber.flatten());
      }
      if (rightNumber.isRegular) {
        if (!leftNumber.isRegular) {
          res.add(this);
        }
      } else {
        res.addAll(rightNumber.flatten());
      }
      return res;
    }

    private SnailfishNumber findPairToExplode(int layer) {
      if (isRegular) {
        return null;
      }
      if (layer == 4) {
        return this;
      }
      SnailfishNumber number = leftNumber.findPairToExplode(layer + 1);
      if (number != null) {
        return number;
      }
      return rightNumber.findPairToExplode(layer + 1);
    }

    private int magnitude() {
      if (isRegular) {
        return value;
      }
      return 3 * leftNumber.magnitude() + 2 * rightNumber.magnitude();
    }

    public String toString() {
      if (isRegular) {
        return String.valueOf(value);
      }
      return "[" + leftNumber.toString() + "," + rightNumber.toString() + "]";
    }

    protected SnailfishNumber clone() {
      if (isRegular) {
        return new SnailfishNumber(this.value);
      }
      return new SnailfishNumber(leftNumber.clone(), rightNumber.clone());
    }
  }
}
