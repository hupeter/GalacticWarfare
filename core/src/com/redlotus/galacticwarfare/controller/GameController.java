package com.redlotus.galacticwarfare.controller;

import com.redlotus.galacticwarfare.model.GameClock;
import com.redlotus.galacticwarfare.model.Universe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import wagu.Block;
import wagu.Board;
import wagu.Table;

public class GameController {
    private static final int MIN_SECTORS_IN_UNIVERSE = 3;
    public static void main(String [] args) {
        // Setup
        GameClock clock = new GameClock();
        Universe universe = new Universe("Star kingdoms", MIN_SECTORS_IN_UNIVERSE);
        KingdomController kingdomController = new KingdomController(universe, clock);
        ReportController reportController = new ReportController(kingdomController);
        for(int i = 0; i < 100; i++) {
            kingdomController.createRandomKingdom();
        }
        System.out.println(reportController.getReport());



        // Game loop
        //int numIterations = 1000; // 1 iteration = 1 hour, 41 days
        //for(int i = 0; i < numIterations; i++) {
        //
        //}
    }
}
