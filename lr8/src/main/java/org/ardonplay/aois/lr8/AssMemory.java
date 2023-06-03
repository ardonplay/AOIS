package org.ardonplay.aois.lr8;


import org.ardonplay.aois.lr7.Binary;
import org.ardonplay.aois.lr7.Memory;
import org.ardonplay.aois.lr8.utils.Buffer;
import org.ardonplay.aois.lr8.utils.LogicOperations;
import org.ardonplay.aois.lr8.utils.MemoryList;
import org.ardonplay.aois.lr8.utils.Sector;

import java.util.*;

public class AssMemory implements Memory {
    private final List<Sector> memory;

    public AssMemory(int size) {
        memory = new MemoryList(size);
    }

    public boolean normalForm = false;


    @Override
    public String toString(){
        if(normalForm){
            return super.toString();
        }
        else {
           return memory.toString();
        }
    }

    public Binary findTheAppropriate(Binary findable) {
        MemoryList list = new MemoryList(memory);
        list.sort();

        Binary max = findable;

        Binary min = new Binary(0);
        for (Binary binary : list) {
            if (binary.compareTo(findable) <= 0) {
                max = binary;
                break;
            }
        }
        for (Binary binary : list) {
            if (binary.compareTo(findable) > 0) {
                min = binary;
                break;
            }
        }

        int count_max = 0;
        int count_min = 0;

        for(int i =0; i < max.getBites().size(); i++){
            if(Objects.equals(max.getBites().get(i), findable.getBites().get(i))){
                count_max++;
            }
            if(Objects.equals(min.getBites().get(i), findable.getBites().get(i))){
                count_min++;
            }
        }
        return count_max > count_min ? max : min;
    }


    public void masking(String symbols) {
        if (symbols.length() == 3) {

           for(int i =0; i < memory.size(); i++){
                Buffer buffer = new Buffer(memory.get(i), i);
                Sector sector = buffer.pull();
                boolean fit = true;
                List<Integer> bites = sector.getKey();
                for (int j = 0; j < symbols.length(); j++) {
                    char symbol = symbols.charAt(j);
                    if (symbol != '*' && bites.get(j) != symbol - '0') {
                        fit = false;
                        break;
                    }
                }

                if (fit) {
                    System.out.println("[До суммы]");
                    System.out.println(sector);
                    List<Integer> sum = LogicOperations.summator(sector.getaPart(), sector.getbPart());
                    sector.setsPart(sum);
                    System.out.println("[После]");
                    System.out.println(sector);

                    push(i, sector);
                }
            }
        }
    }
    public void functionZero(int pos){
        memory.set(pos, new Sector(0));
    }
    public void functionFifty(int pos){
        memory.set(pos, new Sector(Collections.nCopies(16,1)));
    }

    public void functionFive(int firstPos, int secondPos){
        memory.set(firstPos, memory.get(secondPos));
    }

    public void functionTen(int firstPos, int secondPos){
        memory.set(firstPos, LogicOperations.not(memory.get(secondPos)));
    }
    @Override
    public boolean push(int pos, Binary bin) {
        if (pos < this.memory.size()) {
            Sector sector = new Sector(0);
            sector.getBites().clear();
            for (int i = bin.getBites().size() - pos; i < bin.getBites().size(); i++) {
                sector.getBites().add(bin.getBites().get(i));
            }
            for (int i = 0; i < bin.getBites().size() - pos; i++) {
                sector.getBites().add(bin.getBites().get(i));
            }
            this.memory.set(pos, sector);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Binary get(int i) {
        return new Buffer(memory.get(i), i).pull();
    }
}
