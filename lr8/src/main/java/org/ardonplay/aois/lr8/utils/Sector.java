package org.ardonplay.aois.lr8.utils;

import org.ardonplay.aois.lr7.Binary;

import java.util.List;
import java.util.stream.Stream;

public class Sector extends Binary {
    private List<Integer> key;

    private List<Integer> aPart;

    private List<Integer> bPart;

    private List<Integer> sPart;

    public Sector(int word) {
        super(word);
        parse();
    }


    public Sector(List<Integer> bites) {
        super(bites);
        parse();
    }

    private void parse() {
        key = bites.subList(0, 3);
        aPart = bites.subList(3, 7);
        bPart = bites.subList(7, 11);
        sPart = bites.subList(11, 16);
    }

    private void updateBites() {
        List<Integer> newBites = Stream.of(key, aPart, bPart, sPart)
                .flatMap(List::stream)
                .toList();
        bites.clear();
        bites.addAll(newBites);

    }

    public List<Integer> getKey() {
        return key;
    }

    public List<Integer> getaPart() {
        return aPart;
    }

    public List<Integer> getbPart() {
        return bPart;
    }

    public List<Integer> getsPart() {
        return sPart;
    }

    public void setKey(List<Integer> key) {
        this.key = key;
        updateBites();
    }

    public void setaPart(List<Integer> aPart) {
        this.aPart = aPart;
        updateBites();
    }

    public void setbPart(List<Integer> bPart) {
        this.bPart = bPart;
        updateBites();
    }

    public void setsPart(List<Integer> sPart) {
        this.sPart = sPart;
        updateBites();
    }
}
