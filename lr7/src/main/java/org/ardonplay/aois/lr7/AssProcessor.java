package org.ardonplay.aois.lr7;

import java.util.List;
import java.util.Objects;

public class AssProcessor {
    final protected MemoryList memory;

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


    public Binary findTheAppropriate(Binary findable) {
        MemoryList list = new MemoryList(memory);
        list.sort();

        System.out.println(list);
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
