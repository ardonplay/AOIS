package org.ardonplay.aois.lr7;

import java.util.List;
import java.util.Objects;

public class AssProcessor {
    final protected MemoryList memory;

    public AssProcessor(int size){
        memory = new MemoryList(size);

    }

    public boolean push(int pos, Binary bin){
        if(pos < memory.size()) {
            memory.set(pos, bin);
            return true;
        }
        else {
            return false;
        }
    }
    public void sort(){

    }
    public Binary findTheAppropriate(Binary findable){
        MemoryList list = new MemoryList(memory);
        list.sort();
        Binary max = findable;
        int count_prev = 0;
        for (Binary binary : memory) {
            int count = 0;
            for (int j = 0; j < binary.getBites().size(); j++) {
                if (Objects.equals(findable.getBites().get(j), binary.getBites().get(j))) {
                    count++;
                }
            }
            if (count > count_prev) {
                max = binary;
                count_prev = count;
            }
        }
        return max;
    }
    public List<Binary> getByInterval(int start, int end){
       List<Binary> output = new MemoryList();

       for(int i = start; i < end; i++){
           output.add(memory.get(i));
       }

       return output;
    }
    @Override
    public String toString() {
        return memory.toString();
    }
}
