package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/8
 */
public class Day8 {

  private static final String inputFilename = "day8.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day8().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line;
      List<Command> commands = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        String op = line.substring(0, line.indexOf(" "));
        int val = Integer.parseInt(line.substring(line.indexOf(" ") + 1));
        commands.add(new Command(op, val));
      }
      System.out.println(firstPart(commands).acc);
      System.out.println(secondPart(commands).acc);
    }
  }

  private ProgramState firstPart(List<Command> commands) {
    int acc = 0;
    boolean[] v = new boolean[commands.size()];
    int i = 0;
    for (; i < commands.size(); i++) {
      if (v[i]) {
        break;
      }
      v[i] = true;
      Command command = commands.get(i);
      switch (command.op) {
        case "acc":
          acc += command.val;
          break;
        case "jmp":
          i = i + command.val - 1;
          break;
        case "nop":
        default:
          break;
      }
    }
    return new ProgramState(i, acc);
  }

  private ProgramState secondPart(List<Command> commands) {
    for (Command command : commands) {
      String s = command.op;
      switch (s) {
        case "nop":
          command.op = "jmp";
          break;
        case "jmp":
          command.op = "nop";
          break;
        default:
          continue;
      }
      ProgramState exitState = firstPart(commands);
      if (exitState.line == commands.size()) {
        return exitState;
      }
      command.op = s;
    }
    return null;
  }

  public static class Command {
    String op;
    int val;

    public Command(String op, int val) {
      this.op = op;
      this.val = val;
    }
  }

  public static class ProgramState {
    int acc;
    int line;

    public ProgramState(int line, int acc) {
      this.line = line;
      this.acc = acc;
    }
  }
}