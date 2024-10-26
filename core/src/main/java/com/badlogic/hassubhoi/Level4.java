package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Level4 extends GameLevel {

    @Override
    public void setupLevel() {


        // Add pigs
        Texture kingPigTexture = new Texture(Gdx.files.internal("Pigs/kingpig.png"));
        KingPig kingPig = new KingPig(kingPigTexture);
        kingPig.setPosition(600, 150); // Position the king pig
        addPig(kingPig);

        // Add birds
        Texture yellowBirdTexture = new Texture(Gdx.files.internal("Birds/yellow.png"));
        YellowBird yellowBird = new YellowBird(yellowBirdTexture);
        yellowBird.setPosition(150, 150); // Position the yellow bird
        addBird(yellowBird);
    }
}
