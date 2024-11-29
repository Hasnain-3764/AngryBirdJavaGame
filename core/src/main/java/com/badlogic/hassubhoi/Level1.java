package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;


public class Level1 extends GamePlayScreen {
    private Texture background;
    private UIManager uiManager;
    public Level1(AngryBirdGames game, UIManager uiManager) {
        super(game, uiManager,1);

//        background = new Texture("background.png");

        Array<Bird> birds = new Array<>();
        birds.add(new RedBird(this, 450, 225));
        birds.add(new YellowBird(this, 375, 230));
        birds.add(new BlueBird(this, 300, 220));
        birds.add(new BlackBird(this, 225, 215)); // Add a BlackBird
        Array<Structure> glassBlocks = new Array<>();
        Array<Pig> pigs = new Array<>();

        float groundY = 300;
        createGlassSetup(glassBlocks, pigs, 800, groundY);
        createGlassSetup(glassBlocks, pigs, 1200, groundY);
        createGlassSetup(glassBlocks, pigs, 1600, groundY);

        setupLevel(birds, glassBlocks, pigs);
    }

    private void createGlassSetup(Array<Structure> glassBlocks, Array<Pig> pigs, float startX, float groundY) {
//        glassBlocks.add(new GlassStructure(this, startX, groundY, 90));
        glassBlocks.add(new GlassStructure(this, startX + 200, groundY, 90));
//        glassBlocks.add(new GlassStructure(this, startX, groundY + 204, 0));
        glassBlocks.add(new GlassStructure(this, startX + 200, groundY + 240, 0));
        pigs.add(new HelmetPig(this, startX + 200, groundY + 400));

    }


    @Override
    public void render(float delta) {
        super.game.batch.begin();
//        super.game.batch.draw(background,0,0,AngryBirdGames.WIDTH,AngryBirdGames.HEIGHT);
        super.game.batch.end();
        super.render(delta);
    }
}




