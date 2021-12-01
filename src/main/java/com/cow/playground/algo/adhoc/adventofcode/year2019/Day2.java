package com.cow.playground.algo.adhoc.adventofcode.year2019;

import java.util.Arrays;

/**
 * https://adventofcode.com/2019/day/2
 * @author quynh
 *
 */
public class Day2 {
	public static int[] execute(int[] _input, int nounLo, int nounHi, int verbLo, int verbHi) {
		int[] input = null;
		for (int noun = nounLo; noun <= nounHi; noun++) {
			for (int verb = verbLo; verb <= verbHi; verb++) {
				input = Arrays.copyOf(_input, _input.length);
				input[1] = noun;
				input[2] = verb;
				for (int i = 0; i < input.length; i += 4) {
					if (input[i] == 1) {
						input[input[i + 3]] = input[input[i + 1]] + input[input[i + 2]];
					} else if (input[i] == 2) {
						input[input[i + 3]] = input[input[i + 1]] * input[input[i + 2]];
					} else if (input[i] == 99) {
						break;
					}
				}
				if (input[0] == 19690720) {
					return input;
				}
			}
		}
		return input;
	}

	public static void main(String[] args) {
		int[] input = { 1, 0, 0, 3, 1, 1, 2, 3, 1, 3, 4, 3, 1, 5, 0, 3, 2, 13, 1, 19, 1, 19, 10, 23, 1, 23, 13, 27, 1,
				6, 27, 31, 1, 9, 31, 35, 2, 10, 35, 39, 1, 39, 6, 43, 1, 6, 43, 47, 2, 13, 47, 51, 1, 51, 6, 55, 2, 6,
				55, 59, 2, 59, 6, 63, 2, 63, 13, 67, 1, 5, 67, 71, 2, 9, 71, 75, 1, 5, 75, 79, 1, 5, 79, 83, 1, 83, 6,
				87, 1, 87, 6, 91, 1, 91, 5, 95, 2, 10, 95, 99, 1, 5, 99, 103, 1, 10, 103, 107, 1, 107, 9, 111, 2, 111,
				10, 115, 1, 115, 9, 119, 1, 13, 119, 123, 1, 123, 9, 127, 1, 5, 127, 131, 2, 13, 131, 135, 1, 9, 135,
				139, 1, 2, 139, 143, 1, 13, 143, 0, 99, 2, 0, 14, 0 };

		int[] output1 = execute(Arrays.copyOf(input, input.length), 12, 12, 2, 2);
		System.out.println(output1[0]);

		int[] output2 = execute(Arrays.copyOf(input, input.length), 0, 99, 0, 99);
		if (output2[0] == 19690720) {
			System.out.println(output2[1] * 100 + output2[2]);
		}
	}
}