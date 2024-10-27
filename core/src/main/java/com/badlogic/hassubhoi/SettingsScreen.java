
package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SettingsScreen extends ScreenAdapter {
    private UIManager uiManager;
    private Stage stage;
    private Skin skin;

    private ImageButton soundToggleButton;
    private ImageButton iButton;
    private ImageButton backButton;

    private Texture settingHeader;
    private Image settingHeaderImage;

    private BitmapFont labelStyleFont;

    private Label soundLabel;
    private Label iLabel;
    private Label mainMenuLabel;

    // Reference resolution for consistent font scaling
    private final int referenceWidth = 1920;
    private final int referenceHeight = 1080;

    private Texture background;
    private Sprite backgroundSprite;

    private OrthographicCamera camera;
    private Viewport viewport;

    private static final float VIRTUAL_WIDTH = 800; // Base width
    private static final float VIRTUAL_HEIGHT = 480; // Base height
    private SpriteBatch batch;

    private Texture board;
    private Image boardImage;

    private Texture soundOnTexture;
    private Texture soundOffTexture;

    public SettingsScreen(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        viewport = new ScreenViewport(camera);

        stage = new Stage(viewport);  // iewport for the stage as well
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        background = new Texture(Gdx.files.internal("background.png"));
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        backgroundSprite.setPosition(0, 0);

        settingHeader = new Texture(Gdx.files.internal("buttonPro/settingsMenu.png"));
        settingHeaderImage = new Image(settingHeader);

        soundOnTexture = new Texture(Gdx.files.internal("buttonPro/volumeOn.png"));
        soundOffTexture = new Texture(Gdx.files.internal("buttonPro/volumeOff.png"));

        board = new Texture(Gdx.files.internal("buttonPro/boardnew.png"));
        boardImage = new Image(board);

        TextureRegionDrawable soundOnDrawable = new TextureRegionDrawable(soundOnTexture);
        TextureRegionDrawable soundOffDrawable = new TextureRegionDrawable(soundOffTexture);

        // Creating a new button style for the toggle button
        ImageButton.ImageButtonStyle toggleStyle = new ImageButton.ImageButtonStyle();
        toggleStyle.up = soundOnDrawable;
        toggleStyle.checked = soundOffDrawable;

        soundToggleButton = new ImageButton(toggleStyle);

        // Setting the button to be a toggle button
        soundToggleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isChecked = soundToggleButton.isChecked();
                SoundManager.getInstance().setMusicEnabled(!isChecked);
                SoundManager.getInstance().setSoundsEnabled(!isChecked);
            }
        });

        Texture iTexture = new Texture(Gdx.files.internal("buttonPro/i.png"));
        iButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(iTexture)));
        iButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                // implement?
            }
        });

        // the back button is image button
        Texture backButtonTexture = new Texture(Gdx.files.internal("buttonPro/backward.png"));
        backButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(backButtonTexture)));

        backButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showMainMenu();
            }
        });

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 72;
        parameter.color = Color.WHITE;
        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        labelStyleFont = generator.generateFont(parameter);

        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = labelStyleFont;
        soundLabel = new Label("Settings", labelStyle);
        iLabel = new Label("Information", labelStyle);
        mainMenuLabel = new Label("Main Menu", labelStyle);

        updateButtonSizes(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(table);
    }



    private void updateButtonSizes(int width, int height) {
        float buttonSize = Math.min(width, height) * 0.15f;
        float headerWidth = width * 0.3f;
        float headerHeight = headerWidth * settingHeader.getHeight() / settingHeader.getWidth();
        float boardWidth = width * 0.25f;  // 25% of the screen width
        float boardHeight = boardWidth * boardImage.getHeight() / boardImage.getWidth();

        float fontScaleFactor = (float) height / referenceHeight;
        updateLabelFontSize(soundLabel, fontScaleFactor);
        updateLabelFontSize(iLabel, fontScaleFactor);
        updateLabelFontSize(mainMenuLabel, fontScaleFactor);

        Table table = new Table();
        table.setFillParent(true);
        table.left();
        table.add(soundLabel).padRight(20).padLeft(40).padBottom(20);
        table.add(soundToggleButton).size(buttonSize).padBottom(20).padLeft(0);
        table.row();
        table.add(iLabel).padRight(20).padLeft(40).padBottom(20);
        table.add(iButton).size(buttonSize).padBottom(20).padLeft(0);
        table.row();
        table.add(mainMenuLabel).padRight(20).padLeft(40).padBottom(20);
        table.add(backButton).size(buttonSize).padLeft(0);

        Table table2 = new Table();
        table2.setFillParent(true);
        table2.right();
        table2.add(boardImage).pad(10).size(boardWidth, boardHeight);

        Table table3 =  new Table();
        table3.setFillParent(true);
        table3.top();
        table3.add(settingHeaderImage).size(headerWidth, headerHeight).pad(10);

        stage.clear(); // clear previous elements
        stage.addActor(table);
        stage.addActor(table2);
        stage.addActor(table3);
    }

    private void updateLabelFontSize(Label label, float scaleFactor) {
        label.getStyle().font.getData().setScale(scaleFactor);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);  // true to center the camera
        backgroundSprite.setSize(width, height);
        updateButtonSizes(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        labelStyleFont.dispose();
    }
}
