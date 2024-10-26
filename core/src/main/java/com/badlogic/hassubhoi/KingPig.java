package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;

public class KingPig extends Pig {
    public KingPig(Texture texture) {
        super(texture, 30); // Adjust health as needed
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        System.out.println("King Pig takes " + damage + " damage!");
        // Implement additional logic if needed
    }
}
