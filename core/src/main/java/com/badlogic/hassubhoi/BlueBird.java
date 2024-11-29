package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BlueBird extends Bird {
    private boolean hasSplit = false;

    public BlueBird(GamePlayScreen level, float xpos, float ypos) {
        super(level, xpos, ypos);
    }

    @Override
    protected void initializeTexture() {
        texture = new Texture("Birds/blue.png");
        textureWidth = 45;
        textureHeight = 40;
        rad = 20;
    }


    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);

        // Check for user input to activate the splitting power
        if (!hasSplit && hasLaunched && Gdx.input.justTouched()) {
            splitIntoThree();
            hasSplit = true; // Prevent multiple activations
        }
    }

    private void splitIntoThree() {
        Vector2 velocity = body.getLinearVelocity();
        Vector2 position = body.getPosition();

        // Create two additional birds
        BlueBird bird1 = createSplitBird(position.x, position.y - 0.8f, velocity);
        BlueBird bird2 = createSplitBird(position.x, position.y + 0.8f, velocity);

        // Safely schedule adding new birds to the game
        level.scheduleTask(() -> {
            level.birds.add(bird1);
            level.birds.add(bird2);
        });
    }

    private BlueBird createSplitBird(float x, float y, Vector2 velocity) {
        BlueBird bird = new BlueBird(level, x * GamePlayScreen.ppm, y * GamePlayScreen.ppm);
        bird.body.setType(BodyDef.BodyType.DynamicBody);
        bird.body.setTransform(x, y, 0);
        bird.body.setLinearVelocity(velocity);
        bird.hasLaunched = true; // Mark the bird as launched
        return bird;
    }
}
