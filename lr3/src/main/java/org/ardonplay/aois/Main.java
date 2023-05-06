package org.ardonplay.aois;


import org.ardonplay.LogicalExpressionSolver;

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Symbols\n" +
                "+ — disjunction\n" +
                "* — conjuction\n" +
                "~ — equivalence\n" +
                "@ — implication\n" +
                "! — not");
        System.out.print("Type an expression >>> ");
        LogicalExpressionSolver solver = new LogicalExpressionSolver(scanner.nextLine());

        System.out.println(solver);

        CalculationMinimize calculationMinimize = new CalculationMinimize();
        List<List<String>> minimization = calculationMinimize.infinityGluing(solver.getPdnf().getNormalForm());
        System.out.println("СДНФ расчетный первый пробег: " + new MinimizeList(minimization, FormulaType.PDNF));

        System.out.println("СДНФ расчетный подстановка: " + calculationMinimize.minimise(solver.getPdnf().getNormalForm(), FormulaType.PDNF));

        minimization = calculationMinimize.infinityGluing(solver.getPcnf().getNormalForm());
        System.out.println("СКНФ расчетный первый пробег: " + new MinimizeList(minimization, FormulaType.PCNF));

        System.out.println("СКНФ расчетный подстановка: " + calculationMinimize.minimise(solver.getPcnf().getNormalForm(), FormulaType.PCNF));

        TableMinimize tableMinimize = new TableMinimize();

        System.out.println();
        System.out.println("\tТабличный метод\t");
        System.out.println("СДНФ:");
        System.out.println(tableMinimize.tableMinimize(solver.getPdnf().getNormalForm(), FormulaType.PDNF));
        System.out.println("СКНФ:");
        System.out.println(tableMinimize.tableMinimize(solver.getPcnf().getNormalForm(), FormulaType.PCNF));


        System.out.println("\n\tКарно!");
        KarnaughTable karnaughTable = new KarnaughTable(LogicalExpressionSolver.getAllSymbols());

        try {
            System.out.println(karnaughTable.minimize(solver.getLogicalTable(), LogicalExpressionSolver.getAllSymbols(),
                    FormulaType.PDNF));
        }
        catch (IOException e){
            System.out.println("Введено больше чем 3 символа!");
        }
        try {
            System.out.println(karnaughTable.minimize(solver.getLogicalTable(), LogicalExpressionSolver.getAllSymbols(),
                    FormulaType.PCNF));
        }
        catch (IOException e){
            System.out.println("Введено больше чем 3 символа!");
        }
    }
}