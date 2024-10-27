package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Level1 extends GameLevel {

    @Override
    public void setupLevel() {
        Texture woodTextureVert = new Texture(Gdx.files.internal("Structures/woodVert.png"));
        Texture woodTextureHorz = new Texture(Gdx.files.internal("Structures/woodHorz.png"));
        Texture glassTextureHorz = new Texture(Gdx.files.internal("Structures/glassHorz.png"));
        Texture glassTextureVert = new Texture(Gdx.files.internal("Structures/glassVert.png"));
        Texture steelTextureHorz = new Texture(Gdx.files.internal("Structures/steelHorz.png"));

        // structures related to pig
        GlassStructure glassPillar1 = new GlassStructure(glassTextureVert);
        glassPillar1.setPosition(530,60);
        glassPillar1.setSize(10,155);
        addStructure(glassPillar1);

        GlassStructure glassPillar2 = new GlassStructure(glassTextureVert);
        glassPillar2.setPosition(580,60);
        glassPillar2.setSize(10,155);
        addStructure(glassPillar2);

        GlassStructure glassBase1 = new GlassStructure(glassTextureHorz);
        glassBase1.setPosition(530,215);
        glassBase1.setSize(60,10);
        addStructure(glassBase1);

        WoodStructure woodBase1 = new WoodStructure(woodTextureHorz);
        woodBase1.setPosition(530,115);
        woodBase1.setSize(60,10);
        addStructure(woodBase1);

        // pigs on top
        Texture pigTexture = new Texture(Gdx.files.internal("Pigs/pig.png"));
        NormalPig pig1 = new NormalPig(pigTexture);
        pig1.setPosition(532, 225);
        pig1.setSize(50,50);
        addPig(pig1);

        Texture pigTexture2 = new Texture(Gdx.files.internal("Pigs/pig.png"));
        NormalPig pig2 = new NormalPig(pigTexture2);
        pig2.setPosition(540, 125);
        pig2.setSize(35,35);
        addPig(pig2);

        // Add birds
        Texture redBirdTexture = new Texture(Gdx.files.internal("Birds/red.png"));
        RedBird redBird = new RedBird(redBirdTexture);
        redBird.setPosition(150, 150);
        addBird(redBird);

        RedBird redBird2 = new RedBird(redBirdTexture);
        redBird2.setPosition(150, 150);
        addBird(redBird2);

        RedBird redBird3 = new RedBird(redBirdTexture);
        redBird3.setPosition(150, 150);
        addBird(redBird3);


    }
}
