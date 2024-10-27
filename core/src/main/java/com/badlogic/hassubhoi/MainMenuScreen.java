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

        //camera and viewport setup
        camera = new OrthographicCamera();
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        viewport.apply();
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // bkg texture -> bkg sprite
        backgroundTexture = new Texture(Gdx.files.internal("background.png"));
        backgroundSprite = new Sprite(backgroundTexture);
        sprite = new Sprite(backgroundTexture);
        sprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        buttonUpTexture = new Texture("buttonPro/platter.png");
        buttonDownTexture = new Texture("buttonPro/platterDark.png"); // same (implement)

        //custom fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 36;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.WHITE;
        buttonFont = generator.generateFont(parameter);
        generator.dispose();

    // custom button
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));
        playButton = new TextButton("Play", textButtonStyle);
        settingsButton = new TextButton("Settings", textButtonStyle);
        exitButton = new TextButton("Exit", textButtonStyle);

        // putting image platter as a button? why? can we add png(Nope, texture is not ui element)
        Texture menuTexture = new Texture(Gdx.files.internal("buttonPro/mainMenu.png"));
        Image menuImage = new Image(menuTexture);
        // layout by table
        table = new Table();
        table.setFillParent(true);
        table.center();
        table.add(menuImage).size(menuTexture.getWidth()/1.5f, menuTexture.getHeight()/1.5f).pad(10);
        table.row();
        table.add(playButton).size(200, 50).pad(10);
        table.row();
        table.add(settingsButton).size(200, 50).pad(10);
        table.row();
        table.add(exitButton).size(200, 50).pad(10);

        stage.addActor(table);

        // button listeners
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showLevelSelectScreen();  // go to Level Select
            }
        });

        settingsButton.addListener(new ClickListener() { // Settings Button Listener
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showSettingsScreen(); //go to setting
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
        ScreenUtils.clear(0, 0, 0, 1);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        sprite.draw(batch);
        batch.end();

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
