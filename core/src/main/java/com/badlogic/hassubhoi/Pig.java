package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Pig extends Image {
    protected Texture texture;
    protected int health;

    public Pig(Texture texture, int health) {
        super(texture);
        this.texture = texture;
        this.health = health;
        setSize(100, 100); // adjust size here
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            remove();
            //implemnet
        }
    }

    // ddd this method to define getBoundingRectangle
    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        // not implemented.
        // pigs are stationary for now
    }
    public Texture getTexture() {
        return texture;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
