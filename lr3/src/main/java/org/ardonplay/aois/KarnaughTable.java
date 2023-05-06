//由Vladimir Moshchuk制作，或ardonplay，2023。 白俄罗斯明斯克
//
//此类描述用于最小化sdnf和sknf的表格(Karnaught)方法
package org.ardonplay.aois;

import org.ardonplay.aois.KarnaughTableData.DataSet;
import org.ardonplay.aois.KarnaughTableData.DataTable;
import org.ardonplay.aois.KarnaughTableData.KarnaughtGenerator;

import java.io.IOException;
import java.util.*;

public class KarnaughTable {
    final private Map<List<Integer>, List<Integer>> logicalIndexes;

    final private DataSet dataSet;


    final private int rows;
    final private  int columns;



    public KarnaughTable(List<String> symbols) {
        this.dataSet = new DataSet();
        rows = (int) Math.pow(2, (float) (symbols.size() / 2));
        columns = (int) Math.pow(2, (float) (symbols.size() - (symbols.size() / 2)));
        KarnaughtGenerator generator = new KarnaughtGenerator(rows, columns);
        this.logicalIndexes = generator.generateLogicalIndexes(symbols);
    }

    private List<DataTable> getFigures(DataTable table) {
        List<DataTable> figures = new ArrayList<>();
        for (DataTable dataTable : dataSet.getTables()) {
            if (table.contains(dataTable)) {
                if (!figures.isEmpty()) {
                    List<Boolean> booleans = new ArrayList<>();
                    for (DataTable figure : figures) {
                        booleans.add(figure.contains(dataTable));
                    }
                    if (!booleans.contains(true)) {
                        figures.add(dataTable);
                    }
                } else {
                    figures.add(dataTable);
                }
            }
        }
        return figures;
    }


    private List<String> indexesToString(Map<Integer, Integer> commonIndexes, List<String> symbols, FormulaType type) {
        List<String> stringIndex = new ArrayList<>();
        for (Integer index : commonIndexes.keySet()) {
            if(type == FormulaType.PDNF) {
                if (commonIndexes.get(index) == 1) {
                    stringIndex.add(symbols.get(index));
                } else {
                    stringIndex.add("!" + symbols.get(index));
                }
            }
            else {
                if (commonIndexes.get(index) == 0) {
                    stringIndex.add(symbols.get(index));
                } else {
                    stringIndex.add("!" + symbols.get(index));
                }
            }
        }
        return stringIndex;
    }

    public void printTable(DataTable table, List<String> symbols){
        int leftSymbolsSize = (int) Math.pow(2, (float) (symbols.size() / 2))/2;
        List<String> leftSymbols = new ArrayList<>();
        for(int i =0; i < leftSymbolsSize; i++){
            leftSymbols.add(symbols.get(i));
            leftSymbols.add("!" + symbols.get(i));
        }


        List<String> topSymbols = new ArrayList<>(Arrays.asList("!B!C", "!BC", "BC", "B!C"));

        System.out.print("  ");
        for(String topSymbol: topSymbols){
            System.out.print(topSymbol + "\t\t");
        }
        System.out.println();
        Iterator<String> iterator = leftSymbols.iterator();
        for(int i =0; i < table.table().length; i++){
            System.out.print(iterator.next() + "\t");
            for(int j =0; j< table.table()[i].length; j++){
                System.out.print(table.table()[i][j] + "\t\t");
            }
            System.out.println();
        }
    }
    private Map<Integer, Integer> getCommonIndexes(List<List<Integer>> indexes) {
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
        return commonIndexes;
    }

    public List<List<Integer>> getIndexes(DataTable dataTable) {
        List<List<Integer>> indexes = new ArrayList<>();

        for (int i = 0; i < dataTable.table().length; i++) {
            for (int j = 0; j < dataTable.table()[i].length; j++) {
                if (dataTable.table()[i][j] == 1) {
                    indexes.add(logicalIndexes.get(Arrays.asList(i, j)));
                }
            }
        }
        return indexes;
    }

    public DataTable generateKarnaughTable(Map<List<Integer>, Integer> logicalTable) {
        int[][] karnaughTable = new int[rows][columns];

        for (int i = 0; i < karnaughTable.length; i++) {
            for (int j = 0; j < karnaughTable[i].length; j++) {
                karnaughTable[i][j] = logicalTable.get(logicalIndexes.get(Arrays.asList(i, j)));
            }
        }
        return new DataTable(karnaughTable);
    }



    public List<List<String>> minimize(Map<List<Integer>, Integer> logicalTable, List<String> symbols, FormulaType type) throws IOException {
        if(symbols.size() > 3){
           throw new IOException();
        }
        List<List<String>> output = new MinimizeList(type);

        DataTable table = generateKarnaughTable(logicalTable);

        if (type != FormulaType.PDNF) {
            table.reverse();
        }

        List<DataTable> figures = getFigures(table);


        for (DataTable dataTable : figures) {
            List<List<Integer>> indexes = getIndexes(dataTable);

            Map<Integer, Integer> commonIndexes = getCommonIndexes(indexes);

            output.add(indexesToString(commonIndexes, symbols, type));

        }
        printTable(table, symbols);
        return output;
    }
}
