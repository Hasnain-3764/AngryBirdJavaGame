package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class YellowBird extends Bird {
    private boolean hasBoosted = false;

    public YellowBird(GamePlayScreen level,float xpos,float ypos) {
        super(level, xpos, ypos);
    }

    @Override
    protected void initializeTexture() {
        texture = new Texture("yellowbird.png");
        textureHeight = 56;
        textureWidth = 60;
    }

    @Override
    protected void createCircle() {
        // Use a triangle shape instead
        createTriangle();
    }

    private void createTriangle() {
        cDef = new BodyDef();
        cDef.type = BodyDef.BodyType.StaticBody;
        cDef.position.set(xpos / GamePlayScreen.ppm, ypos / GamePlayScreen.ppm);

        body = level.world.createBody(cDef);

        // Side length and height calculations in meters
        float sideLength = 54 / GamePlayScreen.ppm;
        float height = (float) (Math.sqrt(3) / 2 * sideLength); // Height of equilateral triangle

        // Define vertices for the equilateral triangle
        PolygonShape triangleShape = new PolygonShape();
        float[] vertices = {
            0, height / 2,                  // Top vertex
            -sideLength / 2, -height / 2,   // Bottom left vertex
            sideLength / 2, -height / 2     // Bottom right vertex
        };
        triangleShape.set(vertices);

        body.createFixture(triangleShape, 1.0f);
        triangleShape.dispose();
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);

        // Check for user input to activate the power
        if (!hasBoosted && hasLaunched && Gdx.input.justTouched()) {
            body.setLinearVelocity(body.getLinearVelocity().scl(3)); // Double the velocity
            hasBoosted = true; // Prevent multiple activations
        }
    }
}
