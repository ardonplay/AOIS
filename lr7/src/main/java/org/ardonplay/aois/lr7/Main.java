package org.ardonplay.aois.lr7;

import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AssProcessor memory = new AssProcessor(16);

        Random random = new Random();

        memory.push(0, new Binary(10));

        memory.push(0, new Binary(4));

        memory.push(10, new Binary(1));

        System.out.println(memory);

        int number = 4;

        System.out.println(memory.findTheAppropriate(new Binary(number)) + "\n");


        //System.out.println(memory.getByInterval(1, 4));

    }
}