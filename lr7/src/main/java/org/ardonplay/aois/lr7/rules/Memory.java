package org.ardonplay.aois.lr7.rules;

import org.ardonplay.aois.lr6.HashTable;
import org.ardonplay.aois.lr7.Binary;
import org.ardonplay.aois.lr7.implementations.MemoryList;

import java.util.*;

public interface Memory {
    List<Binary> getMemory();

    boolean push(int pos, Binary bin);

    Binary get(int pos);

    int size();

    default int countOfAppropriates(Binary binary, Binary findable) {
        int trigger = 0;

        for (int i = 0; i < binary.getBites().size(); i++) {
            int bit = binary.getBites().get(i);
            int bitFindable = findable.getBites().get(i);
            if (bit == bitFindable && bit != 0) {
                trigger += 1;
            }
        }
        return trigger;
    }

    default List<Binary> getAppropriates(List<Binary> list, Binary findable, Map<Integer, List<Binary>> table) {
        for (Binary binary : list) {
            int trigger = countOfAppropriates(binary, findable);
            if (table.containsKey(trigger)) {
                Objects.requireNonNull(table.get(trigger)).add(binary);
            } else {
                table.put(trigger, new MemoryList(List.of(binary)));
            }
        }
        Optional<Integer> max = table.keySet().stream().max(Integer::compareTo);
        if (max.isPresent()) {
            return table.get(max.get());
        } else {
            return new ArrayList<>();
        }
    }

    default List<Binary> findTheAppropriate(Binary findable) {
        List<Binary> list = new MemoryList(getMemory(), true);

        Map<Integer, List<Binary>> table = new HashTable<>(size());

        return getAppropriates(list, findable, table);
    }
}
