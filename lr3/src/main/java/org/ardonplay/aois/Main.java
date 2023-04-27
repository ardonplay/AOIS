package org.ardonplay.aois;


import org.ardonplay.LogicalExpressionSolver;
import org.ardonplay.aois.KarnoData.DataTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        LogicalExpressionSolver solver = new LogicalExpressionSolver("(A*B)+C");


        List<List<Integer>> logicalTable = new ArrayList<>();

        for(int i =0; i < solver.getMap().getTruthMap().get(solver.getExpressionText()).size(); i++){
            List<Integer> logicalRow = new ArrayList<>();
            for(String s: LogicalExpressionSolver.getAllSymbols()){
                logicalRow.add(solver.getMap().getTruthMap().get(s).get(i));
            }
            logicalRow.add(solver.getMap().getTruthMap().get(solver.getExpressionText()).get(i));
            logicalTable.add(logicalRow);
        }




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

//        DataTable dataTable = new DataTable(new int[][]{
//                {0,0,0,0}, {0, 0, 1, 1}});
//
//        DataTable dataTable1 = new DataTable(new int[][]{{1,1,1,1}, {0,0,0,1}});

//        System.out.println(Arrays.deepToString(dataTable1.table()));
//
//        System.out.println(Arrays.deepToString(dataTable.table()));

//        System.out.println(dataTable1.contains(dataTable));
//        System.out.println(dataTable.contains(dataTable1));
    }
}