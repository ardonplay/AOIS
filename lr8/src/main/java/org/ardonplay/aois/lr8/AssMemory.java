package org.ardonplay.aois.lr8;


import org.ardonplay.aois.lr7.Binary;
import org.ardonplay.aois.lr7.implementations.MemoryList;
import org.ardonplay.aois.lr7.rules.Memory;
import org.ardonplay.aois.lr8.utils.Buffer;
import org.ardonplay.aois.lr8.utils.LogicOperations;
import org.ardonplay.aois.lr8.utils.Sector;

import java.util.*;

public class AssMemory implements Memory {
    private final List<Binary> memory;

    public AssMemory(int size) {
        memory = new MemoryList(size);
    }

    public boolean normalForm = true;

    public void masking(String symbols) {
        if (symbols.length() == 3) {

            for (int i = 0; i < memory.size(); i++) {
                Buffer buffer = new Buffer(new Sector(get(i).getBites()), i);
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

                    pushSector(i, sector);
                }
            }
        }
    }

    public void functionZero(int pos) {
        pushSector(pos, new Sector(0));
    }

    public void functionFifty(int pos) {
        pushSector(pos, new Sector(Collections.nCopies(16, 1)));
    }

    public void functionFive(int firstPos, int secondPos) {
        pushSector(firstPos, get(secondPos));
    }

    public void functionTen(int firstPos, int secondPos) {
        pushSector(firstPos, LogicOperations.not(get(secondPos)));
    }

    @Override
    public List<Binary> getMemory() {
        return memory;
    }

    @Override
    public boolean push(int pos, Binary bin) {
        if(pos < memory.size()) {
            this.memory.set(pos, bin);
            return true;
        }
        else
            return false;
    }


    public boolean pushSector(int pos, Binary bin){
        if (pos < this.memory.get(0).getBites().size()) {
            Sector sector = new Sector(0);
            sector.getBites().clear();

            int position =  bin.getBites().size() - pos;

            for (int i = position; i < bin.getBites().size(); i++) {
                sector.getBites().add(bin.getBites().get(i));
            }
            for (int i = 0; i < position; i++) {
                sector.getBites().add(bin.getBites().get(i));
            }
            for(int i =0;i < sector.getBites().size(); i++){
                memory.get(i).getBites().set(pos, sector.getBites().get(i));
            }
            return true;
        } else {
            return false;
        }
    }

    public Binary getWord(int i) {
        return memory.get(i);
    }

    @Override
    public Binary get(int pos){
        Sector output = new Sector(0);
        output.getBites().clear();
        for(Binary binary: memory){
            output.getBites().add(binary.getBites().get(pos));
        }

        return new Buffer(output, pos).pull();
    }

    @Override
    public int size() {
        return memory.size();
    }

    @Override
    public String toString() {
        if (normalForm) {
            return memory.toString();
        } else {
            List<Binary> memoryViewForBug = new MemoryList();

            for(int i =0; i < memory.size(); i++){
                memoryViewForBug.add(get(i));
            }
            return memoryViewForBug.toString();
        }
    }
}
