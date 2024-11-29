package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public abstract class Pig{
    protected GamePlayScreen level;
    protected int health;
    protected Texture texture;
    public Body body;
    protected float textureWidth;  // Pig texture width
    protected float textureHeight; // Pig texture height
    protected BodyDef pDef;
    protected float posx;
    protected float posy;
    protected float rad;
    protected int hitPoints;

    public Pig(GamePlayScreen level, float posx, float posy) {
        this.level = level;
        this.posx = posx;
        this.posy = posy;
        initializeTexture();
        createCircle();
        initializeHitPoints();
        body.setUserData(this);
    }

    private void createCircle() {
        pDef = new BodyDef();
        pDef.type = BodyDef.BodyType.DynamicBody;
        pDef.position.set(posx / GamePlayScreen.ppm, posy / GamePlayScreen.ppm);  // Initial position

        body = level.world.createBody(pDef);

        CircleShape cShape = new CircleShape();
        cShape.setRadius(rad / GamePlayScreen.ppm); // Set radius based on pig size

        body.createFixture(cShape, 1.0f);
        cShape.dispose();
    }

    protected abstract void initializeTexture();

    protected abstract void initializeHitPoints();

    public void handleCollision() {
        hitPoints--;
        if (hitPoints <= 0 && body != null) {
            if (!level.bodiesToDestroy.contains(body, true)) {
                level.bodiesToDestroy.add(body);
            }
            level.pigs.removeValue(this, true); // Remove from game objects
        }
    }
//
//    public void takeDamage(int damage) {
//        health -= damage;
//        if (health <= 0) {
//            remove();
//            //implemnet
//        }
//    }
public void render(float delta, SpriteBatch batch) {
    Vector2 position = body.getPosition();
    float angle = body.getAngle() * MathUtils.radiansToDegrees;
    level.game.batch.begin();

    float textureWidthMeters = textureWidth / GamePlayScreen.ppm;
    float textureHeightMeters = textureHeight / GamePlayScreen.ppm;

    level.game.batch.draw(
        texture,
        position.x - textureWidthMeters / 2,
        position.y - textureHeightMeters / 2,
        textureWidthMeters / 2, textureHeightMeters / 2, // Origin at center
        textureWidthMeters, textureHeightMeters,
        1, 1,
        angle,
        0, 0,
        texture.getWidth(), texture.getHeight(),
        false, false
    );
    level.game.batch.end();

}

    public void update(float delta) {
        // pig-specific update logic not needed right now
    }

    // ddd this method to define getBoundingRectangle
//    public Rectangle getBoundingRectangle() {
//        return new Rectangle(getX(), getY(), getWidth(), getHeight());
//    }
//
//    @Override
//    public void act(float delta) {
//        super.act(delta);
//        // not implemented.
//        // pigs are stationary for now
//    }
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
