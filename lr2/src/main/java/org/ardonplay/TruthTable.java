package org.ardonplay;

import java.util.*;

public class TruthTable {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter a logical expression: ");
    String expr = sc.nextLine();

    // Extract unique variables from the expression
    Set<Character> variables = new HashSet<>();
    for (char c : expr.toCharArray()) {
      if (Character.isLetter(c)) {
        variables.add(c);
      }
    }

    // Generate all possible combinations of truth values for variables
    List<Map<Character, Boolean>> truthValues = generateTruthValues(variables);

    // Evaluate the expression for each combination of truth values
    for (Map<Character, Boolean> values : truthValues) {
      boolean result = evaluateExpression(expr, values);
      for (char c : variables) {
        System.out.print(values.get(c) ? "1" : "0");
        System.out.print(" ");
      }
      System.out.println(result ? "1" : "0");
    }
  }

  // Generate all possible combinations of truth values for the given set of variables
  private static List<Map<Character, Boolean>> generateTruthValues(Set<Character> variables) {
    List<Map<Character, Boolean>> truthValues = new ArrayList<>();
    int numValues = (int) Math.pow(2, variables.size());
    for (int i = 0; i < numValues; i++) {
      Map<Character, Boolean> values = new HashMap<>();
      int j = 0;
      for (char c : variables) {
        boolean value = (i & (1 << j)) != 0;
        values.put(c, value);
        j++;
      }
      truthValues.add(values);
    }
    return truthValues;
  }

  // Evaluate the given logical expression for the given truth values
  private static boolean evaluateExpression(String expr, Map<Character, Boolean> values) {
    Stack<Boolean> stack = new Stack<>();
    for (char c : expr.toCharArray()) {
      if (Character.isLetter(c)) {
        stack.push(values.get(c));
      } else if (c == '!') {
        boolean operand = stack.pop();
        stack.push(!operand);
      } else if (c == '∧') {
        boolean operand2 = stack.pop();
        boolean operand1 = stack.pop();
        stack.push(operand1 || operand2);
      } else if (c == '*') {
        boolean operand2 = stack.pop();
        boolean operand1 = stack.pop();
        stack.push(operand1 || operand2);
      }
    }
    return stack.pop();
  }
}