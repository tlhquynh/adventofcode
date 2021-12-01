package com.cow.playground.algo.adhoc.adventofcode.year2019;

/**
 * https://adventofcode.com/2019/day/4
 * 
 * @author quynh
 *
 */
public class Day4 {
	public static boolean valid1(int x) {
		String s = x + "";
		char[] a = s.toCharArray();
		boolean duplicate = false;
		for (int i = 1; i < a.length; i++) {
			if (a[i] < a[i - 1]) {
				return false;
			} else if (a[i] == a[i - 1]) {
				duplicate = true;
			}
		}
		if (duplicate) {
			return true;
		}
		return false;
	}

	public static boolean valid2(int x) {
		String s = x + "";
		char[] a = s.toCharArray();
		boolean duplicate = false;
		int countDuplicates = 1;
		for (int i = 1; i < a.length; i++) {
			if (a[i] < a[i - 1]) {
				return false;
			} else if (a[i] == a[i - 1]) {
				countDuplicates++;
			} else {
				if (countDuplicates == 2) {
					duplicate = true;
				}
				countDuplicates = 1;
			}
		}
		if (duplicate || countDuplicates == 2) {
			return true;
		}
		return false;
	}

	public static void main(String[] args) {
		int count1 = 0;
		int count2 = 0;
		for (int i = 353096; i <= 843212; i++) {
			if (valid1(i)) {
				count1++;
			}
			if (valid2(i)) {
				count2++;
			}
		}
		System.out.println(count1);
		System.out.println(count2);
	}
}
