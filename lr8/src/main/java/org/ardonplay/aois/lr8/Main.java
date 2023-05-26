package org.ardonplay.aois.lr8;

import org.ardonplay.aois.lr7.AssProcessor;
import org.ardonplay.aois.lr7.Binary;

public class Main {
    public static void main(String[] args){
        AssMemory memory = new AssMemory(16);

        memory.push(2, new Binary(12));
        System.out.println(memory);
    }
}