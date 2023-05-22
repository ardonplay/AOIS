package org.ardonplay.aois.lr7;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Binary {
    private static final int size = 1 << 4;
    private final List<Integer> binary;

    private List<Integer> toBinary(int word){
        List<Integer> output = new ArrayList<>(size);
        if(word == 0){
            output.add(0);
            return output;
        }

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
        binary = toBinary(word);
    }

    public boolean more(Binary second){
        for(int i =0; i < binary.size(); i++){
            if(binary.get(i) > second.binary.get(i)){
                return true;
            }
        }
        return false;
    }
    public boolean less(Binary second){
        for(int i =0; i < binary.size(); i++){
            if(binary.get(i) < second.binary.get(i)){
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
        return Objects.equals(binary, binary1.binary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(binary);
    }

    @Override
    public String toString() {
        return binary.toString();
    }

}
