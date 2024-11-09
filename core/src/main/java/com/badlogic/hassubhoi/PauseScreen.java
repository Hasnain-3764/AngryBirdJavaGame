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
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.w3c.dom.Text;

public class PauseScreen extends ScreenAdapter {
    private Stage stage;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Viewport viewport;

    private UIManager uiManager;
    private static final float VIRTUAL_WIDTH = 800; // base width
    private static final float VIRTUAL_HEIGHT = 480; // base height

    private Texture background;
    private Sprite backgroundSprite;
    private Texture buttonUpTexture;
    private Texture buttonDownTexture;

    private BitmapFont largeBoldFont;
    private BitmapFont mediumFont;
    private BitmapFont buttonFont;

    private TextButton resumeButton;
    private TextButton mainMenuButton;

    private Texture exitTexture;
    private Texture settinsTexture;
    private ImageButton exitButton;
    private ImageButton settingsButton;

    private int level;
    public PauseScreen(UIManager uiManager, int level){
        this.uiManager = uiManager;
        this.level = level;
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

        background = new Texture("background.png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        backgroundSprite.setPosition(0,0);

        buttonUpTexture = new Texture("buttonPro/platter.png");
        buttonDownTexture = new Texture("buttonPro/platterDark.png");

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 64;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        largeBoldFont = generator.generateFont(parameter);

        // medium Font
        parameter.size = 32;
        parameter.color = Color.BLACK;
        parameter.borderColor = Color.WHITE;
        mediumFont = generator.generateFont(parameter);

        // fornt for the buttosn
        parameter.size = 36;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.WHITE;
        buttonFont = generator.generateFont(parameter);

        Label.LabelStyle largeBoldStyle = new Label.LabelStyle();
        largeBoldStyle.font = largeBoldFont;
        Label.LabelStyle mediumStyle = new Label.LabelStyle();
        mediumStyle.font = mediumFont;

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));

        Label pauseLabel = new Label("Game Paused", largeBoldStyle);
        Label messageLabel = new Label("Your Game is paused, tap 'Resume' button to play", mediumStyle);

        resumeButton = new TextButton("Resume", textButtonStyle);
        mainMenuButton = new TextButton("Main Menu", textButtonStyle);

        exitTexture = new Texture(Gdx.files.internal("buttonPro/exit.png"));
        settinsTexture = new Texture(Gdx.files.internal("buttonPro/settings.png"));
        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)));
        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settinsTexture)));

        // central table
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.top();

        table.add(pauseLabel).padTop(60);
        table.row();
        table.add(messageLabel).padBottom(20).padTop(20);
        table.row();
        table.add(resumeButton).size(200,50).padBottom(20);
        table.row();
        table.add(mainMenuButton).size(200,50);

        // left corner table
        Table table2 = new Table();
        table2.setFillParent(true);
        table2.top().left();
        float buttonSize = viewport.getWorldHeight() * 0.1f;
        table2.add(exitButton).size(buttonSize).padTop(0).padLeft(5).padRight(5).padBottom(5);
        table2.add(settingsButton).size(buttonSize).padTop(0).padRight(10);

        stage.addActor(table);
        stage.addActor(table2);

        // listerners
        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                uiManager.showGamePlayScreen(level);
            }
        });

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showMainMenu();
            }
        });

        exitButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                Gdx.app.exit();
            }
        });
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y){
                uiManager.showSettingsScreen();
            }
        });

    }

    @Override
    public void render(float delta){
        ScreenUtils.clear(1,1,1,0);
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

