package com.badlogic.hassubhoi;

import java.io.Serializable;
import java.util.List;

public class GameState implements Serializable {
    private int level;
    private List<BirdData> birds;
    private List<PigData> pigs;
    private List<StructureData> structures;
    private int currentBirdIndex;

    public GameState(int level, List<BirdData> birds, List<PigData> pigs, List<StructureData> structures, int currentBirdIndex) {
        this.level = level;
        this.birds = birds;
        this.pigs = pigs;
        this.structures = structures;
        this.currentBirdIndex = currentBirdIndex;
    }

    public int getLevel() {
        return level;
    }

    public List<BirdData> getBirds() {
        return birds;
    }

    public List<PigData> getPigs() {
        return pigs;
    }

    public List<StructureData> getStructures() {
        return structures;
    }

    public int getCurrentBirdIndex() {
        return currentBirdIndex;
    }
}
