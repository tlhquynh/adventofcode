package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2020/day/16
 */
public class Day16 {

  private static final String inputFilename = "day16.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day16().solve();
  }

  public void solve() throws IOException {
    Map<String, Set<Range>> fields = new HashMap<>();
    int[] mine = null;
    Set<int[]> theirs = new HashSet<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line = null;
      boolean readingNearbyTickets = false;
      while ((line = reader.readLine()) != null) {
        if (line.isEmpty()) {
          continue;
        }
        if (line.startsWith("your ticket")) {
          mine = readTicket(reader.readLine());
        } else if (line.startsWith("nearby tickets")) {
          readingNearbyTickets = true;
        } else if (readingNearbyTickets) {
          theirs.add(readTicket(line));
        } else {
          int colon = line.indexOf(":");
          Set<Range> ranges = new HashSet<>();
          Pattern p = Pattern.compile("(.*?)(\\d+)-(\\d+)");
          Matcher m = p.matcher(line.substring(colon+1));
          while (m.find()) {
            ranges.add(new Range(Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3))));
          }
          System.out.println(line);
          fields.put(line.substring(0, colon), ranges);
        }
      }
    }
    
    firstPart(fields, theirs);

    secondPart(fields, mine, theirs);
  }

  private void secondPart(Map<String, Set<Range>> fields, int[] mine, Set<int[]> theirs) {
    Set<String>[] possibleFields = new Set[mine.length];
    for (int i = 0; i < mine.length; i++) {
      possibleFields[i] = new HashSet<>(fields.keySet());
      for (int[] other : theirs) {
        for (Iterator<String> iterator = possibleFields[i].iterator(); iterator.hasNext();) {
          boolean possibleField = false;
          for (Range range : fields.get(iterator.next())) {
            if (other[i] >= range.min && other[i] <= range.max) {
              possibleField = true;
            }
          }
          if (!possibleField) {
            iterator.remove();
          }
        }
      }
    }
    for (boolean stop = false; stop == false;) {
      stop = true;
      for (Set<String> pos1 : possibleFields) {
        if (pos1.size() == 1) {
          for (Set<String> pos2 : possibleFields) {
            if (pos1 != pos2) {
              stop &= !pos2.removeAll(pos1);
            }
          }
        }
      }
    }
    long res = 1;
    for (int i = 0; i < possibleFields.length; i++) {
      if (possibleFields[i].size() == 1 && possibleFields[i].toArray(new String[0])[0].startsWith("departure")) {
        System.out.println(possibleFields[i]);
        res *= mine[i];
        System.out.println(res);
      }
    }
  }

  private void firstPart(Map<String, Set<Range>> fields, Set<int[]> theirs) {
    int errors = 0;
    for (Iterator<int[]> iterator = theirs.iterator(); iterator.hasNext();) {
      int[] ticket = iterator.next();
      boolean okTicket = true;
      for (int num : ticket) {
        boolean okNum = false;
        checkNum:
        for (Set<Range> ranges : fields.values()) {
          for (Range range : ranges) {
            if (num >= range.min && num <=  range.max) {
              okNum = true;
              break checkNum;
            }
          }
        }
        if (!okNum) {
          errors += num;
          okTicket = false;
        }
      }
      if (!okTicket) {
        iterator.remove();
      }
    }
    System.out.println(errors);
  }

  private int[] readTicket(String line) {
    String[] tokens = line.split(",");
    int[] res = new int[tokens.length];
    for (int i = 0; i < tokens.length; i++) {
      res[i] = Integer.parseInt(tokens[i]);
    }
    return res;
  }

  class Range {
    int min, max;
    public Range(int min, int max) {
      this.min = min;
      this.max = max;
    }
  }
}