package com.cow.playground.algo.adhoc.adventofcode.year2021;

import java.io.IOException;

public class Aoc2021Story {

  public static void main(String[] args) throws IOException {
    // day 1 scanning the sea bed
    SonarScanner ss = new SonarScanner();
    ss.countDrops();
    ss.countSize3WindowDrops();

    // day 2 submarine starts to explore
    Submarine submarine = new Submarine();
    submarine.setExplorationStrategy(new SimpleExplorationStrategy());
    submarine.explore();
    submarine.setExplorationStrategy(new ComplexExplorationStrategy());
    submarine.explore();

    // day 3 submarine health check
    SubmarineHealthChecker healthCheck = new SubmarineHealthChecker();
    healthCheck.getPowerConsumption();
    healthCheck.getLifeSupportRating();

    // day 4 playing bingo with the giant squid
    BingoGame bingoGame = new BingoGame();
    bingoGame.start();

    // day 5 hypothermal vent danger
    SubmarineHydrothermalVentFinder finder = new SubmarineHydrothermalVentFinder();
    finder.countHorizontalVerticalDangerousSpots();
    finder.countHorizontalVerticalDiagonalDangerousSpots();

    // day 6 lantern fish study
    LanternFishSimulator simulator = new LanternFishSimulator();
    simulator.speculateSchoolSize(80);
    simulator.speculateSchoolSize(256);

    // day 7 escape the whale
    EscapePlan escapePlan = new EscapePlan();
    escapePlan.bestSimpleCrabAlignment();
    escapePlan.bestComplexCrabAlignment();

    // day 8 solve submarine display malfunction
    SubmarineDisplay display = new SubmarineDisplay();
    display.count1478();
    display.findOutputSum();

    // day 9 analyze smoke basin map
    HeightMapAnalyzer mapAnalyzer = new HeightMapAnalyzer();
    mapAnalyzer.detectBasinAreas();

    // day 10 syntax checker
    SyntaxChecker syntaxChecker = new SyntaxChecker();
    syntaxChecker.findErrorScores();

    // day 11 octopus xmas flashes
    OctopusBoard board = new OctopusBoard();
    board.simulate(100);
    board.simulateNonstop();

    // day 12 path finder
    PathFinder pathFinder = new PathFinder();
    pathFinder.countMoreUniquePaths();

    // day 13 origami
    OrigamiSimulator origamiSimulator = new OrigamiSimulator();
    origamiSimulator.foldOnce();
    origamiSimulator.foldAll();
  }


}
