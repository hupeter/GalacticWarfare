package com.redlotus.galacticwarfare.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Sector {
    private static final int MAX_KINGDOMS_PER_SECTOR = 5;

    private final String name;
    private final int galaxyNum;
    private List<Kingdom> kingdoms;

    public Sector(int galaxyNum){
        this("Unnamed Sector " + galaxyNum, galaxyNum, new ArrayList<Kingdom>(Collections.nCopies(MAX_KINGDOMS_PER_SECTOR, (Kingdom)null)));
    }

    public Kingdom getKingdom(int kingdomNum){
        return kingdoms.get(kingdomNum);
    }

    public int getNumKingdoms() {
        int numKingdom = 0;
        for(Kingdom k : kingdoms) {
            if(k != null) {
                numKingdom++;
            }
        }
        return numKingdom;
    }

    public int getFirstEmptySlot() {
        for(int i = 0; i < MAX_KINGDOMS_PER_SECTOR; i++) {
            if(kingdoms.get(i) == null) {
                return i;
            }
        }
        throw new IllegalArgumentException();
    }

    public boolean isEmpty() {
        return getNumKingdoms() == 0;
    }

    public boolean removeKingdom(int kingdomNum, Kingdom k) {
        Kingdom kingdomToRemove = kingdoms.get(kingdomNum);
        if(Kingdom.State.REMOVED == kingdomToRemove.getState()) {
            kingdoms.set(kingdomNum, null);
            return true;
        }
        return false;
    }

    public boolean isFull() {
        return getNumKingdoms() >= MAX_KINGDOMS_PER_SECTOR;
    }

    public long getNetworth() {
        long networth = 0;
        for(Kingdom k : kingdoms) {
            if(k == null) continue;
            networth += k.getNetworth();
        }
        return networth;
    }

    public Kingdom newKingdom(KingdomConfig kConfig){
        if(getNumKingdoms() >= MAX_KINGDOMS_PER_SECTOR) {
            throw new IllegalArgumentException();
        }
        Coordinate kCoord = new Coordinate(galaxyNum, getFirstEmptySlot());
        Kingdom k = new Kingdom(kConfig, kCoord);
        kingdoms.set(kCoord.getKingdomNum(), k);
        return k;
    }
}
