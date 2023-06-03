package org.ardonplay.aois.lr8.utils;

import org.ardonplay.aois.lr7.Binary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogicOperations {

    public static Sector not(Binary binary) {
        Sector output = new Sector(0);
        output.getBites().clear();
        for (Integer bit : binary.getBites()) {
            if (bit == 0) {
                output.getBites().add(1);
            } else {
                output.getBites().add(0);
            }
        }
        return output;
    }


    private static boolean perceptron(int first, int second, boolean trans, List<Integer> sum) {
        if (first == 0 && second == 0) {
            if (trans) {
                sum.add(1);
            } else {
                sum.add(0);
            }
            trans = false;
        }
        else if ((first == 1 && second == 0) ||(first == 0 && second == 1)) {
            if (trans) {
                sum.add(0);
                trans = true;
            } else {
                sum.add(1);
                trans = false;
            }

        }
        else if (first == 1 && second == 1) {
            if (trans) {
                sum.add(1);
            } else {
                sum.add(0);
            }
            trans = true;
        }
        return trans;
    }

    public static List<Integer> summator(List<Integer> first,  List<Integer> second) {
        List<Integer> sum = new ArrayList<>();

        boolean trans = false;


        int index = first.size();


        while (index != 0) {
            int first_binary_symbol = first.get(index-1);
            int second_binary_symbol = second.get(index-1);
            trans = perceptron(first_binary_symbol, second_binary_symbol, trans, sum);
            index -= 1;
        }
        if(trans){
            sum.add(1);
        }
        if(sum.size() == first.size()){
            sum.add(0);
        }
        Collections.reverse(sum);
        return sum;
    }

}
