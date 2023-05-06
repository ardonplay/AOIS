package org.ardonplay.aois;

import java.util.*;

public class TableMinimize {

    final private CalculationMinimize minimize;

    public TableMinimize(){
        this.minimize = new CalculationMinimize();
    }


    private int[][] generateTable(List<List<String>> minimizeList,List<List<String>> constituents){
        int[][] table = new int[minimizeList.size()][constituents.size()];

        for (int[] value : table) {
            Arrays.fill(value, 0);
        }

        for (int i =0; i < minimizeList.size(); i++){
            for(int j = 0; j < constituents.size(); j++){
                if(new HashSet<>(constituents.get(j)).containsAll(minimizeList.get(i))){
                    table[i][j] = 1;
                }
            }
        }
        return table;
    }

    public void printTable(int[][] table, List<List<String>>  minimizeList, List<List<String>> constituents){
        System.out.print("\t\t");
        for(List<String> constituent: constituents){
            System.out.print(constituent + "\t\t");
        }
        System.out.println();
        Iterator<List<String>> iterator = minimizeList.iterator();
        for (int[] ints : table) {
            System.out.print(iterator.next() + "\t\t");
            for (int anInt : ints) {
                System.out.print(anInt + "\t\t\t\t");
            }
            System.out.println();
        }
    }

    public List<Integer> findCoincidences(List<List<String>> minimizeList, int[][] table, int i, int k){
        List<Integer> coincidences = new ArrayList<>();
        for (int j = k; j < minimizeList.size(); j++) {
            if(table[j][i] == 1 && table[k][i] == 1){
                coincidences.add(1);
            }
        }
        for (int j = 0; j < k; j++) {
            if(table[j][i] == 1 && table[k][i] == 1){
                coincidences.add(1);
            }
        }
        return coincidences;
    }

    public List<List<String>> tableMinimize(List<List<String>> constituents, FormulaType type){
        List<List<String>> minimizeList = minimize.gluing(constituents);
        minimizeList = minimize.gluing(minimizeList);

        List<List<String>> output =  new MinimizeList(type);

        int[][] table = generateTable(minimizeList, constituents);

        printTable(table, minimizeList, constituents);

        for(int k = 0; k < minimizeList.size(); k++) {
            boolean unique = false;
            for (int i = 0; i < constituents.size(); i++) {
                List<Integer> coincidences = findCoincidences(minimizeList, table, i, k);

                if(coincidences.size() == 1){
                    unique = true;
                }
            }
            if(unique){
              output.add(minimizeList.get(k));
            }
            else {
                for (int i = 0; i < constituents.size(); i++) {
                    table[k][i] = 0;
                }
            }
        }


        return output;
    }
}
