package com.cow.playground.algo.adhoc.adventofcode.year2020;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * https://adventofcode.com/2020/day/12
 */
public class Day12 {

  private static final String inputFilename = "day12.in";
  private static final String inputPath = "adhoc\\src\\main\\java\\com\\cow\\playground\\algo\\adhoc\\adventofcode\\year2020\\" + inputFilename;

  public static void main(String[] args) throws IOException {
    new Day12().solve();
  }

  public void solve() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(inputPath))) {
      String line;
      final List<Action> actions = new ArrayList<>();
      while ((line = reader.readLine()) != null) {
        actions.add(new Action(line.substring(0, 1), Integer.parseInt(line.substring(1))));
      }
      System.out.println(firstPart(actions, new Position(Direction.EAST, 0, 0)));
      System.out.println(secondPart(actions, new Position(Direction.EAST, 0, 0), new Position(Direction.EAST, 10, 1)));
    }
  }

  private long secondPart(List<Action> actions, Position ship, Position waypoint) {
    for (Action action : actions) {
      switch (action.op) {
        case N:
        case E:
        case W:
        case S:
          action.op.transform(waypoint, action.value);
          break;
        case F:
          Op.N.transform(ship, waypoint.y * action.value);
          Op.E.transform(ship, waypoint.x * action.value);
          break;
        case R:
          for (int i = 0; i < (action.value % 360) / 90; i++) {
            long newX = waypoint.y;
            long newY = -waypoint.x;
            waypoint.x = newX;
            waypoint.y = newY;
          }
          break;
        default:
          for (int i = 0; i < (action.value % 360) / 90; i++) {
            long newX = -waypoint.y;
            long newY = waypoint.x;
            waypoint.x = newX;
            waypoint.y = newY;
          }
          break;
      }
    }
    return Math.abs(ship.x) + Math.abs(ship.y);
  }

  private long firstPart(List<Action> actions, Position ship) {
    for (Action action : actions) {
      action.op.transform(ship, action.value);
    }
    return Math.abs(ship.x) + Math.abs(ship.y);
  }

  enum Op {
    N((position, value) -> position.y += value),
    E((position, value) -> position.x += value),
    S((position, value) -> position.y -= value),
    W((position, value) -> position.x -= value),
    L((position, value) -> {
      value = (value % 360) / 90;
      for (; value > 0; value--) {
        switch (position.facing) {
          case EAST:
            position.facing = Direction.NORTH;
            break;
          case WEST:
            position.facing = Direction.SOUTH;
            break;
          case NORTH:
            position.facing = Direction.WEST;
            break;
          default:
            position.facing = Direction.EAST;
        }
      }
    }),
    R((position, value) -> {
      value = (value % 360) / 90;
      for (; value > 0; value--) {
        switch (position.facing) {
          case EAST:
            position.facing = Direction.SOUTH;
            break;
          case WEST:
            position.facing = Direction.NORTH;
            break;
          case NORTH:
            position.facing = Direction.EAST;
            break;
          default:
            position.facing = Direction.WEST;
        }
      }
    }),
    F((position, value) -> {
      position.x += position.facing.dx * value;
      position.y += position.facing.dy * value;
    });

    private OpExecutor executor;

    Op(OpExecutor executor) {
      this.executor = executor;
    }

    public void transform(Position position, long value) {
      executor.transform(position, value);
    }
  }

  @FunctionalInterface
  interface OpExecutor {
    void transform(Position position, long value);
  }

  class Action {
    private final Op op;
    private final long value;

    public Action(String op, long value) {
      this.op = Op.valueOf(op);
      this.value = value;
    }
  }

  enum Direction {
    EAST(1, 0),
    WEST(-1, 0),
    SOUTH(0, -1),
    NORTH(0, 1);
    private final long dy;
    private final long dx;

    Direction(long dx, long dy) {
      this.dx = dx;
      this.dy = dy;
    }
  }

  class Position {
    private Direction facing;
    private long x;
    private long y;

    public Position(Direction facing, long x, long y) {
      this.facing = facing;
      this.x = x;
      this.y = y;
    }
  }
}