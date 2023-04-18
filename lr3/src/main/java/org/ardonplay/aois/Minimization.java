package minimization;

import operations.Operations;

import java.util.*;

public class Minimization {

    private static final List<String> topVars = new ArrayList<>(Arrays.asList("!B !C", "!B C", "B C", "B !C"));
    private static final List<String> leftVars = new ArrayList<>(Arrays.asList("!A", "A"));

    private static String reverse(String originalString){
        return (originalString.charAt(0)=='!') ? originalString.substring(1) : "!" + originalString;
    }

    private static List<String> divideString(String originalString, boolean isANDSeparator){
        String operator = isANDSeparator ? "&" : "\\|";
        return Arrays.asList(originalString.split(operator));
    }

    private static List<String> divideStringWithBrackets(String originalString, boolean isANDSeparator){
        List<String> res = new ArrayList<>();

        String reverseOperator = isANDSeparator ? "\\|" : "&";

        String[] termArray = originalString.split(reverseOperator);


        if (termArray.length > 1) {
            for (var term : termArray) {
                res.add(term.substring(1, term.length() - 1));
            }
        }
        else
            if (termArray.length!=0){
                res.add(originalString);
            }

        return res;
    }

    private static String findSharedPart(String s1, String s2, boolean isANDSeparator){
        char stringOperator = isANDSeparator ? '&' : '|';
        List<String> firstString = divideString(s1, isANDSeparator);
        List<String> secondString = divideString(s2, isANDSeparator);
        StringBuilder res = new StringBuilder();
        int counter = 0;
        for (String s : firstString) {
            for (String value : secondString) {
                if (s.equals(value)) {
                    counter++;
                    if (res.toString().equals("")) {
                        res.append(s);
                    } else res.append(stringOperator).append(s);
                }
            }
            if (counter == firstString.size()-1){
                break;
            }
        }
        return res.toString().equals("") || res.indexOf(String.valueOf(stringOperator)) == -1 ? "" : "("+res+")";
    }


    private static String gluing(String originalString, boolean isANDSeparator){
        char reverseOperator = isANDSeparator ? '|' : '&';
        StringBuilder resultString = new StringBuilder();

        List<String> functionalParts = divideStringWithBrackets(originalString, isANDSeparator);

        for (int i = 0; i<functionalParts.size(); i++){
            for (int j = i + 1; j < functionalParts.size(); j++) {
                if (!findSharedPart(functionalParts.get(i), functionalParts.get(j), isANDSeparator).equals("")) {
                    if (resultString.toString().equals("")) {
                        resultString.append(findSharedPart(functionalParts.get(i), functionalParts.get(j), isANDSeparator));
                    } else
                        resultString.append(reverseOperator).append(findSharedPart(functionalParts.get(i), functionalParts.get(j), isANDSeparator));
                }
            }
            functionalParts.remove(i);
            i--;
        }
        return resultString.toString();
    }

    private static int checkExcess(List<String> functionParts, int index, boolean isANDSeparator){
        List<List<String>> constituentsMatrix = new ArrayList<>();
        int res = -1;
        for (String functionPart : functionParts) {
            constituentsMatrix.add(divideString(functionPart, isANDSeparator));
        }
        List<String> tempPart = constituentsMatrix.get(index);
        for(int i =0; i < constituentsMatrix.size(); i++){
            if (unionString(constituentsMatrix.get(i), tempPart).size() == tempPart.size()-1 && checkReversed(constituentsMatrix.get(i), tempPart)){
                res = i;
            }
        }
        return res;
    }

    private static boolean checkReversed(List<String> expression1, List<String> expression2){
         for (var str : expression2){
             if (expression1.contains(reverse(str))){
                 return true;
             }
         }
         return false;
    }

    private static List<String> unionString(List<String> expression1, List<String> expression2){
         List<String> res = new ArrayList<>();
         for (var str : expression2){
             if (expression1.contains(str)){
                res.add(str);
             }
         }
         return res;
    }

    private static String fromListToNF(List<String> list, boolean isANDSeparator){
         String reversedOperator = !isANDSeparator ? "&" : "|";
         StringBuilder res = new StringBuilder();
         for (var el : list){
             if (res.toString().equals(""))
                res.append(el);
             else {
                 res.append(reversedOperator).append(el);
             }
         }
         return res.toString();
    }

    private static String NFtoTDF(String originalString, boolean isANDSeparator){
        List<String> functionParts = divideStringWithBrackets(originalString, isANDSeparator);
        StringBuilder res = new StringBuilder();
        String stringOperator = isANDSeparator ? "&" : "\\|";
        String reversedOperator = !isANDSeparator ? "&" : "|";
        for (int i = 0; i< functionParts.size(); i++){
            String unique = "";
            if (checkExcess(functionParts, i, isANDSeparator)!=-1){
                List<String> tempPartList = Arrays.asList(functionParts.get(i).split(stringOperator));
                List<String> checkedPartList = Arrays.asList(functionParts.get(checkExcess(functionParts, i, isANDSeparator)).split(stringOperator));
                String union = fromListToNF(unionString(tempPartList, checkedPartList), isANDSeparator);
                unique = unique + "("+union+")";
                functionParts.remove(checkExcess(functionParts, i, isANDSeparator));
                functionParts.remove(i);
                i--;
            }
            else unique = unique + "("+functionParts.get(i)+")";
            if (res.toString().equals("")){
                res.append(unique);
            }
            else {
                if (res.indexOf(unique) == -1) {
                    res.append(reversedOperator).append(unique);
                }
            }
        }
        return res.toString();
    }

    private static boolean exist (String stringWhat, String stringIn, boolean isANDSeparator){
         String reverseOperator = isANDSeparator ? "\\|" : "&";
         List<String> atomicFormulasInImplications = Arrays.asList(stringWhat.split(reverseOperator));
         List<String> atomicFormulasInConstituents = Arrays.asList(stringIn.substring(1, stringIn.length()-1).split(reverseOperator));
         for(var formulaInImplications : atomicFormulasInImplications){
            if (!atomicFormulasInConstituents.contains(formulaInImplications)){
                return false;
            }
         }
         return true;
    }

    private static void printTable(String SNF, boolean isANDSeparator){
        System.out.format("%20s", "----------");
        var constituents = divideString(SNF, !isANDSeparator);
        for (var str : constituents){
            System.out.format("%20s", str);
        }

        var implications = divideString(gluing(SNF, isANDSeparator), !isANDSeparator);

        List<List<Integer>> constituentsToImplications = new ArrayList<>();

        for (String implication : implications) {
            System.out.format("\n%20s", implication);
            List<Integer> thisConstituentImplications = new ArrayList<>();
            for (int constituentsIndex = 0; constituentsIndex < constituents.size(); constituentsIndex++) {
                if (exist(implication.substring(1, implication.length() - 1), constituents.get(constituentsIndex), !isANDSeparator)) {
                    System.out.format("%20s", "X");
                    thisConstituentImplications.add(constituentsIndex);
                } else System.out.format("%20s", "--");
            }
            constituentsToImplications.add(thisConstituentImplications);
        }

        List<Integer> resId = new ArrayList<>();

        Map <Integer, Boolean> switchedConstituents = createHashMap(constituents);

        for (Map.Entry<Integer, Boolean> entry : switchedConstituents.entrySet()) {
            if (getImplicationsOfConstituents(entry.getKey(), constituentsToImplications).size() == 1){
                switchedConstituents = turnOnConstituent(switchedConstituents , constituentsToImplications.get(getImplicationsOfConstituents(entry.getKey(), constituentsToImplications).get(0)));
                resId.add(getImplicationsOfConstituents(entry.getKey(), constituentsToImplications).get(0));
            }
        }


        while (!isStrongTable(switchedConstituents)){
            int bestChoice = chooseBestVariant(switchedConstituents, constituentsToImplications);
            switchedConstituents = turnOnConstituent(switchedConstituents , constituentsToImplications.get(bestChoice));
            resId.add(bestChoice);
        }

        printResult(implications, resId, isANDSeparator);
    }

    private static int chooseBestVariant(Map<Integer, Boolean> switchedConstituents, List<List<Integer>> constituentsToImplications) {

         Map<Integer, Integer> implicationsToNumberUnswitchedConstituents = new HashMap<>();
         List<Integer> unswitched = new ArrayList<>();
        for (Map.Entry<Integer, Boolean> entry : switchedConstituents.entrySet()) {
            if (!entry.getValue()) {
                unswitched.add(entry.getKey());
            }
        }

         for (int index = 0; index<constituentsToImplications.size(); index++){
             implicationsToNumberUnswitchedConstituents.put(index, intersection(unswitched, constituentsToImplications.get(index)).size());
         }

         return findMax(implicationsToNumberUnswitchedConstituents);
    }

    private static int findMax(Map<Integer, Integer> implicationsToNumberUnswitchedConstituents) {
        int max = -1;
        int index = -1;
        for (Map.Entry<Integer, Integer> entry : implicationsToNumberUnswitchedConstituents.entrySet()) {
            if (entry.getValue() >= max){
                max = entry.getValue();
                index = entry.getKey();
            }
        }
        return index;
    }

    private static List<Integer> intersection(List<Integer> list1, List<Integer> list2){
        List<Integer> intersection = new ArrayList<>(list1);
        intersection.retainAll(list2);
        return intersection;
    }

    private static Map<Integer, Boolean> turnOnConstituent(Map<Integer, Boolean> map, List<Integer> ids){
         for (var id : ids){
             map.put(id, true);
         }
         return map;
    }

    private static Map<Integer, Boolean> createHashMap(List<String> constituents) {
         Map<Integer, Boolean> res = new HashMap<>();
         for(int i = 0; i < constituents.size(); i++){
             res.put(i, false);
         }
         return res;
    }


    private static List<Integer> getImplicationsOfConstituents(int constituent, List<List<Integer>> constituentsToImplications){
         List<Integer> res = new ArrayList<>();
         for(int i =0;i<constituentsToImplications.size(); i++){
             if (constituentsToImplications.get(i).contains(constituent)){
                 res.add(i);
             }
         }
         return res;
    }

    private static boolean isStrongTable(Map<Integer, Boolean> switchedConstituents){
         for (boolean value : switchedConstituents.values()) {
            if (!value) {
                return false;
            }
        }
         return true;
    }

    private static void printResult(List<String> implications, List<Integer> resId, boolean isANDSeparator) {
         String reverseOperator = isANDSeparator ? "|" : "&";
         StringBuilder res = new StringBuilder();
        for (Integer integer : resId) {
            if (!res.toString().equals("")) {
                res.append(reverseOperator);
            }
            res.append(implications.get(integer));
        }
        System.out.println("\n"+res+"\nИтог минимизации: "+NFtoTDF(res.toString(), isANDSeparator));
    }

    private static String getCarnoVars(String[] variables){
        StringBuilder vars = new StringBuilder();
         for (int i = 0; i < variables.length / 2; i++) {
            vars.append(variables[i]);
        }
        vars.append("\\");
        for (int i = variables.length / 2; i < variables.length; i++) {
            vars.append(variables[i]);
        }
        return vars.toString();
    }

    private static void printTopVars(){
        for (var s : topVars) {
            System.out.format("%20s", s);
        }
    }

    private static void carnoMinimization(String[] variables, boolean[] result) {
        String vars = getCarnoVars(variables);
        System.out.format("%20s", vars);
        printTopVars();
        List<List<Boolean>> parts =createKMap(result);

        for(int i = 0; i< leftVars.size(); i++){
            System.out.println();
            System.out.format("%20s", leftVars.get(i));
            for (var b : parts.get(i)){
                System.out.format("%20s", Operations.fromBoolToInt(b));
            }
        }

        printCarno(parts);

    }

    private static List<String> carno(List<List<Boolean>> parts){
        List<String> res = new ArrayList<>();
        for(int i = 0; i< parts.size(); i++){
            for (int start =0; start < parts.get(i).size(); start++) {
                if (start != parts.get(i).size() - 1) {
                    for (int finish = parts.get(i).size() - 1; finish >= start; finish--) {
                        if (checkRow(parts.get(i), start, finish - start) && i != parts.size() - 1 && checkRow(parts.get(i + 1), start, finish - start)) {
                            if(!parts.get(i).get(start) && !parts.get(i).get(parts.get(i).size()-1) && !parts.get(i+1).get(start) && !parts.get(i+1).get(parts.get(i).size()-1)) {
                                var atomTopTerms = getArrayOfAtomTerms(start, finish - start, i);
                                var atomDownTerms = getArrayOfAtomTerms(start, finish - start, i + 1);
                                var union = unionString(atomDownTerms, atomTopTerms);
                                res.add("(" + fromListToNF(union, false) + ")");
                            }
                        } else {
                            if (checkRow(parts.get(i), start, finish - start) && start - finish != 0) {
                                var atomTerms = getArrayOfAtomTerms(start, finish - start, i);
                                res.add("(" + fromListToNF(atomTerms, false) + ")");
                            }
                        }
                    }
                }
                else {
                    if (parts.get(i).get(start) && i!=parts.size()-1 &&!parts.get(i+1).get(start)) {
                        int counter = 0;
                        for (int finish = parts.get(i).size() - 1; finish >= 0; finish--) {
                            if (checkRow(parts.get(i), 0, finish - 1)) {
                                var atomTerms = getArrayOfAtomTerms(0, finish - 1, i);
                                var buffer = getArrayOfAtomTerms(parts.get(i).size() - 1, 0, i);
                                var union = unionString(atomTerms, buffer);
                                res.add("(" + fromListToNF(union, false) + ")");
                                counter++;
                            }
                        }
                        if (counter == 0) {
                            if (checkRow(parts.get(i), start, 0) && i != parts.size() - 1 && checkRow(parts.get(i + 1), start, 0)) {
                                var atomTopTerms = getArrayOfAtomTerms(start, 0, i);
                                var atomDownTerms = getArrayOfAtomTerms(start, 0, i + 1);
                                var union = unionString(atomDownTerms, atomTopTerms);
                                res.add("(" + fromListToNF(union, false) + ")");
                            }
                        }
                    }
                    else {
                        if (parts.get(i).get(start) && i!=parts.size()-1 && parts.get(i+1).get(start)){
                            var atomTopVars =getArrayOfAtomTerms(start, 0, i);
                            var atomDownVars = getArrayOfAtomTerms(start, 0, i+1);
                            var uniqueTerms = unionString(atomDownVars, atomTopVars);
                            for (int finish = 0; finish<parts.get(i).size()-1; finish++){
                                var topVars = getArrayOfAtomTerms(0, finish, i);
                                var downVars = getArrayOfAtomTerms(0, finish, i+1);
                                var union = unionString(topVars, downVars);
                                res.add("("+fromListToNF(unionString(uniqueTerms, union), false)+")");
                            }
                        }
                    }
                }
            }
        }
        res = makeUnique(res);
        return res;
    }

    private static List<String> getArrayOfAtomTerms(int start, int count, int otherTerm){
         List<List<String>> allTerms = new ArrayList<>();
         for(int i =start; i< start+count+1; i++){
             var buff = new ArrayList<>(Arrays.asList(topVars.get(i).split(" ")));
             buff.add(leftVars.get(otherTerm));
             allTerms.add(buff);
         }
         List<String> res = new ArrayList<>(allTerms.get(0));
         for(int i = 1; i< allTerms.size(); i++){
             res = unionString(res, allTerms.get(i));
         }
         return res;
    }


    private static boolean isPower(double a, double b)
    {
        return (int)(Math.log(a)/Math.log(b))==(Math.log(a)/Math.log(b));
    }

    private static boolean checkRow(List<Boolean> list, int start, int count){
         if (isPower(count+1, 2)) {
             for (int i = start; i < start + count+1; i++) {
                 if (!list.get(i)) {
                     return false;
                 }
             }
             return true;
         }
         else return false;
    }

    private static void printCarno(List<List<Boolean>> parts) {
         List<String> res = carno(parts);
         printCarnoRes(res, true);
    }

    private static void printCarnoRes(List<String> res, boolean isANDSeparator) {
        StringBuilder result = new StringBuilder();
        String reverseOperator = isANDSeparator ? "|" : "&";
        for (var s : res){
            if (result.toString().equals("")){
                result = new StringBuilder(s);
            }
            else {
                result.append(reverseOperator).append(s);
            }
        }
        var r = NFtoTDF(result.toString(), isANDSeparator);
        System.out.println("\nИтог минимизации: "+r);
    }

    private static List<String> makeUnique(List<String> list){
        Set<String> set = new HashSet<>(list);
        list.clear();
        list.addAll(set);
        list.removeAll(Arrays.asList(null, "()"));
        return list;
    }

    private static List<List<Boolean>> createKMap(boolean[] result) {
        List<List<Boolean>> res = new ArrayList<>();
        int counter =0;
        for(int i =1; i<3; i++){
            List<Boolean> buff = new ArrayList<>();
            while (counter < result.length/2*i){
                if (result.length/2*i - counter == 2){
                    buff.add(result[counter+1]);
                }
                else
                    if (result.length/2*i - counter == 1){
                        buff.add(result[counter-1]);
                    }
                    else
                        buff.add(result[counter]);
                counter++;
            }
            res.add(buff);
        }
        return res;
    }


    public static void minimize(String[] variables, List<List<Boolean>> table, boolean[] result) {
        String DNF;
        String defaultSDNF = Operations.SDNF(variables, table, result).replaceAll(" ", "");
        String defaultSCNF = Operations.SKNF(variables, table, result).replaceAll(" ", "");
        String TDNF;
        String CNF;
        String TCNF;
        DNF = gluing(defaultSDNF, true);
        TDNF = NFtoTDF(DNF, true);
        CNF = gluing(defaultSCNF, false);
        TCNF = NFtoTDF(CNF, false);

        System.out.println("Расчётный метод:");
        System.out.println("Исходная функция в СДНФ: " + defaultSDNF);

        System.out.println( "ДНФ: " + DNF);
        System.out.println("ТДНФ: " +TDNF );
        System.out.println("Исходная функция в СКНФ: " + defaultSCNF);
        System.out.println("КНФ: " +CNF);
        System.out.println("ТКНФ: " + TCNF);

        System.out.println("\n\nРасчетно-табличный метод\nСДНФ: "+defaultSDNF+"\n");
        printTable(defaultSDNF, true);

        System.out.println("\n\nРасчетно-табличный метод\nСКНФ: "+defaultSCNF+"\n");
        printTable(defaultSCNF, false);

        System.out.println("\nТабличный метод");
        carnoMinimization(variables, result);
    }

}
