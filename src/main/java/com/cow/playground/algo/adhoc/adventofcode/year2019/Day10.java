package com.cow.playground.algo.adhoc.adventofcode.year2019;

import java.util.*;

/**
 * https://adventofcode.com/2019/day/10
 * 
 * @author quynh
 *
 */
public class Day10 {
	public static void main(String[] args) {
		System.out.println(calcGcd(-6, -3));
		part2(part1());
	}

	public static void part2(Point bestPoint) {
		String[] a = { "#..#.#.###.#...##.##....", ".#.#####.#.#.##.....##.#", "##..#.###..###..#####..#",
				"####.#.#..#....#..##.##.", ".#######.#####...#.###..", ".##...#.#.###..###.#.#.#",
				".######.....#.###..#....", ".##..##.#..#####...###.#", "#######.#..#####..#.#.#.",
				".###.###...##.##....##.#", "##.###.##.#.#..####.....", "#.#..##..#..#.#..#####.#",
				"#####.##.#.#.#.#.#.#..##", "#...##.##.###.##.#.###..", "####.##.#.#.####.#####.#",
				".#..##...##..##..#.#.##.", "###...####.###.#.###.#.#", "..####.#####..#####.#.##",
				"..###..###..#..##...#.#.", "##.####...##....####.##.", "####..#..##.#.#....#..#.",
				".#..........#..#.#.####.", "###..###.###.#.#.#....##", "########.#######.#.##.##" };
		int y = bestPoint.y;
		int x = bestPoint.x;
		TreeMap<Slope, Point> seen = new TreeMap<Slope, Point>(new Comparator<Slope>() {
			@Override
			public int compare(Slope o1, Slope o2) {
				if (o1.dir == Slope.UP) {
					if (o2.dir == Slope.UP) {
						return 0;
					} else {
						return -1;
					}
				} else if (o1.dir == Slope.DOWN) {
					if (o2.dir == Slope.DOWN) {
						return 0;
					} else {
						return o2.x > 0 ? 1 : -1;
					}
				} else if (o2.dir == Slope.UP || o2.dir == Slope.DOWN) {
					return -compare(o2, o1);
				} else {
					if (o1.x > 0 && o2.x > 0) {
						return o1.y * o2.x - o2.y * o1.x;
					} else if (o1.x < 0 && o2.x < 0) {
						return o1.y * o2.x - o2.y * o1.x;
					} else
						return o1.x > o2.x ? -1 : 1;
				}
			}
		});
		countVisible(a, y, x, seen);
		int i = 0;
		for (Map.Entry<Slope, Point> entry : seen.entrySet()) {
			i++;
			System.out.println(i + " " + (entry.getValue().x * 100 + entry.getValue().y));
			if (i == 200) {
				break;
			}
		}
	}

	public static Point part1() {
		String[] a = { "#..#.#.###.#...##.##....", ".#.#####.#.#.##.....##.#", "##..#.###..###..#####..#",
				"####.#.#..#....#..##.##.", ".#######.#####...#.###..", ".##...#.#.###..###.#.#.#",
				".######.....#.###..#....", ".##..##.#..#####...###.#", "#######.#..#####..#.#.#.",
				".###.###...##.##....##.#", "##.###.##.#.#..####.....", "#.#..##..#..#.#..#####.#",
				"#####.##.#.#.#.#.#.#..##", "#...##.##.###.##.#.###..", "####.##.#.#.####.#####.#",
				".#..##...##..##..#.#.##.", "###...####.###.#.###.#.#", "..####.#####..#####.#.##",
				"..###..###..#..##...#.#.", "##.####...##....####.##.", "####..#..##.#.#....#..#.",
				".#..........#..#.#.####.", "###..###.###.#.#.#....##", "########.#######.#.##.##" };
		int row = a.length;
		int col = a[0].length();
		int best = 0;
		Point bestPoint = new Point();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if (a[i].charAt(j) == '#') {
					int seen = countVisible(a, i, j);
					if (seen > best) {
						best = seen;
						bestPoint.y = i;
						bestPoint.x = j;
					}
				}
			}
		}
		System.out.println(best + " - " + bestPoint.y + " - " + bestPoint.x);
		return bestPoint;
	}

	private static int countVisible(String[] a, int y, int x) {
		int row = a.length;
		int col = a[0].length();
		Set<Slope> seen = new HashSet<>();
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if ((i != y || j != x) && a[i].charAt(j) == '#') {
					if (j != x) {
						int gcd = Math.abs(calcGcd(i - y, j - x));
						Slope slope = new Slope((i - y) / gcd, (j - x) / gcd, j < x ? Slope.NEG : Slope.POS);
						seen.add(slope);
					} else {
						Slope slope = new Slope(i - y, 0, i < y ? Slope.UP : Slope.DOWN);
						seen.add(slope);
					}
				}
			}
		}
		int res = seen.size();
		return res;
	}

	private static int countVisible(String[] a, int y, int x, Map<Slope, Point> seen) {
		int row = a.length;
		int col = a[0].length();
		System.out.println(row + " " + col);
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if ((i != y || j != x) && a[i].charAt(j) == '#') {
					Slope slope = null;
					if (j != x) {
						int gcd = Math.abs(calcGcd(i - y, j - x));
						slope = new Slope((i - y) / gcd, (j - x) / gcd, j < x ? Slope.NEG : Slope.POS);
					} else {
						slope = new Slope(i - y, 0, i < y ? Slope.UP : Slope.DOWN);
					}
					Point p = seen.get(slope);
					if (p == null) {
						seen.put(slope, new Point(i, j));
					} else {
						if ((y - i) * (y - i) + (x - j) * (x - j) < (y - p.y) * (y - p.y) + (x - p.x) * (x - p.x)) {
							seen.put(slope, new Point(i, j));
						}
					}
				}
			}
		}
		int res = seen.size();
		return res;
	}

	private static int calcGcd(int a, int b) {
		if (b == 0) {
			return a;
		}
		return calcGcd(b, a % b);
	}

	static class Slope {
		static final int POS = 1;
		static final int NEG = -1;
		static final int UP = 2;
		static final int DOWN = 3;
		int y;
		int x;
		int dir;

		Slope(int y, int x, int dir) {
			this.y = y;
			this.x = x;
			this.dir = dir;
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Slope)) {
				return false;
			}
			Slope slope = (Slope) obj;
			if (dir == slope.dir && (dir == UP || dir == DOWN)) {
				return true;
			}
			return y == slope.y && x == slope.x && dir == slope.dir;
		}

		@Override
		public int hashCode() {
			if (dir == UP) {
				return Integer.MAX_VALUE;
			}
			if (dir == DOWN) {
				return Integer.MIN_VALUE;
			}
			int res = 17;
			res = res * 31 + y;
			res = res * 31 + x;
			res = res * 31 + dir;
			return res;
		}
	}

	static class Point {
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
