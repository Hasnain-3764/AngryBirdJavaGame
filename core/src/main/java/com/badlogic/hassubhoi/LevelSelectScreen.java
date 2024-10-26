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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class LevelSelectScreen extends ScreenAdapter {
    private Sprite backgroundSprite;
    private Stage stage;
    private Skin skin;
    private UIManager uiManager;
    private SpriteBatch batch;
    private Texture background;
    private Texture noticeBoard;
    private OrthographicCamera camera;
    private static final float VIRTUAL_WIDTH = 800; // Base width
    private static final float VIRTUAL_HEIGHT = 480; // Base height
    private Viewport viewport;

    private BitmapFont buttonFont;

    private TextButton Level1Button;
    private TextButton Level2Button;
    private TextButton Level3Button;
    private TextButton Level4Button;
    private TextButton exitButton;
    private Texture buttonUpTexture;
    private Texture buttonDownTexture;

    public LevelSelectScreen(UIManager uiManager) {
        this.uiManager = uiManager;
        System.out.println("Level Select Screen Constructor Called");


    }

    @Override
    public void show() {

        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        batch = new SpriteBatch();

        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        // Load background and notice board textures
        background = new Texture(Gdx.files.internal("Level3.png"));
        backgroundSprite = new Sprite(background);
        backgroundSprite.setSize(VIRTUAL_WIDTH, VIRTUAL_HEIGHT);
        backgroundSprite.setPosition(0, 0);
        noticeBoard = new Texture(Gdx.files.internal("buttonPro/selectLevel.png"));


        buttonUpTexture = new Texture("buttonPro/63.png"); // Replace with your texture file
        buttonDownTexture = new Texture("buttonPro/63.png"); // Optional: pressed button texture

        // Generate custom fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/angrybirds-regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        // Button Font with black text color
        parameter.size = 24;
        parameter.color = Color.BLACK;
        parameter.borderWidth = 1;
        parameter.borderColor = Color.WHITE;
        buttonFont = generator.generateFont(parameter);
        generator.dispose();

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = buttonFont;
        textButtonStyle.up = new TextureRegionDrawable(new TextureRegion(buttonUpTexture));
        textButtonStyle.down = new TextureRegionDrawable(new TextureRegion(buttonDownTexture));

        // Create table for layout
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        Level1Button = new TextButton("Level 1",textButtonStyle);
        Level2Button = new TextButton("Level 2",textButtonStyle);
        Level3Button = new TextButton("Level 3",textButtonStyle);
        Level4Button = new TextButton("Level 4",textButtonStyle);
        exitButton = new TextButton("Exit", textButtonStyle);

        // Layout with Table
        table = new Table();
        table.setFillParent(true);
        table.center();
        table.padTop(60);
        // Add Labels and Buttons to the Table
        table.add(Level1Button).size(200,45).padTop(12);
        table.row();
        table.add(Level2Button).size(200,45).padTop(8);
        table.row();
        table.add(Level3Button).size(200,45).padTop(8);
        table.row();
        table.add(Level4Button).size(200,45).padTop(8);
        table.row();

        table.add(exitButton).size(200, 50).padTop(25);
        stage.addActor(table);

        Level1Button.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Level 1 selected");
                uiManager.showGamePlayScreen(1);
            }
        });
        Level2Button.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Level 2 selected");
                uiManager.showGamePlayScreen(2);
            }
        });
        Level3Button.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Level 3 selected");
                uiManager.showGamePlayScreen(3);
            }
        });
        Level4Button.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Level 4 selected");
                uiManager.showGamePlayScreen(5);
            }
        });
        exitButton.addListener(new ClickListener(){
            public void clicked(InputEvent event, float x, float y){
                System.out.println("Exiting Gracefully");
                Gdx.app.exit();
            }
        });

    }

    @Override
    public void render(float delta) {
        // Clear the screen with a dark teal color
        ScreenUtils.clear(0, 0.2f, 0.2f, 1);

        // Draw background and notice board
        batch.setProjectionMatrix(stage.getCamera().combined);
        batch.begin();
        // Draw the background to cover the entire viewport
        batch.draw(background, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        // Draw notice board centered on the screen
        float noticeBoardWidth = viewport.getWorldWidth() * 0.5f;
        float noticeBoardHeight = viewport.getWorldHeight() * 0.8f;
        float noticeBoardX = (viewport.getWorldWidth() - noticeBoardWidth) / 2;
        float noticeBoardY = (viewport.getWorldHeight() - noticeBoardHeight) / 2;
        batch.draw(noticeBoard, noticeBoardX, noticeBoardY, noticeBoardWidth, noticeBoardHeight);
        batch.end();

        // Update and draw the stage (buttons)
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void dispose() {
        // Dispose of all assets to free up resources
        stage.dispose();
        skin.dispose();
        background.dispose();
        noticeBoard.dispose();
        batch.dispose();
    }
}
