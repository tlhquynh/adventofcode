package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProboscideaVolcanium {
    private static final Logger LOGGER = LoggerFactory.getLogger(ProboscideaVolcanium.class);
    private static final String INPUT_FILENAME = "day16.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private Map<String, Valve> nameToValve;
    private int best;
    private List<Valve> bestVisited;

    public ProboscideaVolcanium() throws IOException {
        readInputs();
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH); InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8); BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            nameToValve = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                // Valve EG has flow rate=21; tunnels lead to valves WZ, OF, ZP, QD
                line = line.concat(",");
                String[] tokens = line.split(" ");

                Valve firstValve = nameToValve.getOrDefault(tokens[1], new Valve(tokens[1]));
                nameToValve.put(tokens[1], firstValve);
                firstValve.setFlowRate(Integer.parseInt(tokens[4].substring(5, tokens[4].length() - 1)));

                for (int i = 9; i < tokens.length; i++) {
                    String secondName = tokens[i].substring(0, tokens[i].length() - 1);
                    Valve secondValve = nameToValve.getOrDefault(secondName, new Valve(secondName));
                    nameToValve.put(secondName, secondValve);
                    firstValve.addConnectedValve(secondValve);
                }
            }
        }
    }

    public void task1() {
        best = 0;
        bestVisited = new ArrayList<>();
        dfs(nameToValve.get("AA"), null, 30, new HashMap<Valve, Integer>());
        assert best == 2087;
        LOGGER.info("Most pressure released is {}", best);
    }

    /**
     * Heuristic approach
     */
    public void task2() {
        best = 0;
        bestVisited = new ArrayList<>();
        dfs(nameToValve.get("AA"), null, 26, new HashMap<Valve, Integer>());
        for (Valve valve : bestVisited) {
            valve.flowRate = 0;
        }
        int res = best;
        best = 0;
        bestVisited = new ArrayList<>();
        dfs(nameToValve.get("AA"), null, 26, new HashMap<Valve, Integer>());
        res += best;
        assert res == 2591;
        LOGGER.info("Most pressure released with elephant's help is {}", res);
    }

    private void dfs(Valve current, Valve parent, int timeLeft, Map<Valve, Integer> openValves) {
        if (timeLeft > 0) {
            if (!openValves.containsKey(current) && current.flowRate > 0) {
                openValves.put(current, timeLeft - 1);

                int sum = 0;
                for (Map.Entry<Valve, Integer> entry : openValves.entrySet()) {
                    sum += entry.getKey().flowRate * entry.getValue();
                }
                if (sum > best) {
                    best = sum;
                    bestVisited.clear();
                    bestVisited.addAll(openValves.keySet());
                }

                dfs(current, current, timeLeft - 1, openValves);
                openValves.remove(current);
            }
            for (Valve other : current.others) {
                if (other != parent) {
                    dfs(other, current, timeLeft - 1, openValves);
                }
            }
        }
    }

    static class Valve {
        String name;
        int flowRate;
        List<Valve> others;

        Valve(String name) {
            this.name = name;
            others = new ArrayList<>();
        }

        void addConnectedValve(Valve other) {
            others.add(other);
        }

        void setFlowRate(int flowRate) {
            this.flowRate = flowRate;
        }
    }
}