package org.ardonplay.logic.logicalForms;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.ardonplay.logic.LogicalOperations.getFunctionIndexes;

@Getter
@Setter
public abstract class LogicalForm {

    protected List<Integer> indexes = new ArrayList<>();

    protected char internalConnection;

    protected char externalConnection;

    protected List<List<String>> normalForm = new ArrayList<>() {
        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();
            for (List<String> i : normalForm) {
                output.append("(");
                for (String j : i) {
                    output.append(j);
                    output.append(internalConnection);
                }
                output.replace(output.length() - 1, output.length(), "");
                output.append(")");
                output.append(externalConnection);
            }
            output.replace(output.length() - 1, output.length(), "");
            return String.valueOf(output);
        }
    };

    protected List<List<String>> binaryForm = new ArrayList<>() {
        @Override
        public String toString() {
            StringBuilder output = new StringBuilder();

            for (List<String> index : binaryForm) {
                for (String i : index) {
                    output.append(i);
                }
                output.append(externalConnection);
            }
            output.replace(output.length() - 1, output.length(), "");
            return String.valueOf(output);
        }
    };

    protected int index;

    protected void create(Map<String, List<Integer>> map, String LexemeString) {

        List<Integer> answer = map.get(LexemeString);

        List<Integer> indexes = new ArrayList<>();

        for (int i = 0; i < answer.size(); i++) {
            if (answer.get(i) == index) {
                indexes.add(i);
            }
        }

        for (Integer integer : indexes) {
            StringBuilder number = new StringBuilder();
            List<String> constiteunta = new ArrayList<>();
            List<String> binaryConstiteunta = new ArrayList<>();
            for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
                if (!Objects.equals(entry.getKey(), LexemeString)) {
                    number.append(entry.getValue().get(integer));
                    if (entry.getValue().get(integer) == 0) {
                        constiteunta.add("!" + entry.getKey());
                    } else {
                        constiteunta.add(entry.getKey());
                    }
                }
            }
            normalForm.add(constiteunta);
            number.reverse();
            binaryConstiteunta.add(String.valueOf(number));
            binaryForm.add(binaryConstiteunta);
        }
    }

    @Override
    public String toString() {
        return normalForm + "\n" + binaryForm + "\n" + getFunctionIndexes(String.valueOf(binaryForm));
    }
}
