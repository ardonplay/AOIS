package org.ardonplay.aois.lr8Se;

import org.ardonplay.aois.lr7.Binary;
import org.ardonplay.aois.lr7.Memory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssMemoryList extends ArrayList<Sector> implements List<Sector>{
    public AssMemoryList(int size) {
        super(size);
        addAll(Stream.generate(() -> new Sector(0))
                .limit(size)
                .collect(Collectors.toCollection(ArrayList::new)));
    }
    public AssMemoryList() {
        super();
    }

    public AssMemoryList(List<Sector> memory) {
        super(memory);
    }


    public void sort(){
        List<Sector> output = new ArrayList<>(size());
        int size = size();

        for(int i = 0;i < size; i++){

            Sector max = new Sector(0);
            int index = 0;

            for(int j =0; j < size; j++){
                Buffer buffer = new Buffer(get(j), i);
                if(max.compareTo(buffer.pull()) < 0) {
                    max = buffer.pull();
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
