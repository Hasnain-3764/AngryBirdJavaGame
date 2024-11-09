package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class PauseScreen extends ScreenAdapter {
    private Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    private UIManager uiManager;
    private static final float VIRTUAL_WIDTH = 800; // base width
    private static final float VIRTUAL_HEIGHT = 480; // base height

    public PauseScreen(UIManager uiManager){
        this.uiManager = uiManager;
    }

    @Override
    public void show(){
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH/2, VIRTUAL_HEIGHT/2, 0);
        camera.update();
        stage = new Stage(viewport,batch);
        Gdx.input.setInputProcessor(stage);

        // to be implemented
    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(0,0,0,1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        // something to be drawn
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}

