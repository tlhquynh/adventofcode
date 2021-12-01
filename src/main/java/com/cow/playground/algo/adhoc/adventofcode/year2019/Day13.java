package com.cow.playground.algo.adhoc.adventofcode.year2019;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://adventofcode.com/2019/day/13
 * 
 * @author quynh
 *
 */
public class Day13 {

	private static final int MAX_MODE_COUNTS = 3;

	public static void main(String[] args) {
		long[] input = { 1, 380, 379, 385, 1008, 2159, 334192, 381, 1005, 381, 12, 99, 109, 2160, 1102, 0, 1, 383, 1101,
				0, 0, 382, 20101, 0, 382, 1, 21002, 383, 1, 2, 21102, 37, 1, 0, 1106, 0, 578, 4, 382, 4, 383, 204, 1,
				1001, 382, 1, 382, 1007, 382, 38, 381, 1005, 381, 22, 1001, 383, 1, 383, 1007, 383, 20, 381, 1005, 381,
				18, 1006, 385, 69, 99, 104, -1, 104, 0, 4, 386, 3, 384, 1007, 384, 0, 381, 1005, 381, 94, 107, 0, 384,
				381, 1005, 381, 108, 1105, 1, 161, 107, 1, 392, 381, 1006, 381, 161, 1101, 0, -1, 384, 1105, 1, 119,
				1007, 392, 36, 381, 1006, 381, 161, 1102, 1, 1, 384, 20102, 1, 392, 1, 21101, 0, 18, 2, 21101, 0, 0, 3,
				21102, 1, 138, 0, 1105, 1, 549, 1, 392, 384, 392, 21001, 392, 0, 1, 21102, 18, 1, 2, 21102, 3, 1, 3,
				21101, 0, 161, 0, 1105, 1, 549, 1102, 0, 1, 384, 20001, 388, 390, 1, 21001, 389, 0, 2, 21102, 180, 1, 0,
				1105, 1, 578, 1206, 1, 213, 1208, 1, 2, 381, 1006, 381, 205, 20001, 388, 390, 1, 20102, 1, 389, 2,
				21102, 1, 205, 0, 1106, 0, 393, 1002, 390, -1, 390, 1102, 1, 1, 384, 21002, 388, 1, 1, 20001, 389, 391,
				2, 21102, 1, 228, 0, 1105, 1, 578, 1206, 1, 261, 1208, 1, 2, 381, 1006, 381, 253, 21001, 388, 0, 1,
				20001, 389, 391, 2, 21102, 253, 1, 0, 1106, 0, 393, 1002, 391, -1, 391, 1101, 0, 1, 384, 1005, 384, 161,
				20001, 388, 390, 1, 20001, 389, 391, 2, 21101, 0, 279, 0, 1106, 0, 578, 1206, 1, 316, 1208, 1, 2, 381,
				1006, 381, 304, 20001, 388, 390, 1, 20001, 389, 391, 2, 21101, 304, 0, 0, 1105, 1, 393, 1002, 390, -1,
				390, 1002, 391, -1, 391, 1102, 1, 1, 384, 1005, 384, 161, 20101, 0, 388, 1, 20102, 1, 389, 2, 21102, 0,
				1, 3, 21101, 0, 338, 0, 1106, 0, 549, 1, 388, 390, 388, 1, 389, 391, 389, 21001, 388, 0, 1, 20101, 0,
				389, 2, 21101, 4, 0, 3, 21101, 0, 365, 0, 1105, 1, 549, 1007, 389, 19, 381, 1005, 381, 75, 104, -1, 104,
				0, 104, 0, 99, 0, 1, 0, 0, 0, 0, 0, 0, 205, 17, 15, 1, 1, 19, 109, 3, 21201, -2, 0, 1, 21201, -1, 0, 2,
				21102, 1, 0, 3, 21101, 414, 0, 0, 1106, 0, 549, 22102, 1, -2, 1, 22101, 0, -1, 2, 21102, 1, 429, 0,
				1105, 1, 601, 1202, 1, 1, 435, 1, 386, 0, 386, 104, -1, 104, 0, 4, 386, 1001, 387, -1, 387, 1005, 387,
				451, 99, 109, -3, 2106, 0, 0, 109, 8, 22202, -7, -6, -3, 22201, -3, -5, -3, 21202, -4, 64, -2, 2207, -3,
				-2, 381, 1005, 381, 492, 21202, -2, -1, -1, 22201, -3, -1, -3, 2207, -3, -2, 381, 1006, 381, 481, 21202,
				-4, 8, -2, 2207, -3, -2, 381, 1005, 381, 518, 21202, -2, -1, -1, 22201, -3, -1, -3, 2207, -3, -2, 381,
				1006, 381, 507, 2207, -3, -4, 381, 1005, 381, 540, 21202, -4, -1, -1, 22201, -3, -1, -3, 2207, -3, -4,
				381, 1006, 381, 529, 21202, -3, 1, -7, 109, -8, 2105, 1, 0, 109, 4, 1202, -2, 38, 566, 201, -3, 566,
				566, 101, 639, 566, 566, 1201, -1, 0, 0, 204, -3, 204, -2, 204, -1, 109, -4, 2106, 0, 0, 109, 3, 1202,
				-1, 38, 593, 201, -2, 593, 593, 101, 639, 593, 593, 21001, 0, 0, -2, 109, -3, 2106, 0, 0, 109, 3, 22102,
				20, -2, 1, 22201, 1, -1, 1, 21102, 1, 383, 2, 21102, 218, 1, 3, 21102, 1, 760, 4, 21102, 1, 630, 0,
				1105, 1, 456, 21201, 1, 1399, -2, 109, -3, 2106, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
				1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 2, 0, 0, 2, 0, 2, 2,
				0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 2, 0, 0, 2, 2, 2, 2, 0, 0, 0, 2, 0, 0, 2, 0, 0, 1, 1, 0, 0, 2, 2, 2, 0,
				2, 0, 0, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 2, 0, 2, 2, 0, 0, 2, 2, 0, 2, 0, 0, 0, 0, 2, 2, 0, 1, 1, 0, 2, 0,
				2, 2, 2, 0, 2, 0, 0, 0, 2, 2, 2, 0, 2, 2, 2, 0, 2, 2, 0, 0, 2, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 2, 0, 1, 1,
				0, 2, 0, 2, 2, 2, 0, 0, 0, 0, 0, 2, 2, 2, 0, 2, 0, 2, 2, 2, 0, 0, 0, 0, 2, 2, 0, 2, 0, 2, 2, 2, 2, 2, 0,
				0, 1, 1, 0, 2, 2, 0, 2, 2, 0, 0, 2, 0, 2, 0, 0, 0, 0, 2, 2, 2, 0, 2, 2, 0, 2, 2, 2, 0, 2, 0, 0, 2, 0, 0,
				2, 2, 2, 0, 1, 1, 0, 2, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 2, 0, 0, 2, 0, 2, 0, 2, 2, 0, 0, 0, 2, 0, 2, 0, 2,
				2, 0, 2, 2, 0, 0, 0, 1, 1, 0, 2, 2, 2, 0, 0, 2, 0, 0, 0, 0, 2, 2, 2, 2, 2, 2, 0, 2, 2, 0, 0, 0, 0, 2, 2,
				0, 0, 2, 2, 2, 2, 2, 0, 0, 0, 1, 1, 0, 0, 2, 0, 0, 0, 0, 2, 2, 0, 2, 0, 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 2,
				2, 2, 0, 0, 0, 0, 2, 0, 2, 0, 0, 2, 0, 1, 1, 0, 2, 0, 0, 2, 2, 2, 2, 0, 2, 2, 0, 0, 2, 0, 0, 0, 2, 2, 0,
				2, 2, 2, 0, 2, 0, 2, 0, 0, 0, 2, 2, 0, 2, 0, 0, 1, 1, 0, 2, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 2, 2, 2, 0, 0,
				0, 0, 0, 2, 2, 2, 0, 0, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 1, 1, 0, 0, 2, 0, 2, 2, 0, 2, 2, 2, 0, 2, 0, 2,
				2, 2, 0, 0, 0, 2, 0, 0, 2, 2, 0, 2, 0, 2, 2, 2, 0, 0, 0, 2, 2, 0, 1, 1, 0, 2, 2, 2, 0, 0, 0, 0, 2, 2, 0,
				2, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 0, 2, 2, 0, 0, 2, 0, 2, 2, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1,
				1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, 1, 93, 28, 50, 41, 28, 28, 5, 61, 57, 40, 39, 86, 74, 63, 37, 7, 89, 55, 13, 59,
				61, 39, 41, 25, 89, 55, 1, 13, 39, 76, 27, 49, 19, 29, 79, 37, 48, 8, 16, 71, 78, 19, 92, 45, 6, 66, 28,
				76, 40, 88, 77, 71, 92, 17, 44, 68, 62, 29, 48, 11, 98, 6, 88, 71, 96, 77, 87, 30, 90, 3, 58, 73, 29,
				87, 40, 48, 79, 16, 91, 29, 77, 2, 37, 83, 49, 81, 42, 91, 6, 23, 89, 97, 30, 43, 7, 40, 76, 35, 76, 15,
				13, 53, 44, 55, 34, 27, 32, 1, 57, 81, 61, 57, 90, 86, 4, 4, 39, 47, 39, 52, 38, 70, 38, 33, 54, 84, 34,
				63, 61, 76, 93, 5, 38, 38, 47, 75, 38, 94, 38, 81, 98, 89, 19, 54, 32, 42, 82, 30, 50, 25, 5, 71, 8, 34,
				86, 23, 42, 43, 7, 83, 98, 37, 4, 53, 83, 52, 1, 36, 20, 59, 76, 16, 56, 23, 27, 90, 83, 64, 22, 54, 3,
				15, 74, 67, 53, 16, 28, 77, 35, 84, 18, 65, 2, 56, 27, 90, 32, 40, 83, 4, 66, 95, 96, 32, 51, 40, 92,
				27, 62, 2, 53, 78, 31, 46, 89, 84, 23, 31, 75, 2, 79, 76, 73, 55, 24, 17, 97, 43, 49, 80, 1, 75, 92, 22,
				27, 36, 39, 87, 20, 73, 79, 74, 29, 17, 55, 60, 20, 23, 19, 34, 71, 28, 73, 41, 7, 67, 66, 21, 81, 93,
				93, 65, 95, 15, 35, 47, 44, 56, 3, 50, 20, 89, 74, 35, 86, 76, 70, 46, 96, 60, 67, 58, 84, 50, 6, 79,
				51, 91, 97, 40, 13, 3, 48, 91, 77, 14, 84, 94, 42, 33, 46, 88, 92, 73, 73, 40, 55, 37, 63, 26, 93, 8,
				14, 71, 37, 65, 57, 86, 95, 65, 87, 12, 3, 57, 31, 89, 42, 57, 71, 41, 36, 1, 79, 9, 97, 55, 98, 14, 1,
				36, 39, 39, 77, 65, 61, 1, 32, 47, 58, 57, 81, 93, 73, 81, 21, 77, 5, 2, 81, 34, 64, 84, 57, 50, 30, 57,
				27, 42, 18, 19, 76, 43, 17, 21, 16, 26, 5, 93, 66, 61, 5, 72, 26, 53, 48, 80, 54, 85, 55, 56, 80, 91,
				61, 52, 79, 86, 16, 71, 20, 46, 89, 54, 63, 5, 31, 73, 26, 87, 8, 82, 32, 26, 71, 35, 23, 39, 33, 3, 55,
				70, 23, 13, 40, 48, 78, 78, 94, 76, 46, 94, 90, 18, 73, 92, 27, 70, 90, 44, 45, 14, 79, 39, 83, 17, 94,
				23, 98, 42, 7, 2, 90, 9, 95, 4, 89, 54, 89, 69, 5, 57, 5, 75, 52, 56, 94, 41, 53, 56, 74, 69, 84, 92,
				58, 15, 96, 26, 48, 66, 67, 92, 55, 75, 37, 74, 81, 65, 74, 23, 14, 42, 92, 24, 58, 66, 52, 94, 96, 18,
				23, 30, 66, 17, 43, 68, 47, 83, 85, 43, 43, 64, 63, 83, 90, 81, 60, 62, 87, 37, 48, 26, 71, 63, 60, 47,
				60, 59, 40, 49, 28, 32, 54, 63, 94, 3, 21, 6, 64, 36, 77, 1, 86, 11, 57, 79, 97, 4, 55, 7, 49, 31, 60,
				38, 14, 15, 78, 35, 94, 43, 78, 78, 70, 56, 87, 76, 44, 43, 15, 50, 79, 22, 62, 2, 44, 41, 45, 34, 62,
				38, 31, 92, 19, 55, 14, 7, 78, 35, 33, 40, 69, 8, 90, 52, 19, 33, 38, 41, 15, 49, 62, 23, 29, 78, 73,
				76, 87, 82, 18, 62, 30, 53, 85, 37, 18, 20, 86, 86, 92, 46, 96, 69, 88, 48, 71, 69, 43, 90, 88, 46, 7,
				61, 28, 30, 84, 22, 19, 44, 60, 6, 21, 53, 98, 58, 55, 95, 23, 96, 56, 12, 10, 19, 92, 10, 30, 43, 91,
				18, 26, 78, 71, 22, 78, 34, 17, 36, 82, 12, 97, 28, 8, 86, 48, 41, 47, 28, 52, 22, 14, 6, 25, 24, 17,
				76, 25, 5, 70, 29, 92, 36, 80, 64, 40, 12, 74, 96, 74, 33, 59, 59, 32, 96, 26, 24, 93, 64, 6, 48, 49,
				62, 73, 48, 96, 75, 14, 62, 29, 18, 71, 71, 61, 26, 97, 77, 76, 59, 24, 11, 96, 19, 34, 80, 16, 23, 77,
				76, 71, 19, 7, 58, 20, 43, 25, 75, 30, 78, 54, 13, 16, 47, 92, 17, 64, 30, 15, 83, 17, 66, 37, 31, 95,
				95, 334192 };

		Map<Point, Integer> points = new HashMap<>();
		executePart1(Arrays.copyOf(input, input.length), points, new int[1], 99);
		int blockCount = 0;
		for (Integer type : points.values()) {
			if (type == Point.TILE_BLOCK) {
				blockCount++;
			}
		}
		System.out.println("block tile count: " + blockCount);
		System.out.println("============================");
		points = new HashMap<>();
		input[0] = 2; // play for free
		executePart1(Arrays.copyOf(input, input.length), points, new int[1], 99);
	}

	public static long executePart1(long[] memArray, Map<Point, Integer> paintedPoints, int[] currPointer,
			int stopCode) {

		final int SETTING_X = 1;
		final int SETTING_Y = 2;
		final int SETTING_TYPE = 3;
		Map<Integer, Long> mem = new HashMap<>();
		long lastOutput = Long.MIN_VALUE;
		int relativeBase = 0;
		int maxMem = memArray.length;
		Point curr = new Point();
		int step = SETTING_X;
		int inputs = 0;
		Point ball = null;
		Point paddle = null;

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
				doInput(mem, modes, relativeBase, ball.x == paddle.x ? 0 : ball.x < paddle.x ? -1 : 1, (int) value1);
				System.out.println("count input " + (++inputs));
				i += 2;
				break;
			case 4:
				lastOutput = doOutput(mem, modes, relativeBase, value1);
				if (step == SETTING_X) {
					curr.x = (int) lastOutput;
					step = SETTING_Y;
				} else if (step == SETTING_Y) {
					curr.y = (int) lastOutput;
					step = SETTING_TYPE;
				} else {
					paintedPoints.put(curr, (int) lastOutput);
					step = SETTING_X;
					if (lastOutput == Point.TILE_BALL) {
						ball = curr;
					} else if (lastOutput == Point.TILE_HORIZONTAL_PADDLE) {
						paddle = curr;
					}
					curr = new Point();
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
		System.out.println("output=" + res);
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
		static final int TILE_EMPTY = 0;
		static final int TILE_WALL = 1;
		static final int TILE_BLOCK = 2;
		static final int TILE_HORIZONTAL_PADDLE = 3;
		static final int TILE_BALL = 4;

		int y;
		int x;

		Point() {

		}

		Point(int y, int x) {
			this.y = y;
			this.x = x;
			System.out.println("new point (y,x) : " + y + " " + x);
		}
	}
}