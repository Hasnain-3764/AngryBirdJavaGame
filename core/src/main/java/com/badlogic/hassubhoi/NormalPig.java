package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;

public class NormalPig extends Pig {
    public NormalPig(Texture texture) {
        super(texture, 10); // Adjust health as needed
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        System.out.println("Normal Pig takes " + damage + " damage!");
        // Implement additional logic if needed
    }
}
