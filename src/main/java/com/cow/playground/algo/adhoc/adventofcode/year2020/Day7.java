package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * https://adventofcode.com/2020/day/7
 */
public class Day7 {

  private static final String inputFilename = "day7.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day7().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      Map<String, Map<String, Integer>> rules = new HashMap<>();
      String line;
      while ((line = reader.readLine()) != null) {
        parse(line, rules);
      }

      System.out.println(bfs("shiny gold", rules).size());
      System.out.println(dfs("shiny gold", rules) - 1);
    }
  }

  private int dfs(String outermostBag, Map<String, Map<String, Integer>> rules) {
    int res = 1;
    if (rules.containsKey(outermostBag)) {
      for (Map.Entry<String, Integer> entry : rules.get(outermostBag).entrySet()) {
        res += dfs(entry.getKey(), rules) * entry.getValue();
      }
    }
    return res;
  }

  private Set<String> bfs(String innermostBag, Map<String, Map<String, Integer>> rules) {
    Map<String, Set<String>> adj = new HashMap<>();
    Set<String> allBags = new HashSet<>();
    for (Map.Entry<String, Map<String, Integer>> entry : rules.entrySet()) {
      String outer = entry.getKey();
      allBags.add(outer);
      for (String inner : entry.getValue().keySet()) {
        if (!adj.containsKey(inner)) {
          adj.put(inner, new HashSet<>());
        }
        adj.get(inner).add(outer);
      }
    }
    Deque<String> queue = new ArrayDeque<>();
    queue.addLast(innermostBag);
    Set<String> originalAllBags = new HashSet<>(allBags);
    while (!queue.isEmpty()) {
      String inner = queue.removeFirst();
      if (adj.containsKey(inner)) {
        for (String outer : adj.get(inner)) {
          if (allBags.contains(outer)) {
            allBags.remove(outer);
            queue.addLast(outer);
          }
        }
      }
    }
    originalAllBags.removeAll(allBags);
    return originalAllBags;
  }

  private void parse(String text, Map<String, Map<String, Integer>> rules) {

    Map<String, Integer> rule = new HashMap<>();

    Pattern p = Pattern.compile("(.*?) bags contain");
    Matcher m = p.matcher(text);
    m.find();
    String enclosing = m.group(1);
    rules.put(enclosing, rule);

    p = Pattern.compile(" (\\d+)( (\\d+))* |(bags|bag)(\\.|,)");
    m = p.matcher(text);
    while (m.find()) {
      rule.put(m.group(2), Integer.parseInt(m.group(1)));
    }
  }
}