package org.ardonplay.aois.lr8;

import org.ardonplay.aois.lr7.AssProcessor;
import org.ardonplay.aois.lr7.MemoryList;
public class AssMemory extends AssProcessor {

    public AssMemory(int size) {
        super(size);
    }



    @Override
    public String toString(){
        StringBuilder builder = new StringBuilder();
        for(int i =0; i < memory.get(0).getBites().size(); i++){
            for (org.ardonplay.aois.lr7.Binary binary : memory) {
                builder.append(binary.getBites().get(i));
            }
            builder.append("\n");
        }
        return String.valueOf(builder);
    }


}
