package com.redlotus.galacticwarfare.controller;

import com.redlotus.galacticwarfare.model.GameClock;
import com.redlotus.galacticwarfare.model.Kingdom;
import com.redlotus.galacticwarfare.model.Sector;
import com.redlotus.galacticwarfare.model.Universe;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import wagu.Block;
import wagu.Board;
import wagu.Table;

public class ReportController {
    private static final KingdomComparatorByNetworth KINGDOM_NETWORTH_COMPARATOR = new KingdomComparatorByNetworth();
    private static final int BOARD_WIDTH = 120;
    private static final int CLOCK_DIFF_FOR_ACTIVE = 5; // player is active if they made an action in the last 5 ticks
    private static final int SHOW_TOP_N = 10;

    private final KingdomController kingdomController;
    private final Universe universe;
    private final GameClock clock;
    private final NumberFormat numberFormat;

    public ReportController(KingdomController kingdomController){
        this.kingdomController = kingdomController;
        this.universe = kingdomController.getUniverse();
        this.clock = kingdomController.getClock();
        this.numberFormat = NumberFormat.getInstance();
    }

    @SuppressWarnings("NewApi")
    public String getReport() {
        Board board = new Board(BOARD_WIDTH);
        List<Kingdom> allKingdoms = kingdomController.findAllKingdoms();
        allKingdoms.sort(KINGDOM_NETWORTH_COMPARATOR);

        long totalNetworth = 0;
        List<Kingdom> topKingdoms = new ArrayList<>();
        for(int i = allKingdoms.size() - 1; i >= 0; i--) {
            Kingdom k = allKingdoms.get(i);
            totalNetworth += k.getNetworth();
            if(topKingdoms.size() < Math.min((int)(allKingdoms.size()/3), SHOW_TOP_N)){
                topKingdoms.add(k);
            }
        }

        List<Sector> allSectors = universe.getSectors();
        Sector topSector = allSectors.get(0);
        for(int i = 0; i < allSectors.size(); i++) {
            Sector s = allSectors.get(i);
            if(s.getNetworth() > topSector.getNetworth()) {
                topSector = s;
            }
        }

        // Report title block
        int blockIndex = 0;
        String title = (universe.getName() + " Report").toUpperCase();
        Block titleBlock = new Block(board, BOARD_WIDTH - 2, 3, title).setDataAlign(Block.DATA_CENTER).allowGrid(false);
        board.setInitialBlock(titleBlock);

        // Universe title block
        String universeTitle = "Universe Report";
        Block universeTitleBlock = new Block(board, BOARD_WIDTH-2, 1, universeTitle).setDataAlign(Block.DATA_CENTER);
        board.getBlock(blockIndex++).setBelowBlock(universeTitleBlock);

        // Universe report block
        List<String> universeReportHeaders = Arrays.asList("Description", "Value");
        List<List<String>> universeReportRows = Arrays.asList(
                Arrays.asList("Players online", String.format("%d/%d", kingdomController.findAllActiveKingdoms(CLOCK_DIFF_FOR_ACTIVE).size(), allKingdoms.size())),
                Arrays.asList("Number of sectors ", Integer.toString(universe.getSectors().size())),
                Arrays.asList("Total networth", numberFormat.format(totalNetworth)),
                Arrays.asList("Top player", String.format("%s [%d,%d]", topKingdoms.get(0).getConfig().getRulerName(), topKingdoms.get(0).getCoordinate().getGalaxyNum(), topKingdoms.get(0).getCoordinate().getKingdomNum())),
                Arrays.asList("Top sector", String.format("%s [%d]", topSector.getName(), topSector.getGalaxyNum()))
        );
        board.appendTableTo(blockIndex, Board.APPEND_BELOW, new Table(board, BOARD_WIDTH, universeReportHeaders, universeReportRows)
                .setColAlignsList(Arrays.asList(Block.DATA_CENTER, Block.DATA_CENTER))
                .setColWidthsList(Arrays.asList((int) (BOARD_WIDTH/2) - 3, (int) (BOARD_WIDTH/2)))
                .setGridMode(Table.GRID_FULL));
        blockIndex+=universeReportRows.size() * universeReportRows.get(0).size() + 1;

        // Networth report block
        String networthTitle = "Networth Leaderboard";
        Block networthTitleBlock = new Block(board, BOARD_WIDTH-2, 1, networthTitle).setDataAlign(Block.DATA_CENTER);
        board.getBlock(blockIndex++).setBelowBlock(networthTitleBlock);
        blockIndex+=universeReportRows.get(0).size()-1;

        // Networth report
        List<String> networthReportHeaders = Arrays.asList("Rank", "Player", "Networth", "Planet Type", "Race");
        List<List<String>> networthReportRows = new ArrayList<>();
        for(int i = 0; i < topKingdoms.size(); i++){
            Kingdom k = topKingdoms.get(i);
            networthReportRows.add(Arrays.asList(Integer.toString(i+1), k.getConfig().getRulerName() + " " + k.getCoordinate().displayString(),
                    numberFormat.format(k.getNetworth()), k.getConfig().getPlanetType().getName(),
                    k.getConfig().getRace().getName()));
        }
        board.appendTableTo(blockIndex, Board.APPEND_BELOW, new Table(board, BOARD_WIDTH, networthReportHeaders, networthReportRows)
                .setColAlignsList(Arrays.asList(Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER))
                .setColWidthsList(Arrays.asList(4, BOARD_WIDTH-4-17-20-15-6, 17, 20, 15))
                .setGridMode(Table.GRID_FULL));
        blockIndex+=networthReportRows.size() * networthReportRows.get(0).size() + 1;

        // Action report block
        String actionTitle = "Action Report";
        Block actionTitleBlock = new Block(board, BOARD_WIDTH-2, 1, actionTitle).setDataAlign(Block.DATA_CENTER);
        board.getBlock(blockIndex++).setBelowBlock(actionTitleBlock);
        blockIndex+=networthReportRows.get(0).size()-1;

        // Action report
        List<String> actionReportHeaders = Arrays.asList("Rank", "Location", "Units Lost", "Networth Lost", "Timestamp");
        List<List<String>> actionReportRows = Arrays.asList(
                Arrays.asList("1", "N/A [N/A, N/A]", "N/A", "N/A", "N/A"),
                Arrays.asList("2", "N/A [N/A, N/A]", "N/A", "N/A", "N/A"),
                Arrays.asList("3", "N/A [N/A, N/A]", "N/A", "N/A", "N/A")
                /*,
                Arrays.asList("2", "John Doe [2, 5]", "100", "1000", "2021-04-15"),
                Arrays.asList("3", "John Doe [2, 5]", "100", "1000", "2021-04-15"),
                Arrays.asList("4", "John Doe [2, 5]", "100", "1000", "2021-04-15"),
                Arrays.asList("5", "John Doe [2, 5]", "100", "1000", "2021-04-15"),
                Arrays.asList("6", "John Doe [2, 5]", "100", "1000", "2021-04-15"),
                Arrays.asList("7", "John Doe [2, 5]", "100", "1000", "2021-04-15"),
                Arrays.asList("8", "John Doe [2, 5]", "100", "1000", "2021-04-15")*/
        );
        board.appendTableTo(blockIndex, Board.APPEND_BELOW, new Table(board, BOARD_WIDTH, actionReportHeaders, actionReportRows)
                .setColAlignsList(Arrays.asList(Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER, Block.DATA_CENTER))
                .setColWidthsList(Arrays.asList(4, BOARD_WIDTH-4-10-12-20-6, 10, 12, 20))
                .setGridMode(Table.GRID_FULL));
        blockIndex+=actionReportRows.size() * actionReportRows.get(0).size() + 1;
        return board.build().getPreview();
    }

    private static class KingdomComparatorByNetworth implements Comparator<Kingdom> {
        // negative is less
        @Override
        public int compare(Kingdom k1, Kingdom k2) {
            if(k1.getNetworth() < k2.getNetworth()) {
                return -1;
            } else if(k1.getNetworth() == k2.getNetworth()){
                return 0;
            } else {
                return 1;
            }
        }
    }
}
