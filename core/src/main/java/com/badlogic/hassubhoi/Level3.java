package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Level3 extends GamePlayScreen {
    private Texture background;
        public Level3(AngryBirdGames game, UIManager uiManager) {
        super(game, uiManager,3);
        Array<Bird> birds = new Array<>();
        birds.add(new RedBird(this, 450, 225));
        birds.add(new YellowBird(this, 375, 230));
        birds.add(new BlueBird(this, 300, 220));
        birds.add(new YellowBird(this, 225, 220));
        birds.add(new RedBird(this, 150, 220));
        birds.add(new BlackBird(this, 75, 220));


        Array<WoodStructure> woodBlocks = new Array<>();
        Array<SteelStructure> steelBlocks = new Array<>();
        Array<GlassStructure> glassBlocks = new Array<>();

        Array<Pig> pigs = new Array<>();

        float groundY = 300;

//        createGlassSetup(glassBlocks, pigs, 800, groundY);
        createStructure(glassBlocks, woodBlocks, steelBlocks, pigs, 800, groundY);
        createStructure(glassBlocks, woodBlocks, steelBlocks, pigs, 1000, groundY);
        anotherStructure(glassBlocks,woodBlocks,steelBlocks,pigs,1400,groundY);

        setupLevel(birds, mergeMaterials(glassBlocks,woodBlocks, steelBlocks), pigs);
    }



    private void createStructure(Array<GlassStructure> glassBlocks, Array<WoodStructure> woodBlocks, Array<SteelStructure> steelBlocks, Array<Pig> pigs, float startX, float groundY) {
        // Base layer: Two vertical rock blocks

        steelBlocks.add(new SteelStructure(this, startX, groundY +102 , 90)); // Left vertical wooden block
        steelBlocks.add(new SteelStructure(this, startX + 184, groundY +102, 90)); // Right vertical wooden block
        steelBlocks.add(new SteelStructure(this, startX +92, groundY + 215, 0)); // Top horizontal wooden block
        pigs.add(new HelmetPig(this, startX + 100, groundY + 100));
        pigs.add(new NormalPig(this, startX + 100, groundY + 250));
        pigs.add(new KingPig(this, startX + 100, groundY + 550));
        woodBlocks.add(new WoodStructure(this, startX+20, groundY +340 , 90)); // Left vertical wooden block
        woodBlocks.add(new WoodStructure(this, startX + 164, groundY +340, 90)); // Right vertical wooden block
        woodBlocks.add(new WoodStructure(this, startX +92, groundY + 500, 0));
    }

    public void anotherStructure(Array<GlassStructure> glassBlocks, Array<WoodStructure> woodBlocks, Array<SteelStructure> steelBlocks, Array<Pig> pigs, float startX, float groundY){
            steelBlocks.add(new SteelStructure(this, startX, groundY, 90));
        steelBlocks.add(new SteelStructure(this, startX + 200, groundY, 90));
        steelBlocks.add(new SteelStructure(this, startX, groundY +205, 90));
        steelBlocks.add(new SteelStructure(this, startX + 200, groundY + 205, 90));
        steelBlocks.add(new SteelStructure(this, startX + 100, groundY + 204 +1 + 204, 0));
        // Add two pigs between the rock blocks
        pigs.add(new HelmetPig(this, startX + 90, groundY ));
        pigs.add(new HelmetPig(this, startX + 100, groundY + 200 ));


        // Middle layer: Horizontal wooden block
        steelBlocks.add(new SteelStructure(this, startX , groundY + 204, 0));

        // Top layer: SteelStructure block on top of the wooden block
        steelBlocks.add(new SteelStructure(this, startX + 200, groundY + 204, 0));
        woodBlocks.add(new WoodStructure(this, startX + 40, groundY + 100 + 204 + 1 + 204, 65));
        pigs.add(new KingPig(this, startX + 100 , groundY + 5 + 204 + 1 + 204));
        woodBlocks.add(new WoodStructure(this, startX + 200 - 44 , groundY + 100 + 204 + 1 + 204, -65));

    }

    private Array<Structure> mergeMaterials(Array<GlassStructure> glassBlocks, Array<WoodStructure> woodBlocks, Array<SteelStructure> steelBlocks) {
        Array<Structure> allBlocks = new Array<>();
        allBlocks.addAll(woodBlocks);
        allBlocks.addAll(steelBlocks);
        allBlocks.addAll(glassBlocks);
        return allBlocks;
    }

    @Override
    public void render(float delta) {
        super.game.batch.begin();
        super.game.batch.end();

        super.render(delta);
    }
}
