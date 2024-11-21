// debugging class. // learning concepts class
package com.badlogic.hassubhoi;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Play implements Screen {

    private OrthographicCamera camera;
    private World world;
    private Box2DDebugRenderer debugRenderer;

    private UIManager uiManager;

    public Play(UIManager uiManager){
        this.uiManager = uiManager;
    }

    @Override
    public void show(){

        world = new World(new Vector2(0, -9.81f), true);
        debugRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));


        // body definition
        BodyDef balldef = new BodyDef();
        balldef.type = BodyDef.BodyType.DynamicBody;
        balldef.position.set(0,1); // this is 1 meters.
//        balldef.position.set(0, 500/pixelsToMeters)

        // ball shape(circle)
        CircleShape shape = new CircleShape();
        shape.setRadius(0.25f);

        // fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f; // 2.5 kg in 1 square meter.
        fixtureDef.friction = 0.25f;
        fixtureDef.restitution = 0.75f;

        world.createBody(balldef).createFixture(fixtureDef); // or
//        Fixture fixture = ball.createFixture(fixtureDef);
        // or
//        world.createBody(balldef).createFixture(fixtureDef);

        shape.dispose();
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        ScreenUtils.clear(0,0,0,1);
        debugRenderer.render(world, camera.combined);

    }

    @Override
    public void resize(int width, int height){

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
//        batch.dispose();
//        image.dispose();
        debugRenderer.dispose();

    }
}
