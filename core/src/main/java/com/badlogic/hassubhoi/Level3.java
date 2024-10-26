package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Level3 extends GameLevel {

    @Override
    public void setupLevel() {
        Texture woodTextureVert = new Texture(Gdx.files.internal("Structures/woodVert.png"));
        Texture glassTextureHorz = new Texture(Gdx.files.internal("Structures/glassHorz.png"));
        Texture glassTextureVert = new Texture(Gdx.files.internal("Structures/glassVert.png"));
        Texture steelTextureHorz = new Texture(Gdx.files.internal("Structures/steelHorz.png"));
        Texture steelTextureVert = new Texture(Gdx.files.internal("Structures/steelVert.png"));
        Texture woodTextureHorz = new Texture(Gdx.files.internal("Structures/woodHorz.png"));

        // structures
        // code related to right structures
        GlassStructure gp1 = new GlassStructure(glassTextureVert);
        gp1.setPosition(530,60);
        gp1.setSize(10,155);
        addStructure(gp1);

        GlassStructure gp2 = new GlassStructure(glassTextureVert);
        gp2.setPosition(580,60);
        gp2.setSize(10,155);
        addStructure(gp2);

        GlassStructure gb1 = new GlassStructure(glassTextureHorz);
        gb1.setPosition(530,215);
        gb1.setSize(60,10);
        addStructure(gb1);

        WoodStructure wb1 = new WoodStructure(woodTextureHorz);
        wb1.setPosition(530,115);
        wb1.setSize(60,10);
        addStructure(wb1);


        // code related to midddle structures

        SteelStructure steelPillar1 = new SteelStructure(steelTextureVert);
        steelPillar1.setPosition(320, 60);
        steelPillar1.setSize(10,90);
        addStructure(steelPillar1);

        SteelStructure steelPillar2 = new SteelStructure(steelTextureVert);
        steelPillar2.setPosition(460, 60);
        steelPillar2.setSize(10,90);
        addStructure(steelPillar2);

        WoodStructure woodPillar1 = new WoodStructure(woodTextureVert);
        woodPillar1.setPosition(290,60);
        woodPillar1.setSize(10,185);
        addStructure(woodPillar1);

        WoodStructure woodPillar2 = new WoodStructure(woodTextureVert);
        woodPillar2.setPosition(490,65);
        woodPillar2.setSize(10, 180);
        addStructure(woodPillar2);

        SteelStructure steelBase1 = new SteelStructure(steelTextureHorz);
        steelBase1.setPosition(320,150);
        steelBase1.setSize(150,10);
        addStructure(steelBase1);

        GlassStructure glassBase2 = new GlassStructure(glassTextureHorz);
        glassBase2.setPosition(290,245);
        glassBase2.setSize(210,10);
        addStructure(glassBase2);

        // Pigs
        Texture kingPigTexture = new Texture(Gdx.files.internal("Pigs/kingpig.png"));
        KingPig kingPig1 = new KingPig(kingPigTexture);
        kingPig1.setPosition(532, 225);
        kingPig1.setSize(50,50);
        addPig(kingPig1);

        Texture pigTexture = new Texture(Gdx.files.internal("Pigs/pig.png"));
        NormalPig pig1 = new NormalPig(pigTexture);
        pig1.setPosition(540, 125);
        pig1.setSize(35,35);
        addPig(pig1);

        NormalPig pig2 = new NormalPig(pigTexture);
        pig2.setPosition(370,60);
        pig2.setSize(65,65);
        addPig(pig2);

        Texture helmetPigTexture = new Texture(Gdx.files.internal("Pigs/helmetpig.png"));
        HelmetPig helmetPig1 = new HelmetPig(helmetPigTexture);
        helmetPig1.setPosition(330,160);
        helmetPig1.setSize(45,45);
        addPig(helmetPig1);

        HelmetPig helmetPig2 = new HelmetPig(helmetPigTexture);
        helmetPig2.setPosition(415,160);
        helmetPig2.setSize(45,45);
        addPig(helmetPig2);


        // Add birds
        Texture blackBirdTexture = new Texture(Gdx.files.internal("Birds/black.png"));
        BlackBird blackBird = new BlackBird(blackBirdTexture);
        blackBird.setPosition(150, 150); // Position the black bird
        addBird(blackBird);

        Texture blueBirdTexture = new Texture(Gdx.files.internal("Birds/blue.png"));
        BlueBird blueBird = new BlueBird(blueBirdTexture);
        blueBird.setPosition(150, 150); // Position the blue bird
        addBird(blueBird);

        Texture yellowBirdText1 = new Texture(Gdx.files.internal("Birds/yellow.png"));
        YellowBird yellowBird1 = new YellowBird(yellowBirdText1);
        yellowBird1.setPosition(150,80);
        addBird(yellowBird1);

//        Texture blueBirdTexture = new Texture(Gdx.files.internal("blue.png"));
        BlueBird blueBird2 = new BlueBird(blueBirdTexture);
        blueBird2.setPosition(150, 150); // Position the blue bird
        addBird(blueBird2);


    }
}
