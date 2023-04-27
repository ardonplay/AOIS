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


    public List<List<String>> minimize(Map<List<Integer>, Integer> logicalTable, List<String> symbols, boolean pdnf){
        List<List<String>> output = new MinimizeList(pdnf);

        int WEIGHT = 4;
        int HEIGHT = 2;

        int[][] karnoTable = new int[HEIGHT][WEIGHT];

        for(int i =0; i< karnoTable.length; i++){
            for(int j =0; j < karnoTable[i].length; j++){
                karnoTable[i][j] = logicalTable.get(logicalIndexes.get(Arrays.asList(i,j)));
            }
        }

        DataTable table = new DataTable(karnoTable);


        if(!pdnf){
            table.reverse();
            //dataSet.reverse();
        }

        List<DataTable> figures = new ArrayList<>();
        for(DataTable dataTable: dataSet.getTables()){
            if (table.contains(dataTable)){
                if(!figures.isEmpty()) {
                    List<Boolean> booleans = new ArrayList<>();
                    for (DataTable figure : figures) {
                        booleans.add(figure.contains(dataTable));
                    }
                    if(!booleans.contains(true)){
                        figures.add(dataTable);
                    }
                }
                else {
                    figures.add(dataTable);
                }
            }
        }


        for(DataTable dataTable: figures){
            List<List<Integer>> indexes = new ArrayList<>();
            for(int i =0; i < dataTable.table().length; i++){
                for(int j =0; j < dataTable.table()[i].length; j++){
                    if(dataTable.table()[i][j] == 1){
                        indexes.add(logicalIndexes.get(Arrays.asList(i, j)));
                    }
                }
            }


            Map<Integer, Integer> commonIndexes = new HashMap<>();


            for (int i = 0; i < indexes.get(0).size(); i++) {
                boolean isCommon = true;
                int commonValue = indexes.get(0).get(i);
                for (int j = 1; j < indexes.size(); j++) {
                    if (indexes.get(j).get(i) != commonValue) {
                        isCommon = false;
                        break;
                    }
                }
                if (isCommon) {
                    commonIndexes.put(i, commonValue);
                }
            }
            List<String> stringIndex = new ArrayList<>();
            for(Integer index: commonIndexes.keySet()){
                if(commonIndexes.get(index) == 1) {
                    stringIndex.add(symbols.get(index));
                }
                else {
                    stringIndex.add("!" + symbols.get(index));
                }
            }
            output.add(stringIndex);

        }

        return output;
    }
}
