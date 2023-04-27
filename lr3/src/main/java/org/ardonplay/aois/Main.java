package org.ardonplay.aois;


import org.ardonplay.LogicalExpressionSolver;
import org.ardonplay.aois.KarnoData.DataTable;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        LogicalExpressionSolver solver = new LogicalExpressionSolver("(A*B)+C");


        KarnoTable karnoTable = new KarnoTable();


        karnoTable.minimize(solver.getLogicalTable());




       //System.out.println(logicalTable);
//
//
//        System.out.println(solver);
//        AnalyticalMinimize minimize = new AnalyticalMinimize();
//
//        List<List<String>> pdnf = solver.getPdnf().getNormalForm();
//        List<List<String>> minimization = minimize.minimise(pdnf);
//        System.out.println(minimization);
//        System.out.println(minimize.minimiseSecond(minimization, true));
//
//
//
//        minimization = minimize.minimise(solver.getPcnf().getNormalForm());
//        System.out.println(minimization);
//        System.out.println(minimize.minimiseSecond(minimization, false));
//
//
//        TableMinimize tableMinimize = new TableMinimize();
//
//        System.out.println(tableMinimize.tableMinimize(solver.getPdnf().getNormalForm()));
//
//        System.out.println(tableMinimize.tableMinimize(solver.getPcnf().getNormalForm()));

        DataTable dataTable = new DataTable(new int[][]{
                {0,1,1,0}, {0, 1, 1, 0}});

        DataTable dataTable1 = new DataTable(new int[][]{{0,1,0,0}, {0,0,0,0}});

//        System.out.println(Arrays.deepToString(dataTable1.table()));
//
//        System.out.println(Arrays.deepToString(dataTable.table()));

        System.out.println(dataTable.contains(dataTable1));
//        System.out.println(dataTable.contains(dataTable1));
    }
}