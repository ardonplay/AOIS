package org.ardonplay.aois;

import org.ardonplay.logic.LogicalOperations;

import java.util.*;

public class AnalyticalMinimize {
    private final Map<String, Integer> symbols = new HashMap<>();

    public void addSymbols(List<String> expression){
        for (String variable : expression) {
            if (variable.contains("!")) {
                symbols.put(variable, 1);
                symbols.put(variable.substring(1), 0);
            } else {
                symbols.put(variable, 1);
                symbols.put("!" +variable, 0);
            }
        }
    }

    private int stringSum(String first, String second){
        if(Objects.equals(first, "!" + second) ||
                Objects.equals("!" + first, second)){
            return 1;
        }
        else {
            return 0;
        }
    }

    private void indexChecker(List<Object> indexes){
        for(int i =0; i < indexes.size() -1; i++){
            Object current = indexes.get(i);
            Object next = indexes.get(i+1);

            if(current instanceof String && next instanceof String){
                if(Objects.equals(current, next)){
                    indexes.remove(i+1);
                }
                else {
                    indexes.set(i, stringSum((String) current, (String) next));
                    indexes.remove(i + 1);
                }
                i--;
            }
            else if(current instanceof Integer && next instanceof Integer){
                indexes.set(i, (int) current + (int) next);
                indexes.remove(i + 1);
                i--;
            }
            else if(current instanceof Integer && next instanceof String){
                indexes.set(i, next);
                indexes.remove(i + 1);
                i--;
            }
            else if(current instanceof String && next instanceof Integer){
                indexes.set(i, current);
                indexes.remove(i + 1);
                i--;
            }
        }
    }
    public void solve(List<String> strings, List<Object> indexes, boolean sdnf){
        List<Integer> tempList = new ArrayList<>();

        String var = "";
        for (String variable : strings) {
            if (symbols.containsKey(variable)) {
                tempList.add(symbols.get(variable));
                if (tempList.size() == 2) {
                    if(sdnf) {
                        tempList.set(0, LogicalOperations.conjunction(tempList.get(0), tempList.get(1)));
                    }
                    else {
                        tempList.set(0, LogicalOperations.disjunction(tempList.get(0), tempList.get(1)));
                    }
                    tempList.remove(1);
                }
            } else {
                var = variable;
            }
        }
        if (tempList.size() != 0) {
            if (tempList.get(0) == 0) {
                indexes.add(tempList.get(0));
            } else {
                if (!Objects.equals(var, ""))
                    indexes.add(var);
                else {
                    indexes.add(tempList.get(0));
                }
            }
        }

    }

    public List<List<String>> minimiseSecond(List<List<String>> constituents, boolean sdnf) {
        List<List<String>> output = new ArrayList<>();

        for (int i = 0; i < constituents.size(); i++) {
            addSymbols(constituents.get(i));

            List<Object> indexes = new ArrayList<>();

            for (List<String> strings : constituents) {
                if (constituents.get(i) != strings) {
                    solve(strings, indexes, sdnf);
                }
            }
            indexChecker(indexes);

            if(indexes.get(0) instanceof String){
              output.add(constituents.get(i));
            }
            symbols.clear();
        }
        return output;
    }


    public List<List<String>> minimise(List<List<String>> constituents) {
        List<List<String>> output = new ArrayList<>();

        if (constituents.size() == 1) {
            return output;
        }
//        constituents.sort((list1, list2) -> {
//            int totalLength1 = 0;
//            for (String s : list1) {
//                totalLength1 += s.length();
//            }
//            int totalLength2 = 0;
//            for (String s : list2) {
//                totalLength2 += s.length();
//            }
//            return Integer.compare(totalLength2, totalLength1);
//        });

        for (int i = 0; i < constituents.size(); i++) {
            for (int j = i+1; j < constituents.size(); j++) {
                List<String> variable1 = constituents.get(i);
                List<String> variable2 = constituents.get(j);
                int count = 0;
                int index = 0;
                boolean finded = false;
                for (int k = 0; k < variable1.size(); k++) {
                    if (Objects.equals(variable1.get(k), "!" + variable2.get(k)) ||
                            Objects.equals("!" + variable1.get(k), variable2.get(k))) {
                        count++;
                    }
                    if(count == 1 && !finded){
                        index = k;
                        finded = true;
                    }
                }
                if(count == 1){
                    List<String> tempList = new ArrayList<>(constituents.get(j));
                    tempList.remove(index);
                    output.add(tempList);
                }
            }
        }


        return output;
    }
}