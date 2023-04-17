package org.ardonplay.logic.logicalForms;

import java.util.List;
import java.util.Map;

public class Pdnf extends LogicalForm{


    public Pdnf(Map<String, List<Integer>> map, String LexemeString){
        this.externalConnection = '+';
        this.internalConnection = '*';
        this.index = 1;
        this.create(map, LexemeString);
    }
}
