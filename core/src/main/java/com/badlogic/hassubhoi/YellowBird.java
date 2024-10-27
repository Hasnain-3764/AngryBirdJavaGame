package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;

public class YellowBird extends Bird {
    private boolean hasBoosted = false;

    public YellowBird(Texture texture) {
        super(texture);
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
