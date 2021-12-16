package com.cow.playground.algo.adhoc.adventofcode.year2021;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PacketDecoder {

  private static final Logger LOGGER = LoggerFactory.getLogger(PacketDecoder.class);

  private static final String INPUT_FILENAME = "day16.in";
  private static final String INPUT_PATH = "year2021\\" + INPUT_FILENAME;
  private static final String[] HEX_TO_BIN = {"0000", "0001", "0010", "0011", "0100", "0101", "0110", "0111", "1000", "1001", "1010", "1011", "1100", "1101", "1110", "1111"};
  private Packet packet;

  public PacketDecoder() throws IOException {
    readInputs();
  }

  public int sumAllVersions() {
    int res = packet.sumAllVersions();

    LOGGER.info("day 16 task 1: 854");
    LOGGER.info("sum of all versions={}", res);

    return res;
  }

  public long evaluate() {
    long res = packet.evaluate();

    LOGGER.info("day 16 task 2: 186189840660");
    LOGGER.info("packet evaluation={}", res);

    return res;
  }

  private void readInputs() throws IOException {
    try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(INPUT_PATH);
         InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
         BufferedReader reader = new BufferedReader(streamReader)) {
      String line;
      while ((line = reader.readLine()) != null) {
        packet = new Packet(line, true);
      }
    }
  }

  private class Packet {

    private final int[] BIN_LOC = {0, 2};
    private final int[] TYPE_LOC = {3, 5};
    private final int[] LENGTH_TYPE_LOC = {6, 6};

    private String data;
    private String bin;
    private int version;
    private boolean isLiteral;
    private int type;
    private boolean isBitLength;
    private int length;
    private String value;
    private List<Packet> children;

    public Packet(String data, boolean isHex) {
      if (isHex) {
        this.data = data;
        this.bin = toBinary();
      } else {
        this.bin = data;
      }
      process();
    }

    public long evaluate() {
      long res = 0;
      if (type == 0) {
        if (children == null) {
          res = Long.parseLong(value, 2);
        } else {
          for (Packet packet : children) {
            res += packet.evaluate();
          }
        }
      } else if (type == 1) {
        if (children == null) {
          res = Long.parseLong(value, 2);
        } else {
          res = 1;
          for (Packet packet : children) {
            res *= packet.evaluate();
          }
        }
      } else if (type == 2) {
        if (children == null) {
          res = Long.parseLong(value, 2);
        } else {
          res = Long.MAX_VALUE;
          for (Packet packet : children) {
            res = Math.min(res, packet.evaluate());
          }
        }
      } else if (type == 3) {
        if (children == null) {
          res = Long.parseLong(value, 2);
        } else {
          res = Long.MIN_VALUE;
          for (Packet packet : children) {
            res = Math.max(res, packet.evaluate());
          }
        }
      } else if (type == 4) {
        res = Long.parseLong(value, 2);
      } else if (type == 5) {
        res = children.get(0).evaluate() > children.get(1).evaluate() ? 1 : 0;
      } else if (type == 6) {
        res = children.get(0).evaluate() < children.get(1).evaluate() ? 1 : 0;
      } else if (type == 7) {
        res = children.get(0).evaluate() == children.get(1).evaluate() ? 1 : 0;
      }
      return res;
    }

    private int sumAllVersions() {
      int res = version;
      if (children != null) {
        for (Packet child : children) {
          res += child.sumAllVersions();
        }
      }
      return res;
    }

    private void process() {
      version = Integer.parseInt(bin.substring(BIN_LOC[0], BIN_LOC[1] + 1), 2);
      type = Integer.parseInt(bin.substring(TYPE_LOC[0], TYPE_LOC[1] + 1), 2);
      isLiteral = type == 4;
      processLenValue();
      if (!isLiteral) {
        children = new ArrayList<>();
        if (isBitLength) {
          int offset = 0;
          do {
            Packet child = new Packet(value.substring(offset), false);
            children.add(child);
            offset += child.bin.length();
          } while (offset + 10 <= length);
          value = value.substring(0, offset);
          length = value.length();
          bin = bin.substring(0, LENGTH_TYPE_LOC[1] + 1 + 15 + length);
        } else {
          int offset = 0;
          for (int i = 0; i < length; i++) {
            Packet child = new Packet(value.substring(offset), false);
            children.add(child);
            offset += child.bin.length();
          }
          value = value.substring(0, offset);
          length = value.length();
          bin = bin.substring(0, LENGTH_TYPE_LOC[1] + 1 + 11 + length);
        }
      }
    }

    private void processLenValue() {
      if (isLiteral) {
        // literal
        StringBuilder sb = new StringBuilder();
        for (int i = TYPE_LOC[1] + 1; i < bin.length(); i += 5) {
          char header = bin.charAt(i);
          sb.append(bin, i + 1, i + 1 + 4);
          if (header == '0') {
            // reduce bin
            bin = bin.substring(0, i + 1 + 4);
            break;
          }
        }
        isBitLength = true;
        value = sb.toString();
        length = value.length();
      } else {
        int lengthType = Integer.parseInt(bin.substring(LENGTH_TYPE_LOC[0], LENGTH_TYPE_LOC[1] + 1), 2);
        if (lengthType == 0) {
          isBitLength = true;
          length = Integer.parseInt(bin.substring(LENGTH_TYPE_LOC[1] + 1, LENGTH_TYPE_LOC[1] + 1 + 15), 2);
          value = bin.substring(LENGTH_TYPE_LOC[1] + 1 + 15);
        } else {
          isBitLength = false;
          length = Integer.parseInt(bin.substring(LENGTH_TYPE_LOC[1] + 1, LENGTH_TYPE_LOC[1] + 1 + 11), 2);
          value = bin.substring(LENGTH_TYPE_LOC[1] + 1 + 11);
        }
      }
    }

    private String toBinary() {
      if (bin != null) {
        return bin;
      }
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < data.length(); i++) {
        char ch = data.charAt(i);
        if ('0' <= ch && ch <= '9') {
          sb.append(HEX_TO_BIN[ch - '0']);
        } else {
          sb.append(HEX_TO_BIN[ch - 'A' + 10]);
        }
      }
      return bin = sb.toString();
    }
  }
}
