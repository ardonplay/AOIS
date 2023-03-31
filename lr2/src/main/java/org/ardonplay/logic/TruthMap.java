package org.ardonplay.logic;

import java.util.*;

import static org.ardonplay.logic.LogicalOperations.changeValueOfSymbol;
import static org.ardonplay.logic.Solver.expr;
import static org.ardonplay.logic.Solver.symbols;

public class TruthMap {

  List<String> allSymbols;
  Map<String, List<Integer>> TruthMap = new TreeMap<>(Collections.reverseOrder());

  public TruthMap(List<String> allSymbols) {
    this.allSymbols = allSymbols;
  }

  @Override
  public String toString() {
    StringBuilder string = new StringBuilder();
    int size = TruthMap.get(allSymbols.get(0)).size();

    TruthMap.forEach((s, k) -> System.out.print(s + "\t"));
    System.out.println();
    for (int i = 0; i < size; i++) {
      for (String key : TruthMap.keySet()) {
        string.append(TruthMap.get(key).get(i)).append("\t");
      }
     string.append("\n");
    }

    return String.valueOf(string);
  }

  public void createTable(String expressionText, LexemeBuffer lexemeBuffer,
      List<String> allSymbols) {
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
  }

  public void put(String expr, List<Integer> list) {
    this.TruthMap.put(expr, list);
  }

  public Map<String, List<Integer>> getTruthMap() {
    return TruthMap;
  }
}
