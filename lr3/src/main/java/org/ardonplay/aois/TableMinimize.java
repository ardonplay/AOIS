package org.ardonplay.aois;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TableMinimize {

    final private AnalyticalMinimize minimize;

    public TableMinimize(){
        this.minimize = new AnalyticalMinimize();
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

    public void printTable(int[][] table){
        for (int[] ints : table) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
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

    public List<List<String>> tableMinimize(List<List<String>> constituents, boolean pdnf){
        List<List<String>> minimizeList = minimize.minimise(constituents, pdnf);

        List<List<String>> output =  new MinimizeList(pdnf);

        int[][] table = generateTable(minimizeList, constituents);

        printTable(table);

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
