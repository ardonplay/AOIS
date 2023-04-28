package org.ardonplay.logic.logicalForms;

import java.util.List;
import java.util.Map;

public class Pcnf extends LogicalForm{

    public Pcnf(Map<String, List<Integer>> map, String LexemeString){
        this.externalConnection = '*';
        this.internalConnection = '+';
        this.index = 0;
        this.checker = 1;
        this.create(map, LexemeString);
    }
}
