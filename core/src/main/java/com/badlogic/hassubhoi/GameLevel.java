package com.badlogic.hassubhoi;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public abstract class GameLevel {
    protected Array<Structure> structures;
    protected Array<Pig> pigs;
    protected Array<Bird> birds;
    protected World world;

    public GameLevel() {
        structures = new Array<>();
        pigs = new Array<>();
        birds = new Array<>();
    }

    public void setWorld(World world) {
        this.world = world;
    }
    public void addBird(Bird bird) {
        birds.add(bird);
    }

    public Array<Bird> getBirds() {
        return birds;
    }

    public abstract void setupLevel();// implement

    public void addStructure(Structure structure) {
        structures.add(structure);
    }

    public void addPig(Pig pig) {
        pigs.add(pig);
    }

    public Array<Structure> getStructures() {
        return structures;
    }

    public Array<Pig> getPigs() {
        return pigs;
    }

}
