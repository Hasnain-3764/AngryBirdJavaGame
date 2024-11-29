package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class GlassStructure extends Structure {
    public GlassStructure(GamePlayScreen level, float posX, float posY, float rotation) {
        super(level, posX, posY, rotation);
    }

    @Override
    protected void initializeTexture() {
        texture = new Texture("glass.png");
        width = 198;  // Width for Glass material
        height = 20; // Height for Glass material
    }

    @Override
    protected void initializeHitPoints() {
        hitPoints = 1; // Glass requires 1 hit
    }
}
