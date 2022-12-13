package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DistressSignal {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistressSignal.class);
    private static final String INPUT_FILENAME = "day13.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    List<Datum[]> pairs;

    public DistressSignal() throws IOException {
        readInputs();
    }

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            pairs = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                if (!line.isBlank()) {
                    pairs.add(new Datum[]{Datum.parseDatum(line), Datum.parseDatum(reader.readLine())});
                }
            }
        }
    }

    public void task1() {
        int res = 0;
        for (int i = 0; i < pairs.size(); i++) {
            Datum[] pair = pairs.get(i);
            res += Datum.compare(pair[0], pair[1]) < 0 ? i + 1 : 0;
        }
        assert res == 5659;
        LOGGER.info("Sum of right-ordered pairs is {}", res);
    }

    public void task2() {
        List<Datum> all = new ArrayList<>();
        Datum two = Datum.listOf(Datum.listOf(Datum.valueOf(2)));
        Datum six = Datum.listOf(Datum.listOf(Datum.valueOf(6)));
        all.add(two);
        all.add(six);
        for (Datum[] pair : pairs) {
            all.add(pair[0]);
            all.add(pair[1]);
        }
        all.sort(Datum::compare);
        int twoId = 1;
        int sixId = 1;
        for (int i = 1; i < all.size(); i++) {
            if (all.get(i) == two) {
                twoId = i + 1;
            } else if (all.get(i) == six) {
                sixId = i + 1;
            }
        }
        int res = twoId * sixId;
        assert res == 22110;
        LOGGER.info("Decoder key is {}", res);
    }

    static class Datum {
        boolean isList;
        int value;
        List<Datum> data;

        private Datum() {
            this.isList = true;
            data = new ArrayList<>();
        }

        private Datum(int value) {
            this.isList = false;
            this.value = value;
        }

        static Datum listOf(Datum... data) {
            Datum res = new Datum();
            res.data.addAll(Arrays.asList(data));
            return res;
        }

        static Datum valueOf(int value) {
            return new Datum(value);
        }

        static Datum parseDatum(String s) {
            return parseDatum(s, 0, s.length() - 1);
        }

        private static Datum parseDatum(String s, int from, int to) {
            if (s.charAt(from) == '[') {
                Datum res = new Datum();
                int open = 0;
                int prev = from + 1;
                for (int i = from + 1; i <= to - 1; i++) {
                    if (s.charAt(i) == '[') {
                        open++;
                    } else if (s.charAt(i) == ']') {
                        open--;
                    } else if (s.charAt(i) == ',' && open == 0) {
                        res.data.add(Datum.parseDatum(s, prev, i - 1));
                        prev = i + 1;
                    }
                }
                if (prev <= to - 1) {
                    res.data.add(Datum.parseDatum(s, prev, to - 1));
                }
                return res;
            }
            return Datum.valueOf(Integer.parseInt(s.substring(from, to + 1)));
        }

        static int compare(Datum u, Datum v) {
            if (!u.isList && !v.isList) {
                return Integer.compare(u.value, v.value);
            }
            if (u.isList && v.isList) {
                for (int i = 0; i < Math.min(u.data.size(), v.data.size()); i++) {
                    int cmp = Datum.compare(u.data.get(i), v.data.get(i));
                    if (cmp != 0) {
                        return cmp;
                    }
                }
                return Integer.compare(u.data.size(), v.data.size());
            }
            if (u.isList) {
                return Datum.compare(u, Datum.listOf(v));
            }
            return Datum.compare(Datum.listOf(u), v);
        }
    }
}