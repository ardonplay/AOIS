package org.ardonplay.logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class LogicalOperations {

  public static int disjunction(int A, int B) {
    switch (A + B) {
      case 2, 1 -> {
        return 1;
      }
      case 0 -> {
        return 0;
      }
    }
    return 0;
  }

  public static int implication(int A, int B) {
    return disjunction(not(A), B);
  }

  public static int conjuction(int A, int B) {
    return A * B;
  }

  public static int equivalence(int A, int B) {
    return conjuction(implication(A, B), implication(B, A));
  }

  public static int not(int A) {
    if (A == 0) {
      return 1;
    } else {
      return 0;
    }
  }

  public static int toDecimal(Map<String, ArrayList<Integer>> map, String LexemeString) {
    ArrayList<Integer> answer = map.get(LexemeString);

    int degree = answer.size() - 1;

    int output = 0;
    for (Integer index : answer) {
      output += index * Math.pow(2, degree);
      degree--;
    }
    return output;
  }

  public static void createPCNF(Map<String, ArrayList<Integer>> map, String LexemeString) {
    ArrayList<Integer> answer = map.get(LexemeString);

    ArrayList<Integer> indexes = new ArrayList<>();

    for (int i = 0; i < answer.size(); i++) {
      if (answer.get(i) == 0) {
        indexes.add(i);
      }
    }
    StringBuilder string = new StringBuilder();

    string.append("PCNF: ");
    for (Integer integer : indexes) {

      string.append("(");
      for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
        if (!Objects.equals(entry.getKey(), LexemeString)) {
          if (entry.getValue().get(integer) == 0) {
            string.append("!").append(entry.getKey());
          } else {
            string.append(entry.getKey());
          }
          string.append("\\/");
        }
      }
      string.replace(string.length() - 2, string.length(), "");
      string.append(") /\\ ");
    }
    string.replace(string.length()-3, string.length(), "");
    System.out.println();
    System.out.println(string);
  }

  public static void createPDNF(Map<String, ArrayList<Integer>> map, String LexemeString) {
    ArrayList<Integer> answer = map.get(LexemeString);

    ArrayList<Integer> indexes = new ArrayList<>();

    for (int i = 0; i < answer.size(); i++) {
      if (answer.get(i) == 1) {
        indexes.add(i);
      }
    }
    StringBuilder string = new StringBuilder();

    string.append("PCNF: ");
    for (Integer integer : indexes) {

      string.append("(");
      for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
        if (!Objects.equals(entry.getKey(), LexemeString)) {
          if (entry.getValue().get(integer) == 0) {
            string.append("!").append(entry.getKey());
          } else {
            string.append(entry.getKey());
          }
          string.append("/\\");
        }
      }
      string.replace(string.length() - 2, string.length(), "");
      string.append(") \\/ ");
    }
    string.replace(string.length()-3, string.length(), "");
    System.out.println();
    System.out.println(string);
  }
}
