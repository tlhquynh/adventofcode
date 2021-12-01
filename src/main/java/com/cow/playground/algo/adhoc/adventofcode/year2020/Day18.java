package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * https://adventofcode.com/2020/day/18
 */
public class Day18 {

  private static final String inputFilename = "day18.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day18().solve();
  }

  public void solve() throws IOException {
    long sum1 = 0;
    long sum2 = 0;
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line = reader.readLine();
      for (; line != null; line = reader.readLine()) {
        sum1 += firstCompute(line.replace(" ", ""));
        sum2 += secondCompute(line.replace(" ", ""));
      }
      System.out.println(sum1);
      System.out.println(sum2);
    }
  }

  private long firstCompute(String s) {
    Deque<Long> nums = new ArrayDeque<>();
    Deque<Character> ops = new ArrayDeque<>();
    for (char ch : s.toCharArray()) {
      if ('0' <= ch && ch <= '9') {
        addNum(nums, ops, ch - '0' + 0L);
      } else if (ch == ')') {
        long lastNum = nums.removeFirst();
        ops.removeFirst(); // (
        addNum(nums, ops, lastNum);
      } else {
        ops.addFirst(ch);
      }
    }
    System.out.println(nums);
    return nums.peekFirst();
  }

  private void addNum(Deque<Long> nums, Deque<Character> ops, Long num) {
    Character lastOp = ops.peekFirst();
    if (lastOp == null || lastOp.equals('(')) {
      nums.addFirst(num);
    }
    else {
      lastOp = ops.removeFirst();
      long lastNum = nums.removeFirst();
      if (lastOp == '*') {
        nums.addFirst(lastNum * num);
      } else if (lastOp == '+') {
        nums.addFirst(lastNum + num);
      } else {
        nums.addFirst(lastNum - num);
      }
    }
  }

  private long secondCompute(String s) {
    Deque<Long> nums = new ArrayDeque<>();
    Deque<Character> ops = new ArrayDeque<>();
    for (char ch : s.toCharArray()) {
      if ('0' <= ch && ch <= '9') {
        addNum2(nums, ops, ch - '0' + 0L);
      } else if (ch == ')') {
        long lastNum = nums.removeFirst();
        while (ops.peekFirst().equals('*')) {
          ops.removeFirst();
          lastNum = nums.removeFirst() * lastNum;
        }
        ops.removeFirst(); // (
        addNum2(nums, ops, lastNum);
      } else {
        ops.addFirst(ch);
      }
    }
    // handling *
    long lastNum = nums.removeFirst();
    while (ops.size() > 0) {
      ops.removeFirst();
      lastNum = nums.removeFirst() * lastNum;
    }
    System.out.println(nums);
    return lastNum;
  }

  private void addNum2(Deque<Long> nums, Deque<Character> ops, Long num) {
    Character lastOp = ops.peekFirst();
    if (lastOp == null || !lastOp.equals('+')) {
      nums.addFirst(num);
    }
    else {
      ops.removeFirst();
      long lastNum = nums.removeFirst();
      nums.addFirst(lastNum + num);
    }
  }
}