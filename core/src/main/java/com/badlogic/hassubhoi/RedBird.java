package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RedBird extends Bird {
    public RedBird(GamePlayScreen level, float xpos, float ypos) {
        super(level, xpos, ypos);
    }

    @Override
    protected void initializeTexture() {
        texture = new Texture("redbird.png");
        textureWidth = 80;
        textureHeight = 80;
        rad = 20;
    }
}
