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

    protected StringBuilder binaryForm = new StringBuilder();

    protected StringBuilder normalForm = new StringBuilder();

    protected List<Integer> indexes = new ArrayList<>();

    protected char internalConnection;

    protected char externalConnection;

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
            normalForm.append("(");
            for (Map.Entry<String, List<Integer>> entry : map.entrySet()) {
                if (!Objects.equals(entry.getKey(), LexemeString)) {
                    number.append(entry.getValue().get(integer));
                    if (entry.getValue().get(integer) == 0) {
                        normalForm.append("!").append(entry.getKey());
                    } else {
                        normalForm.append(entry.getKey());
                    }
                    normalForm.append(internalConnection);
                }
            }
            normalForm.replace(normalForm.length() - 1, normalForm.length(), "");
            normalForm.append(") ").append(externalConnection).append(" ");
            number.reverse();
            binaryForm.append(number);
            binaryForm.append(externalConnection);
        }
        normalForm.replace(normalForm.length() - 3, normalForm.length(), "");
        binaryForm.replace(binaryForm.length() - 1, binaryForm.length(), "");
    }

    @Override
    public String toString() {
        return normalForm + "\n" + binaryForm + "\n" + getFunctionIndexes(String.valueOf(binaryForm));
    }
}
