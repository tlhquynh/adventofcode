package com.cow.playground.algo.adhoc.adventofcode.year2022;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class OutOfSpace {
    private static final Logger LOGGER = LoggerFactory.getLogger(OutOfSpace.class);
    private static final String INPUT_FILENAME = "day7.in";
    private static final String INPUT_PATH = "year2022\\" + INPUT_FILENAME;
    private static final int REQUIRED_FREE_SPACE = 30_000_000;
    private static final int TOTAL_DISK_SPACE = 70_000_000;
    private File rootDir;
    private int candidateSize;
    private int usedSpace;
    private int best;

    private void readInputs() throws IOException {

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
             InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
             BufferedReader reader = new BufferedReader(streamReader)) {

            rootDir = new File("/");
            File currentDir = null;
            boolean isListing = false;
            String line;
            while ((line = reader.readLine()) != null) {
                if (isListing && !line.startsWith("$")) {
                    String[] tokens = line.split(" ");
                    if ("dir".equals(tokens[0])) {
                        currentDir.addSubFile(new File(tokens[1]));
                    } else {
                        currentDir.addSubFile(new File(tokens[1], Integer.parseInt(tokens[0])));
                    }
                } else {
                    isListing = false;
                    if (line.contains("$ cd")) {
                        String[] tokens = line.split(" ");
                        switch (tokens[2]) {
                            case "/": currentDir = rootDir; break;
                            case "..": currentDir = currentDir.getParent(); break;
                            default: currentDir = currentDir.getSubDir(tokens[2]);
                        }
                    } else if (line.contains("$ ls")) {
                        isListing = true;
                    }
                }
            }
        }
    }
    public OutOfSpace() throws IOException {
        readInputs();
    }
    public void task1() {
        candidateSize = 0;
        usedSpace = diagnose(rootDir, 100000);
        assert candidateSize == 1792222;
        LOGGER.info("Total size of folder candidates is {}", candidateSize);
    }
    
    public void task2() {
        best = Integer.MAX_VALUE;
        int freeSpace = TOTAL_DISK_SPACE - usedSpace;
        int additionalRequiredSpace = REQUIRED_FREE_SPACE - freeSpace;
        findMin(rootDir, additionalRequiredSpace);
        assert best == 1112963;
        LOGGER.info("Min folder to delete is {}", best);
    }

    private int diagnose(File currentDir, int maxSize) {
        int size = 0;
        for (File subFile : currentDir.getSubFiles()) {
            if (subFile.isFolder) {
                size += diagnose(subFile, maxSize);
            } else {
                size += subFile.getSize();
            }
        }
        if (size <= maxSize) {
            candidateSize += size;
        }
        return size;
    }

    private int findMin(File currentDir, int minSize) {
        int size = 0;
        for (File subFile : currentDir.getSubFiles()) {
            if (subFile.isFolder) {
                size += findMin(subFile, minSize);
            } else {
                size += subFile.getSize();
            }
        }
        if (minSize <= size && size < best) {
            best = size;
        }
        return size;
    }

    static class File {
        String name;
        int size;
        boolean isFolder;
        List<File> subFiles;
        File parent;
        public File(String name, int size) {
            this.parent = null;
            this.name = name;
            this.size = size;
            this.isFolder = false;
            subFiles = null;
        }
        public File(String name) {
            this.parent = null;
            this.name = name;
            this.size = 0;
            this.isFolder = true;
            subFiles = new ArrayList<>();
        }
        private void addSubFile(File file) {
            if (!isFolder) {
                throw new InvalidParameterException("Adding file to a file! Folder required.");
            }
            subFiles.add(file);
            file.parent = this;
        }

        public File getParent() {
            return parent;
        }
        public File getSubDir(String name) {
            for (File subFile : subFiles) {
                if (subFile.isFolder && name.equals(subFile.name)) {
                    return subFile;
                }
            }
            return null;
        }
        public List<File> getSubFiles() {
            return subFiles;
        }

        public int getSize() {
            return size;
        }
    }
}
