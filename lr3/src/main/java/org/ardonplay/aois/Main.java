package org.ardonplay.aois;


import org.ardonplay.LogicalExpressionSolver;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        LogicalExpressionSolver solver = new LogicalExpressionSolver("(A*B)+C");

        System.out.println(solver);
        AnalyticalMinimize minimize = new AnalyticalMinimize();

        List<List<String>> minimization = minimize.minimise(solver.getPdnf().getNormalForm(), true);
        System.out.println("СДНФ расчетный первый пробег: " + minimization);
        System.out.println("СДНФ расчетный подстановка: " + minimize.minimiseSecond(minimization, true));

        minimization = minimize.minimise(solver.getPcnf().getNormalForm(), false);
        System.out.println("СКНФ расчетный первый пробег: " + minimization);
        System.out.println("СКНФ расчетный подстановка: " + minimize.minimiseSecond(minimization, false));

        TableMinimize tableMinimize = new TableMinimize();

        System.out.println();
        System.out.println("\tТабличный метод\t");
        System.out.println("СДНФ:");
        System.out.println(tableMinimize.tableMinimize(solver.getPdnf().getNormalForm(), true));
        System.out.println("СКНФ:");
        System.out.println(tableMinimize.tableMinimize(solver.getPcnf().getNormalForm(), false));




        System.out.println("\n\tКарно!");
        KarnaughTable karnaughTable = new KarnaughTable();


        System.out.println(karnaughTable.minimize(solver.getLogicalTable(), LogicalExpressionSolver.getAllSymbols(),
                true));
        System.out.println(karnaughTable.minimize(solver.getLogicalTable(), LogicalExpressionSolver.getAllSymbols(),
                false));

    }
}