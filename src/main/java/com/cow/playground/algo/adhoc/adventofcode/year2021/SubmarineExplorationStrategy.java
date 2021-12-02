package com.cow.playground.algo.adhoc.adventofcode.year2021;

import java.util.List;

public interface SubmarineExplorationStrategy {
  long explore(long startDepth, long startHorizontal);

  void setOps(List<String> ops);

  void setVals(List<Integer> vals);
}
