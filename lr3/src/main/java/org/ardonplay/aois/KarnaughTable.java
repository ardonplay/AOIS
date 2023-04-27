package org.ardonplay.aois;

import org.ardonplay.aois.KarnaughTableData.DataSet;
import org.ardonplay.aois.KarnaughTableData.DataTable;

import java.util.*;

public class KarnaughTable {
    final private Map<List<Integer>, List<Integer>> logicalIndexes;

    final private DataSet dataSet;

    int WEIGHT = 4;
    int HEIGHT = 2;

    private void setLogicalIndexes() {
        logicalIndexes.put(Arrays.asList(0, 0), Arrays.asList(0, 0, 0));
        logicalIndexes.put(Arrays.asList(0, 1), Arrays.asList(0, 0, 1));
        logicalIndexes.put(Arrays.asList(0, 2), Arrays.asList(0, 1, 1));
        logicalIndexes.put(Arrays.asList(0, 3), Arrays.asList(0, 1, 0));
        logicalIndexes.put(Arrays.asList(1, 0), Arrays.asList(1, 0, 0));
        logicalIndexes.put(Arrays.asList(1, 1), Arrays.asList(1, 0, 1));
        logicalIndexes.put(Arrays.asList(1, 2), Arrays.asList(1, 1, 1));
        logicalIndexes.put(Arrays.asList(1, 3), Arrays.asList(1, 1, 0));
    }

    public KarnaughTable() {
        this.logicalIndexes = new HashMap<>();
        this.dataSet = new DataSet();
        setLogicalIndexes();
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


    private List<String> indexesToString(Map<Integer, Integer> commonIndexes, List<String> symbols) {
        List<String> stringIndex = new ArrayList<>();
        for (Integer index : commonIndexes.keySet()) {
            if (commonIndexes.get(index) == 1) {
                stringIndex.add(symbols.get(index));
            } else {
                stringIndex.add("!" + symbols.get(index));
            }
        }
        return stringIndex;
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
        int[][] karnaughTable = new int[HEIGHT][WEIGHT];

        for (int i = 0; i < karnaughTable.length; i++) {
            for (int j = 0; j < karnaughTable[i].length; j++) {
                karnaughTable[i][j] = logicalTable.get(logicalIndexes.get(Arrays.asList(i, j)));
            }
        }
        return new DataTable(karnaughTable);
    }



    public List<List<String>> minimize(Map<List<Integer>, Integer> logicalTable, List<String> symbols, boolean pdnf) {
        List<List<String>> output = new MinimizeList(pdnf);

        DataTable table = generateKarnaughTable(logicalTable);

        if (!pdnf) {
            table.reverse();
        }

        List<DataTable> figures = getFigures(table);


        for (DataTable dataTable : figures) {
            List<List<Integer>> indexes = getIndexes(dataTable);

            Map<Integer, Integer> commonIndexes = getCommonIndexes(indexes);

            output.add(indexesToString(commonIndexes, symbols));

        }

        return output;
    }
}
