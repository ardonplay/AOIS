package org.ardonplay.aois;

import java.util.ArrayList;
import java.util.List;

public class MinimizeList extends ArrayList<List<String>>{

    final private char externalConnection;
    final private char internalConnection;


    public MinimizeList(char internalConnection, char externalConnection){
        super();
        this.externalConnection = externalConnection;
        this.internalConnection = internalConnection;
    }

    public MinimizeList(boolean pdnf){
        super();
        if(pdnf){
            this.externalConnection = '+';
            this.internalConnection = '*';
        }
        else {
            this.externalConnection = '*';
            this.internalConnection = '+';
        }
    }

    public MinimizeList(FormulaType type) {
        super();
        if(type == FormulaType.PDNF){
            this.externalConnection = '+';
            this.internalConnection = '*';
        }
        else {
            this.externalConnection = '*';
            this.internalConnection = '+';
        }
    }

    public MinimizeList(List<List<String>> minimization, FormulaType type) {
        super(minimization);

        if(type == FormulaType.PDNF){
            this.externalConnection = '+';
            this.internalConnection = '*';
        }
        else {
            this.externalConnection = '*';
            this.internalConnection = '+';
        }
    }

    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (List<String> i : this) {
            string.append("(");
            for (String j : i) {
                string.append(j);
                string.append(internalConnection);
            }
            string.replace(string.length() - 1, string.length(), "");
            string.append(")");
            string.append(externalConnection);
        }
        string.replace(string.length() - 1, string.length(), "");
        return String.valueOf(string);
    }
}
