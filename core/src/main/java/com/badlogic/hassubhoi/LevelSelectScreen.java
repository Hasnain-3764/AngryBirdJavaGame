//package com.badlogic.hassubhoi;
//
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.ScreenAdapter;
//import com.badlogic.gdx.graphics.Color;
//import com.badlogic.gdx.graphics.OrthographicCamera;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.Sprite;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.Table;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
//import com.badlogic.gdx.utils.ScreenUtils;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.utils.viewport.Viewport;
//
//public class LevelSelectScreen extends ScreenAdapter {
//    private Sprite backgroundSprite;
//    private Stage stage;
//    private Skin skin;
//    private UIManager uiManager;
//    private SpriteBatch batch;
//    private Texture background;
//    private Texture noticeBoard;
//    private OrthographicCamera camera;
//    private static final float VIRTUAL_WIDTH = 800; // Base width
//    private static final float VIRTUAL_HEIGHT = 480; // Base height
//    private Viewport viewport;
//
//    private BitmapFont buttonFont;
//
//    private TextButton Level1Button;
//    private TextButton Level2Button;
//    private TextButton Level3Button;
//    private TextButton Level4Button;
//    private TextButton mainMenuButton;
//
//    private Texture buttonUpTexture;
//    private Texture buttonDownTexture;
//
//    private ImageButton exitButton; // this is imagebutton as this is derived from png
//    private ImageButton settingsButton;
//    private Texture exitTexture;
//    private Texture settinsTexture;
//    private Table table1;
//    private Table table2;
//
//    public LevelSelectScreen(UIManager uiManager) {
//        this.uiManager = uiManager;
//        System.out.println("Level Select Screen Constructor Called");
//
//
//    }
//
//    @Override
//    public void show() {
//
//        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
//        batch = new SpriteBatch();
//
//        stage = new Stage(viewport, batch);
//        Gdx.input.setInputProcessor(stage);
//
//        // Load background and notice board textures
//        background = new Texture(Gdx.files.internal("background.png"));
//        backgroundSprite = new Sprite(background);
//        backgroundSprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
//        backgroundSprite.setPosition(0, 0);
//        noticeBoard = new Texture(Gdx.files.internal("buttonPro/selectLevel.png"));
//
//
//        buttonUpTexture = new Texture("buttonPro/platter.png"); // Replace with your texture file
//        buttonDownTexture = new Texture("buttonPro/platterDark.png"); // Optional: pressed button texture
//
//        // Generate custom fonts
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
//
//        // Button Font with black text color
//        parameter.size = 24;
//        parameter.color = Color.BLACK;
//        parameter.borderWidth = 1;
//        parameter.borderColor = Color.WHITE;
//        buttonFont = generator.generateFont(parameter);
//        generator.dispose();
//
//        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
//        textButtonStyle.font = buttonFont;
//        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
//        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));
//
//        // Create table1 for layout
//        table1 = new Table();
//        table1.setFillParent(true);
//        table1.center();
//
//        Level1Button = new TextButton("Level 1",textButtonStyle);
//        Level2Button = new TextButton("Level 2",textButtonStyle);
//        Level3Button = new TextButton("Level 3",textButtonStyle);
//        Level4Button = new TextButton("Level 4",textButtonStyle);
//        mainMenuButton = new TextButton("Main Menu", textButtonStyle);
//        // some more buttons(image)
//        exitTexture = new Texture(Gdx.files.internal("buttonPro/exit.png"));
//        settinsTexture = new Texture(Gdx.files.internal("buttonPro/settings.png"));
//        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)));
//        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settinsTexture)));
//
//        // Layout with Table
//        table1 = new Table();
//        table1.setFillParent(true);
//        table1.center();
//        table1.padTop(60);
//        // Add Labels and Buttons to the Table
//        table1.add(Level1Button).size(200,45).padTop(12);
//        table1.row();
//        table1.add(Level2Button).size(200,45).padTop(8);
//        table1.row();
//        table1.add(Level3Button).size(200,45).padTop(8);
//        table1.row();
//        table1.add(Level4Button).size(200,45).padTop(8);
//        table1.row();
//
//        table1.add(mainMenuButton).size(200, 50).padTop(25);
//
//        table2 = new Table();
//        table2.setFillParent(true);
//        table2.top().left();
//        float buttonSize = viewport.getWorldHeight() * 0.1f; // 10% of viewport
//        table2.add(exitButton).size(buttonSize).padTop(0).padLeft(5).padRight(5).padBottom(5);
//        table2.add(settingsButton).size(buttonSize).padTop(0).padRight(10);
//
//        stage.addActor(table1);
//        stage.addActor(table2);
//
//        exitButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.exit();
//            }
//        });
//        settingsButton.addListener(new ClickListener(){
//            @Override
//            public void clicked(InputEvent event, float x , float y){
//                uiManager.showSettingsScreen();
//            }
//        });
//        Level1Button.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y){
//                System.out.println("Level 1 selected");
//                uiManager.showGamePlayScreen(1);
//            }
//        });
//        Level2Button.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y){
//                System.out.println("Level 2 selected");
//                uiManager.showGamePlayScreen(2);
//            }
//        });
//        Level3Button.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y){
//                System.out.println("Level 3 selected");
//                uiManager.showGamePlayScreen(3);
//            }
//        });
//        Level4Button.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y){
//                System.out.println("Level 4 selected");
//                uiManager.showGamePlayScreen(4);
//            }
//        });
//        mainMenuButton.addListener(new ClickListener(){
//            public void clicked(InputEvent event, float x, float y){
//                System.out.println("Showing main menu");
//                uiManager.showMainMenu();
//            }
//        });
//
//    }
//
//    @Override // here buttons are drawn using stage, while backgorund and noticeboad used batch.
//    public void render(float delta) {
//        ScreenUtils.clear(0, 0f, 0f, 1);
//
//        // craw background and notice board
//        batch.setProjectionMatrix(stage.getCamera().combined);
//        batch.begin();
//        // drawing the background so that it cover the entire viewport
//        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
//
//        // centring noticeboard
//        float noticeBoardWidth = viewport.getWorldWidth() * 0.5f;
//        float noticeBoardHeight = viewport.getWorldHeight() * 0.8f;
//        float noticeBoardX = (viewport.getWorldWidth() - noticeBoardWidth) / 2;
//        float noticeBoardY = (viewport.getWorldHeight() - noticeBoardHeight) / 2;
//        batch.draw(noticeBoard, noticeBoardX, noticeBoardY, noticeBoardWidth, noticeBoardHeight);
//        batch.end();
//
//        // update and draw the stage(buttons)
//        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
//        stage.draw();
//    }
//
//    @Override
//    public void resize(int width, int height) {
//        viewport.update(width, height, true);
//        Gdx.input.setInputProcessor(stage);
//    }
//
//    @Override
//    public void dispose() {
//        // Dispose of all assets to free up resources
//        stage.dispose();
//        skin.dispose();
//        background.dispose();
//        noticeBoard.dispose();
//        batch.dispose();
//    }
//}



package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LevelSelectScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Stage stage;
    private Texture background;
    private Texture noticeBoard;
    private BitmapFont buttonFont;
    private Viewport viewport;

    private TextButton level1Button;
    private TextButton level2Button;
    private TextButton level3Button;
    private TextButton level4Button;
    private TextButton mainMenuButton;
    private ImageButton exitButton;
    private ImageButton settingsButton;

    private Texture exitTexture;
    private Texture settingsTexture;

    private UIManager uiManager;

    public LevelSelectScreen(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    @Override
    public void show() {
        // Initialize viewport and batch
        viewport = new FitViewport(800, 480);
        batch = new SpriteBatch();
        stage = new Stage(viewport, batch);

        // Set input processor to stage
        Gdx.input.setInputProcessor(stage);

        // Load assets
        background = new Texture(Gdx.files.internal("background.png"));
        noticeBoard = new Texture(Gdx.files.internal("buttonPro/selectLevel.png"));
        exitTexture = new Texture(Gdx.files.internal("buttonPro/exit.png"));
        settingsTexture = new Texture(Gdx.files.internal("buttonPro/settings.png"));

        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 24;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.WHITE;
        buttonFont = generator.generateFont(parameter);
        generator.dispose();

        // Create buttons
        createButtons();

        // Add buttons to the stage
        stage.addActor(createMainTable());
        stage.addActor(createSideTable());
    }

    private void createButtons() {
        // Create button styles
        Texture buttonUpTexture = new Texture(Gdx.files.internal("buttonPro/platter.png"));
        Texture buttonDownTexture = new Texture(Gdx.files.internal("buttonPro/platterDark.png"));
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));

        // Create level buttons
        level1Button = new TextButton("Level 1", textButtonStyle);
        level2Button = new TextButton("Level 2", textButtonStyle);
        level3Button = new TextButton("Level 3", textButtonStyle);
        level4Button = new TextButton("Level 4", textButtonStyle);
        mainMenuButton = new TextButton("Main Menu", textButtonStyle);

        // Create image buttons
        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)));
        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settingsTexture)));

        // Add listeners
        level1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showGamePlayScreen(1);
            }
        });

        level2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showGamePlayScreen(2);
            }
        });

        level3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showGamePlayScreen(3);
            }
        });

        level4Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showGamePlayScreen(4);
            }
        });

        mainMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showMainMenu();
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showSettingsScreen();
            }
        });
    }

    private Table createMainTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.center();
        table.padTop(60);

        // Add level buttons
        table.add(level1Button).size(200, 45).padTop(12);
        table.row();
        table.add(level2Button).size(200, 45).padTop(8);
        table.row();
        table.add(level3Button).size(200, 45).padTop(8);
        table.row();
        table.add(level4Button).size(200, 45).padTop(8);
        table.row();
        table.add(mainMenuButton).size(200, 50).padTop(25);

        return table;
    }

    private Table createSideTable() {
        Table table = new Table();
        table.setFillParent(true);
        table.top().left();
        float buttonSize = viewport.getWorldHeight() * 0.1f;

        table.add(exitButton).size(buttonSize).padTop(0).padLeft(5).padRight(5).padBottom(5);
        table.add(settingsButton).size(buttonSize).padTop(0).padRight(10);

        return table;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0f, 0f, 1);

        // Draw background and notice board
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        float noticeBoardWidth = viewport.getWorldWidth() * 0.5f;
        float noticeBoardHeight = viewport.getWorldHeight() * 0.8f;
        float noticeBoardX = (viewport.getWorldWidth() - noticeBoardWidth) / 2;
        float noticeBoardY = (viewport.getWorldHeight() - noticeBoardHeight) / 2;
        batch.draw(noticeBoard, noticeBoardX, noticeBoardY, noticeBoardWidth, noticeBoardHeight);
        batch.end();

        // Draw stage
        stage.act(Math.min(delta, 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        stage.dispose();
        background.dispose();
        noticeBoard.dispose();
        batch.dispose();
    }
}
