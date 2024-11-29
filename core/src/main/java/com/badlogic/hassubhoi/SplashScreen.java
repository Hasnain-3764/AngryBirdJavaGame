package com.badlogic.hassubhoi;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.Gdx;

public class SplashScreen implements Screen {
    private AngryBirdGames game; // Reference to main game class
    private SpriteBatch batch;
    private Sprite sprite;
//    private Texture image;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TextureAtlas textureAtlas;
    private TextureAtlas textureAtlas2;
    private Sprite loadingSprite;
    private TextureRegion textureRegion;
    private TextureRegion textureRegion2;
    private TextureRegion loadingSpriteRegionNew; // For new animation

    private static final float VIRTUAL_WIDTH = 800; // Base width
    private static final float VIRTUAL_HEIGHT = 480; // Base height

    private long startTime;

    private Stage stage;
    private Skin skin;
    private int currentFrame = 0;
    private int MAX_FRAMES = 5;
    private int MAX_FRAMES2 = 117;
    private float frameDurationOld = 0.95f; // Duration of each frame for old animation
    private float frameDurationNew = 0f;
    private float timeSinceLastFrameOld = 1f;
    private int currentFrameOld = 0; // Current frame for old animation
    private int currentFrameNew = 0;
    private float timeSinceLastFrameNew = 0f; // Time accumulator for new animation

    public SplashScreen(AngryBirdGames game) {
        this.game = game;
        batch = new SpriteBatch();
//        image = new Texture("splashScreen.png");
//        sprite = new Sprite(image);

        // set up the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        viewport.apply();

        startTime = TimeUtils.millis();

        // scale the sprite to be a percentage of the screen
        float screenWidth = VIRTUAL_WIDTH;
        float screenHeight = VIRTUAL_HEIGHT;
//        float aspectRatio = image.getWidth() / (float) image.getHeight();

        // adjust sprite size based on viewport size
        float factor = 0.95f; // percent of screen covered
//        sprite.setSize(screenWidth * factor, (screenWidth * factor) / aspectRatio);
//        sprite.setPosition(
//            (VIRTUAL_WIDTH - sprite.getWidth()) / 2,
//            (VIRTUAL_HEIGHT - sprite.getHeight()) / 2
//        );
        textureAtlas = new TextureAtlas(Gdx.files.internal("loading/NewSpriteSheets/files.atlas"));
        textureAtlas2 = new TextureAtlas(Gdx.files.internal("movie/spritesheets/file.atlas"));

        textureRegion = textureAtlas.findRegion("000");
        textureRegion2 = textureAtlas2.findRegion("000");
        loadingSprite = new Sprite(textureRegion);
        loadingSprite.setPosition(
            viewport.getWorldWidth() / 2f - loadingSprite.getWidth() / 2f,
            viewport.getWorldHeight() / 10f
        );

    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera), batch);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);  // clear background
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//        batch.draw(image, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // accumulate time and switch frame if enough time has passed
        timeSinceLastFrameOld += delta;
        if (timeSinceLastFrameOld >= frameDurationOld) {
            currentFrame++;
            if (currentFrame > MAX_FRAMES) {
                currentFrame = 0;
            }
            timeSinceLastFrameOld = 0f;
        }

        timeSinceLastFrameNew += delta;
        if (timeSinceLastFrameNew >= frameDurationNew) {
            currentFrameNew++;
            if (currentFrameNew >= MAX_FRAMES2) {
                currentFrameNew = 0;
            }
            loadingSpriteRegionNew = textureAtlas2.findRegion(String.format("%03d", currentFrameNew)); // +1 for file1.png
            timeSinceLastFrameNew = 0f;

        }

        // sprite animation logic
        // since rendering is done too fast, using 0.1f of rendering speed.
        TextureRegion currentFrameRegion = textureAtlas.findRegion(String.format("%03d", currentFrame));
        if (currentFrameRegion != null) {
            batch.draw(currentFrameRegion, loadingSprite.getX(), loadingSprite.getY(), loadingSprite.getWidth(), loadingSprite.getHeight());
        }
        if (loadingSpriteRegionNew != null) {
            batch.draw(loadingSpriteRegionNew, viewport.getWorldWidth() / 2f - loadingSpriteRegionNew.getRegionWidth() / 2f,
                viewport.getWorldHeight() / 1.5f - loadingSpriteRegionNew.getRegionHeight() / 1.5f);
        }



        batch.end();

        // handle stage updates
        stage.act(delta);
        stage.draw();

        // automatically go to main menu aftre 5 sec
        if (Gdx.input.justTouched() || TimeUtils.timeSinceMillis(startTime) > 5000) {
            game.getUIManager().showMainMenu();  // Transition to main menu
        }
    }


    @Override // using aspect ratio to maintain different window sizes
    public void resize(int width, int height) {
        viewport.update(width, height, true);


        float loadingBarWidth = viewport.getWorldWidth() * 0.8f; // covering 80% of screen width
        float aspectRatio = loadingSprite.getWidth() / loadingSprite.getHeight();
        float loadingBarHeight = loadingBarWidth / (aspectRatio*1.5f);

        float bottomMargin = viewport.getWorldHeight() / 12f; // 10% margin from below
        loadingSprite.setSize(loadingBarWidth, loadingBarHeight);
        loadingSprite.setPosition(
            (viewport.getWorldWidth() - loadingBarWidth) / 2,
            bottomMargin
        );
    }


    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
//        image.dispose();
        stage.dispose();
        skin.dispose();

    }
}
