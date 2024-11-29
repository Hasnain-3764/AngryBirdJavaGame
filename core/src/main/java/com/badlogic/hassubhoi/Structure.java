package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Structure extends Image {
    protected GamePlayScreen level;
    protected Texture texture;
    public Body body;
    protected float width;    // Material width
    protected float height;   // Material height
    protected BodyDef mDef;
    protected int hitPoints;

    public Structure(GamePlayScreen level, float posX, float posY, float rotation) {
        this.level = level;
        initializeTexture();
        createRectangle(posX, posY, rotation);
        initializeHitPoints();

        body.setUserData(this);
    }
    // Create Box2D body with rectangle shape, using position and rotation
    private void createRectangle(float posX, float posY, float rotation) {
        mDef = new BodyDef();
        mDef.type = BodyDef.BodyType.DynamicBody;  // Typically, materials are static
        mDef.position.set(posX / GamePlayScreen.ppm, posY / GamePlayScreen.ppm);  // Set the initial position
        mDef.angle = rotation * MathUtils.degreesToRadians; // Set rotation in radians

        body = level.world.createBody(mDef);

        PolygonShape rectangle = new PolygonShape();
        rectangle.setAsBox(width / 2 / GamePlayScreen.ppm, height / 2 / GamePlayScreen.ppm); // Set width and height in Box2D units

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = rectangle;
        fixtureDef.density = 1.0f;      // Standard density
        fixtureDef.friction = 1.0f;

        body.createFixture(fixtureDef);
        rectangle.dispose();
    }
    public void handleCollision() {
        hitPoints--;
        if (hitPoints <= 0 && body != null) {
            if (!level.bodiesToDestroy.contains(body, true)) {
                level.bodiesToDestroy.add(body);
            }
            level.structures.removeValue(this, true); // Remove from game objects
        }
    }

    public void render(float delta, SpriteBatch batch) {
        Vector2 position = body.getPosition();
        float angle = body.getAngle() * MathUtils.radiansToDegrees;
        level.game.batch.begin();

        float widthMeters = width / GamePlayScreen.ppm;
        float heightMeters = height / GamePlayScreen.ppm;

        level.game.batch.draw(
            texture,
            position.x - widthMeters / 2,
            position.y - heightMeters / 2,
            widthMeters / 2, heightMeters / 2, // Origin at center
            widthMeters, heightMeters,
            1, 1,
            angle,
            0, 0,
            texture.getWidth(), texture.getHeight(),
            false, false
        );
        level.game.batch.end();

    }

    protected abstract void initializeTexture();
    protected abstract void initializeHitPoints();

//    public void takeDamage(int damage) {
//        health -= damage;
//        if (health <= 0) {
//            remove();
//        }
//    }

    //method to define getBoundingRectangle
//    public Rectangle getBoundingRectangle() {
//        return new Rectangle(getX(), getY(), getWidth(), getHeight());
//    }

    public void update(float delta) {
        // strucutre-specific update logic not needed right now
    }

    public Texture getTexture() {
        return texture;
    }

    public int getHitPoints() {
        return hitPoints;
    }
    public void setHitPoints(int hitPoints) {
        this.hitPoints = hitPoints;
    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//    }
}
