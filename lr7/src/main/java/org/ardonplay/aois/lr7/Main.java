package org.ardonplay.aois.lr7;

public class Main {
    public static void main(String[] args) {
        AssProcessor memory = new AssProcessor(32);

        memory.push(0, new Binary(10));

        memory.push(10, new Binary(17));

        memory.push(10, new Binary(18));

        memory.push(8, new Binary(2));

        System.out.println(memory.findTheAppropriate(new Binary(18)));

        Binary binary = new Binary(25);
        System.out.println(binary.toDecimal());
        System.out.println(memory.getByInterval(0, 10));
        System.out.println(memory);
    }
}