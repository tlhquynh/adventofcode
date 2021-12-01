package com.cow.playground.algo.adhoc.adventofcode.year2019;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * https://adventofcode.com/2019/day/5
 * 
 * @author quynh
 *
 */
public class Day5 {

	private static final int MAX_MODE_COUNTS = 3;

	public static void main(String[] args) {
		long[] input = { 3, 225, 1, 225, 6, 6, 1100, 1, 238, 225, 104, 0, 1101, 11, 91, 225, 1002, 121, 77, 224, 101,
				-6314, 224, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 3, 224, 1, 223, 224, 223, 1102, 74, 62, 225,
				1102, 82, 7, 224, 1001, 224, -574, 224, 4, 224, 102, 8, 223, 223, 1001, 224, 3, 224, 1, 224, 223, 223,
				1101, 28, 67, 225, 1102, 42, 15, 225, 2, 196, 96, 224, 101, -4446, 224, 224, 4, 224, 102, 8, 223, 223,
				101, 6, 224, 224, 1, 223, 224, 223, 1101, 86, 57, 225, 1, 148, 69, 224, 1001, 224, -77, 224, 4, 224,
				102, 8, 223, 223, 1001, 224, 2, 224, 1, 223, 224, 223, 1101, 82, 83, 225, 101, 87, 14, 224, 1001, 224,
				-178, 224, 4, 224, 1002, 223, 8, 223, 101, 7, 224, 224, 1, 223, 224, 223, 1101, 38, 35, 225, 102, 31,
				65, 224, 1001, 224, -868, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 5, 224, 1, 223, 224, 223, 1101, 57,
				27, 224, 1001, 224, -84, 224, 4, 224, 102, 8, 223, 223, 1001, 224, 7, 224, 1, 223, 224, 223, 1101, 61,
				78, 225, 1001, 40, 27, 224, 101, -89, 224, 224, 4, 224, 1002, 223, 8, 223, 1001, 224, 1, 224, 1, 224,
				223, 223, 4, 223, 99, 0, 0, 0, 677, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1105, 0, 99999, 1105, 227, 247,
				1105, 1, 99999, 1005, 227, 99999, 1005, 0, 256, 1105, 1, 99999, 1106, 227, 99999, 1106, 0, 265, 1105, 1,
				99999, 1006, 0, 99999, 1006, 227, 274, 1105, 1, 99999, 1105, 1, 280, 1105, 1, 99999, 1, 225, 225, 225,
				1101, 294, 0, 0, 105, 1, 0, 1105, 1, 99999, 1106, 0, 300, 1105, 1, 99999, 1, 225, 225, 225, 1101, 314,
				0, 0, 106, 0, 0, 1105, 1, 99999, 1008, 677, 226, 224, 1002, 223, 2, 223, 1006, 224, 329, 101, 1, 223,
				223, 8, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 344, 101, 1, 223, 223, 1107, 226, 677, 224, 102, 2,
				223, 223, 1006, 224, 359, 101, 1, 223, 223, 1007, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 374, 101,
				1, 223, 223, 7, 677, 677, 224, 102, 2, 223, 223, 1005, 224, 389, 1001, 223, 1, 223, 108, 677, 677, 224,
				1002, 223, 2, 223, 1005, 224, 404, 101, 1, 223, 223, 1008, 226, 226, 224, 102, 2, 223, 223, 1005, 224,
				419, 1001, 223, 1, 223, 1107, 677, 226, 224, 102, 2, 223, 223, 1005, 224, 434, 1001, 223, 1, 223, 1108,
				677, 677, 224, 102, 2, 223, 223, 1006, 224, 449, 1001, 223, 1, 223, 7, 226, 677, 224, 102, 2, 223, 223,
				1005, 224, 464, 101, 1, 223, 223, 1008, 677, 677, 224, 102, 2, 223, 223, 1005, 224, 479, 101, 1, 223,
				223, 1007, 226, 677, 224, 1002, 223, 2, 223, 1006, 224, 494, 101, 1, 223, 223, 8, 677, 226, 224, 1002,
				223, 2, 223, 1005, 224, 509, 101, 1, 223, 223, 1007, 677, 677, 224, 1002, 223, 2, 223, 1006, 224, 524,
				101, 1, 223, 223, 107, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 539, 101, 1, 223, 223, 107, 226, 677,
				224, 102, 2, 223, 223, 1005, 224, 554, 1001, 223, 1, 223, 7, 677, 226, 224, 102, 2, 223, 223, 1006, 224,
				569, 1001, 223, 1, 223, 107, 677, 677, 224, 1002, 223, 2, 223, 1005, 224, 584, 101, 1, 223, 223, 1107,
				677, 677, 224, 102, 2, 223, 223, 1005, 224, 599, 101, 1, 223, 223, 1108, 226, 677, 224, 102, 2, 223,
				223, 1006, 224, 614, 101, 1, 223, 223, 8, 226, 226, 224, 102, 2, 223, 223, 1006, 224, 629, 101, 1, 223,
				223, 108, 226, 677, 224, 102, 2, 223, 223, 1005, 224, 644, 1001, 223, 1, 223, 108, 226, 226, 224, 102,
				2, 223, 223, 1005, 224, 659, 101, 1, 223, 223, 1108, 677, 226, 224, 102, 2, 223, 223, 1006, 224, 674,
				1001, 223, 1, 223, 4, 223, 99, 226 };
		execute(Arrays.copyOf(input, input.length), new int[] { 2 }, new int[1]);
		System.out.println("============================");
		execute(Arrays.copyOf(input, input.length), new int[] { 5 }, new int[1]);
	}

	public static long execute(long[] memArray, int[] input, int[] currPointer) {
		return execute(memArray, input, currPointer, 99);
	}

	public static long execute(long[] memArray, int[] input, int[] currPointer, int stopCode) {

		Map<Integer, Long> mem = new HashMap<>();
		long lastOutput = Long.MIN_VALUE;
		int inputIndex = 0;
		int relativeBase = 0;
		int maxMem = memArray.length;

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
				doInput(mem, modes, relativeBase, input[inputIndex++], (int) value1);
				i += 2;
				break;
			case 4:
				System.out.println("i=" + i);
				lastOutput = doOutput(mem, modes, relativeBase, value1);
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
}