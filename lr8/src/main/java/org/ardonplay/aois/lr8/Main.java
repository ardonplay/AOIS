package org.ardonplay.aois.lr8;


import org.ardonplay.aois.lr8.utils.Sector;

public class Main {
    public static void main(String[] args) {

        AssMemory memory = new AssMemory(16);

        memory.push(0, new Sector(255));
        memory.push(1, new Sector(10));


        memory.push(5, new Sector(5687));

        memory.push(6, new Sector(48960));

        memory.functionTen(1,2);

       // System.out.println(memory);


        System.out.println(memory);
         memory.masking("101");

        System.out.println(memory);

        System.out.println(memory.get(6));

        //System.out.println(memory);

        //System.out.println(memory.findTheAppropriate(new Binary(11)));
    }
}