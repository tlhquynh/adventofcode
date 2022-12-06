package com.cow.playground.algo.adhoc.adventofcode.year2022;

import java.io.IOException;

public class Aoc2022Story {
    public static void main(String[] args) throws IOException {
        // Day 1: Calorie counting
        CalorieCounting cc = new CalorieCounting();
        cc.findTops();

        // Day 2: Rock Paper Scissors
        RockPaperScissors rps = new RockPaperScissors();
        rps.task1();
        rps.task2();

        // Day 3: Rucksack Reorganization
        RucksackReorganization rr = new RucksackReorganization();
        rr.task1();
        rr.task2();

        // Day 4: Camp Cleanup
        CampCleanup campCleanup = new CampCleanup();
        campCleanup.findEnclosingPairs();
        campCleanup.findOverlappingPairs();

        // Day 5: Supply Stacks
        SupplyStacks ss = new SupplyStacks();
        ss.rearrangeByCrateMover9000();
        ss.rearrangeByCrateMover9001();

        // Day 6: Tuning Trouble
        TuningTrouble tt = new TuningTrouble();
        tt.task1();
        tt.task2();
    }
}
