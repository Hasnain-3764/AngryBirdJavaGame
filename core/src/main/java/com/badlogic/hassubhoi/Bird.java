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


    protected final float gravity = -980f;
    protected final float maxStretchDistance = 200f;

    protected final Vector2 slingshotPosition = new Vector2(150, 150);
    protected final Vector2 groundLevel = new Vector2(0, 50);

    private boolean isPaused = false;

    public Bird(Texture texture) {
        super(texture);
        this.texture = texture;
        setSize(100, 100);
        resetPosition();
        addListener(new BirdInputListener());
    }

    private void resetPosition() {
        position = new Vector2(slingshotPosition);
        velocity = new Vector2(0, 0);
        isLaunched = false;
        setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
    }

    // not required as of now
    @Override
    public void act(float delta) {
        if (isPaused) {
            return;  // Skip updates if the game is paused
        }
        super.act(delta);

        if (isLaunched) {
            velocity.y += gravity * delta;
            position.mulAdd(velocity, delta);

            //  collision with ground
            if (position.y <= groundLevel.y) {
                position.y = groundLevel.y;
                isLaunched = false;
                // remove the bird if it lands
                remove();
            }
            setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);

            // check for collisions with pigs and structures
            if (getStage() != null) {
                Actor[] actorsArray = getStage().getActors().toArray(Actor.class);
                for (Actor actor : actorsArray) {
                    if (actor instanceof Pig) {
                        Pig pig = (Pig) actor;
                        if (this.getBoundingRectangle().overlaps(pig.getBoundingRectangle())) {
                            pig.takeDamage(10); //adjust damage value as needed
                            remove();
                            break;
                        }
                    } else if (actor instanceof Structure) {
                        Structure structure = (Structure) actor;
                        if (this.getBoundingRectangle().overlaps(structure.getBoundingRectangle())) {
                            structure.takeDamage(5);
                            // removing bird since it collided
                            remove();
                            break;
                        }
                    }
                }
            }
        }
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public Rectangle getBoundingRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

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
                Vector2 dragVector = stageCoordinates.cpy().sub(slingshotPosition);
                if (dragVector.len() > maxStretchDistance) {
                    dragVector.nor().scl(maxStretchDistance);
                }
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
        Vector2 launchVector = slingshotPosition.cpy().sub(position);
        float launchPower = launchVector.len() * 8f;
        return launchVector.nor().scl(launchPower);
    }


    private void launchBird() {
        Vector2 launchVector = slingshotPosition.cpy().sub(position);
        float launchPower = launchVector.len() * 8f;
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
