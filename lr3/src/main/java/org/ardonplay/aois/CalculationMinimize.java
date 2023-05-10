//由Vladimir Moshchuk制作，或ardonplay，2023。 白俄罗斯明斯克
//
//此类描述了最小化sdnf和sknf的计算方法
package org.ardonplay.aois;


import org.ardonplay.logic.LogicalOperations;

import java.util.*;
import java.util.stream.Collectors;

public class CalculationMinimize {

    public List<List<String>> minimise(List<List<String>> constituents, FormulaType type) {
        List<List<String>> gl = infinityGluing(constituents);
        gl = removeRedundancy(gl, type);
        gl = infinityGluing(gl);
        return new MinimizeList(gl, type);
    }

    public List<List<String>> infinityGluing(List<List<String>> constituents) {
        List<List<String>> previousList = new ArrayList<>();
        List<List<String>> currentList = gluing(constituents);

        while (!previousList.equals(currentList)) {
            previousList = new ArrayList<>(currentList);
            currentList = gluing(previousList);
        }

        return currentList;
    }


    public List<List<String>> gluing(List<List<String>> constituents) {
        Set<List<String>> result = new HashSet<>(constituents);
        Set<List<String>> exclude = new HashSet<>();

        for (int i = 0; i < constituents.size(); i++) {
            for (int j = 0; j < constituents.size(); j++) {
                if (i == j)
                    continue;
                List<String> fusion = tryGluing(constituents.get(i), constituents.get(j));
                if (fusion != null) {
                    exclude.add(constituents.get(i));
                    exclude.add(constituents.get(j));
                    result.add(fusion);

                }
            }
        }
        result.removeAll(exclude);
        return result.stream().toList();
    }

    public boolean isOpposite(String firstTerm, String secondTerm) {
        return Objects.equals("!" + firstTerm, secondTerm) || Objects.equals(firstTerm, "!" + secondTerm);
    }

    private List<String> tryGluing(List<String> first, List<String> second) {
        int differencesCount = 0;
        int differenceIndex = -1;
        if (first.size() == 1 || second.size() == 1)
            return null;

        for (int i = 0; i < first.size(); i++) {
            String term1 = first.get(i);
            String term2 = second.get(i);

            if (!term1.equals(term2)) {
                if (isOpposite(term1, term2)) {
                    differencesCount++;
                    differenceIndex = i;

                    if (differencesCount > 1) {
                        return null;
                    }
                } else return null;
            }
        }

        if (differencesCount == 1) {
            List<String> result = new ArrayList<>(first);
            result.remove(first.get(differenceIndex));
            return result;
        } else {
            return null;
        }
    }

    private List<List<String>> removeRedundancy(List<List<String>> constituents, FormulaType type) {
        if (constituents.size() == 1)
            return constituents;

        List<List<String>> result = new ArrayList<>(constituents);
        Set<List<String>> exclude = new HashSet<>();


        for (int i = 0; i < constituents.size(); i++) {
            List<String> implicant = constituents.get(i);
            Map<String, Integer> valuesMap = createValuesMap(implicant, type);

            boolean formulaValue = formulaValue(
                    constituents.stream().filter(c -> c != implicant).collect(Collectors.toList()), valuesMap, type);

            if (formulaValue) {
                exclude.add(implicant);
            }
        }
        result.removeAll(exclude);
        return result;
    }

    private boolean formulaValue(List<List<String>> constituents, Map<String, Integer> valuesMap, FormulaType type) {
        List<Boolean> tempList = new ArrayList<>(List.of(false));
        for (List<String> implicant : constituents) {
            tempList.add(implicantValue(implicant, valuesMap, type));

            if (type == FormulaType.PDNF) {
                tempList.set(0, tempList.get(0) && tempList.get(1));
            } else {
                tempList.set(0, tempList.get(0) || tempList.get(1));
            }
            tempList.remove(1);
        }
        return (type == FormulaType.PDNF) == tempList.get(0);
    }

    private Map<String, Integer> createValuesMap(List<String> implicant, FormulaType type) {
        Map<String, Integer> valuesMap = new HashMap<>();

        for (String term : implicant) {
            if (term.startsWith("!")) {
                if (type == FormulaType.PDNF) {
                    valuesMap.put(term, 1);
                    valuesMap.put(term.substring(1), 0);
                } else if (type == FormulaType.PCNF) {
                    valuesMap.put(term, 0);
                    valuesMap.put(term.substring(1), 1);
                }
            } else {
                if (type == FormulaType.PDNF) {
                    valuesMap.put(term, 1);
                    valuesMap.put("!" + term, 0);
                } else if (type == FormulaType.PCNF) {
                    valuesMap.put(term, 0);
                    valuesMap.put("!" + term, 1);
                }
            }
        }
        return valuesMap;
    }

    private boolean implicantValue(List<String> implicant, Map<String, Integer> valuesMap, FormulaType type) {
        switch (type) {
            case PDNF -> {
                return disjunction(implicant, valuesMap) == 1;
            }
            case PCNF -> {
                return conjunction(implicant, valuesMap) == 0;
            }
            default -> {
                return false;
            }
        }
    }

    static class FirstAndSecond {
        Integer first;
        Integer second;

        public FirstAndSecond(Integer first, Integer second) {
            this.first = first;
            this.second = second;
        }
    }

    private FirstAndSecond firstAndSecondToIntegerValue(FirstAndSecond firstAndSecond, List<String> implicant) {
        firstAndSecond.first = Objects.requireNonNullElseGet(firstAndSecond.first, () -> implicant.get(0).startsWith("!") ? 1 : 0);
        firstAndSecond.second = Objects.requireNonNullElseGet(firstAndSecond.second, () -> implicant.get(1).startsWith("!") ? 1 : 0);
        return firstAndSecond;
    }

    private Integer disjunction(List<String> implicant, Map<String, Integer> valuesMap) {
        if (implicant.size() > 2) {

            FirstAndSecond firstAndSecond = new FirstAndSecond(valuesMap.get(implicant.get(0)),
                    valuesMap.get(implicant.get(0)));
            firstAndSecondToIntegerValue(firstAndSecond, implicant);
            return LogicalOperations.conjunction(firstAndSecond.first, firstAndSecond.second);
        } else {
            return 1;
        }
    }

    private Integer conjunction(List<String> implicant, Map<String, Integer> valuesMap) {
        if (implicant.size() > 2) {
            FirstAndSecond firstAndSecond = new FirstAndSecond(valuesMap.get(implicant.get(0)),
                    valuesMap.get(implicant.get(0)));
            firstAndSecondToIntegerValue(firstAndSecond, implicant);
            return LogicalOperations.disjunction(firstAndSecond.first, firstAndSecond.second);
        } else {
            return 0;
        }
    }
}
