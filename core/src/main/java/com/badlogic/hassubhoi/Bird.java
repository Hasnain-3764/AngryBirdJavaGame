package com.badlogic.hassubhoi;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.hassubhoi.GamePlayScreen.ppm;

public abstract class Bird{
    protected GamePlayScreen level;
    protected Texture texture;
    protected Vector2 position;
    protected Vector2 velocity;
    protected boolean isDragging = false;
    protected boolean isLaunched = false;
    private boolean playedLaunchSound = false;
    public Body body;
    protected float textureWidth;
    protected float textureHeight;
    protected BodyDef cDef;
    protected float xpos;
    protected float ypos;
    protected float rad;
    private Vector2 initialTouchPos;
    private boolean trajectoryVisible = true;
    private Array<Vector2> trajectoryPoints;
    private float stationaryTime = 0;
    public boolean hasLaunched = false;
    private float activityTime = 0;

    protected final float gravity = -980f;
    protected final float maxStretchDistance = 200f;

    protected final Vector2 slingshotPosition = new Vector2(150, 150);
    protected final Vector2 groundLevel = new Vector2(0, 50);

    public Bird(GamePlayScreen level, float xpos, float ypos) {
//        super(texture);
//        this.texture = texture;
        this.level = level;
        this.xpos = xpos;
        this.ypos = ypos;
        initializeTexture();
        createCircle();
//        setSize(100, 100);
//        resetPosition();
//        addListener(new BirdInputListener());
        trajectoryPoints = new Array<>();
        initialTouchPos = new Vector2(540,380);
        body.setUserData(this);
    }

    protected abstract void initializeTexture();

    protected void createCircle() {
        cDef = new BodyDef();
        cDef.type = BodyDef.BodyType.StaticBody;  // Start as static for dragging
        cDef.position.set(xpos / ppm, ypos / ppm);

        body = level.world.createBody(cDef);

        CircleShape cShape = new CircleShape();
        cShape.setRadius(rad / ppm);

        body.createFixture(cShape, 1.0f);
        cShape.dispose();
    }

    private void resetPosition() {
        position = new Vector2(slingshotPosition);
        velocity = new Vector2(0, 0);
        isLaunched = false;
//        setPosition(position.x - getWidth() / 2, position.y - getHeight() / 2);
    }

    //    public Rectangle getBoundingRectangle() {
//        return new Rectangle(getX(), getY(), getWidth(), getHeight());
//    }
    public boolean isReadyForRemoval() {
        return hasLaunched && stationaryTime >= 1f;
    }

//    @Override
//    public void draw(Batch batch, float parentAlpha) {
//        super.draw(batch, parentAlpha);
//    }


    public Vector2 getLaunchVelocity() {
        Vector2 launchVector = slingshotPosition.cpy().sub(position);
        float launchPower = launchVector.len() * 8f;
        return launchVector.nor().scl(launchPower);
    }

    public void update(float delta) {
        if (hasLaunched) {
            activityTime += delta;
            Vector2 velocity = body.getLinearVelocity();
            if (velocity.len() < 0.1f) {
                stationaryTime += delta;
                if (stationaryTime >= 1f) {
                    level.bodiesToDestroy.add(body);
                    level.birds.removeValue(this, true);
                }
            } else {
                stationaryTime = 0;
            }
            if (activityTime > 8f) {
                level.bodiesToDestroy.add(body);
                level.birds.removeValue(this, true);
            }
        }
    }


    private void launchBird() {
        Vector2 launchVector = slingshotPosition.cpy().sub(position);
        float launchPower = launchVector.len() * 8f;
        velocity = launchVector.nor().scl(launchPower);
        isLaunched = true;
    }

    public void render(float delta, SpriteBatch batch) {
        // Convert texture dimensions from pixels to meters
        float textureWidthMeters = textureWidth / ppm;
        float textureHeightMeters = textureHeight / ppm;

        level.game.batch.begin();
        Vector2 position = body.getPosition();
        float angle = body.getAngle() * MathUtils.radiansToDegrees;

        level.game.batch.draw(
            texture,
            position.x  - (textureWidth / ppm) / 2, // Adjust position for scaled texture width
            position.y  - (textureHeight / ppm) / 2, // Adjust position for scaled texture height
            (textureWidth ) / 2, (textureHeight ) / 2, // Origin (scaled)
            textureWidth / ppm, textureHeight / ppm, // Size (scaled)
            1, 1, // Scale .. constant
            0, // Rotation angle
            0, 0, // Texture region origin
            texture.getWidth(), texture.getHeight(), // Texture region dimensions
            false, false // Flip flags
        );
        level.game.batch.end();

        // Draw trajectory if needed
        if (trajectoryVisible) {
            level.shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            level.shapeRenderer.setColor(Color.RED);
            for (Vector2 point : trajectoryPoints) {
                level.shapeRenderer.circle(point.x * ppm, point.y * ppm, 5);
            }
            level.shapeRenderer.end();
        }

        Vector2 velocity = body.getLinearVelocity();

        if (hasLaunched) {
            activityTime += delta;
            // Only count stationary time if the bird has been launched
            if (velocity.len() < 0.1f) { // Bird is nearly stationary
                stationaryTime += delta;
                if (stationaryTime >= 1f) { // Destroy after 1 second of being stationary
                    level.world.destroyBody(body);
                    level.birds.removeValue(this, true);
                }
            } else {
                stationaryTime = 0; // Reset timer if the bird moves
            }

            if (activityTime > 8f) { // Destroy after 1 second of being stationary
                level.world.destroyBody(body);
                level.birds.removeValue(this, true);
            }
        }
    }

    public boolean startDrag(Vector2 cursorPosition) {
        Vector2 launchPosition = new Vector2(535 / GamePlayScreen.ppm, 375 / GamePlayScreen.ppm);
        Vector2 bodyPosition = body.getPosition();

        if (bodyPosition.epsilonEquals(launchPosition, 0.5f) && isCursorTouchingBody(cursorPosition)) {
            isDragging = true;
            body.setType(BodyDef.BodyType.StaticBody);
            return true; // Indicate that dragging has started
        }
        return false; // Dragging did not start
    }


    public void drag(Vector2 cursorPosition) {
        if (isDragging) {
            body.setTransform(cursorPosition.x, cursorPosition.y, body.getAngle());
            calculateTrajectory(cursorPosition.x, cursorPosition.y);
        }
    }

    public void release() {
        if (isDragging) {
            isDragging = false;
            hasLaunched = true;
            body.setType(BodyDef.BodyType.DynamicBody);
            trajectoryVisible = false; // Hide trajectory when released
            body.setLinearVelocity(calculateInitialVelocity());
        }
    }

    private Vector2 calculateInitialVelocity() {
        Vector2 currentPos = body.getPosition().scl(ppm); // Convert to pixels
        Vector2 dragVector = initialTouchPos.cpy().sub(currentPos); // Drag vector
        float distance = dragVector.len(); // Distance from catapult center

        float maxSpeed = 20f; // Scale this for maximum speed
        float speed = Math.min(distance / 20f, maxSpeed); // Adjust scaling factor for desired feel
        Vector2 tempPosition = body.getPosition();

        return dragVector.nor().scl(speed).scl(1); // Scale and reverse direction
    }

    private void calculateTrajectory(float endX, float endY) {
        trajectoryPoints.clear();
        Vector2 startPos = body.getPosition();
        Vector2 initialVelocity = calculateInitialVelocity();

        float timeStep = 0.1f; // Interval for trajectory calculation
        for (int i = 0; i < 30; i++) { // Adjust the number of points for trajectory
            float time = i * timeStep;
            float gravity = level.world.getGravity().y; // Box2D gravity in meters/sec²

            Vector2 position = new Vector2(
                startPos.x + initialVelocity.x * time,
                startPos.y + initialVelocity.y * time + 0.5f * gravity * time * time
            );
            trajectoryPoints.add(position);
        }
    }

    public boolean isCursorTouchingBody(Vector2 cursorPos) {
        for (var fixture : body.getFixtureList()) {
            if (fixture.testPoint(cursorPos.x, cursorPos.y)) {
                return true;
            }
        }
        return false;
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
//    public abstract void activateSpecialAbility();
}
