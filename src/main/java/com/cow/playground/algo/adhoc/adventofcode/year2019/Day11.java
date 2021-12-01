package com.cow.playground.algo.adhoc.adventofcode.year2019;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://adventofcode.com/2019/day/11
 * 
 * @author quynh
 *
 */
public class Day11 {

	private static final int MAX_MODE_COUNTS = 3;

	public static void main(String[] args) {
		long[] input = { 3, 8, 1005, 8, 310, 1106, 0, 11, 0, 0, 0, 104, 1, 104, 0, 3, 8, 1002, 8, -1, 10, 101, 1, 10,
				10, 4, 10, 1008, 8, 0, 10, 4, 10, 1001, 8, 0, 29, 1, 2, 11, 10, 1, 1101, 2, 10, 2, 1008, 18, 10, 2, 106,
				3, 10, 3, 8, 1002, 8, -1, 10, 1001, 10, 1, 10, 4, 10, 1008, 8, 1, 10, 4, 10, 102, 1, 8, 67, 2, 105, 15,
				10, 3, 8, 1002, 8, -1, 10, 101, 1, 10, 10, 4, 10, 1008, 8, 0, 10, 4, 10, 1001, 8, 0, 93, 2, 1001, 16,
				10, 3, 8, 102, -1, 8, 10, 1001, 10, 1, 10, 4, 10, 1008, 8, 1, 10, 4, 10, 102, 1, 8, 119, 3, 8, 1002, 8,
				-1, 10, 1001, 10, 1, 10, 4, 10, 1008, 8, 1, 10, 4, 10, 101, 0, 8, 141, 2, 7, 17, 10, 1, 1103, 16, 10, 3,
				8, 1002, 8, -1, 10, 101, 1, 10, 10, 4, 10, 108, 0, 8, 10, 4, 10, 102, 1, 8, 170, 3, 8, 1002, 8, -1, 10,
				1001, 10, 1, 10, 4, 10, 1008, 8, 1, 10, 4, 10, 1002, 8, 1, 193, 1, 7, 15, 10, 2, 105, 13, 10, 1006, 0,
				92, 1006, 0, 99, 3, 8, 1002, 8, -1, 10, 101, 1, 10, 10, 4, 10, 108, 1, 8, 10, 4, 10, 101, 0, 8, 228, 1,
				3, 11, 10, 1006, 0, 14, 1006, 0, 71, 3, 8, 1002, 8, -1, 10, 101, 1, 10, 10, 4, 10, 1008, 8, 0, 10, 4,
				10, 101, 0, 8, 261, 2, 2, 2, 10, 1006, 0, 4, 3, 8, 102, -1, 8, 10, 101, 1, 10, 10, 4, 10, 108, 0, 8, 10,
				4, 10, 101, 0, 8, 289, 101, 1, 9, 9, 1007, 9, 1049, 10, 1005, 10, 15, 99, 109, 632, 104, 0, 104, 1,
				21101, 0, 387240009756L, 1, 21101, 327, 0, 0, 1105, 1, 431, 21101, 0, 387239486208L, 1, 21102, 1, 338,
				0, 1106, 0, 431, 3, 10, 104, 0, 104, 1, 3, 10, 104, 0, 104, 0, 3, 10, 104, 0, 104, 1, 3, 10, 104, 0,
				104, 1, 3, 10, 104, 0, 104, 0, 3, 10, 104, 0, 104, 1, 21102, 3224472579L, 1, 1, 21101, 0, 385, 0, 1106,
				0, 431, 21101, 0, 206253952003L, 1, 21102, 396, 1, 0, 1105, 1, 431, 3, 10, 104, 0, 104, 0, 3, 10, 104,
				0, 104, 0, 21102, 709052072296L, 1, 1, 21102, 419, 1, 0, 1105, 1, 431, 21102, 1, 709051962212L, 1,
				21102, 430, 1, 0, 1106, 0, 431, 99, 109, 2, 21202, -1, 1, 1, 21102, 1, 40, 2, 21102, 462, 1, 3, 21102,
				452, 1, 0, 1105, 1, 495, 109, -2, 2105, 1, 0, 0, 1, 0, 0, 1, 109, 2, 3, 10, 204, -1, 1001, 457, 458,
				473, 4, 0, 1001, 457, 1, 457, 108, 4, 457, 10, 1006, 10, 489, 1101, 0, 0, 457, 109, -2, 2105, 1, 0, 0,
				109, 4, 2102, 1, -1, 494, 1207, -3, 0, 10, 1006, 10, 512, 21101, 0, 0, -3, 22101, 0, -3, 1, 21202, -2,
				1, 2, 21102, 1, 1, 3, 21101, 531, 0, 0, 1105, 1, 536, 109, -4, 2106, 0, 0, 109, 5, 1207, -3, 1, 10,
				1006, 10, 559, 2207, -4, -2, 10, 1006, 10, 559, 21202, -4, 1, -4, 1105, 1, 627, 22102, 1, -4, 1, 21201,
				-3, -1, 2, 21202, -2, 2, 3, 21102, 1, 578, 0, 1105, 1, 536, 21202, 1, 1, -4, 21102, 1, 1, -1, 2207, -4,
				-2, 10, 1006, 10, 597, 21101, 0, 0, -1, 22202, -2, -1, -2, 2107, 0, -3, 10, 1006, 10, 619, 21201, -1, 0,
				1, 21102, 1, 619, 0, 106, 0, 494, 21202, -2, -1, -2, 22201, -4, -2, -4, 109, -5, 2106, 0, 0 };
		Map<Point, Integer> paintedPoints = new HashMap<>();
		executePart1(Arrays.copyOf(input, input.length), paintedPoints, new int[1], 99);
		System.out.println("painted points: " + paintedPoints.size());
		System.out.println("============================");
		paintedPoints = new HashMap<>();
		executePart2(Arrays.copyOf(input, input.length), paintedPoints, new int[1], 99);
		System.out.println("painted points: " + paintedPoints.size());
		int maxY = Integer.MIN_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int minX = Integer.MAX_VALUE;
		for (Point p : paintedPoints.keySet()) {
			maxY = Math.max(maxY, p.y);
			maxX = Math.max(maxX, p.x);
			minY = Math.min(minY, p.y);
			minX = Math.min(minX, p.x);
		}
		int[][] a = new int[maxY - minY + 1][maxX - minX + 1];
		for (Map.Entry<Point, Integer> entry : paintedPoints.entrySet()) {
			a[entry.getKey().y - minY][entry.getKey().x - minX] = entry.getValue();
		}
		OutputStream os = null;
		try {
			os = new FileOutputStream(new File("day11.txt"));
			for (int i = 0; i < a.length; i++) {
				String s = "";
				for (int j = 0; j < a[i].length; j++) {
					if (a[i][j] == 0) {
						s += ".";
					} else {
						s += "+";
					}
				}
				s += "\n";
				os.write(s.getBytes(), 0, s.length());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static long executePart2(long[] memArray, Map<Point, Integer> paintedPoints, int[] currPointer,
			int stopCode) {

		final int WILL_PAINT = 1;
		final int WILL_TURN = 2;
		Map<Integer, Long> mem = new HashMap<>();
		long lastOutput = Long.MIN_VALUE;
		int relativeBase = 0;
		int maxMem = memArray.length;
		Point curr = new Point();
		int step = WILL_PAINT;
		int color = Point.WHITE; // first panel is white but the rest is black

		for (int i = 0; i < memArray.length; i++) {
			mem.put(i, (long) memArray[i]);
		}

		for (int i = currPointer[0]; i < maxMem;) {
			int opcode = getOpcode(mem.get(i).intValue());
			int[] modes = getModes(mem.get(i).intValue());
			long value1 = mem.containsKey(i + 1) ? mem.get(i + 1) : 0;
			long value2 = mem.containsKey(i + 2) ? mem.get(i + 2) : 0;
			long value3 = mem.containsKey(i + 3) ? mem.get(i + 3) : 0;
			switch (opcode) {
			case 1:
				// addition
				doAddition(mem, modes, relativeBase, value1, value2, (int) value3);
				i += 4;
				break;
			case 2:
				// multiplication
				doMultiplication(mem, modes, relativeBase, value1, value2, (int) value3);
				i += 4;
				break;
			case 3:
				Integer input = paintedPoints.get(curr);
				if (paintedPoints.get(curr) == null) {
					doInput(mem, modes, relativeBase, color, (int) value1);
					color = Point.BLACK;
				} else {
					doInput(mem, modes, relativeBase, input, (int) value1);
				}
				i += 2;
				break;
			case 4:
				System.out.println("i=" + i);
				lastOutput = doOutput(mem, modes, relativeBase, value1);
				if (step == WILL_PAINT) {
					paintedPoints.put(curr, (int) lastOutput);
					step = WILL_TURN;
				} else {
					if (lastOutput == 0) {
						curr = curr.moveToPoint(Point.LEFT);
					} else {
						curr = curr.moveToPoint(Point.RIGHT);
					}
					step = WILL_PAINT;
				}
				i += 2;
				if (stopCode == 4) {
					currPointer[0] = i;
					return lastOutput;
				}
				break;
			case 5:
				if (doJumpTrue(mem, modes, relativeBase, value1)) {
					i = (int) getValue(mem, modes[1], relativeBase, value2);
				} else {
					i += 3;
				}
				break;
			case 6:
				if (doJumpFalse(mem, modes, relativeBase, value1)) {
					i = (int) getValue(mem, modes[1], relativeBase, value2);
				} else {
					i += 3;
				}
				break;
			case 7:
				doLessThan(mem, modes, relativeBase, value1, value2, (int) value3);
				i += 4;
				break;
			case 8:
				doEqual(mem, modes, relativeBase, value1, value2, (int) value3);
				i += 4;
				break;
			case 9:
				relativeBase = doRelativeBase(mem, modes, relativeBase, value1);
				i += 2;
				break;
			case 99:
				i += 1;
				if (stopCode == 99) {
					currPointer[0] = i;
					return lastOutput;
				}
			}
		}
		return Integer.MAX_VALUE;
	}

	public static long executePart1(long[] memArray, Map<Point, Integer> paintedPoints, int[] currPointer,
			int stopCode) {

		final int WILL_PAINT = 1;
		final int WILL_TURN = 2;
		Map<Integer, Long> mem = new HashMap<>();
		long lastOutput = Long.MIN_VALUE;
		int relativeBase = 0;
		int maxMem = memArray.length;
		Point curr = new Point();
		int step = WILL_PAINT;
		int color = Point.BLACK;

		for (int i = 0; i < memArray.length; i++) {
			mem.put(i, (long) memArray[i]);
		}

		for (int i = currPointer[0]; i < maxMem;) {
			int opcode = getOpcode(mem.get(i).intValue());
			int[] modes = getModes(mem.get(i).intValue());
			long value1 = mem.containsKey(i + 1) ? mem.get(i + 1) : 0;
			long value2 = mem.containsKey(i + 2) ? mem.get(i + 2) : 0;
			long value3 = mem.containsKey(i + 3) ? mem.get(i + 3) : 0;
			switch (opcode) {
			case 1:
				// addition
				doAddition(mem, modes, relativeBase, value1, value2, (int) value3);
				i += 4;
				break;
			case 2:
				// multiplication
				doMultiplication(mem, modes, relativeBase, value1, value2, (int) value3);
				i += 4;
				break;
			case 3:
				Integer input = paintedPoints.get(curr);
				if (paintedPoints.get(curr) == null) {
					doInput(mem, modes, relativeBase, color, (int) value1);
				} else {
					doInput(mem, modes, relativeBase, input, (int) value1);
				}
				i += 2;
				break;
			case 4:
				System.out.println("i=" + i);
				lastOutput = doOutput(mem, modes, relativeBase, value1);
				if (step == WILL_PAINT) {
					paintedPoints.put(curr, (int) lastOutput);
					step = WILL_TURN;
				} else {
					if (lastOutput == 0) {
						curr = curr.moveToPoint(Point.LEFT);
					} else {
						curr = curr.moveToPoint(Point.RIGHT);
					}
					step = WILL_PAINT;
				}
				i += 2;
				if (stopCode == 4) {
					currPointer[0] = i;
					return lastOutput;
				}
				break;
			case 5:
				if (doJumpTrue(mem, modes, relativeBase, value1)) {
					i = (int) getValue(mem, modes[1], relativeBase, value2);
				} else {
					i += 3;
				}
				break;
			case 6:
				if (doJumpFalse(mem, modes, relativeBase, value1)) {
					i = (int) getValue(mem, modes[1], relativeBase, value2);
				} else {
					i += 3;
				}
				break;
			case 7:
				doLessThan(mem, modes, relativeBase, value1, value2, (int) value3);
				i += 4;
				break;
			case 8:
				doEqual(mem, modes, relativeBase, value1, value2, (int) value3);
				i += 4;
				break;
			case 9:
				relativeBase = doRelativeBase(mem, modes, relativeBase, value1);
				i += 2;
				break;
			case 99:
				i += 1;
				if (stopCode == 99) {
					currPointer[0] = i;
					return lastOutput;
				}
			}
		}
		return Integer.MAX_VALUE;
	}

	private static int doRelativeBase(Map<Integer, Long> mem, int[] modes, int relativeBase, long input) {
		return (int) (relativeBase + getValue(mem, modes[0], relativeBase, input));
	}

	private static boolean doJumpTrue(Map<Integer, Long> mem, int[] modes, int relativeBase, long input) {
		return getValue(mem, modes[0], relativeBase, input) != 0;
	}

	private static boolean doJumpFalse(Map<Integer, Long> mem, int[] modes, int relativeBase, long input) {
		return getValue(mem, modes[0], relativeBase, input) == 0;
	}

	private static void doLessThan(Map<Integer, Long> mem, int[] modes, int relativeBase, long input1, long input2,
			int outputAddr) {
		setValue(mem, modes[2], relativeBase,
				getValue(mem, modes[0], relativeBase, input1) < getValue(mem, modes[1], relativeBase, input2) ? 1L : 0L,
				outputAddr);
	}

	private static void doEqual(Map<Integer, Long> mem, int[] modes, int relativeBase, long input1, long input2,
			int outputAddr) {
		setValue(mem, modes[2], relativeBase,
				getValue(mem, modes[0], relativeBase, input1) == getValue(mem, modes[1], relativeBase, input2) ? 1L
						: 0L,
				outputAddr);
	}

	private static void doAddition(Map<Integer, Long> mem, int[] modes, int relativeBase, long input1, long input2,
			int outputAddr) {
		setValue(mem, modes[2], relativeBase,
				getValue(mem, modes[0], relativeBase, input1) + getValue(mem, modes[1], relativeBase, input2),
				outputAddr);
	}

	private static void doMultiplication(Map<Integer, Long> mem, int[] modes, int relativeBase, long input1,
			long input2, int outputAddr) {

		setValue(mem, modes[2], relativeBase,
				getValue(mem, modes[0], relativeBase, input1) * getValue(mem, modes[1], relativeBase, input2),
				outputAddr);
	}

	private static void setValue(Map<Integer, Long> mem, int mode, int relativeBase, long input, int outputAddress) {
		switch (mode) {
		case 0:
			mem.put(outputAddress, input);
			return;
		case 2:
			mem.put(outputAddress + relativeBase, input);
			return;
		}
		return;
	}

	private static void doInput(Map<Integer, Long> mem, int[] modes, int relativeBase, long input, int outputAddress) {
		setValue(mem, modes[0], relativeBase, input, outputAddress);
	}

	private static long doOutput(Map<Integer, Long> mem, int[] modes, int relativeBase, long input) {
		long res = getValue(mem, modes[0], relativeBase, input);
		System.out.println(res);
		return res;
	}

	private static long getValue(Map<Integer, Long> mem, int mode, int relativeBase, long input) {
		switch (mode) {
		case 0:
			if (!mem.containsKey((int) input)) {
				mem.put((int) input, 0L);
			}
			return mem.get((int) input);
		case 1:
			return input;
		case 2:
			if (!mem.containsKey((int) (relativeBase + input))) {
				mem.put((int) (relativeBase + input), 0L);
			}
			return mem.get((int) (relativeBase + input));
		}
		return -1;
	}

	private static int getOpcode(int x) {
		return x % 100;
	}

	private static int[] getModes(int x) {
		x = x / 100;
		int[] modes = new int[MAX_MODE_COUNTS];
		for (int i = 0; i < MAX_MODE_COUNTS; i++) {
			modes[i] = x % 10;
			x /= 10;
		}
		return modes;
	}

	static class Point {
		static final int UP = 0;
		static final int DOWN = 1;
		static final int LEFT = 2;
		static final int RIGHT = 3;
		static final int BLACK = 0;
		static final int WHITE = 1;

		int y;
		int x;
		int dir;

		Point() {

		}

		Point(int y, int x, int dir) {
			this.y = y;
			this.x = x;
			this.dir = dir;
			System.out.println("new point (y,x,dir) : " + y + " " + x + " " + dir);
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Point)) {
				return false;
			}
			Point p = (Point) obj;
			return x == p.x && y == p.y;
		}

		@Override
		public int hashCode() {
			int res = 17;
			res = 31 * res + x;
			res = 31 * res + y;
			return res;
		}

		Point moveToPoint(int turnTo) {
			if (turnTo == LEFT) {
				switch (dir) {
				case UP:
					return new Point(y, x - 1, LEFT);
				case DOWN:
					return new Point(y, x + 1, RIGHT);
				case LEFT:
					return new Point(y + 1, x, DOWN);
				case RIGHT:
					return new Point(y - 1, x, UP);
				}
			} else if (turnTo == RIGHT) {
				switch (dir) {
				case UP:
					return new Point(y, x + 1, RIGHT);
				case DOWN:
					return new Point(y, x - 1, LEFT);
				case LEFT:
					return new Point(y - 1, x, UP);
				case RIGHT:
					return new Point(y + 1, x, DOWN);
				}
			}
			return null;
		}
	}
}