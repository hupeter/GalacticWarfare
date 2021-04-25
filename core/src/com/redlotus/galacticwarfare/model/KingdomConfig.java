package com.redlotus.galacticwarfare.model;

import com.github.javafaker.Faker;
import com.redlotus.galacticwarfare.utils.GWUtils;

import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@AllArgsConstructor
@Data
public class KingdomConfig {
    private String rulerName;
    private PlanetType planetType;
    private RaceType race;

    public KingdomConfig() {
        this(GWUtils.generateName(), // random name
                PlanetType.values()[(new Random().nextInt(PlanetType.values().length))], // random planet type
                RaceType.values()[new Random().nextInt(RaceType.values().length)] // random race type
                );
    }

    @Getter
    public static enum PlanetType {
        Mountainous("Mountainous", KingdomModifiers.builder().income(0.25f).explorationCost(-0.25f).powerProduction(-0.2f).build()),
        Forest("Forest and Wilderness", KingdomModifiers.builder().population(0.25f).barrackSpace(0.3f).returnTime(0.1f).build()),
        Terra("Terra Form", KingdomModifiers.builder().researchRequired(-0.3f).researchMaximum(0.1f).income(-0.1f).build()),
        Mystic("Mystical Lands", KingdomModifiers.builder().returnTime(-0.25f).powerRequired(-0.4f).offensiveStrength(-0.1f).build()),
        Volcanic("Volcanic Inferno", KingdomModifiers.builder().powerRequired(-0.85f).powerStorageCapacity(5.0f).population(-0.1f).build()),
        Tundra("Jagged Tundra",KingdomModifiers.builder().unitCost(-0.2f).powerProduction(0.25f).buildingCost(0.1f).probeProduction(-0.1f).build()),
        Oceanic("Oceanic", KingdomModifiers.builder().defensiveStrength(0.25f).buildingCost(-0.4f).researchRequired(0.2f).build()),
        Arid("Desert Wasteland", KingdomModifiers.builder().offensiveStrength(0.25f).offensiveUnitCost(-0.4f).population(-0.1f).build()),
        Mixed("Multiple Terrain", KingdomModifiers.builder().offensiveStrength(0.15f).defensiveStrength(0.1f).build()),
        ;

        private final String name;
        private final KingdomModifiers mods;

        private PlanetType(String name, KingdomModifiers mods) {
            this.name = name;
            this.mods = mods;
        }
    }

    @Builder
    @Getter
    public static class KingdomModifiers {
        // Money
        private float income;
        private float population;
        private float buildingCost; // lower better
        private float explorationCost; // lower better
        private float offensiveUnitCost; // lower better
        private float unitCost; // Lower better
        private float powerProduction;
        private float probeProduction;
        private float baseSoldierCostReduction;

        // Tech
        private float researchRequired; // lower better
        private float researchMaximum;

        // Strength
        private float defensiveStrength;
        private float offensiveStrength;
        private float probeDamage;
        private float probeLosses; // Lower better
        private float returnTime; // Lower better
        private float barrackSpace;
        private float powerRequired;
        private float powerStorageCapacity;

        public KingdomModifiers add(KingdomModifiers mod) {
            KingdomModifiers newMod = KingdomModifiers.builder()
                    .income(this.income + mod.income)
                    .buildingCost(this.buildingCost + mod.buildingCost)
                    .explorationCost(this.explorationCost + mod.explorationCost)
                    .offensiveUnitCost(this.offensiveUnitCost + mod.offensiveUnitCost)
                    .population(this.population + mod.population)
                    .researchRequired(this.researchRequired + mod.researchRequired)
                    .researchMaximum(this.researchMaximum + mod.researchMaximum)
                    .defensiveStrength(this.defensiveStrength + mod.defensiveStrength)
                    .offensiveStrength(this.offensiveStrength + mod.offensiveStrength)
                    .powerProduction(this.powerProduction + mod.powerProduction)
                    .build();
            return newMod;
        }
    }

    @Getter
    public static enum RaceType {
        Terran(KingdomModifiers.builder().population(0.1f).baseSoldierCostReduction(-75f).returnTime(0.1f).build()),
        Xivornai(KingdomModifiers.builder().income(0.1f).build()),
        Gistrami(KingdomModifiers.builder().offensiveStrength(0.1f).returnTime(-0.1f).income(-0.1f).build()),
        Mafielven(KingdomModifiers.builder().defensiveStrength(0.1f).researchRequired(-0.1f).probeProduction(-0.1f).build()),
        Qanut(KingdomModifiers.builder().buildingCost(-0.15f).powerProduction(0.1f).build()),
        Shadow(KingdomModifiers.builder().probeDamage(0.2f).probeLosses(-0.2f).build())
        ;

        private final KingdomModifiers mods;

        private RaceType(KingdomModifiers mods) {
            this.mods = mods;
        }

        public String getName(){
            return name();
        }
    }

    @Getter
    public static enum Unit {
        Soldier(1,1,3,150),
        Trooper(4,0,6,350),
        Dragoon(5,0,7,450),
        LaserTrooper(0,4,7,400),
        LaserDragoon(0,5,8,550),
        HighGuardLancer(5,5,12,800),
        Tanks(9,9,22,1750),
        TacticalFighter(12,0,18,1500),
        Scientist(0,0,8,1000),
        ;

        private final int offensiveStrength;
        private final int defensiveStrength;
        private final int networth;
        private final int cost;

        private Unit(int offStr, int defStr, int networth, int cost) {
            this.offensiveStrength = offStr;
            this.defensiveStrength = defStr;
            this.networth = networth;
            this.cost = cost;
        }
    }
}
