package com.redlotus.galacticwarfare.model;

import lombok.Value;

@Value
public class Coordinate {
    private final int galaxyNum;
    private final int kingdomNum;

    public String displayString() {
        return "[" + galaxyNum + "," + kingdomNum + "]";
    }
}
