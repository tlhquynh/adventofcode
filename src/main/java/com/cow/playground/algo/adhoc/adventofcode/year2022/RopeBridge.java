package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.*;

public class RopeBridge {
    private static final Logger LOGGER = LoggerFactory.getLogger(RopeBridge.class);
    private static final String INPUT_FILENAME = "day9.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private static List<Move> moves;

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            moves = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(" ");
                moves.add(new Move(Direction.get(tokens[0].charAt(0)),
                        Integer.parseInt(tokens[1])));
            }
        }
    }

    public RopeBridge() throws IOException {
        readInputs();
    }

    public int pullRope(int ropeLEngth) {
        Set<Position> visit = new HashSet<>();
        Position[] rope = new Position[ropeLEngth];
        for (int i = 0; i < ropeLEngth; i++) {
            rope[i] = new Position(0, 0);
        }
        visit.add(rope[rope.length - 1]);
        for (Move move : moves) {
            for (int i = 0; i < move.steps; i++) {
                rope[0] = rope[0].move(move.direction);
                for (int j = 1; j < ropeLEngth; j++) {
                    rope[j] = rope[j].follow(rope[j - 1]);
                }
                visit.add(rope[rope.length - 1]);
            }
        }
        return visit.size();
    }

    public void task1() {
        int res = pullRope(2);
        assert res == 6090;
        LOGGER.info("2nd knot visited {} places", res);
    }

    public void task2() {
        int res = pullRope(10);
        assert res == 2566;
        LOGGER.info("10th knot visited {} places", res);
    }

    static class Position {
        int x, y;

        Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isTouching(Position other) {
            return Math.abs(x - other.x) <= 1 && Math.abs(y - other.y) <= 1;
        }

        public Position follow(Position other) {
            Position next = new Position(x, y);

            if (!isTouching(other)) {
                if (next.x != other.x) {
                    next.x -= (next.x - other.x) / Math.abs(next.x - other.x);
                }
                if (next.y != other.y) {
                    next.y -= (next.y - other.y) / Math.abs(next.y - other.y);
                }
                LOGGER.debug("Follow to ({}, {})", next.x, next.y);
            }
            return next;
        }

        public Position move(Direction direction) {
            return new Position(x + direction.dx, y + direction.dy);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return "Point(" + x + ", " + y + ")";
        }
    }

    enum Direction {
        LEFT('L', 0, -1),
        DOWN('D', -1, 0),
        UP('U', +1, 0),
        RIGHT('R', 0, +1);
        final int dy, dx;
        final char direction;

        Direction(char direction, int dy, int dx) {
            this.direction = direction;
            this.dy = dy;
            this.dx = dx;
        }

        public static Direction get(char direction) {
            for (Direction d : values()) {
                if (d.direction == direction) {
                    return d;
                }
            }
            throw new InvalidParameterException("Invalid direction " + direction);
        }
    }

    static class Move {
        Direction direction;
        int steps;

        Move(Direction direction, int steps) {
            this.direction = direction;
            this.steps = steps;
        }
    }
}