package org.ardonplay.aois.lr8.utils;

import java.util.ArrayList;
import java.util.List;

public class Buffer {
    private Sector sector;

    public Buffer(Sector sector, int pos) {
        load(sector, pos);
    }

    public Sector pull() {
        return sector;
    }

    public void load(Sector sector, int pos) {
        List<Integer> normalBinary = new ArrayList<>();
        for (int i = pos; i < sector.getBites().size(); i++) {
            normalBinary.add(sector.getBites().get(i));
        }

        for (int i = 0; i < pos; i++) {
            normalBinary.add(sector.getBites().get(i));
        }

        this.sector = new Sector(normalBinary);
    }

    @Override
    public String toString() {
        return sector.toString();
    }
}
