package org.ardonplay.aois.lr7;

import org.ardonplay.aois.lr7.implementations.AssProcessor;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        AssProcessor memory = new AssProcessor(16);

        Random random = new Random();

        List<Integer> numbers = List.of(12, 67, 82, 257, 64300, 23743, 125, 657, 892, 4, 76, 45, 32, 4000, 789, 2852);
        for(int i =0; i < memory.size(); i++){
            memory.push(i, new Binary(numbers.get(i)));
        }

        System.out.println("Проинициализированная память");
        System.out.println(memory);

        int number = random.nextInt(100);

        System.out.println("Поиск по соответсвию для числа: " + new Binary(number));
        System.out.println(memory.findTheAppropriate(new Binary(number)) + "\n");

        int top = 4;
        int bottom = 1;
        System.out.println("Верхняя граница(значение в ячейке под индексом " + top + "): " +memory.get(top));
        System.out.println("Нижняя граница(значение в ячейке под индексом " + bottom + "): " +memory.get(bottom));
        System.out.println(memory.getByInterval(bottom, top));

    }
}