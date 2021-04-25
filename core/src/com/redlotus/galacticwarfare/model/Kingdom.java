package com.redlotus.galacticwarfare.model;

import java.util.Arrays;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Kingdom {
    //private Land land;
    //private Population population;
    private KingdomConfig config;
    private Coordinate coordinate;
    private long networth;
    private State state;

    public Kingdom(KingdomConfig config, Coordinate coordinate) {
        this.config = config;
        this.coordinate = coordinate;
        this.networth = (long)(Math.random() * 100000); // TODO: FIXIT!
        this.state = State.PROVISIONING;
    }

    public long getLastActionTime() {
        return 0;
    }

    public static enum State {
        PROVISIONING,
        NEWBIE,
        ACTIVE,
        DEAD,
        FROZEN,
        REMOVED
        ;

        public boolean isValidTransition(State t) {
            switch(this) {
                case PROVISIONING: return NEWBIE == t;
                case NEWBIE: return ACTIVE == t;
                case ACTIVE: return FROZEN == t || DEAD == t;
                case DEAD: return REMOVED == t;
                case FROZEN: return ACTIVE == t || REMOVED == t;
                case REMOVED: return false; // terminal state
                default:
                    throw new IllegalStateException();
            }
        }
    }
}
