package com.cow.playground.algo.adhoc.adventofcode.year2019;

/**
 * https://adventofcode.com/2019/day/12
 * 
 * @author quynh
 *
 */
public class Day12 {

	private static final int MOON_COUNT = 4;
	private static final int DIM_COUNT = 3;
	private static final int STEP_COUNT = 1000;

	public static void main(String[] args) {
		int[][] pos = { { -7, -8, 9 }, { -12, -3, -4 }, { 6, -17, -9 }, { 4, -10, -6 } };
		int[][] veloc = new int[MOON_COUNT][DIM_COUNT];
		for (int i = 1; i <= STEP_COUNT; i++) {
			// update velocity
			for (int j = 0; j < MOON_COUNT; j++) {
				for (int k = j + 1; k < MOON_COUNT; k++) {
					for (int v = 0; v < DIM_COUNT; v++) {
						if (pos[j][v] < pos[k][v]) {
							veloc[j][v]++;
							veloc[k][v]--;
						} else if (pos[j][v] > pos[k][v]) {
							veloc[j][v]--;
							veloc[k][v]++;
						}
					}
				}
			}
			// update positions
			for (int j = 0; j < MOON_COUNT; j++) {
				for (int k = 0; k < DIM_COUNT; k++) {
					pos[j][k] += veloc[j][k];
				}
			}
		}
		// compute energy
		int energy = 0;
		for (int i = 0; i < MOON_COUNT; i++) {
			int pot = 0;
			int kin = 0;
			for (int j = 0; j < DIM_COUNT; j++) {
				pot += Math.abs(pos[i][j]);
				kin += Math.abs(veloc[i][j]);
			}
			energy += pot * kin;
		}
		System.out.println(energy);
	}

}