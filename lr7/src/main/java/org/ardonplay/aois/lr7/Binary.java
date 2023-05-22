package org.ardonplay.aois.lr7;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Binary {
    private static final int size = 1 << 4;
    private final List<Integer> bites;

    public List<Integer> getBites() {
        return bites;
    }

    public int toDecimal() {
        int decimal = 0;

        for (int i = bites.size() - 1; i >= 0; i--) {
            if (bites.get(i) == 1) {
                decimal += Math.pow(2, bites.size() - 1 - i);
            }
        }

        return decimal;
    }

    private List<Integer> toBinary(int word){
        List<Integer> output = new ArrayList<>(size);

        while (word > 0) {
            int remainder = word % 2;
            output.add(0, remainder);
            word /= 2;
        }
        int zeroSize = size - output.size();

        for(int i =0; i < zeroSize; i++){
            output.add(0,0);
        }
        return output;
    }
    public Binary(int word){
        bites = toBinary(word);
    }

    public boolean more(Binary second){
        for(int i = 0; i < bites.size(); i++){
            if(bites.get(i) > second.bites.get(i)){
                return true;
            }
        }
        return false;
    }
    public boolean less(Binary second){
        for(int i = 0; i < bites.size(); i++){
            if(bites.get(i) < second.bites.get(i)){
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Binary binary1 = (Binary) o;
        return Objects.equals(bites, binary1.bites);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bites);
    }

    @Override
    public String toString() {
        return bites.toString();
    }

}
