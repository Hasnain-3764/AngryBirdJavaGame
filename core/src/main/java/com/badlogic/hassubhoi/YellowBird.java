package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.World;

public class YellowBird extends Bird {
    private boolean hasBoosted = false;

    public YellowBird(World world, Texture texture) {
        super(world, texture);
    }
    @Override
    public void activateSpecialAbility() {
        if (!hasBoosted) {
            System.out.println("Yellow Bird increases speed!");
            // increase the bird's speed
            velocity.scl(4f); // Increase speed by 50%
            hasBoosted = true;
        }
    }
}
