package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends ScreenAdapter {
    private Stage stage;
    private Skin skin;
    private UIManager uiManager;
    private SpriteBatch batch;
    private Texture backgroundTexture;
    private Sprite sprite;

    private TextButton playButton;
    private TextButton settingsButton;
    private TextButton exitButton;

    private Table table;
    private static final float VIRTUAL_WIDTH = 800; // Base width
    private static final float VIRTUAL_HEIGHT = 480; // Base height

    private OrthographicCamera camera;
    private Viewport viewport;
    private Sprite backgroundSprite;
    private Texture buttonUpTexture;
    private Texture buttonDownTexture;
    private BitmapFont buttonFont;

    public MainMenuScreen(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        // Set up the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        // Center the camera
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Load the background texture and create a sprite
        backgroundTexture = new Texture(Gdx.files.internal("Level3.png"));
        backgroundSprite = new Sprite(backgroundTexture);
        sprite = new Sprite(backgroundTexture);
        sprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        buttonUpTexture = new Texture("buttonPro/63.png"); // Replace with your texture file
        buttonDownTexture = new Texture("buttonPro/63.png"); // Optional: pressed button texture

        // Generate custom fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 36;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.BLACK;
        buttonFont = generator.generateFont(parameter);
        generator.dispose();


        // Create Button Style without NinePatch
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));

        // Create Buttons
        playButton = new TextButton("Play", textButtonStyle);
        settingsButton = new TextButton("Settings", textButtonStyle);
        exitButton = new TextButton("Exit", textButtonStyle);

        // Position buttons using a table
        table = new Table();
        table.setFillParent(true); // Make the table fill the stage
        table.center();

        // Add buttons to the table without fixed sizes
        table.add(playButton).size(200, 50).pad(10);
        table.row();
        table.add(settingsButton).size(200, 50).pad(10);
        table.row();
        table.add(exitButton).size(200, 50).pad(10);

        // Add table to stage
        stage.addActor(table);

        // Button listeners
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showLevelSelectScreen();  // Go to Level Select
            }
        });

        settingsButton.addListener(new ClickListener() { // Settings Button Listener
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showSettingsScreen();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();  // Exit game
            }
        });
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        camera.position.set(viewport.getWorldWidth() / 2, viewport.getWorldHeight() / 2, 0);
        camera.update();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1, 1, 1, 1);  // Clear to a dark background color

        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Draw background
        batch.begin();
        sprite.draw(batch);
        batch.end();

        // Draw stage (buttons)
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
        backgroundTexture.dispose();
        skin.dispose();
    }
}
