package com.badlogic.hassubhoi;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class GamePlayScreen extends ScreenAdapter {
    private UIManager uiManager;
    private Stage stage;
    private Skin skin;
    private ImageButton soundButton;
    private FitViewport viewport;
    private SpriteBatch batch;

    private Array<Bird> birds;
    private Array<Pig> pigs;
    private Array<Structure> structures;

    //    private Texture background;
    private Texture platform;
    private Texture slingshotTexture;
    private ShapeRenderer shapeRenderer;

    private Queue<Bird> birdQueue;
    private Bird currentBird;
    private final float groundLevelY = 50f; // Adjust to match your ground level

    private int level;

    private int currentFrame = 1;
    private int MAX_FRAMES;
    private TextureAtlas textureAtlas;
    private TextureRegion textureRegion;

    private float frameDuration = 0.1f; // Time per frame in seconds (0.1 seconds per frame = 10 FPS)
    private float timeSinceLastFrame = 0f;


    private boolean isPaused = false;
    // Audio
//    private Music backgroundMusic;
    private Sound birdLaunchSound;
    private Sound pigHitSound;
    private Sound levelCompleteSound;
    private Sound gameOverSound;

    private boolean levelCompleted = false;
    private float levelCompleteTimer = 0f;
    private final float LEVEL_COMPLETE_DELAY = 2f; // 2 seconds delay\

    private ImageButton pauseButton;
    private ImageButton restartButton;
    private ImageButton exitButton;
    private ImageButton levelSelectButton;
    private ImageButton levelCompleteButton;
    private ImageButton settingsButton;
    private ImageButton levelFailButton;

    public GamePlayScreen(UIManager uiManager, int level) {
        System.out.println("Initializing GamePlayScreen for level: " + level);
        this.uiManager = uiManager;
        this.level = level;
        // Other initialization code
        System.out.println("GamePlayScreen setup complete.");

        this.viewport = new FitViewport(800, 480); // Adjust as needed
        this.batch = new SpriteBatch();

        this.stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);


//        String backgroundFile = uiManager.getSelectedBackground();
//        background = new Texture(Gdx.files.internal(backgroundFile));
//        background = new Texture("Level" + level + ".png");
//        platform = new Texture("platform.png");
//        textureAtlas = new TextureAtlas(Gdx.files.internal("level1/Spritesheets/loadSprite.atlas"));
//        textureRegion = textureAtlas.findRegion("002");
        slingshotTexture = new Texture("slingshot.png");

//        if (level == 1) {
//            textureAtlas = new TextureAtlas(Gdx.files.internal("level1/Spritesheets/loadSprite.atlas"));
//        } else if (level == 2) {
//            textureAtlas = new TextureAtlas(Gdx.files.internal("level2/Spritesheets/loadSprite.atlas"));
//        }
//        else if (level == 3){
//            textureAtlas = new TextureAtlas(Gdx.files.internal("level3/Spritesheets/loadSprite.atlas"));
//        }

        textureAtlas = new TextureAtlas(Gdx.files.internal("level1/Spritesheets/loadSprite.atlas"));
        MAX_FRAMES = textureAtlas.getRegions().size;


        // Initialize arrays
        birds = new Array<>();
        pigs = new Array<>();
        structures = new Array<>();

        shapeRenderer = new ShapeRenderer();

        // Load music and sounds
//        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background_music.mp3"));
        birdLaunchSound = Gdx.audio.newSound(Gdx.files.internal("bird_launch.wav"));
        pigHitSound = Gdx.audio.newSound(Gdx.files.internal("pig_hit.wav"));
        levelCompleteSound = Gdx.audio.newSound(Gdx.files.internal("level_complete.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("game_over.wav"));

//        backgroundMusic.setLooping(true);
//        backgroundMusic.play();

        birdQueue = new Queue<>();
        loadLevel(level);
        setNextBird();
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));

        // Load button textures
        Texture pauseTexture = new Texture(Gdx.files.internal("buttonPro/pauseButton.png"));
        Texture restartTexture = new Texture(Gdx.files.internal("buttonPro/restart.png"));
        Texture exitTexture = new Texture(Gdx.files.internal("buttonPro/exit.png"));
        Texture levelSelectTexture = new Texture(Gdx.files.internal("buttonPro/hamburger.png"));
        Texture levelCompleteTexture = new Texture(Gdx.files.internal("buttonPro/forward2.png"));
        Texture settinsTexture = new Texture(Gdx.files.internal("buttonPro/settings.png"));
        Texture levelFailTexture = new Texture(Gdx.files.internal("buttonPro/levelFail.png"));

        // Create ImageButtons with TextureRegionDrawables
        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
        restartButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(restartTexture)));
        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)));
        levelSelectButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(levelSelectTexture)));
        levelCompleteButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(levelCompleteTexture)));
        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settinsTexture)));
        levelFailButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(levelFailTexture)));

        // Add listeners
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
//                togglePause();
                boolean isChecked = pauseButton.isChecked();
                SoundManager.getInstance().setMusicEnabled(!isChecked);
                SoundManager.getInstance().setSoundsEnabled(!isChecked);
            }
        });

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showGamePlayScreen(level);
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        levelSelectButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showLevelSelectScreen();
//                uiManager.showLevelCompleteScreen(level);
            }
        });
        levelCompleteButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y){
                uiManager.showLevelCompleteScreen(level);
            }
        });
        settingsButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y){
                uiManager.showSettingsScreen();
            }
        });
        levelFailButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x , float y){
                uiManager.showGameOverScreen(level);
            }
        });


        // Create tables to hold the buttons
        Table leftButtonTable = new Table();
        Table rightButtonTable = new Table();

        leftButtonTable.top().right();
        rightButtonTable.top().left();

        leftButtonTable.setFillParent(true);
        rightButtonTable.setFillParent(true);

        float buttonSize = viewport.getWorldHeight() * 0.1f; // 10% of viewport height

        // Add three buttons to the left table
        leftButtonTable.add(levelCompleteButton).size(buttonSize).padTop(0).padLeft(5).padRight(5).padBottom(5);
        leftButtonTable.add(pauseButton).size(buttonSize).padTop(0).padLeft(5).padRight(5).padBottom(5);
        leftButtonTable.add(restartButton).size(buttonSize).padTop(0).padLeft(5).padRight(5).padBottom(5);
        leftButtonTable.add(levelSelectButton).size(buttonSize).padTop(0).padLeft(5).padRight(5).padBottom(5);

        // Add the fourth button to the right table
        rightButtonTable.add(exitButton).size(buttonSize).padTop(0).padRight(10);
        rightButtonTable.add(settingsButton).size(buttonSize).padTop(0).padRight(10);
        rightButtonTable.add(levelFailButton).size(buttonSize).padTop(0).padRight(10);

        // Add the tables to the stage
        stage.addActor(leftButtonTable);
        stage.addActor(rightButtonTable);
    }


    private void togglePause() {
        isPaused = !isPaused;
        if (isPaused) {
//            pauseButton.setText("Resume");
            // Stop sounds
//            SoundManager.getInstance().pauseAllSounds(); // to be implemented
            // pause animations or game logic
        } else {
//            pauseButton.setText("Pause");
            // Resume sounds
//            SoundManager.getInstance().resumeAllSounds(); // to be implememnted
            // resume animations or game logic
        }
    }



    private void setNextBird() {
        if (birdQueue.size > 0) {
            currentBird = birdQueue.removeFirst();
            birds.add(currentBird);
            // Set the bird's position on the slingshot
            currentBird.setPosition(currentBird.getSlingshotPosition().x - currentBird.getWidth() / 2,
                currentBird.getSlingshotPosition().y - currentBird.getHeight() / 2);
            stage.addActor(currentBird);
        } else {
            currentBird = null;
            // No more birds; handle end of level if needed
        }
    }

    private void loadLevel(int levelNumber) {
        GameLevel level = LevelManager.loadLevel(levelNumber);
        level.setupLevel();

        // Add birds to the queue
//        birdQueue.addAll(level.getBirds());
        for (Bird bird : level.getBirds()) {
            birdQueue.addLast(bird);
        }

        // Add pigs and structures to the stage
        for (Pig pig : level.getPigs()) {
            pigs.add(pig);
            stage.addActor(pig);
        }

        for (Structure structure : level.getStructures()) {
            structures.add(structure);
            stage.addActor(structure);
        }
    }


    private void drawSlingshotElastic() {
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1); // Black color for the elastic

        for (Bird bird : birds) {
            if (bird.isDragging()) {
                Vector2 position = bird.getPositionVector();
                Vector2 slingshotPosition = bird.getSlingshotPosition();

                // Draw line from slingshot to bird
                shapeRenderer.line(slingshotPosition.x, slingshotPosition.y, position.x, position.y);
            }
        }

        shapeRenderer.end();
    }


    @Override
    public void render(float delta) {
        // Update the game logic
        if (!isPaused) {
            // Update game logic, animations, etc.
            // Example: updateGame(delta);
            checkGameState(); // Check for game over or level completion
        }


        ScreenUtils.clear(1,1,1,1);

        batch.setProjectionMatrix(stage.getCamera().combined);

        batch.begin();

        // Accumulate delta time to control the frame switch rate
        timeSinceLastFrame += delta;

        // Switch frame if enough time has passed
        if (timeSinceLastFrame >= frameDuration) {
            currentFrame++;
            if (currentFrame >= MAX_FRAMES) {
                currentFrame = 1; // Reset to first frame
            }
            timeSinceLastFrame = 0f; // Reset the time counter
        }

        // Draw the current frame
        TextureRegion currentFrameRegion = textureAtlas.findRegion(String.format("%03d", currentFrame));
        batch.draw(currentFrameRegion, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());  // Adjust to fit viewport

        // Draw the slingshot
        batch.draw(slingshotTexture, 125, 50, 50, 100);

        // Check if level is completed and handle the delay
        if (levelCompleted) {
            levelCompleteTimer += delta;
            if (levelCompleteTimer >= LEVEL_COMPLETE_DELAY) {
                if (pigs.size == 0) {
                    uiManager.showLevelCompleteScreen(level);
                } else {
                    uiManager.showGameOverScreen(level);
                }
                levelCompleted = false; // Reset for the next level
            }
        }

        batch.end();

        stage.act(delta);
        stage.draw();

        // Draw the slingshot elastic
        drawSlingshotElastic();

        // Draw trajectory prediction if the bird is being dragged
        if (currentBird != null && currentBird.isDragging()) {
            drawTrajectory(currentBird);
        }

        // Check for bird special ability
        if (Gdx.input.justTouched()) {
            for (Bird bird : birds) {
                if (bird.isLaunched && !bird.isDragging()) {
                    bird.activateSpecialAbility();
                }
            }
        }

        // Check if current bird has been launched and is no longer active
        if (currentBird != null && !stage.getActors().contains(currentBird, true)) {
            setNextBird();
        }
    }


    private void drawTrajectory(Bird bird) {
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);

        Vector2 initialPosition = bird.getPositionVector();
        Vector2 initialVelocity = bird.getLaunchVelocity();
        float timeStep = 0.1f;
        int maxSteps = 30;

        Vector2 position = new Vector2(initialPosition);
        Vector2 velocity = new Vector2(initialVelocity);

        for (int i = 0; i < maxSteps; i++) {
            // Update position and velocity
            position.mulAdd(velocity, timeStep);
            velocity.y += bird.gravity * timeStep;

            // Draw a small circle at the predicted position
            shapeRenderer.circle(position.x, position.y, 2);

            // Stop if trajectory goes below ground level
            if (position.y < groundLevelY) {
                break;
            }
        }

        shapeRenderer.end();
    }


    private void checkGameState() {
        // Remove pigs that have been destroyed
        for (int i = pigs.size - 1; i >= 0; i--) {
            Pig pig = pigs.get(i);
            if (!stage.getActors().contains(pig, true)) {
                pigs.removeIndex(i);
            }
        }

        // Remove birds that have been launched and are no longer active
        for (int i = birds.size - 1; i >= 0; i--) {
            Bird bird = birds.get(i);
            if (!stage.getActors().contains(bird, true)) {
                birds.removeIndex(i);
            }
        }

        // Check if the level is completed
        if (!levelCompleted) {
            if (pigs.size == 0) {
                levelCompleteSound.play();
                levelCompleted = true;
                levelCompleteTimer = 0f;
            } else if (birds.size == 0 && currentBird == null) {
                gameOverSound.play();
                levelCompleted = true;
                levelCompleteTimer = 0f;
            }
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        stage.getViewport().update(width, height, true);

    }



    @Override
    public void dispose() {
        batch.dispose();
//        background.dispose();
        platform.dispose();
        slingshotTexture.dispose();
        stage.dispose();
        skin.dispose();

        shapeRenderer.dispose();
//        backgroundMusic.dispose();
        birdLaunchSound.dispose();
        pigHitSound.dispose();
        levelCompleteSound.dispose();
        gameOverSound.dispose();
        // Dispose of textures
        for (Bird bird : birds) {
            bird.getTexture().dispose();
        }
        for (Pig pig : pigs) {
            pig.getTexture().dispose();
        }
        for (Structure structure : structures) {
            structure.getTexture().dispose();
        }
    }
}
