package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class HelmetPig extends Pig {
    public HelmetPig(GamePlayScreen level, float posx, float posy) {
        super(level, posx, posy);
    }

    @Override
    protected void initializeTexture() {
        texture = new Texture("Pigs/helmetpig.png");
        textureWidth = 80;  // Width for MediumPig
        textureHeight = 79; // Height for MediumPig
        rad = 30;
    }

    @Override
    protected void initializeHitPoints() {
        hitPoints = 2;
    }
}
