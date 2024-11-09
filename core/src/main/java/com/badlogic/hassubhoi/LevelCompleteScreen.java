package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
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
    private Texture buttonUpTexture;
    private Texture buttonDownTexture;

    private BitmapFont largeBoldFont;
    private BitmapFont mediumFont;
    private BitmapFont buttonFont;

    private static final float VIRTUAL_WIDTH = 800; // base width
    private static final float VIRTUAL_HEIGHT = 480; // base height

    private OrthographicCamera camera;
    private Viewport viewport;

    private TextButton nextLevelButton;
    private TextButton mainMenuButton;
    private Table table;
    private Sprite backgroundSprite;

    private Texture exitTexture;
    private Texture settinsTexture;
    private Texture starTexture;

    private ImageButton exitButton; // this is imagebutton as this is derived from png
    private ImageButton settingsButton;
//    private ImageButton starToggleButton1;
//    private ImageButton starToggleButton2;
//    private ImageButton starToggleButton3;

    private ImageButton starToggleButton1;
    private ImageButton starToggleButton2;
    private ImageButton starToggleButton3;


    private Texture starButton1Up;
    private Texture starButton1Down;

    public LevelCompleteScreen(UIManager uiManager, int currentLevel) {
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
        camera.position.set(VIRTUAL_WIDTH / 2, VIRTUAL_HEIGHT / 2, 0);
        camera.update();
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // background and button textures
        background = new Texture("background.png");
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        buttonUpTexture = new Texture("buttonPro/platter.png");
        buttonDownTexture = new Texture("buttonPro/platterDark.png");

        // angrybird custom font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // bold Font for "Congratulations"
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

        generator.dispose();

        // Labels
        Label.LabelStyle largeBoldStyle = new Label.LabelStyle();
        largeBoldStyle.font = largeBoldFont;
        Label.LabelStyle mediumStyle = new Label.LabelStyle();
        mediumStyle.font = mediumFont;
        Label congratulationsLabel = new Label("CONGRATULATIONS", largeBoldStyle);
        Label clearedLevelLabel = new Label("You cleared the level!", mediumStyle);

        //using button style, (learn- how to modify skin)
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));

        //Buttons
        nextLevelButton = new TextButton("Next Level", textButtonStyle);
        mainMenuButton = new TextButton("Main Menu", textButtonStyle);
        //some more buttons
        exitTexture = new Texture(Gdx.files.internal("buttonPro/exit.png"));
        settinsTexture = new Texture(Gdx.files.internal("buttonPro/settings.png"));
//        starTexture = new Texture(Gdx.files.internal("buttonPro/star.png"));
        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)));
        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settinsTexture)));
//        starToggleButton1 = new ImageButton(new TextureRegionDrawable(new TextureRegion(starTexture)));
//        starToggleButton2 = new ImageButton(new TextureRegionDrawable(new TextureRegion(starTexture)));
//        starToggleButton3 = new ImageButton(new TextureRegionDrawable(new TextureRegion(starTexture)));

        starButton1Up = new Texture(Gdx.files.internal("buttonPro/whiteStar.png"));
        starButton1Down = new Texture(Gdx.files.internal("buttonPro/starBright.png"));

        TextureRegionDrawable starButton1UpDrawable = new TextureRegionDrawable(starButton1Up);
        TextureRegionDrawable starButton1DownDrawable = new TextureRegionDrawable(starButton1Down);

        ImageButton.ImageButtonStyle toggleStyle = new ImageButton.ImageButtonStyle();
        toggleStyle.up = starButton1UpDrawable;
        toggleStyle.down = starButton1DownDrawable;
        starToggleButton1 = new ImageButton(toggleStyle);
        starToggleButton2 = new ImageButton(toggleStyle);
        starToggleButton3 = new ImageButton(toggleStyle);

        starToggleButton1.addAction(Actions.forever(Actions.sequence(
            Actions.run(() -> starToggleButton1.getStyle().imageUp = starButton1DownDrawable),  //bright star
            Actions.delay(0.8f),
            Actions.run(() -> starToggleButton1.getStyle().imageUp = starButton1UpDrawable),   //normal star
            Actions.delay(0.8f)
        )));

        starToggleButton2.addAction(Actions.forever(Actions.sequence(
            Actions.run(() -> starToggleButton2.getStyle().imageUp = starButton1DownDrawable),
            Actions.delay(0.2f),
            Actions.run(() -> starToggleButton2.getStyle().imageUp = starButton1UpDrawable),
            Actions.delay(0.2f)
        )));

        starToggleButton3.addAction(Actions.forever(Actions.sequence(
            Actions.run(() -> starToggleButton3.getStyle().imageUp = starButton1DownDrawable),
            Actions.delay(0.8f),
            Actions.run(() -> starToggleButton3.getStyle().imageUp = starButton1UpDrawable),
            Actions.delay(0.8f)
        )));


        //table for layout
        table = new Table();
        table.setFillParent(true);
        table.center();
        table.top();

        table.add(congratulationsLabel).padTop(60);
        table.row();
        table.add(clearedLevelLabel).padBottom(20).padTop(20);
        table.row();
//        making a table of stars only for better layout

        Table starTable = new Table();
        starTable.add(starToggleButton1).size(50, 50).pad(5);
        starTable.add(starToggleButton2).size(50, 50).pad(5);
        starTable.add(starToggleButton3).size(50, 50).pad(5);

        table.add(starTable).colspan(3).center().padBottom(20);

//        table.row();
        table.row();
        table.add(nextLevelButton).size(200, 50).padBottom(20);
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

        // listeners
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

        //update camera
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        backgroundSprite.draw(batch);
        batch.end();

        //draw stage
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
