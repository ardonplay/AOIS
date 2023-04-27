package org.ardonplay.aois;


import org.ardonplay.LogicalExpressionSolver;
import org.ardonplay.aois.KarnoData.DataTable;

import java.util.*;

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
        System.out.println(minimization);
        System.out.println(minimize.minimiseSecond(minimization, false));

        TableMinimize tableMinimize = new TableMinimize();

        System.out.println(tableMinimize.tableMinimize(solver.getPdnf().getNormalForm()));

        System.out.println(tableMinimize.tableMinimize(solver.getPcnf().getNormalForm()));

        KarnoTable karnoTable = new KarnoTable();


        System.out.println(karnoTable.minimize(solver.getLogicalTable(), LogicalExpressionSolver.getAllSymbols()));

    }
}