package com.redlotus.galacticwarfare.model;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.Value;
import wagu.Block;
import wagu.Board;
import wagu.Table;

@SuppressWarnings("NewApi")
@Data
public class Universe {
    private static final SectorComparatorForNewKingdom SECTOR_COMPARATOR_FOR_NEW_KINGDOM = new SectorComparatorForNewKingdom();

    private final String name;
    private List<Sector> sectors;

    public Universe(String name, int numSectors){
        this.name = name;
        sectors = new ArrayList<>();
        for(int galaxyNum = 0; galaxyNum < numSectors; galaxyNum++) {
            sectors.add(new Sector(galaxyNum));
        }
    }

    public Sector getSector(int galaxyNum){
        return sectors.get(galaxyNum);
    }

    public Sector addSector(){
        Sector s = new Sector(sectors.size());
        sectors.add(s);
        return s;
    }

    public Sector getOrCreateSectorForNewKingdom() {
        List<Sector> sectors = new ArrayList<>();
        sectors.addAll(this.sectors);
        sectors.sort(SECTOR_COMPARATOR_FOR_NEW_KINGDOM);
        Sector bestSector = sectors.get(0);
        if(bestSector.isFull()) {
            bestSector = addSector();
        }
        return bestSector;
    }

    private static class SectorComparatorForNewKingdom implements Comparator<Sector> {
        // negative if s1 is better for new kingdom than s2
        // positive if s2 is better for new kingdom than s1
        // 0 if equal
        @Override
        public int compare(Sector s1, Sector s2) {
            // empty is best
            if(s1.isEmpty() && s2.isEmpty()) return 0;
            if(s1.isEmpty()) return Integer.MIN_VALUE;
            if(s2.isEmpty()) return Integer.MAX_VALUE;

            // full is worse
            if(s1.isFull()) return Integer.MAX_VALUE;
            if(s2.isFull()) return Integer.MIN_VALUE;

            // lowest networth is best, but if they're all small and close, use number of kingdom instead
            int networthDiffIn100K;
            try {
                networthDiffIn100K = Math.toIntExact(Math.round(s1.getNetworth() / 100000d - s2.getNetworth() / 100000d));
            } catch(ArithmeticException e){
                networthDiffIn100K = Integer.MAX_VALUE;
            }
            if(networthDiffIn100K != 0) {
                return networthDiffIn100K;
            }

            // sectors with least networth are best
            return s1.getNumKingdoms() - s2.getNumKingdoms();
        }
    }
}
