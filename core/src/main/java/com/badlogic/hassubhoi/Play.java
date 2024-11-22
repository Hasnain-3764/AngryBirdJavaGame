package com.badlogic.hassubhoi;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Play implements Screen {
    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    private UIManager uiManager;

    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

    private SpriteBatch batch;
    private Body groundBody;
    private Body slingshotBody;
    private Body ballBody;

    private Texture slingshotTexture;
    private Texture ballTexture;

    private boolean isDragging = false;
    private Vector2 dragStart = new Vector2();
    private Vector2 dragEnd = new Vector2();
    private int bounceCount = 0;

    private boolean ballRemoved = false;

    public Play(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / 25f, Gdx.graphics.getHeight() / 25f);

        slingshotTexture = new Texture(Gdx.files.internal("slingshot.png"));
        ballTexture = new Texture(Gdx.files.internal("buttonPro/platter.png"));

        createGround();
        createSlingshot();
        createBall();

        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (ballRemoved) return false; // Do nothing if ball is removed

                Vector2 worldCoordinates = screenToWorld(screenX, screenY);
                if (ballBody.getFixtureList().first().testPoint(worldCoordinates)) {
                    isDragging = true;
                    dragStart.set(worldCoordinates);
                }
                return true;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isDragging) {
                    dragEnd.set(screenToWorld(screenX, screenY));
                }
                return true;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isDragging) {
                    isDragging = false;
                    Vector2 force = dragStart.sub(dragEnd).scl(2.5f); // Decreased scaling factor for realistic speed
                    ballBody.applyLinearImpulse(force, ballBody.getWorldCenter(), true);
                }
                return true;
            }
        });
    }

    private void createGround() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, -4);

        ChainShape groundShape = new ChainShape();
        groundShape.createChain(new Vector2[]{new Vector2(-50, -4), new Vector2(50, -4)});

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = groundShape;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0;

        groundBody = world.createBody(bodyDef);
        groundBody.createFixture(fixtureDef);

        groundShape.dispose();
    }

    private void createSlingshot() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(-6, -3);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 1);

        slingshotBody = world.createBody(bodyDef);
        slingshotBody.createFixture(shape, 0);

        shape.dispose();
    }

    private void createBall() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(-6, -2);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;
        fixtureDef.restitution = 0.7f; // Bounciness

        ballBody = world.createBody(bodyDef);
        ballBody.createFixture(fixtureDef);

        shape.dispose();
    }

    private Vector2 screenToWorld(int screenX, int screenY) {
        Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
        return new Vector2(worldCoordinates.x, worldCoordinates.y);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0, 0, 0, 1);

        if (!ballRemoved) {
            world.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);

            // Bounce counter logic
            if (ballBody.getLinearVelocity().y == 0 && ballBody.getPosition().y <= -4) {
                bounceCount++;
            }

            if (bounceCount >= 3 && !ballRemoved) {
                removeBall();
            }
        }

        camera.update();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        batch.draw(slingshotTexture, -6.5f, -4, 1, 2);

        if (!ballRemoved) {
            batch.draw(ballTexture, ballBody.getPosition().x - 0.5f, ballBody.getPosition().y - 0.5f, 1, 1);
        }

        batch.end();

        debugRenderer.render(world, camera.combined);
    }

    private void removeBall() {
        if (ballBody != null) {
            world.destroyBody(ballBody);
            ballBody = null;
            ballRemoved = true;
        }
    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = width / 25f;
        camera.viewportHeight = height / 25f;
        camera.update();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        batch.dispose();
        slingshotTexture.dispose();
        ballTexture.dispose();
        debugRenderer.dispose();
        world.dispose();
    }

}
