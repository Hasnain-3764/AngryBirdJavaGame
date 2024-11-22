package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class RedBird extends Bird {
    public RedBird(World world, Texture texture) {
        super(world, texture);
    }

    @Override
    public void activateSpecialAbility() {
        //red bird has no special ability
    }
}
