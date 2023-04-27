package org.ardonplay.aois.KarnoData;

import java.util.Arrays;

public record DataTable(int[][] table) {

    public boolean contains(DataTable compared) {

        for (int i = 0; i < compared.table.length; i++) {
            for (int j = 0; j < compared.table[i].length; j++) {
                if (compared.table[i][j] == 1) {
                    if(table[i][j] != compared.table[i][j]){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public void reverse(){
        for(int i =0; i < table.length; i++){
            for(int j =0; j < table[i].length; j++){
                if(table[i][j] == 1){
                    table[i][j] = 0;
                }
                else {
                    table[i][j] = 1;
                }
            }
        }
    }

}

