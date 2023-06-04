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

    public boolean normalForm = false;

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

                    push(i, sector);
                }
            }
        }
    }

    public void functionZero(int pos) {
        push(pos, new Sector(0));
    }

    public void functionFifty(int pos) {
        push(pos, new Sector(Collections.nCopies(16, 1)));
    }

    public void functionFive(int firstPos, int secondPos) {
        push(firstPos, get(secondPos));
    }

    public void functionTen(int firstPos, int secondPos) {
        push(firstPos, LogicOperations.not(get(secondPos)));
    }

    @Override
    public List<Binary> getMemory() {
        return memory;
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
        return new Buffer(new Sector(memory.get(i).getBites()), i).pull();
    }

    @Override
    public int size() {
        return memory.size();
    }

    @Override
    public String toString() {
        if (normalForm) {
            List<Binary> normalMemory = new MemoryList();

            for (int i = 0; i < memory.size(); i++) {
                normalMemory.add(get(i));
            }

            return normalMemory.toString();
        } else {
            return memory.toString();
        }
    }
}
