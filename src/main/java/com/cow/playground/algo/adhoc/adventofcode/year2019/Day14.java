package com.cow.playground.algo.adhoc.adventofcode.year2019;

import java.util.HashMap;
import java.util.Map;

/**
 * https://adventofcode.com/2019/day/14
 * 
 * @author quynh
 *
 */
public class Day14 {

	public static void main(String[] args) {
		System.out.println(part1());
		System.out.println(part2());
	}

	private static long part2() {
		final long ALL_ORE = 1_000_000_000_000L;
		long left = ALL_ORE / part1();
		long right = left + left;
		long best = -1;
		long mostFuel = -1;
		while (left <= right) {
			long mid = (left + right) / 2;
			long ore = solve(mid);
			if (ore < ALL_ORE) {
				best = Math.max(best, ore);
				mostFuel = mid;
				left = mid + 1;
			} else if (ore > ALL_ORE) {
				right = mid - 1;
			} else {
				break;
			}
		}
		return mostFuel;
	}

	private static long part1() {
		Map<String, Chemical> chemicals = getInput();
		long ore = solve(chemicals, new HashMap<String, Long>(), "FUEL", 1);
		return ore;
	}

	private static long solve(long fuelAmount) {
		Map<String, Chemical> chemicals = getInput();
		long ore = solve(chemicals, new HashMap<String, Long>(), "FUEL", fuelAmount);
		return ore;
	}

	private static long solve(Map<String, Chemical> chemicals, Map<String, Long> leftovers, String productName,
			long productAmount) {
		if ("ORE".equals(productName)) {
			return productAmount;
		}
		Chemical product = chemicals.get(productName);
		Long leftover = leftovers.get(productName);
		long multiplier = (productAmount - (leftover == null ? 0 : leftover) + product.amount - 1) / product.amount;
		leftovers.put(productName, multiplier * product.amount - (productAmount - (leftover == null ? 0 : leftover)));
		long ore = 0;
		for (int i = 0; i < product.reactants.length; i++) {
			ore += solve(chemicals, leftovers, product.reactants[i].name, product.reactants[i].amount * multiplier);
		}
		return ore;
	}

	private static Map<String, Chemical> getInput() {
		String[] rawEquations = { "8 LHFV => 3 PMVMQ",
				"2 ZXNM, 1 PSVLS, 4 GRDNT, 26 GLZH, 3 VHJX, 16 BGPF, 1 LHVTN => 4 BTQL",
				"10 NKHSG, 20 FCPC, 11 GRDNT => 5 HDJB", "6 WPZN, 1 LHFV => 7 BGPF", "1 WDXT, 1 PLCNZ => 3 QHFKR",
				"12 LCHZ => 1 TPXCK", "11 LSNG => 4 XFGH", "195 ORE => 4 GRNC", "8 XFGQ => 1 GRDNT", "1 FBRG => 5 LCHZ",
				"7 XZBJ, 8 RSZF, 9 SVDX => 9 LWDP", "20 WDXT => 5 RQFRT", "1 LXQWG, 1 GLZH => 6 SDLJ",
				"4 XFGH => 1 GCZLZ", "1 WPZN => 1 FBRG", "19 XZBJ => 5 WXGV", "1 GDXC => 6 WDXT",
				"1 WXGV, 1 NKHSG, 2 LWDP => 5 FCNPB", "4 LWDP, 5 BGPF => 9 KLRB", "1 GMRN => 4 GLZH",
				"1 RQFRT => 5 SVDX", "2 HWKG => 7 LHFV", "2 LCHZ, 13 JTJT, 10 TPXCK => 3 RSZF", "29 MZTVH => 6 TSGR",
				"9 NRFLK, 1 SVDX => 5 NKHSG", "123 ORE => 9 GDXC", "1 PZPBV, 21 PMVMQ, 1 GCZLZ => 8 SKZGB",
				"3 GRNC, 5 GDXC => 8 QZVM",
				"6 VTDQ, 13 TCQW, 3 FCNPB, 48 PSVLS, 3 RLNF, 73 BTQL, 5 MHRVG, 26 BGPF, 26 HDJB, 5 XFGQ, 6 HTFL => 1 FUEL",
				"5 QZVM, 2 JTJT => 1 PXKHG", "3 LSNG, 1 PMVMQ => 8 VTDQ", "31 XFGH => 1 FCPC", "9 PSVLS => 8 FWGTF",
				"1 GRNC => 3 WPZN", "16 JBXDX, 4 GRNC => 6 HWKG", "1 SKZGB, 5 RSZF => 4 XZBJ", "134 ORE => 9 CTDRZ",
				"1 SVDX, 2 TPXCK => 7 JTJT", "6 RQFRT, 4 KBCW => 3 BGNLR", "12 KLRB, 12 LHFV => 4 HTFL",
				"2 GMRN => 6 XFGQ", "16 WNSW, 12 SKZGB => 8 LXQWG", "2 NRFLK, 2 CTDRZ => 9 JBXDX", "1 PZPBV => 8 RLNF",
				"2 JTJT, 5 GCZLZ => 3 WNSW", "5 WXGV, 2 LCHZ => 2 SCDS", "1 QHFKR => 3 GMRN",
				"10 JTJT, 2 HRCG => 8 KBCW", "7 HWKG => 4 PSVLS", "7 WNSW, 1 PXKHG, 3 BGNLR => 9 MZTVH",
				"15 TPXCK, 11 LHFV => 5 HRCG", "1 LSNG, 1 HWKG => 3 PZPBV", "7 BGPF => 9 PLCNZ", "1 ZGWT => 6 ZXNM",
				"26 NKHSG, 1 LHFV, 2 JTJT, 26 WXGV, 6 SDLJ, 1 KLRB, 1 TSGR => 8 TCQW", "154 ORE => 4 NRFLK",
				"1 GMRN => 3 VHJX", "5 QZVM, 3 GDXC => 7 LSNG", "5 WNSW => 5 ZGWT",
				"6 QHFKR, 8 PZPBV, 10 FBRG, 13 FWGTF, 1 LHVTN, 4 SCDS, 8 VHJX, 7 TSGR => 6 MHRVG",
				"12 GLZH => 5 LHVTN" };
		Map<String, Chemical> chemicals = new HashMap<>();
		for (String rawEquation : rawEquations) {
			String[] sides = rawEquation.split(" => ");
			String[] reactants = sides[0].split(", ");
			String[] names = new String[reactants.length];
			long[] amounts = new long[reactants.length];
			for (int i = 0; i < reactants.length; i++) {
				String[] chemical = reactants[i].split(" ");
				names[i] = chemical[1];
				amounts[i] = Long.parseLong(chemical[0]);
			}
			String[] chemical = sides[1].split(" ");
			addEquation(chemicals, chemical[1], Long.parseLong(chemical[0]), names, amounts);
		}
		return chemicals;
	}

	private static void addEquation(Map<String, Chemical> chemicals, String name, long amount, String[] reactantNames,
			long[] reactantAmounts) {
		Chemical[] reactants = new Chemical[reactantNames.length];
		for (int i = 0; i < reactantNames.length; i++) {
			Chemical reactant = new Chemical();
			reactant.name = reactantNames[i];
			reactant.amount = reactantAmounts[i];
			reactants[i] = reactant;
		}
		Chemical product = new Chemical();
		product.name = name;
		product.amount = amount;
		product.reactants = reactants;
		chemicals.put(name, product);
	}

	static class Chemical {
		String name;
		long amount;
		Chemical[] reactants;

		public Chemical getChemical(String name) {
			Chemical chemical = new Chemical();
			chemical.name = name;
			return chemical;
		}

		public boolean equals(Object o) {
			if (!(o instanceof Chemical)) {
				return false;
			}
			Chemical chemical = (Chemical) o;
			return chemical.name == name;
		}
	}
}