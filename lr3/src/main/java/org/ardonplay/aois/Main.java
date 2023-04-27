package org.ardonplay.aois;


import org.ardonplay.LogicalExpressionSolver;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        LogicalExpressionSolver solver = new LogicalExpressionSolver("(A*B)+C");

        System.out.println(solver);
        AnalyticalMinimize minimize = new AnalyticalMinimize();

        List<List<String>> pdnf = solver.getPdnf().getNormalForm();
        List<List<String>> minimization = minimize.minimise(pdnf, true);
        System.out.println("СДНФ просто обрезка: " + minimize.minimise(minimization, true));
        System.out.println("СДНФ не просто обрезка: " + minimize.minimiseSecond(minimization, true));

        minimization = minimize.minimise(solver.getPcnf().getNormalForm(), false);
        System.out.println("СКНФ просто обрезка: " + minimization);
        System.out.println("СКНФ не просто обрезка: " + minimize.minimiseSecond(minimization, false));

        TableMinimize tableMinimize = new TableMinimize();

        System.out.println();
        System.out.println(tableMinimize.tableMinimize(solver.getPdnf().getNormalForm(), true));

        System.out.println(tableMinimize.tableMinimize(solver.getPcnf().getNormalForm(), false));

        KarnoTable karnoTable = new KarnoTable();


        System.out.println(karnoTable.minimize(solver.getLogicalTable(), LogicalExpressionSolver.getAllSymbols(),
                true));
        System.out.println(karnoTable.minimize(solver.getLogicalTable(), LogicalExpressionSolver.getAllSymbols(),
                false));

    }
}