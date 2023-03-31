package org.ardonplay;


import static org.ardonplay.logic.LogicalOperations.createPCNF;
import static org.ardonplay.logic.LogicalOperations.createPDNF;
import static org.ardonplay.logic.LogicalOperations.toDecimal;
import static org.ardonplay.logic.ScobesParser.getAllSymbols;
import static org.ardonplay.logic.Solver.lexAnalyze;

import java.util.*;


import java.util.stream.Collectors;

import org.ardonplay.logic.Lexeme;
import org.ardonplay.logic.LexemeBuffer;
import org.ardonplay.logic.TruthMap;

public class Main {
    public static void main(String[] args) {
        Scanner stringScanner = new Scanner(System.in);
        System.out.print("Type an expression:");
        String expressionText = stringScanner.nextLine();

        List<Lexeme> lexemes = lexAnalyze(expressionText);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);
        List<String> allSymbols = getAllSymbols(lexemeBuffer);

        TruthMap map = new TruthMap();

        allSymbols = allSymbols.stream().distinct().collect(Collectors.toList());

        allSymbols.forEach(s -> map.put(s, new ArrayList<>()));

        map.put(expressionText, new ArrayList<>());

        map.createTable(expressionText, lexemeBuffer, allSymbols);
        map.printTable(allSymbols);

        System.out.format("Index: %s", toDecimal(map.getTruthMap(), expressionText));

        createPCNF(map.getTruthMap(), expressionText);
        createPDNF(map.getTruthMap(), expressionText);
    }


}
