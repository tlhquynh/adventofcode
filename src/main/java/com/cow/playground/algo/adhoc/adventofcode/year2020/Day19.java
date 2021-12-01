package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * https://adventofcode.com/2020/day/18
 */
public class Day19 {

  private static final String inputFilename = "day19.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;
  private static final int MAX_QUERY_LENGTH = 89;

  public static void main(String[] args) throws IOException {
    new Day19().solve();
  }

  public void solve() throws IOException {
    Map<Integer, Rule> rules = new HashMap<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      int count = 0;
      boolean updatedForPart2 = false;
      String line = reader.readLine();
      for (; line != null; line = reader.readLine()) {
        System.out.println(line);
        if (!line.isEmpty()) {
          int colon = line.indexOf(":");
          if (colon >= 0) {
            // rule
            int ruleId = Integer.parseInt(line.substring(0, colon));
            Rule rule = rules.getOrDefault(ruleId, new Rule(ruleId));
            String[] subRules = line.substring(colon+1).split("\\|");
            for (String subRule : subRules) {
              System.out.println(subRule);
              int charId = subRule.indexOf(" \"");
              if (charId >= 0) {
                rule.addValidMessage(subRule.substring(charId+2, charId+3));
              } else {
                String[] parts = subRule.trim().split(" ");
                Rule[] ruleParts = new Rule[parts.length];
                for (int i = 0; i < ruleParts.length; i++) {
                  int rulePartId = Integer.parseInt(parts[i]);
                  ruleParts[i] = rules.getOrDefault(rulePartId, new Rule(rulePartId));
                  rules.putIfAbsent(rulePartId, ruleParts[i]);
                }
                rule.addSubRule(ruleParts);
              }
              rules.putIfAbsent(ruleId, rule);
            }
          } else {
            // message
            Set<String> validMessages = find(rules, 0);
            if (!updatedForPart2) {
              Rule rule8 = rules.get(8);
              Rule rule42 = rules.get(42);
              Set<String> temp;
              do {
                temp = new HashSet<>();
//                System.out.println(rule42.validMessages.size() + " " + rule8.validMessages.size());
                for (String s1 : rule42.validMessages) {
                  for (String s2 : rule8.validMessages) {
                    String s = s1 + s2;
                    if (s.length() <= MAX_QUERY_LENGTH) {
                      temp.add(s);
                    }
                  }
                }
                System.out.println(temp);
                rule8.validMessages.addAll(temp);
              } while (!temp.isEmpty());
              Rule rule11 = rules.get(11);
              Rule rule31 = rules.get(31);
              do {
                temp = new HashSet<>();
                for (String s1 : rule42.validMessages) {
                  for (String s2 : rule11.validMessages) {
                    for (String s3 : rule31.validMessages) {
                      String s = s1 + s2 + s3;
                      if (s.length() <= MAX_QUERY_LENGTH) {
                        temp.add(s);
                      }
                    }
                  }
                }
                rule11.validMessages.addAll(temp);
              } while (!temp.isEmpty());
              updatedForPart2 = true;
            }
            if (validMessages.contains(line)) {
              count++;
            }
          }
        }
      }
      System.out.println(count);
    }
  }

  /**
   * generate all strings from ruleId
   * @param rules
   * @param ruleId
   * @return
   */
  private Set<String> find(Map<Integer, Rule> rules, int ruleId) {
    Set<String> res = new HashSet<>();
      Rule rule = rules.get(ruleId);
      if (rule.validMessages != null) {
        return rule.validMessages;
      }
      for (Rule[] subRule : rule.subRules) {
        res.addAll(buildValidMessages(rules, subRule));
      }
    return rule.validMessages = res;
  }

  /**
   * build all valid messages from a sub-rule
   * @param rules
   * @param subRule
   * @return
   */
  private Set<String> buildValidMessages(Map<Integer, Rule> rules, Rule[] subRule) {
    Set<String> res1 = find(rules, subRule[0].id);
    for (int i = 1; i < subRule.length; i++) {
      Set<String> temp = new HashSet<>();
      Set<String> res2 = find(rules, subRule[i].id);
      System.out.println(res1.size() + " " + res2.size());
      for (String s1 : res1) {
        for (String s2 : res2) {
          String s = s1 + s2;
          if (s.length() <= MAX_QUERY_LENGTH) {
            temp.add(s);
          }
        }
      }
      res1 = temp;
    }
    return res1;
  }

  private class Rule {
    int id;
    List<Rule[]> subRules;
    Set<String> validMessages;
    public Rule(int id) {
      this.id = id;
      subRules = new ArrayList<>();
    }
    public void addSubRule(Rule[] subRule) {
      this.subRules.add(subRule);
    }
    public void addValidMessage(String message) {
      if (validMessages == null) {
        validMessages = new HashSet<>();
      }
      this.validMessages.add(message);
    }
    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Rule rule = (Rule) o;
      return id == rule.id;
    }
    @Override
    public int hashCode() {
      return Objects.hash(id);
    }
  }
}