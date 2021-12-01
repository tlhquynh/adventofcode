package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2020/day/17
 */
public class Day17 {

  private static final String inputFilename = "day17.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day17().solve();
  }

  public void solve() throws IOException {
    Set<MultiDimCell> active3dCells = new HashSet<>();
    Set<MultiDimCell> active4dCells = new HashSet<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line = reader.readLine();
      for (int y = 0, z = 0, w = 0; line != null; line = reader.readLine(), y++) {
        for (int x = 0; x < line.length(); x++) {
          if (line.charAt(x) == '#') {
            active3dCells.add(new MultiDimCell(x, y, z));
            active4dCells.add(new MultiDimCell(x, y, z, w));
          }
        }
      }
    }

    reboot(active3dCells, 6);
    reboot(active4dCells, 6);
  }

  private void reboot(Set<MultiDimCell> activeCells, int times) {
    for (int i = 0; i < times; i++) {
      Set<MultiDimCell> next = new HashSet<>();
      Set<MultiDimCell> inactiveCells = new HashSet<>();
      for (MultiDimCell activeCell : activeCells) {
        int countActive = 0;
        for (MultiDimCell neighbor : activeCell.neighborList()) {
          if (activeCells.contains(neighbor)) {
            countActive++;
          } else {
            inactiveCells.add(neighbor);
          }
        }
        if (countActive == 2 || countActive == 3) {
          next.add(activeCell);
        }
      }
      for (MultiDimCell inactiveCell : inactiveCells) {
        int countActive = 0;
        for (MultiDimCell neighbor : inactiveCell.neighborList()) {
          if (activeCells.contains(neighbor)) {
            countActive++;
          }
        }
        if (countActive == 3) {
          next.add(inactiveCell);
        }
      }
      activeCells = next;
    }
    System.out.println(activeCells.size());
  }

  class MultiDimCell {
    int[] dims;
    public MultiDimCell(int... dims) {
      this.dims = dims;
    }
    public List<MultiDimCell> neighborList() {
      return neighborList(0, new int[dims.length]);
    }
    private List<MultiDimCell> neighborList(int step, int[] dims) {
      List<MultiDimCell> res = new ArrayList<>();
      if (step == dims.length) {
        for (int i = 0; i < dims.length; i++) {
          if (dims[i] != this.dims[i]) {
            res.add(new MultiDimCell(dims.clone()));
            return res;
          }
        }
      } else {
        for (int delta = -1; delta <= 1; delta++) {
          dims[step] = this.dims[step] + delta;
          res.addAll(neighborList(step+1, dims));
        }
      }
      return res;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      MultiDimCell that = (MultiDimCell) o;
      return Arrays.equals(dims, that.dims);
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(dims);
    }
  }
}