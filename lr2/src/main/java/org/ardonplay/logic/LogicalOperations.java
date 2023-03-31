package org.ardonplay.logic;

import java.util.ArrayList;
import java.util.List;
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

  public static int conjunction(int A, int B) {
    return A * B;
  }

  public static int equivalence(int A, int B) {
    return conjunction(implication(A, B), implication(B, A));
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
  public static int toDecimal(String string) {

    ArrayList<Integer> answer = new ArrayList<>();
    for (char str : string.toCharArray()) {
      answer.add(Integer.parseInt(String.valueOf(str)));
    }
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

    StringBuilder integerString = new StringBuilder();

    string.append("PCNF: ");
    for (Integer integer : indexes) {

      string.append("(");
      for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
        if (!Objects.equals(entry.getKey(), LexemeString)) {
          if (entry.getValue().get(integer) == 0) {
            string.append("!").append(entry.getKey());
            integerString.append(not(entry.getValue().get(integer)));
          } else {
            string.append(entry.getKey());
            integerString.append(not(entry.getValue().get(integer)));
          }
          string.append("\\/");
        }
      }
      string.replace(string.length() - 2, string.length(), "");
      string.append(") /\\ ");
      integerString.append("*");
    }
    printLogicalFunction(string, integerString);
  }

  private static List<Integer> stringToList(String string){
    List<Integer> list = new ArrayList<>();

    StringBuilder temp = new StringBuilder();

    for(char c: string.toCharArray()){
      if(c != '*' && c != '+'){
        temp.append(c);
      }
      else {
        list.add(toDecimal(String.valueOf(temp)));
        temp.replace(0, temp.length(), "");
      }
    }
    list.add(toDecimal(String.valueOf(temp)));
    return list;
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

    StringBuilder integerString = new StringBuilder();

    string.append("PDNF: ");
    for (Integer integer : indexes) {

      string.append("(");
      for (Map.Entry<String, ArrayList<Integer>> entry : map.entrySet()) {
        if (!Objects.equals(entry.getKey(), LexemeString)) {
          if (entry.getValue().get(integer) == 0) {
            string.append("!").append(entry.getKey());
            integerString.append(entry.getValue().get(integer));
          } else {
            string.append(entry.getKey());
            integerString.append(entry.getValue().get(integer));
          }
          string.append("/\\");
        }
      }
      string.replace(string.length() - 2, string.length(), "");
      string.append(") \\/ ");
      integerString.append("+");
    }
    printLogicalFunction(string, integerString);
  }

  private static void printLogicalFunction(StringBuilder string, StringBuilder integerString) {
    string.replace(string.length()-3, string.length(), "");
    integerString.replace(integerString.length() -1, integerString.length(), "");
    System.out.println();
    System.out.println(string);
    System.out.println(integerString);
    System.out.println(stringToList(String.valueOf(integerString)));
  }

  public static void changeValueOfSymbol(LexemeBuffer lexemeBuffer, String symbol, int value) {
    lexemeBuffer.lexemes.stream()
            .filter(lexeme -> lexeme.symbol != null)
            .filter(lexeme -> Objects.equals(
                    lexeme.symbol.name, symbol))
            .forEach(lexeme -> lexeme.symbol.value = value);
  }
}
