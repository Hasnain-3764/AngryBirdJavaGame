package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class Level2 extends GameLevel {

    private World world;

    public Level2(World world) {
        this.world = world;
    }

    @Override
    public void setupLevel() {
        Texture woodTextureVert = new Texture(Gdx.files.internal("Structures/woodVert.png"));
        Texture glassTextureHorz = new Texture(Gdx.files.internal("Structures/glassHorz.png"));
        Texture glassTextureVert = new Texture(Gdx.files.internal("Structures/glassVert.png"));
        Texture steelTextureHorz = new Texture(Gdx.files.internal("Structures/steelHorz.png"));

        // woodbase 1 and 2 and glassbase 1 cater to helmet pig
        WoodStructure woodPillar1 = new WoodStructure(woodTextureVert);
        woodPillar1.setPosition(590, 55);
        woodPillar1.setSize(10,95);
        addStructure(woodPillar1);

        WoodStructure woodPillar2 = new WoodStructure(woodTextureVert);
        woodPillar2.setPosition(650, 45);
        woodPillar2.setSize(10,105);
        addStructure(woodPillar2);

        GlassStructure glassPillar1 = new GlassStructure(glassTextureVert);
        glassPillar1.setPosition(570,60);
        glassPillar1.setSize(10,195);
        addStructure(glassPillar1);

        GlassStructure glassPillar2 = new GlassStructure(glassTextureVert);
        glassPillar2.setPosition(670,45);
        glassPillar2.setSize(10, 210);
        addStructure(glassPillar2);

        GlassStructure glaasBase1 = new GlassStructure(glassTextureHorz);
        glaasBase1.setPosition(590,150);
        glaasBase1.setSize(70,10);
        addStructure(glaasBase1);

        GlassStructure glassBase2 = new GlassStructure(glassTextureHorz);
        glassBase2.setPosition(570,245);
        glassBase2.setSize(110,10);
        addStructure(glassBase2);
//

        GlassStructure glassPillar3 = new GlassStructure(glassTextureVert);
        glassPillar3.setPosition(330,60);
        glassPillar3.setSize(10,85);
        addStructure(glassPillar3);

        GlassStructure glassPillar4 = new GlassStructure(glassTextureVert);
        glassPillar4.setPosition(380,60);
        glassPillar4.setSize(10,85);
        addStructure(glassPillar4);

        GlassStructure glassBase3 = new GlassStructure(glassTextureHorz);
        glassBase3.setPosition(330,145);
        glassBase3.setSize(60,10);
        addStructure(glassBase3);

        // pigs
        Texture pigTexture = new Texture(Gdx.files.internal("Pigs/pig.png"));
        NormalPig pig1 = new NormalPig(pigTexture);
        pig1.setPosition(335,155);
        pig1.setSize(45,45);
        addPig(pig1);

        Texture helmetPigTexture = new Texture(Gdx.files.internal("Pigs/helmetpig.png"));
        HelmetPig helmetPig = new HelmetPig(helmetPigTexture);
        helmetPig.setPosition(600, 160); // helmet pig location
        helmetPig.setSize(50,50);
        addPig(helmetPig);


        // Add birds
        Texture redBirdText1 = new Texture(Gdx.files.internal("Birds/red.png"));
        RedBird redBird1 = new RedBird(world, redBirdText1);
        redBird1.setPosition(150,0);
        addBird(redBird1);

        RedBird redBird2 = new RedBird(world, redBirdText1);
        redBird2.setPosition(150,0);
        addBird(redBird2);

        RedBird redBird3 = new RedBird(world, redBirdText1);
        redBird3.setPosition(150,0);
        addBird(redBird3);



        Texture blueBirdTexture = new Texture(Gdx.files.internal("Birds/blue.png"));
        BlueBird blueBird = new BlueBird(world, blueBirdTexture);
        blueBird.setPosition(150, 150); // Position the blue bird
        addBird(blueBird);

        BlueBird blueBird2 = new BlueBird(world, blueBirdTexture);
        blueBird2.setPosition(150, 150); // Position the blue bird
        addBird(blueBird2);


        Texture yellowBirdText1 = new Texture(Gdx.files.internal("Birds/yellow.png"));
        YellowBird yellowBird1 = new YellowBird(world, yellowBirdText1);
        yellowBird1.setPosition(150,80);
        addBird(yellowBird1);

        YellowBird yellowBird2 = new YellowBird(world, yellowBirdText1);
        yellowBird2.setPosition(150,80);
        addBird(yellowBird2);


    }
}
