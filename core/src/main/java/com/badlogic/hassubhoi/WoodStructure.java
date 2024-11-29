package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class WoodStructure extends Structure {
    public WoodStructure(GamePlayScreen level, float posX, float posY, float rotation) {
        super(level, posX, posY, rotation);
    }

    @Override
    protected void initializeTexture() {
            texture = new Texture("wood.png");
            width = 204;  // Width for Rock material
            height = 20;  // Height for Rock material (square)
    }

    @Override
    protected void initializeHitPoints() {
        hitPoints = 2;
    }
}
