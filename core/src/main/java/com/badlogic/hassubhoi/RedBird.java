package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;

public class RedBird extends Bird {
    public RedBird(Texture texture) {
        super(texture);
    }

    @Override
    public void activateSpecialAbility() {
        // Red bird has no special ability
    }
}
