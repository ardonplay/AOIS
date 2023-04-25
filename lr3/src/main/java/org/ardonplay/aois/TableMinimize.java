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

    public List<List<String>> tableMinimize(List<List<String>> constituents){

        List<List<String>> minimizeList = minimize.minimise(constituents);
        List<List<String>> output =  new ArrayList<>();

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

        for (int[] ints : table) {
            for (int anInt : ints) {
                System.out.print(anInt + " ");
            }
            System.out.println();
        }

        for(int k = 0; k < minimizeList.size(); k++) {
            boolean unique = false;
            for (int i = 0; i < constituents.size(); i++) {
                List<Integer> ogo = new ArrayList<>();
                for (int j = k; j < minimizeList.size(); j++) {
                    if(table[j][i] == 1 && table[k][i] == 1){
                        ogo.add(1);
                    }
                }
                for (int j = 0; j < k; j++) {
                    if(table[j][i] == 1 && table[k][i] == 1){
                        ogo.add(1);
                    }
                }
                if(ogo.size() == 1){
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
