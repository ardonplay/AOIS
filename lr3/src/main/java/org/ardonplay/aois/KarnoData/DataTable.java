package org.ardonplay.aois.KarnoData;

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

}

