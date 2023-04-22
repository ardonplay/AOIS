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
        List<List<String>> output = minimize.minimise(constituents);

        int[][] table = new int[constituents.size()][output.size()];

        for (int[] value : table) {
            Arrays.fill(value, 0);
        }


        for (int i =0; i < constituents.size(); i++){
            for(int j = 0; j < output.size(); j++){
              if(new HashSet<>(constituents.get(i)).containsAll(output.get(j))){
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
        return output;
    }
}
