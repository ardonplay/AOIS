package org.ardonplay;


import static org.ardonplay.LogicalOperations.toDecimal;
import static org.ardonplay.Solver.expr;
import static org.ardonplay.Solver.lexAnalyze;
import static org.ardonplay.Solver.symbols;

import com.sun.source.tree.TryTree;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Main {

  public static void changeValueOfSymbol(LexemeBuffer lexemeBuffer, String symbol, int value) {
    lexemeBuffer.lexemes.stream()
        .filter(lexeme -> lexeme.symbol != null)
        .filter(lexeme -> Objects.equals(
            lexeme.symbol.name, symbol))
        .forEach(lexeme -> lexeme.symbol.value = value);
  }

  public static List<String> getAllSymbols(LexemeBuffer lexemeBuffer) {
    return lexemeBuffer.lexemes.stream().filter(lexeme -> lexeme.symbol != null)
        .map(lexeme -> lexeme.symbol.name).toList();
  }

  public static Map<Integer, Integer> getAllScobes(String expression) {
    Stack<Integer> stack = new Stack<>();
    Map<Integer, Integer> map = new HashMap<>();
    for (int i = 0; i < expression.length(); i++) {
      if (expression.charAt(i) == '!' && expression.charAt(i + 1) == '(') {
        stack.push(i);
      } else if (expression.charAt(i) == '(') {
        if (i - 1 >= 0 && expression.charAt(i - 1) == '!') {
          continue;
        } else {
          stack.push(i);
        }
      } else if (expression.charAt(i) == ')') {

        map.put(stack.pop(), i + 1);

      }
    }
    return map;
  }

  public static void main(String[] args) {
    String expressionText = "!((A+B) * !(A*C))";
    List<Lexeme> lexemes = lexAnalyze(expressionText);
    LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
    List<String> allSymbols = getAllSymbols(lexemeBuffer);

    Map<String, ArrayList<Integer>> TruthMap = new TreeMap<>(Collections.reverseOrder());

    ArrayList<Entry<Integer, Integer>> scopes = new ArrayList<>(
        getAllScobes(expressionText).entrySet());

    for (Entry<Integer, Integer> subexpr : scopes) {
      System.out.print(expressionText.substring(subexpr.getKey(), subexpr.getValue()) + "\t");
    }
    System.out.println();

    allSymbols = allSymbols.stream().distinct().collect(Collectors.toList());


    allSymbols.forEach(s -> TruthMap.put(s, new ArrayList<>()));

    TruthMap.put(expressionText, new ArrayList<>());

    int rows = (int) Math.pow(2, symbols.size());
    for (int i = 0; i < rows; i++) {
      for (int j = symbols.size() - 1; j >= 0; j--) {
        int value = (i / (int) Math.pow(2, j)) % 2;
        changeValueOfSymbol(lexemeBuffer, allSymbols.get(symbols.size() - 1 - j), value);
        TruthMap.get(allSymbols.get(symbols.size() - 1 - j)).add(value);
      }
      int result = expr(lexemeBuffer);
      TruthMap.get(expressionText).add(result);
      lexemeBuffer.setPos(0);
    }

    TruthMap.forEach((key, value) -> System.out.print(key + "\t"));
    System.out.println();

    int size = TruthMap.get(allSymbols.get(0)).size();

    for(int i =0; i < size; i++){
      for (String key : TruthMap.keySet()) {
        System.out.print(TruthMap.get(key).get(i) + "\t");
      }
      System.out.println();
    }

    System.out.format("Index: %s", toDecimal(TruthMap, expressionText));

  }

}
