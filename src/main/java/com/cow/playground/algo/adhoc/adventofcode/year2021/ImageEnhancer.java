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


public class ImageEnhancer {

  private static final Logger LOGGER = LoggerFactory.getLogger(ImageEnhancer.class);

  private static final String INPUT_FILENAME = "day20.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;

  private String enhanceAlgo;
  private List<List<Integer>> inputImage;

  public ImageEnhancer() throws IOException {
    readInputs();
  }

  public List<List<Integer>> enhance(int enhancingTimes) {
    // as image expands horizontally and vertically 2 pixels each time AND output image also shrinks horizontally
    // and vertically 2 pixels each time (because pixels on the border are not calculated), margins of 2 times of
    // enhancement are added to the original images
    List<List<Integer>> clone = addMargins(enhancingTimes);
    print(clone);
    for (int time = 0; time < enhancingTimes; time++) {
      List<List<Integer>> temp = new ArrayList<>();
      for (int i = 1; i < clone.size() - 1; i++) {
        List<Integer> row = new ArrayList<>();
        for (int j = 1; j < clone.get(i).size() - 1; j++) {
          int val = 0;
          for (int u = -1; u <= 1; u++) {
            for (int v = -1; v <= 1; v++) {
              val = val * 2 + clone.get(i + u).get(j + v);
            }
          }
          LOGGER.trace("val={}", val);
          row.add(enhanceAlgo.charAt(val) == '.' ? 0 : 1);
        }
        temp.add(row);
      }
      clone = temp;
      print(clone);
    }
    int res = 0;
    for (int i = 0; i < clone.size(); i++) {
      for (int j = 0; j < clone.get(i).size(); j++) {
        res += clone.get(i).get(j);
      }
    }

    LOGGER.info("day 20 task 1 / 2: 5268 / 16875");
    LOGGER.info("bright pixel count={}", res);

    return clone;
  }

  private void print(List<List<Integer>> image) {
    StringBuilder sb = new StringBuilder();
    sb.append("new state\n");
    for (List<Integer> row : image) {
      sb.append("\n").append(row);
    }
    LOGGER.debug(sb.toString());
  }

  private List<List<Integer>> addMargins(int enhancingTimes) {
    int nCol = inputImage.get(0).size();
    List<List<Integer>> res = new ArrayList<>();
    int margin = enhancingTimes * 2;
    // add top margin
    for (int i = 0; i < margin; i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j < margin + nCol + margin; j++) {
        row.add(0);
      }
      res.add(row);
    }
    // add left and right margins
    for (int i = 0; i < inputImage.size(); i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j < margin; j++) {
        row.add(0);
      }
      row.addAll(inputImage.get(i));
      for (int j = 0; j < margin; j++) {
        row.add(0);
      }
      res.add(row);
    }
    // add bottom margin
    for (int i = 0; i < margin; i++) {
      List<Integer> row = new ArrayList<>();
      for (int j = 0; j < margin + nCol + margin; j++) {
        row.add(0);
      }
      res.add(row);
    }
    return res;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      enhanceAlgo = reader.readLine();
      inputImage = new ArrayList<>();
      String line;
      while ((line = reader.readLine()) != null) {
        if (!line.isEmpty()) {
          List<Integer> row = new ArrayList<>();
          for (int i = 0; i < line.length(); i++) {
            row.add(line.charAt(i) == '.' ? 0 : 1);
          }
          inputImage.add(row);
        }
      }
    }
  }
}
