package org.ardonplay.aois.KarnaughTableData;

import java.util.*;

public class KarnaughtGenerator {

    private List<Integer> iterator(List<Integer> binaryList1) {

        List<Integer> binaryList2 = new ArrayList<>(binaryList1);

        Collections.fill(binaryList2, 0);

        binaryList2.set(binaryList2.size() - 1, 1);

        int maxLength = Math.max(binaryList1.size(), binaryList2.size());
        List<Integer> result = new ArrayList<>(Collections.nCopies(maxLength, 0));
        int carry = 0;

        for (int i = 0; i < maxLength; i++) {
            int digit1 = i < binaryList1.size() ? binaryList1.get(binaryList1.size() - i - 1) : 0;
            int digit2 = i < binaryList2.size() ? binaryList2.get(binaryList2.size() - i - 1) : 0;
            int sum = digit1 + digit2 + carry;
            result.set(maxLength - i - 1, sum % 2);
            carry = sum / 2;
        }

        if (carry != 0) {
            result.add(0, carry);
        }

        return result;
    }

    public Map<List<Integer>, List<Integer>> generateLogicalIndexes(List<String> symbols) {
        Map<List<Integer>, List<Integer>> logicalIndexes = new HashMap<>();
        int rows = (int) Math.pow(2, (float) (symbols.size() / 2));
        int columns = (int) Math.pow(2, (float) (symbols.size() - (symbols.size() / 2)));

        List<Integer> value = Collections.nCopies(symbols.size(), 0);

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns / 2; j++) {
                logicalIndexes.put(List.of(i, j), value);
                value = iterator(value);
            }

            for (int j = columns - 1; j > columns / 2 - 1; j--) {
                logicalIndexes.put(List.of(i, j), value);
                value = iterator(value);
            }
        }

        return logicalIndexes;
    }

    public List<DataTable> generateDataSet(int rows, int colums){

        int magicNumber = 3;
        int size = (int) Math.pow(magicNumber, (rows+colums));
        List<DataTable> dataTables = new ArrayList<>(size);


        System.out.println((int) Math.pow(magicNumber, (float)(rows+colums)/2));

        return dataTables;
    }


}
