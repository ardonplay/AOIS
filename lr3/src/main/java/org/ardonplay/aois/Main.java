package org.ardonplay.aois;


import org.ardonplay.LogicalExpressionSolver;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        LogicalExpressionSolver solver = new LogicalExpressionSolver("(A*B)+C");


        System.out.println(solver);
        AnalyticalMinimize minimize = new AnalyticalMinimize();

        List<List<String>> pdnf = solver.getPdnf().getNormalForm();
        List<List<String>> minimization = minimize.minimise(pdnf);
        System.out.println(minimization);
        System.out.println(minimize.minimiseSecond(minimization, true));

        minimization = minimize.minimise(solver.getPcnf().getNormalForm());
        System.out.println();
        System.out.println(minimization);
        System.out.println(minimize.minimiseSecond(minimization, false));

        System.out.println("\n\n\n\n\n\n");

        TableMinimize tableMinimize = new TableMinimize();

        tableMinimize.tableMinimize(solver.getPcnf().getNormalForm());

    }
}