package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;
import org.w3c.dom.Text;

import javax.swing.*;

public class Level4 extends GameLevel {

    private World world;

    public Level4(World world) {
        this.world = world;
    }

    @Override
    public void setupLevel() {

        // to be implemented

        // Add pigs
        Texture kingPigTexture = new Texture(Gdx.files.internal("Pigs/kingpig.png"));
        KingPig kingPig = new KingPig(kingPigTexture);
        kingPig.setPosition(600, 150); // Position the king pig
        addPig(kingPig);

        // add birds
        Texture yellowBirdTexture = new Texture(Gdx.files.internal("Birds/yellow.png"));
        YellowBird yellowBird = new YellowBird(world, yellowBirdTexture);
        yellowBird.setPosition(150, 150); // Position the yellow bird
        addBird(yellowBird);

        YellowBird yellowBird2 = new YellowBird(world, yellowBirdTexture);
        yellowBird2.setPosition(150,150);
        addBird(yellowBird2);

        YellowBird yellowBird3 = new YellowBird(world, yellowBirdTexture);
        yellowBird3.setPosition(150,150);
        addBird(yellowBird3);

        // only 3 needed as of now...
    }
}
