package org.ardonplay.aois.lr7.implementations;

import java.util.*;

import org.ardonplay.aois.lr6.HashTable;
import org.ardonplay.aois.lr7.Binary;
import org.ardonplay.aois.lr7.rules.Memory;

public class AssProcessor implements Memory {
    private final List<Binary> memory;

    public List<Binary> getMemory() {
        return memory;
    }

    public AssProcessor(int size) {
        memory = new MemoryList(size);
    }


    public boolean push(int pos, Binary bin) {
        if (pos < memory.size()) {
            memory.set(pos, bin);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Binary get(int pos) {
        return memory.get(pos);
    }

    @Override
    public int size() {
        return memory.size();
    }


    @Override
    public List<Binary> findTheAppropriate(Binary findable) {
        List<Binary> list = new MemoryList(memory, true);

        Map<Integer, List<Binary>> table = new HashTable<>(memory.size());

        return getAppropriates(list, findable, table);
    }

    public List<Binary> getByInterval(int start, int end) {
        List<Binary> output = new MemoryList();

        Binary first = memory.get(start);

        Binary second = memory.get(end);

        for (Binary binary : memory) {
            if (binary.compareTo(first) > 0 && binary.compareTo(second) < 0) {
                output.add(binary);
            }
        }
        return output;
    }

    @Override
    public String toString() {
        return memory.toString();
    }
}
