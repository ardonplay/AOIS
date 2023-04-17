package org.ardonplay;

import lombok.Getter;
import lombok.Setter;
import org.ardonplay.logic.Lexeme;
import org.ardonplay.logic.LexemeBuffer;
import org.ardonplay.logic.TruthMap;
import org.ardonplay.logic.logicalForms.Pcnf;
import org.ardonplay.logic.logicalForms.Pdnf;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.ardonplay.logic.LogicalOperations.toDecimal;
import static org.ardonplay.logic.Solver.getAllSymbols;
import static org.ardonplay.logic.Solver.lexAnalyze;

@Getter
@Setter
public class LogicalExpressionSolver {
    final private Pcnf pcnf;

    final private Pdnf pdnf;

    final private TruthMap map;

    final String expressionText;

    public LogicalExpressionSolver(String expressionText){
        this.expressionText = expressionText;
        List<Lexeme> lexemes = lexAnalyze(expressionText);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);

        List<String> allSymbols = getAllSymbols(lexemeBuffer);

        allSymbols = allSymbols.stream().distinct().collect(Collectors.toList());

        map = new TruthMap(allSymbols);

        allSymbols.forEach(s -> map.put(s, new ArrayList<>()));

        map.put(expressionText, new ArrayList<>());

        map.createTable(expressionText, lexemeBuffer, allSymbols);

        pcnf = new Pcnf(map.getTruthMap(), expressionText);
        pdnf = new Pdnf(map.getTruthMap(), expressionText);
    }

    @Override
    public String toString(){
        return map + "\n" + "Index: " +  toDecimal(map.getTruthMap(), expressionText) + "\n"
                + "PCNF: " + pcnf + "\n" + "PDNF: " + pdnf;
    }

}
