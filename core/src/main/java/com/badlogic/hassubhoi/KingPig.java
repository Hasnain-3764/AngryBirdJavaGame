package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;

public class KingPig extends Pig {
    public KingPig(GamePlayScreen level, float posx, float posy) {
        super(level, posx, posy);
    }

    @Override
    protected void initializeTexture() {
        texture = new Texture("Pigs/kingpig.png");
        textureWidth = 100;  // Width for BigPig
        textureHeight = 99; // Height for BigPig
        rad = 40;
    }
    @Override
    protected void initializeHitPoints() {
        hitPoints = 3;
    }
}
