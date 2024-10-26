package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class LevelCompleteScreen extends ScreenAdapter {
    private Stage stage;
    private UIManager uiManager;
    private int currentLevel;
    private SpriteBatch batch;
    private Texture background;
    private Texture buttonUpTexture; // Texture for the button
    private Texture buttonDownTexture; // Optional: Texture for button pressed state

    private BitmapFont largeBoldFont;
    private BitmapFont mediumFont;
    private BitmapFont buttonFont;

    private static final float VIRTUAL_WIDTH = 800; // Base width
    private static final float VIRTUAL_HEIGHT = 480; // Base height

    private OrthographicCamera camera;
    private Viewport viewport;


    private TextButton nextLevelButton;
    private TextButton mainMenuButton;
    private Table table;
    private Sprite backgroundSprite;

    public LevelCompleteScreen(UIManager uiManager, int currentLevel) {
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
        Gdx.input.setInputProcessor(stage);

        // Load background and button textures
        background = new Texture("Level3.png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        buttonUpTexture = new Texture("buttonPro/63.png"); // Replace with your texture file
        buttonDownTexture = new Texture("buttonPro/63.png"); // Optional: pressed button texture

        // Generate custom fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // Large Bold Font for "Congratulations"
        parameter.size = 64;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        largeBoldFont = generator.generateFont(parameter);

        // Medium Font for "You cleared the level"
        parameter.size = 32;
        parameter.color = Color.BLACK;
        parameter.borderColor = Color.WHITE;
        mediumFont = generator.generateFont(parameter);

        // Button Font with black text color
        parameter.size = 36;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.WHITE;
        buttonFont = generator.generateFont(parameter);

        generator.dispose();

        // Create Labels
        Label.LabelStyle largeBoldStyle = new Label.LabelStyle();
        largeBoldStyle.font = largeBoldFont;

        Label.LabelStyle mediumStyle = new Label.LabelStyle();
        mediumStyle.font = mediumFont;

        Label congratulationsLabel = new Label("CONGRATULATIONS", largeBoldStyle);
        Label clearedLevelLabel = new Label("You cleared the level!", mediumStyle);

        // Create Button Style without NinePatch
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));

        // Create Buttons
        nextLevelButton = new TextButton("Next Level", textButtonStyle);
        mainMenuButton = new TextButton("Main Menu", textButtonStyle);

        // Layout with Table
        table = new Table();
        table.setFillParent(true);
        table.center();

        // Add Labels and Buttons to the Table
        table.add(congratulationsLabel).padBottom(20);
        table.row();
        table.add(clearedLevelLabel).padBottom(40);
        table.row();
        table.add(nextLevelButton).size(200, 50).padBottom(20);
        table.row();
        table.add(mainMenuButton).size(200, 50);

        stage.addActor(table);

        // Button listeners
        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showGamePlayScreen(currentLevel + 1);
            }
        });

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showMainMenu();
            }
        });
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
