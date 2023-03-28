package org.ardonplay;

import java.util.ArrayList;
import java.util.Map;

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
}
