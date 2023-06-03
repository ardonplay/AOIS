package org.ardonplay.aois.lr8Se;

import org.ardonplay.aois.lr7.Binary;

import java.util.ArrayList;
import java.util.List;

public class Sector extends Binary {
    private List<Integer> key;

    private List<Integer> aPart;

    private List<Integer> bPart;

    private List<Integer> sPart;
    public Sector(int word) {
        super(word);
        parse();
    }


    public Sector(List<Integer> bites){
        super(bites);
        parse();
    }

    private void parse(){
        key = bites.subList(0,3);
        aPart = bites.subList(3,7);
        bPart = bites.subList(7,11);
        sPart = bites.subList(11, 16);
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
    }

    public void setaPart(List<Integer> aPart) {
        this.aPart = aPart;
    }

    public void setbPart(List<Integer> bPart) {
        this.bPart = bPart;
    }

    public void setsPart(List<Integer> sPart) {
        this.sPart = sPart;
    }
}
