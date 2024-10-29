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
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    private Texture buttonUpTexture; // texture for the button
    private Texture buttonDownTexture;

    private ImageButton exitButton; // this is imagebutton as this is derived from png
    private ImageButton settingsButton;
    private Texture exitTexture;
    private Texture settinsTexture;


    public GameOverScreen(UIManager uiManager, int currentLevel) {
        this.uiManager = uiManager;
        this.currentLevel = currentLevel;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();
        // set up the camera and viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();

        // center the camera
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        background = new Texture("background.png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        backgroundSprite.setPosition(0, 0);

        buttonUpTexture = new Texture("buttonPro/platter.png");
        buttonDownTexture = new Texture("buttonPro/platterDark.png"); // implement

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf")); // implement
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 64;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        largeBoldFont = generator.generateFont(parameter);

        parameter.size = 32;
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

        // creating labels for sorry and fail message
        Label.LabelStyle largeBoldStyle = new Label.LabelStyle();
        largeBoldStyle.font = largeBoldFont;
        Label.LabelStyle mediumStyle = new Label.LabelStyle();
        mediumStyle.font = mediumFont;
        Label sorryLabel = new Label("SORRY", largeBoldStyle);
        Label failLevelLabel = new Label("You failed the level!", mediumStyle);

        // craeting button for retry and main menu.
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));


        //some more buttons
        exitTexture = new Texture(Gdx.files.internal("buttonPro/exit.png"));
        settinsTexture = new Texture(Gdx.files.internal("buttonPro/settings.png"));
        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)));
        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settinsTexture)));


        retryButton = new TextButton("Retry", textButtonStyle);
        mainMenuButton = new TextButton("Main Menu", textButtonStyle);

        exitButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x , float y){
                Gdx.app.exit();
            }
        });
        settingsButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                uiManager.showSettingsScreen();
            }
        });

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
        // centralised layout(implement)
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

        Table table2 = new Table();
        table2.setFillParent(true);
        table2.top().left();
        float buttonSize = viewport.getWorldHeight() * 0.1f; // 10% of viewport
        table2.add(exitButton).size(buttonSize).padTop(0).padLeft(5).padRight(5).padBottom(5);
        table2.add(settingsButton).size(buttonSize).padTop(0).padRight(10);


        stage.addActor(table);
        stage.addActor(table2);

    }

    @Override // update camera, draw batch, draw stage.
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        backgroundSprite.draw(batch);
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
        background.dispose();
        buttonUpTexture.dispose();
        buttonDownTexture.dispose();
        largeBoldFont.dispose();
        mediumFont.dispose();
        buttonFont.dispose();
    }

}
