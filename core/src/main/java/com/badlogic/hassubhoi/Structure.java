package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Structure extends Image {
    protected Texture texture;
    protected int health;

    public Structure(Texture texture, int health) {
        super(texture);
        this.texture = texture;
        this.health = health;
        setSize(64, 64); // adjust size
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            remove();
        }
    }

    //method to define getBoundingRectangle
    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
