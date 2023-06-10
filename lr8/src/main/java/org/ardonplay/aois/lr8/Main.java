package org.ardonplay.aois.lr8;


import org.ardonplay.aois.lr7.Binary;
import org.ardonplay.aois.lr8.utils.Sector;

import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {

        AssMemory memory = new AssMemory(16);
        List<Integer> numbers = List.of(50400, 67, 82, 257, 64300, 23743, 125, 657, 892, 4, 76, 45, 32, 4000, 789, 2852);

        for (int i = 0; i < memory.size(); i++) {
            memory.push(i, new Sector(numbers.get(i)));
        }
        System.out.println("Память:");
        System.out.println(memory);

        System.out.println("Срезы по столбцам:");
        memory.normalForm = false;
        System.out.println(memory);


        System.out.println("Функция 0 для позиции 5:");
        memory.functionZero(5);
        System.out.println("Столбец 5:");
        System.out.println(memory.get(5));
        System.out.println(memory);

        System.out.println("Функция 15 для позиции 12:");
        memory.functionFifty(12);
        System.out.println("Столбец 12:");
        System.out.println(memory.get(12));
        System.out.println(memory);

        System.out.println("Функция 10 для позиций 7 и 15:");
        memory.functionTen(7, 15);
        System.out.println("Столбец 7:");
        System.out.println(memory.get(7));
        System.out.println("Столбец 15:");
        System.out.println(memory.get(15));
        System.out.println(memory);

        System.out.println("Функция 5 для позиций 3 и 10:");
        memory.functionFive(3, 10);
        System.out.println("Столбец 3:");
        System.out.println(memory.get(3));
        System.out.println("Столбец 10:");
        System.out.println(memory.get(10));
        System.out.println(memory);

        System.out.println("Сумма сектора а и сектора b по маске: 1*0");
        memory.masking("1*0");

        System.out.println(memory);


        Random random = new Random();
        int number = random.nextInt(100);

        System.out.println("Поиск по соответсвию для числа: " + new Binary(number));
        System.out.println(memory.findTheAppropriate(new Binary(number)));

        memory.normalForm = true;
        System.out.println(memory);
    }
}