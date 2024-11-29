package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;

public class NormalPig extends Pig {
    public NormalPig(GamePlayScreen level, float posx, float posy) {
        super(level, posx, posy);
    }

    @Override
    protected void initializeTexture() {
        texture = new Texture("bigpig.png");
        textureWidth = 50;  // Width for SmallPig
        textureHeight = 48; // Height for SmallPig
        rad = 24;
    }

    protected void initializeHitPoints() {
        hitPoints = 1; // SmallPig requires 1 hit
    }

}
