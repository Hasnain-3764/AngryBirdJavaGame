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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameOverScreen extends ScreenAdapter {
    private Stage stage;
    private UIManager uiManager;
    private int currentLevel;
    private SpriteBatch batch;
    private Texture background;
    private BitmapFont largeBoldFont;
    private BitmapFont mediumFont;
    private BitmapFont buttonFont;

    private static final float VIRTUAL_WIDTH = 800; // Base width
    private static final float VIRTUAL_HEIGHT = 480; // Base height

    private OrthographicCamera camera;
    private Viewport viewport;

    private TextButton retryButton;
    private TextButton mainMenuButton;
    private Table table;
    private Sprite backgroundSprite;
    private Texture buttonUpTexture; // Texture for the button
    private Texture buttonDownTexture;

    public GameOverScreen(UIManager uiManager, int currentLevel) {
        this.uiManager = uiManager;
        this.currentLevel = currentLevel;
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

        background = new Texture("Level3.png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        backgroundSprite.setPosition(0, 0);

        buttonUpTexture = new Texture("buttonPro/63.png"); // Replace with your texture file
        buttonDownTexture = new Texture("buttonPro/63.png"); // Optional: pressed button texture

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf")); // Replace with your font file
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 64; // Adjust size as needed
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        largeBoldFont = generator.generateFont(parameter);

        parameter.size = 32; // Adjust size as needed
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.WHITE;
        mediumFont = generator.generateFont(parameter);

        parameter.size = 36;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.WHITE;
        buttonFont = generator.generateFont(parameter);

        generator.dispose();

        Label.LabelStyle largeBoldStyle = new Label.LabelStyle();
        largeBoldStyle.font = largeBoldFont;

        Label.LabelStyle mediumStyle = new Label.LabelStyle();
        mediumStyle.font = mediumFont;


        Label sorryLabel = new Label("SORRY", largeBoldStyle);
        Label failLevelLabel = new Label("You failed the level!", mediumStyle);

        // Create Button Style without NinePatch
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));

        // Create Buttons
        retryButton = new TextButton("Retry", textButtonStyle);
        mainMenuButton = new TextButton("Main Menu", textButtonStyle);

        // Button listeners
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showGamePlayScreen(currentLevel);
            }
        });

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showMainMenu();
            }
        });

        // Layout with Table
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        table.add(sorryLabel).padBottom(20);
        table.row();
        table.add(failLevelLabel).padBottom(40);
        table.row();
        table.add(retryButton).size(200, 50).padBottom(20);
        table.row();
        table.add(mainMenuButton).size(200, 50);

        stage.addActor(table);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        // Update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        // Draw background
        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        // Draw stage (buttons)
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
        background.dispose();
        buttonUpTexture.dispose();
        buttonDownTexture.dispose();
        largeBoldFont.dispose();
        mediumFont.dispose();
        buttonFont.dispose();
    }

}
