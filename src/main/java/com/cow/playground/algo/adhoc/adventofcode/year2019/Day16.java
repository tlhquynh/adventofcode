package com.cow.playground.algo.adhoc.adventofcode.year2019;

/**
 * https://adventofcode.com/2019/day/14
 * 
 * @author quynh
 *
 */
/*
public class Day16 {

	public static void main(String[] args) {
		System.out.println(part1());
		// System.out.println(part2());
	}

	public static String part1() {
		final int phaseCount = 100;
		final int[] pattern = {0, 1, 0, -1};
		String input = "59708072843556858145230522180223745694544745622336045476506437914986923372260274801316091345126141549522285839402701823884690004497674132615520871839943084040979940198142892825326110513041581064388583488930891380942485307732666485384523705852790683809812073738758055115293090635233887206040961042759996972844810891420692117353333665907710709020698487019805669782598004799421226356372885464480818196786256472944761036204897548977647880837284232444863230958576095091824226426501119748518640709592225529707891969295441026284304137606735506294604060549102824977720776272463738349154440565501914642111802044575388635071779775767726626682303495430936326809";
		input = "0" + input; // add an additional '0' to avoid implementation of special treatment
		for (int i = 0; i < phaseCount; i++) {
			String s = "";
			for (int j = 1; j < input.length(); j++) {
				int sum = 0;
				for (int k = 0; k < input.length(); k++) {
					sum += (input.charAt(k) - '0') * (pattern[(k / j) % pattern.length]);
				}
				sum = Math.abs(sum) % 10;
				s += sum;
			}
			input = "0" + s;
			System.out.println(input);
		}
		return input.substring(1, 9);
	}
	
	public static String part2() {
		final int phaseCount = 100;
		final int[] pattern = {0, 1, 0, -1};
		final String input = "59708072843556858145230522180223745694544745622336045476506437914986923372260274801316091345126141549522285839402701823884690004497674132615520871839943084040979940198142892825326110513041581064388583488930891380942485307732666485384523705852790683809812073738758055115293090635233887206040961042759996972844810891420692117353333665907710709020698487019805669782598004799421226356372885464480818196786256472944761036204897548977647880837284232444863230958576095091824226426501119748518640709592225529707891969295441026284304137606735506294604060549102824977720776272463738349154440565501914642111802044575388635071779775767726626682303495430936326809";
		final int baseLength = input.length();
		final int rep = 10_000;
		// the result = B x B x ... x B x A
		// create vector array A
		int[] a = new int[baseLength * rep];
		for (int i = 0; i < baseLength; i++) {
			a[i] = input.charAt(i) - '0';
		}
		for (int i = 1; i < rep; i++) {
			System.arraycopy(a, 0, a, baseLength * i, baseLength);
		}
		// only care about 8 numbers in array B from the location specified in the first 7 digit of input
		int loc = Integer.parseInt(input.substring(0, 7));
		int eightDigits = Integer.parseInt(input.substring(loc % baseLength, loc % baseLength + 8));
		int[][] b = new int[8][baseLength * rep];
		for (int i = loc; i < loc+8; i++) {
			for (int k = 0; k < baseLength * rep; k++) {
				b[i-loc][k] = pattern[(k / (i + 1)) % pattern.length];
			}
		}
		int[] res = new int[8];
		int[][] newB = new int[8][baseLength * rep];
		for (int i = 1; i < phaseCount; i++) {
			for (int j = 0; j < 8; j++) {
				for (int u = 0; u < baseLength * rep; u++) {
					for (int v = 0; v < baseLength * rep; v++) {
						newB[j][u] += b[j][v] * (v,u)
					}
				}
				res[j] = Math.abs(res[j]) % 10;
			}
		}
	}
}

 */