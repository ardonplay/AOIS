package org.ardonplay.aois;

import org.ardonplay.logic.LogicalOperations;

import java.util.*;

import static org.ardonplay.logic.LogicalOperations.conjunction;
import static org.ardonplay.logic.LogicalOperations.disjunction;

public class AnalyticalMinimize {
    private final Map<String, Integer> symbols = new HashMap<>();

    public void addSymbols(List<String> expression){
        for (String variable : expression) {
            if (variable.contains("!")) {
                symbols.put(variable, 0);
                symbols.put(variable.substring(1), 1);
            } else {
                symbols.put(variable, 1);
                symbols.put("!" +variable, 0);
            }
        }
    }

    public void indexesCheck(List<Object> indexes, boolean sdnf){
        for (int index = 0; index < indexes.size() - 1; index++) {
            Object obj1 = indexes.get(index);
            Object obj2 = indexes.get(index+1);
            if (obj1 instanceof Integer && obj2 instanceof Integer) {
                if(!sdnf)
                    indexes.set(index, disjunction((int) obj1, (int) obj2));
                else
                    indexes.set(index, conjunction((int) obj1, (int) obj2));
                indexes.remove(obj2);
            }
            else if(obj1 instanceof Integer && obj2 instanceof String){
                    if((int) obj1 == 0){
                        indexes.set(index, 0);
                        indexes.remove(obj2);
                    } else if ((int) obj1 == 1) {
                       indexes.remove(obj2);
                        indexes.set(index, obj2);
                   }

            } else if (obj1 instanceof String && obj2 instanceof Integer) {

                    if((int) obj2 == 0){
                        indexes.set(index, obj2);
                        indexes.remove(obj1);
                    } else if ((int) obj2 == 1) {
                        indexes.remove(obj2);
                        indexes.set(index, obj1);
                    }

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
        List<List<String>> output = new ArrayList<>(constituents);

        for (int i = 0; i < output.size(); i++) {
            addSymbols(output.get(i));
            List<Object> indexes = new ArrayList<>();

            for (List<String> strings : output) {
                if (output.get(i) != strings) {
                    solve(strings, indexes, sdnf);
                }
            }

            indexesCheck(indexes, sdnf);

            if(indexes.size() != 0 && indexes.get(0) instanceof Integer){
                output.remove(i);
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
                    System.out.println(index);
                    List<String> tempList = new ArrayList<>(constituents.get(j));
                    tempList.remove(index);
                    output.add(tempList);
                }
            }
        }


        return output;
    }
}
