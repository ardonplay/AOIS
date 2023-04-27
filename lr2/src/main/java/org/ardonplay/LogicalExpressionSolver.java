package org.ardonplay;

import lombok.Getter;
import lombok.Setter;
import org.ardonplay.logic.Lexeme;
import org.ardonplay.logic.LexemeBuffer;
import org.ardonplay.logic.TruthMap;
import org.ardonplay.logic.logicalForms.Pcnf;
import org.ardonplay.logic.logicalForms.Pdnf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.ardonplay.logic.LogicalOperations.toDecimal;
import static org.ardonplay.logic.Solver.getSymbols;
import static org.ardonplay.logic.Solver.lexAnalyze;

@Getter
@Setter
public class LogicalExpressionSolver {
    final private Pcnf pcnf;

    final private Pdnf pdnf;

    final private TruthMap map;

    final String expressionText;


    final private Map<List<Integer>, Integer> logicalTable =  new HashMap<>();


    private void setLogicalTable(){
        for(int i =0; i < map.getTruthMap().get(expressionText).size(); i++){
            List<Integer> logicalRow = new ArrayList<>();
            for(String s: LogicalExpressionSolver.getAllSymbols()){
                logicalRow.add(map.getTruthMap().get(s).get(i));
            }

            logicalTable.put(logicalRow, map.getTruthMap().get(expressionText).get(i));
        }
    }
    public static List<String> getAllSymbols() {
        return allSymbols;
    }

    private static List<String> allSymbols;

    public LogicalExpressionSolver(String expressionText){
        this.expressionText = expressionText;
        List<Lexeme> lexemes = lexAnalyze(expressionText);
        LexemeBuffer lexemeBuffer = new LexemeBuffer(lexemes);

        allSymbols = getSymbols(lexemeBuffer);

        allSymbols = allSymbols.stream().distinct().collect(Collectors.toList());

        map = new TruthMap(allSymbols);

        allSymbols.forEach(s -> map.put(s, new ArrayList<>()));

        map.put(expressionText, new ArrayList<>());

        map.createTable(expressionText, lexemeBuffer, allSymbols);

        setLogicalTable();

        pcnf = new Pcnf(map.getTruthMap(), expressionText);
        pdnf = new Pdnf(map.getTruthMap(), expressionText);
    }

    @Override
    public String toString(){
        return map + "\n" + "Index: " +  toDecimal(map.getTruthMap(), expressionText) + "\n"
                + "PCNF: " + pcnf + "\n" + "PDNF: " + pdnf;
    }

}
