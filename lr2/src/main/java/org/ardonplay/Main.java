package org.ardonplay;


import static org.ardonplay.logic.LogicalOperations.createPCNF;
import static org.ardonplay.logic.LogicalOperations.createPDNF;
import static org.ardonplay.logic.LogicalOperations.toDecimal;
import static org.ardonplay.logic.Solver.getAllSymbols;
import static org.ardonplay.logic.Solver.lexAnalyze;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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

        allSymbols = allSymbols.stream().distinct().collect(Collectors.toList());

        TruthMap map = new TruthMap(allSymbols);

        allSymbols.forEach(s -> map.put(s, new ArrayList<>()));

        map.put(expressionText, new ArrayList<>());

        map.createTable(expressionText, lexemeBuffer, allSymbols);


        System.out.println(map);

        System.out.format("Index: %s\n", toDecimal(map.getTruthMap(), expressionText));

        System.out.println(createPCNF(map.getTruthMap(), expressionText));
        System.out.println(createPDNF(map.getTruthMap(), expressionText));

    }


}
