package com.cow.playground.algo.adhoc.adventofcode.year2019;

import java.util.Arrays;

/**
 * https://adventofcode.com/2019/day/7
 * 
 * @author quynh
 *
 */
public class Day7 {

	private static final int THRUSTER_COUNT = 5;
	private static final long[] MEM_ARRAY = { 3, 8, 1001, 8, 10, 8, 105, 1, 0, 0, 21, 46, 59, 84, 93, 102, 183, 264,
			345, 426, 99999, 3, 9, 1002, 9, 4, 9, 1001, 9, 3, 9, 102, 2, 9, 9, 1001, 9, 5, 9, 102, 3, 9, 9, 4, 9, 99, 3,
			9, 1002, 9, 3, 9, 101, 4, 9, 9, 4, 9, 99, 3, 9, 1002, 9, 4, 9, 1001, 9, 4, 9, 102, 2, 9, 9, 1001, 9, 2, 9,
			1002, 9, 3, 9, 4, 9, 99, 3, 9, 1001, 9, 5, 9, 4, 9, 99, 3, 9, 1002, 9, 4, 9, 4, 9, 99, 3, 9, 101, 2, 9, 9,
			4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 1002, 9, 2, 9,
			4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4,
			9, 3, 9, 1002, 9, 2, 9, 4, 9, 99, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9,
			4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4,
			9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 99, 3, 9, 1002, 9, 2, 9,
			4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4,
			9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9,
			3, 9, 102, 2, 9, 9, 4, 9, 99, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9,
			3, 9, 1001, 9, 1, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3,
			9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1001, 9, 1, 9, 4, 9, 99, 3, 9, 101, 1, 9, 9, 4, 9,
			3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 102, 2, 9, 9, 4, 9, 3, 9, 1002, 9, 2, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3,
			9, 101, 1, 9, 9, 4, 9, 3, 9, 1001, 9, 2, 9, 4, 9, 3, 9, 101, 2, 9, 9, 4, 9, 3, 9, 101, 1, 9, 9, 4, 9, 3, 9,
			101, 1, 9, 9, 4, 9, 99 };
	private static long best;

	public static void main(String[] args) {
		int[] v = new int[THRUSTER_COUNT];

		best = Long.MIN_VALUE;
		Arrays.fill(v, 0);
		tryAllPart1(0, v, 0);
		System.out.println("BEST=" + best);
		long[][] mems = new long[THRUSTER_COUNT][];
		for (int i = 0; i < THRUSTER_COUNT; i++) {
			mems[i] = Arrays.copyOf(MEM_ARRAY, MEM_ARRAY.length);
		}

		best = Long.MIN_VALUE;
		Arrays.fill(v, 0);
		int[] currPointers = new int[THRUSTER_COUNT];
		tryAllPart2(0, v, 0, mems, currPointers);
		System.out.println("BEST=" + best);
	}

	private static void tryAllPart1(int id, int[] v, long chainInput) {
		if (id == THRUSTER_COUNT) {
			best = Math.max(best, chainInput);
			return;
		}
		for (int i = 0; i < THRUSTER_COUNT; i++) {
			if (v[i] == 0) {
				v[i] = 1;
				long newChainInput = Day5.execute(Arrays.copyOf(MEM_ARRAY, MEM_ARRAY.length),
						new int[] { i, (int) chainInput }, new int[1]);
				tryAllPart1(id + 1, v, newChainInput);
				v[i] = 0;
			}
		}
	}

	private static void tryAllPart2(int _id, int[] v, long chainInput, long[][] _mems, int[] _currPointers) {
		if (_id >= THRUSTER_COUNT) {
			int id = _id % THRUSTER_COUNT;
			System.out.println("_id=" + _id);
			if (chainInput == Integer.MAX_VALUE) {
				return;
			} else {
				if (id == 0) {
					best = Math.max(best, chainInput);
				}
				long[][] mems = new long[THRUSTER_COUNT][];
				for (int j = 0; j < THRUSTER_COUNT; j++) {
					mems[j] = Arrays.copyOf(_mems[j], _mems[j].length);
				}
				int[] currPointer = { _currPointers[id] };
				long newChainInput = Day5.execute(mems[id], new int[] { (int) chainInput }, currPointer, 4);
				int[] currPointers = Arrays.copyOf(_currPointers, THRUSTER_COUNT);
				currPointers[id] = currPointer[0];
				tryAllPart2(_id + 1, v, newChainInput, mems, currPointers);
			}
		}
		for (int i = 0; i < THRUSTER_COUNT; i++) {
			if (v[i] == 0) {
				v[i] = 1;
				long[][] mems = new long[THRUSTER_COUNT][];
				for (int j = 0; j < THRUSTER_COUNT; j++) {
					mems[j] = Arrays.copyOf(_mems[j], _mems[j].length);
				}
				int[] currPointer = { _currPointers[_id] };
				long newChainInput = Day5.execute(mems[_id], new int[] { i + 5, (int) chainInput }, currPointer, 4);
				int[] currPointers = Arrays.copyOf(_currPointers, THRUSTER_COUNT);
				currPointers[_id] = currPointer[0];
				tryAllPart2(_id + 1, v, newChainInput, mems, currPointers);
				v[i] = 0;
			}
		}
	}

}