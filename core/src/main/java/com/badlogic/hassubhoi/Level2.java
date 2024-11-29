package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Level2 extends GamePlayScreen {
    private Texture background;

    public Level2(AngryBirdGames game, UIManager uiManager) {
        super(game, uiManager,2);
        Array<Bird> birds = new Array<>();
        birds.add(new RedBird(this, 450, 225));
        birds.add(new YellowBird(this, 375, 230));
        birds.add(new BlueBird(this, 300, 220));
        birds.add(new YellowBird(this, 225, 220));
        birds.add(new RedBird(this, 150, 220));


        Array<GlassStructure> glassBlocks = new Array<>();
        Array<WoodStructure> woodBlocks = new Array<>();
        Array<Pig> pigs = new Array<>();

        float groundY = 203;

        createStructure(glassBlocks, woodBlocks, pigs, 900, groundY);
        createStructure(glassBlocks, woodBlocks, pigs, 1100, groundY);


        setupLevel(birds, mergeMaterials(glassBlocks, woodBlocks), pigs);
    }

//    private void createSetup()
    private void createStructure(Array<GlassStructure> glassBlocks, Array<WoodStructure> woodBlocks, Array<Pig> pigs, float startX, float groundY) {
        woodBlocks.add(new WoodStructure(this, startX, groundY +102 , 90)); // Left vertical wooden block
        woodBlocks.add(new WoodStructure(this, startX + 184, groundY +102, 90)); // Right vertical wooden block
        woodBlocks.add(new WoodStructure(this, startX +92, groundY + 215, 0)); // Top horizontal wooden block
        pigs.add(new HelmetPig(this, startX + 100, groundY + 100));
        pigs.add(new NormalPig(this, startX + 100, groundY + 250));
        pigs.add(new KingPig(this, startX + 100, groundY + 550));
        woodBlocks.add(new WoodStructure(this, startX+20, groundY +340 , 90)); // Left vertical wooden block
        woodBlocks.add(new WoodStructure(this, startX + 164, groundY +340, 90)); // Right vertical wooden block
        woodBlocks.add(new WoodStructure(this, startX +92, groundY + 500, 0)); // Top horizontal wooden block// Pig inside the hut's rectangular base
        // Roof: Two glass blocks at 45-degree angles
        glassBlocks.add(new GlassStructure(this, startX + 40, groundY + 600, 65)); // Left sloped glass block
        glassBlocks.add(new GlassStructure(this, startX + 140, groundY + 600, -65)); // Right sloped glass block

    }


    private Array<Structure> mergeMaterials(Array<GlassStructure> glassBlocks, Array<WoodStructure> woodBlocks) {
        Array<Structure> allBlocks = new Array<>();
        allBlocks.addAll(glassBlocks);
        allBlocks.addAll(woodBlocks);
        return allBlocks;
    }

    @Override
    public void render(float delta) {
        super.game.batch.begin();
        super.game.batch.end();

        super.render(delta);
    }
}
