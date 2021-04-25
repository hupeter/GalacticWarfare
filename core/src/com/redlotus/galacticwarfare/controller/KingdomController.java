package com.redlotus.galacticwarfare.controller;

import com.redlotus.galacticwarfare.model.Coordinate;
import com.redlotus.galacticwarfare.model.GameClock;
import com.redlotus.galacticwarfare.model.Kingdom;
import com.redlotus.galacticwarfare.model.KingdomConfig;
import com.redlotus.galacticwarfare.model.Sector;
import com.redlotus.galacticwarfare.model.Universe;
import com.redlotus.galacticwarfare.utils.GWUtils;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;

@Getter
public class KingdomController {
    private final Universe universe;
    private final GameClock clock;

    public KingdomController(Universe universe, GameClock clock){
        this.universe = universe;
        this.clock = clock;
    }

    // CREATE
    public Kingdom createKingdom(KingdomConfig kConfig) {
        Kingdom existingKingdom = getKingdomByName(kConfig.getRulerName());
        if(existingKingdom != null){
            throw new IllegalArgumentException("Cannot create new kingdom with same ruler name");
        }
        Sector s = universe.getOrCreateSectorForNewKingdom();
        return s.newKingdom(kConfig);
    }

    public Kingdom createRandomKingdom() {
        KingdomConfig kConfig = new KingdomConfig();
        boolean isCreated = false;
        int failedAttempts = 0;

        while (!isCreated && failedAttempts < 3) {
            try {
                return createKingdom(kConfig);
            } catch (Exception e) {
                failedAttempts++;
                kConfig.setRulerName(kConfig.getRulerName() + " the " + GWUtils.ordinal(failedAttempts));
            }
        }
        throw new IllegalStateException("Couldn't create kingdom, last kingdom name attempted was called " + kConfig.getRulerName());
    }

    // GET
    public Kingdom getKingdomByName(String name) {
        List<Sector> sectors = universe.getSectors();
        for(Sector s : sectors) {
            List<Kingdom> kingdoms = s.getKingdoms();
            for(Kingdom k : kingdoms) {
                if(k == null) {
                    continue;
                }
                if(name.equals(k.getConfig().getRulerName())){
                    return k;
                }
            }
        }
        return null;
    }

    public Kingdom getKingdomByCoord(Coordinate c) {
        try {
            return universe.getSector(c.getGalaxyNum()).getKingdom(c.getKingdomNum());
        } catch (Exception e) {
            return null;
        }
    }

    // FIND
    public List<Kingdom> findAllKingdoms() {
        List<Kingdom> kingdoms = new ArrayList<>();
        List<Sector> sectors = universe.getSectors();
        for(Sector s : sectors) {
            for(Kingdom k : s.getKingdoms()) {
                if(k == null) {
                    continue;
                }
                kingdoms.add(k);
            }
        }
        return kingdoms;
    }

    /**
     *
     * @param clockDiff defines what active means, clockDiff of 100 means a kingdom is active if the last action was
     *                  done within the last 100 ticks.
     * @return
     */
    public List<Kingdom> findAllActiveKingdoms(long clockDiff) {
        List<Kingdom> kingdoms = new ArrayList<>();
        List<Sector> sectors = universe.getSectors();
        for(Sector s : sectors) {
            for(Kingdom k : s.getKingdoms()) {
                if(k == null) {
                    continue;
                }
                if(clock.getCurrentTime() - k.getLastActionTime() < clockDiff){
                    kingdoms.add(k);
                }
            }
        }
        return kingdoms;
    }

    public List<String> findAllRulerNames() {
        List<String> rulerNames = new ArrayList<>();
        List<Sector> sectors = universe.getSectors();
        for(Sector s : sectors) {
            List<Kingdom> kingdoms = s.getKingdoms();
            for(Kingdom k : kingdoms) {
                if(k == null) {
                    continue;
                }
                rulerNames.add(k.getConfig().getRulerName());
            }
        }
        return rulerNames;
    }
}
