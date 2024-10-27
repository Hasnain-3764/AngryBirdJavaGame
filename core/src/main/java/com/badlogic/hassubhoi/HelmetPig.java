package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;

public class HelmetPig extends Pig {
    public HelmetPig(Texture texture) {
        super(texture, 10);
    }

    @Override
    public void takeDamage(int damage) {
//        int reducedDamage = damage / 2;
        super.takeDamage(damage);
        System.out.println("Helmet Pig takes " + damage + " damage due to helmet protection!");
        // implement
    }
}
