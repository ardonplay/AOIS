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
    List<Integer> stringToInteger = new ArrayList<>();
    for(char c: string.toCharArray()){
      stringToInteger.add(Integer.valueOf(String.valueOf(c)));
    }
    return toDecimal(stringToInteger);
  }

  public static List<Integer> getFunctionIndexes(String string) {

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


  public static void changeValueOfSymbol(LexemeBuffer lexemeBuffer, String symbol, int value) {
    lexemeBuffer.lexemes.stream()
        .filter(lexeme -> lexeme.symbol != null)
        .filter(lexeme -> Objects.equals(
            lexeme.symbol.name, symbol))
        .forEach(lexeme -> lexeme.symbol.value = value);
  }
}
