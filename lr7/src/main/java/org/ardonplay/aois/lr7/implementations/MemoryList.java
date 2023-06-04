package org.ardonplay.aois.lr7.implementations;

import org.ardonplay.aois.lr7.Binary;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MemoryList extends ArrayList<Binary> implements List<Binary>{
    public MemoryList(int size) {
        super(size);
        addAll( Stream.generate(() -> new Binary(0))
                .limit(size)
                .collect(Collectors.toCollection(ArrayList::new)));
    }
    public MemoryList() {
        super();
    }

    public MemoryList(List<Binary> memory) {
        super(memory);
    }
    public MemoryList(List<Binary> memory, boolean sort) {
        super(memory);
        if(sort){
            sort();
        }
    }

    public void sort(){
        List<Binary> output = new ArrayList<>(size());
        int size = size();

        for(int i = 0;i < size; i++){

            Binary max = new Binary(0);
            int index = 0;

            for(int j =0; j < size; j++){
                if(max.compareTo(get(j)) < 0) {
                    max = get(j);
                    index = j;
                }
            }
            size--;
            i = 0;
            remove(index);
            output.add(max);
        }
        clear();
        addAll(output);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder space = new StringBuilder("\t".repeat((int) Math.pow(2, Math.sqrt(size())/16)));
        for(int i =0; i< size(); i++){
            if(i % 16 == 0)
                space.replace(space.lastIndexOf("\t"),space.lastIndexOf("\t"),"");
            stringBuilder.append("[0x").append(Integer.toHexString(i)).append("]").append(space).append(get(i)).append("\n");
        }
        return String.valueOf(stringBuilder);
    }
}
