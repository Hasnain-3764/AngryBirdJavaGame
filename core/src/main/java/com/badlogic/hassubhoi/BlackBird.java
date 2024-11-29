package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

public class BlackBird extends Bird {
    private boolean hasExploded = false;
    private final float explosionRadius = 5f; // Adjust the radius as needed

    public BlackBird(GamePlayScreen level, float xpos, float ypos) {
        super(level, xpos, ypos);
    }

    @Override
    protected void initializeTexture() {
        texture = new Texture("Birds/black.png");
        textureWidth = 80;
        textureHeight = 80;
        rad = 20;
    }

    @Override
    public void render(float delta, SpriteBatch batch) {
        super.render(delta, batch);

        // Check for user input to activate the explosion
        if (!hasExploded && hasLaunched && Gdx.input.justTouched()) {
            explode();
            hasExploded = true; // Prevent multiple activations
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta);

        // If the bird hits something, trigger the explosion
        if (!hasExploded && hasLaunched && body.getLinearVelocity().len() < 0.1f) {
            explode();
            hasExploded = true;
        }
    }

    public void explode() {
        // Get the position of the bird
        Vector2 position = body.getPosition();

        // Create a circular area to detect bodies within the explosion radius
        CircleShape explosionShape = new CircleShape();
        explosionShape.setRadius(explosionRadius);

        // Create a temporary body for the explosion sensor
        BodyDef explosionBodyDef = new BodyDef();
        explosionBodyDef.type = BodyDef.BodyType.StaticBody;
        explosionBodyDef.position.set(position);

        Body explosionBody = level.world.createBody(explosionBodyDef);

        FixtureDef explosionFixtureDef = new FixtureDef();
        explosionFixtureDef.shape = explosionShape;
        explosionFixtureDef.isSensor = true; // Sensor so it doesn't affect physics

        explosionBody.createFixture(explosionFixtureDef);

        // Collect all fixtures in the explosion radius
        Array<Fixture> affectedFixtures = new Array<>();
        level.world.QueryAABB(new QueryCallback() {
            @Override
            public boolean reportFixture(Fixture fixture) {
                // Exclude the bird's own body
                if (fixture.getBody() != body) {
                    affectedFixtures.add(fixture);
                }
                return true;
            }
        }, position.x - explosionRadius, position.y - explosionRadius, position.x + explosionRadius, position.y + explosionRadius);

        // Apply effects to affected fixtures
        for (Fixture fixture : affectedFixtures) {
            Body affectedBody = fixture.getBody();
            Object userData = affectedBody.getUserData();

            // Apply explosion force
            Vector2 bodyPosition = affectedBody.getPosition();
            Vector2 forceDirection = bodyPosition.cpy().sub(position).nor();
            float distance = bodyPosition.dst(position);
            float forceMagnitude = (explosionRadius - distance) * 50f; // Adjust the force scaling as needed

            affectedBody.applyLinearImpulse(forceDirection.scl(forceMagnitude), bodyPosition, true);

            // Apply damage if the body is a Pig or Structure
            if (userData instanceof Pig) {
                Pig pig = (Pig) userData;
                pig.handleCollision(); // Or implement a method to reduce hit points
            } else if (userData instanceof Structure) {
                Structure structure = (Structure) userData;
                structure.handleCollision(); // Or implement a method to reduce hit points
            }
        }

        // Clean up the explosion body and shape
        level.world.destroyBody(explosionBody);
        explosionShape.dispose();

        // Remove the bird from the game
        level.bodiesToDestroy.add(body);
        level.birds.removeValue(this, true);

        // Optionally, play an explosion sound or animation here
    }
}
