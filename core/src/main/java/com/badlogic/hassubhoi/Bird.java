package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Bird extends Image {
    protected Texture texture;
    protected Vector2 position;
    protected Vector2 velocity;
    protected boolean isDragging = false;
    protected boolean isLaunched = false;
    private boolean playedLaunchSound = false;

    // Physics parameters
    protected final float gravity = -980f; // Adjust as needed
    protected final float maxStretchDistance = 200f; // Adjust as needed

    // Slingshot position
    protected final Vector2 slingshotPosition = new Vector2(150, 150); // Adjust as needed
    protected final Vector2 groundLevel = new Vector2(0, 50); // Adjust as needed

    public Bird(Texture texture) {
        super(texture);
        this.texture = texture;
        setSize(100, 100); // Adjust size as needed
        resetPosition();
        addListener(new BirdInputListener());
    }

    private void resetPosition() {
        position = new Vector2(slingshotPosition);
        velocity = new Vector2(0, 0);
        isLaunched = false;
        setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (isLaunched) {
            // Apply gravity to vertical velocity
            velocity.y += gravity * delta;

            // Update position based on velocity
            position.mulAdd(velocity, delta);

            // Check for collision with ground
            if (position.y <= groundLevel.y) {
                position.y = groundLevel.y;
                isLaunched = false;
                // Remove the bird after it lands
                remove();
            }
            setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);

            // Check for collisions with pigs and structures
            if (getStage() != null) {
                // Copy the actors to avoid concurrent modification
                Actor[] actorsArray = getStage().getActors().toArray(Actor.class);
                for (Actor actor : actorsArray) {
                    if (actor instanceof Pig) {
                        Pig pig = (Pig) actor;
                        if (this.getBoundingRectangle().overlaps(pig.getBoundingRectangle())) {
                            pig.takeDamage(10); // Adjust damage value as needed
                            // Remove the bird after collision
                            remove();
                            break;
                        }
                    } else if (actor instanceof Structure) {
                        Structure structure = (Structure) actor;
                        if (this.getBoundingRectangle().overlaps(structure.getBoundingRectangle())) {
                            structure.takeDamage(5); // Adjust damage as needed
                            // Remove the bird after collision
                            remove();
                            break;
                        }
                    }
                }
            }
        }
    }

    // Add this method to define getBoundingRectangle
    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    // Input Listener for dragging and launching
    private class BirdInputListener extends InputListener {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            if (!isLaunched) {
                isDragging = true;
                return true;
            }
            return false;
        }

        @Override
        public void touchDragged(InputEvent event, float x, float y, int pointer) {
            if (isDragging) {
                Vector2 stageCoordinates = new Vector2(event.getStageX(), event.getStageY());

                // Calculate the drag vector
                Vector2 dragVector = stageCoordinates.cpy().sub(slingshotPosition);

                // Limit the drag distance
                if (dragVector.len() > maxStretchDistance) {
                    dragVector.nor().scl(maxStretchDistance);
                }

                // Update the bird's position
                position = slingshotPosition.cpy().add(dragVector);
                setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
            }
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            if (isDragging) {
                isDragging = false;
                if (!isLaunched) {
                    launchBird();
                }
            }
        }
    }
    public Vector2 getLaunchVelocity() {
        // Calculate launch velocity based on current drag position
        Vector2 launchVector = slingshotPosition.cpy().sub(position);
        float launchPower = launchVector.len() * 8f; // Same as in launchBird()
        return launchVector.nor().scl(launchPower);
    }


    private void launchBird() {
        // Calculate launch velocity based on drag vector
        Vector2 launchVector = slingshotPosition.cpy().sub(position); // Opposite direction
        float launchPower = launchVector.len() * 8f; // Adjust multiplier as needed
        velocity = launchVector.nor().scl(launchPower);
        isLaunched = true;
    }

    public boolean isDragging() {
        return isDragging;
    }

    public Vector2 getPositionVector() {
        return position;
    }

    public Vector2 getSlingshotPosition() {
        return slingshotPosition;
    }

    public boolean hasPlayedLaunchSound() {
        return playedLaunchSound;
    }

    public void setPlayedLaunchSound(boolean playedLaunchSound) {
        this.playedLaunchSound = playedLaunchSound;
    }
    public Texture getTexture() {
        return texture;
    }
    public abstract void activateSpecialAbility();
}
