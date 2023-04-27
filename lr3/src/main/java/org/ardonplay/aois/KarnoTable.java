package org.ardonplay.aois;

import org.ardonplay.aois.KarnoData.DataSet;
import org.ardonplay.aois.KarnoData.DataTable;

import java.util.*;

public class KarnoTable {
    final  private Map<List<Integer>, List<Integer>> logicalIndexes;

    final private DataSet dataSet;

    private void setLogicalIndexes(){
        logicalIndexes.put(Arrays.asList(0,0), Arrays.asList(0,0,0));
        logicalIndexes.put(Arrays.asList(0,1), Arrays.asList(0,0,1));
        logicalIndexes.put(Arrays.asList(0,2), Arrays.asList(0,1,1));
        logicalIndexes.put(Arrays.asList(0,3), Arrays.asList(0,1,0));
        logicalIndexes.put(Arrays.asList(1,0), Arrays.asList(1,0,0));
        logicalIndexes.put(Arrays.asList(1,1), Arrays.asList(1,0,1));
        logicalIndexes.put(Arrays.asList(1,2), Arrays.asList(1,1,1));
        logicalIndexes.put(Arrays.asList(1,3), Arrays.asList(1,1,0));
    }
    public KarnoTable(){
        this.logicalIndexes = new HashMap<>();
        this.dataSet = new DataSet();
        setLogicalIndexes();
    }


    public List<List<String>> minimize(Map<List<Integer>, Integer> logicalTable){
        List<List<String>> output = new ArrayList<>();

        int[][] karnoTable = new int[2][4];

        for(int i =0; i< karnoTable.length; i++){
            for(int j =0; j < karnoTable[i].length; j++){
                karnoTable[i][j] = logicalTable.get(logicalIndexes.get(Arrays.asList(i,j)));
            }
        }

        DataTable table = new DataTable(karnoTable);

        for(DataTable dataTable: dataSet.getTables()){
            System.out.println(table.contains(dataTable));
        }

        for (int[] ints : karnoTable) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

        return output;
    }
}
