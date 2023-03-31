package org.ardonplay.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

  public static int conjunction(int A, int B) {
    return A * B;
  }

  public static int equivalence(int A, int B) {
    return conjunction(implication(A, B), implication(B, A));
  }

  public static int not(int A) {
    return A == 0 ? 1 : 0;
  }

  private static int toDecimal(List<Integer> answer){
    int degree = answer.size() - 1;

    int output = 0;
    for (Integer index : answer) {
      output += index * Math.pow(2, degree);
      degree--;
    }
    return output;
  }
  public static int toDecimal(Map<String, List<Integer>> map, String LexemeString) {
   return toDecimal(map.get(LexemeString));
  }

  public static int toDecimal(String string) {
    return toDecimal(Stream.of(string)
        .map(Integer::parseInt)
        .collect(Collectors.toList()));
  }

  public static String createPCNF(Map<String, List<Integer>> map, String LexemeString) {
    List<Integer> answer = map.get(LexemeString);

    List<Integer> indexes = new ArrayList<>();

    for (int i = 0; i < answer.size(); i++) {
      if (answer.get(i) == 0) {
        indexes.add(i);
      }
    }
    StringBuilder string = new StringBuilder();

    StringBuilder integerString = new StringBuilder();


    string.append("PCNF: ");
    for (Integer integer : indexes) {
      StringBuilder number = new StringBuilder();
      string.append("(");
      for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
        if (!Objects.equals(entry.getKey(), LexemeString)) {
          number.append(entry.getValue().get(integer));
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
      number.reverse();
      integerString.append(number);
      integerString.append("*");
    }
    return printLogicalFunction(string, integerString);
  }

  private static List<Integer> stringToList(String string) {

    List<Integer> list = new ArrayList<>();

    StringBuilder temp = new StringBuilder();

    for (char c : string.toCharArray()) {
      if (c != '*' && c != '+') {
        temp.append(c);
      } else {
        list.add(toDecimal(String.valueOf(temp)));
        temp.replace(0, temp.length(), "");
      }
    }
    list.add(toDecimal(String.valueOf(temp)));
    return list;
  }

  public static String createPDNF(Map<String, List<Integer>> map, String LexemeString) {
    List<Integer> answer = map.get(LexemeString);

    List<Integer> indexes = new ArrayList<>();

    for (int i = 0; i < answer.size(); i++) {
      if (answer.get(i) == 1) {
        indexes.add(i);
      }
    }
    StringBuilder string = new StringBuilder();

    StringBuilder integerString = new StringBuilder();

    string.append("PDNF: ");
    for (Integer integer : indexes) {

      string.append("(");
      StringBuilder number = new StringBuilder();
      for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
        if (!Objects.equals(entry.getKey(), LexemeString)) {
          number.append(entry.getValue().get(integer));
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
      number.reverse();
      integerString.append(number);
      integerString.append("+");
    }
    return printLogicalFunction(string, integerString);
  }

  private static String printLogicalFunction(StringBuilder string, StringBuilder integerString) {
    string.replace(string.length() - 3, string.length(), "");
    integerString.replace(integerString.length() - 1, integerString.length(), "");
    return string + "\n" + integerString + "\n" + stringToList(String.valueOf(integerString));
  }

  public static void changeValueOfSymbol(LexemeBuffer lexemeBuffer, String symbol, int value) {
    lexemeBuffer.lexemes.stream()
        .filter(lexeme -> lexeme.symbol != null)
        .filter(lexeme -> Objects.equals(
            lexeme.symbol.name, symbol))
        .forEach(lexeme -> lexeme.symbol.value = value);
  }
}
