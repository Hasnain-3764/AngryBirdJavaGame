package com.badlogic.hassubhoi;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GamePlayScreen implements Screen {
    private UIManager uiManager;
    private Stage stage;
    private FitViewport viewport;
//    private SpriteBatch game.batch;

    public Array<Bird> birds;
    public Array<Pig> pigs;
    public Array<Structure> structures;
    private Texture catp;

    private Texture background;
    private Texture platform;
    private Texture slingshotTexture;
    public ShapeRenderer shapeRenderer;

    private Queue<Bird> birdQueue;
    private Bird currentBird;
    private final float groundLevelY = 50f;
    // groundlevelY  is same for slingshot and bird landing

    public int level;

    private int currentFrame = 1;
    private int MAX_FRAMES; // should be final?
    private TextureAtlas textureAtlas;


    private boolean isPaused = false;
    // audio
//    private Music backgroundMusic;
    private Sound birdLaunchSound;
    private Sound pigHitSound;
    private Sound levelCompleteSound;
    private Sound gameOverSound;

    private boolean levelCompleted = false;
    private float levelCompleteTimer = 0f;
    private final float LEVEL_COMPLETE_DELAY = 2f; // 2 seconds delay\

    //    private ImageButton pauseButton;
//    private ImageButton playButton;
    private ImageButton saveToggleButton;
    private ImageButton playPauseToggleButton;
    private ImageButton restartButton;
    private ImageButton exitButton;
    private ImageButton levelSelectButton;
    private ImageButton levelCompleteButton;
    private ImageButton settingsButton;
//    private ImageButton levelFailButton;
    private ImageButton saveGameButton;
    private ImageButton playPauseSoundButton;

    public Texture levelHeader;
    private Image levelHeaderImage;

    public static final float ppm = 100f; // Pixels per meter
    public World world;
    private Box2DDebugRenderer b2dr;
    public Array<Body> bodiesToDestroy;
    private int currentBirdIndex = 0;
    private static final float launchX = 535; // In pixels
    private static final float launchY = 375; // In pixels
    public OrthographicCamera camera;
    private java.util.Queue<Runnable> scheduledTasks;

    private Texture buttonUpTexture;
    private Texture buttonDownTexture;
    private BitmapFont buttonFont;


    public AngryBirdGames game;

     public Texture ground;
    private boolean start_collision = false;

    private Texture testTexture;

    private Catapult catapult;
    private Stage pauseStage;

    private InputMultiplexer inputMultiplexer;

    public GamePlayScreen(AngryBirdGames game, UIManager uiManager) {
        this.uiManager = uiManager;
        this.game = game;
        initializeCommonComponents();
    }

    public GamePlayScreen(AngryBirdGames game, UIManager uiManager, int level) {
        this(game, uiManager);
        System.out.println("Initializing GamePlayScreen for level: " + level);
        this.level = level;
        initializeLevelSpecificComponents();
    }

    private void initializeLevelSpecificComponents() {
//        loadLevelHeader();
        initialiseUI(); // Initialize UI after setting level
    }

    private void initializeCommonComponents(){
        ground = new Texture("ground.png");
        background = new Texture("background.png");

        System.out.println("GamePlayScreen setup complete.");
        camera = new OrthographicCamera(Gdx.graphics.getWidth() / ppm, Gdx.graphics.getHeight() / ppm);
        viewport = new FitViewport(Gdx.graphics.getWidth() / ppm, Gdx.graphics.getHeight() / ppm, camera);
//        viewport = new FitViewport(1920 / ppm, 1080 / ppm, camera);
        stage = new Stage(new ScreenViewport());

        world = new World(new Vector2(0, -9.81f), true);
        b2dr = new Box2DDebugRenderer();

        scheduledTasks = new LinkedList<>();
        bodiesToDestroy = new Array<>();

        catapult = new Catapult(game, camera);

        shapeRenderer = new ShapeRenderer();

        // Initialize arrays
        birds = new Array<>();
        pigs = new Array<>();
        structures = new Array<>();
        birdQueue = new Queue<>();

//        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("background_music.mp3")); // moved background music to
        birdLaunchSound = Gdx.audio.newSound(Gdx.files.internal("bird_launch.wav"));
        pigHitSound = Gdx.audio.newSound(Gdx.files.internal("pig_hit.wav"));
        levelCompleteSound = Gdx.audio.newSound(Gdx.files.internal("level_complete.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("game_over.wav"));

//        initialiseUI();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();

        createGround();
        Gdx.input.setInputProcessor(stage); // Set the stage as input processor
//        backgroundMusic.setLooping(true);
//        backgroundMusic.play();

        setupInputHandling();
        setupContactListener();

    }

    private void createGround() {
        BodyDef gbDef = new BodyDef();
        gbDef.position.set(0,0);

        Body gBody = world.createBody(gbDef);

        PolygonShape gShape = new PolygonShape();
        gShape.setAsBox(1920 / ppm, 205 / ppm);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = gShape;
        fixtureDef.density = 1.0f;      // Standard density
        fixtureDef.friction = 5.0f;

        gBody.createFixture(fixtureDef);
        gShape.dispose();

        //Right Boundary
        BodyDef rightBoundDef = new BodyDef();
        rightBoundDef.position.set(1950/ppm,0);

        Body rBound = world.createBody(rightBoundDef);

        PolygonShape rbShape = new PolygonShape();
        rbShape.setAsBox(20/ppm,1080/ppm);

        rBound.createFixture(rbShape,1.0f);
        rbShape.dispose();
    }

    public void scheduleTask(Runnable task) {
        scheduledTasks.add(task);
    }


    public void initialiseUI() {
        // loading all button textures
//        testTexture = new Texture(Gdx.files.internal("buttonPro/1.png")); // Ensure test.png exists in assets

        Texture pauseTexture = new Texture(Gdx.files.internal("buttonPro/pauseButton.png"));
        Texture playTexture = new Texture(Gdx.files.internal("buttonPro/play.png"));
        Texture restartTexture = new Texture(Gdx.files.internal("buttonPro/restart.png"));
        Texture exitTexture = new Texture(Gdx.files.internal("buttonPro/exit.png"));
        Texture levelSelectTexture = new Texture(Gdx.files.internal("buttonPro/hamburger.png"));
//        Texture levelCompleteTexture = new Texture(Gdx.files.internal("buttonPro/forward2.png"));
        Texture settinsTexture = new Texture(Gdx.files.internal("buttonPro/settings.png"));
//        Texture levelFailTexture = new Texture(Gdx.files.internal("buttonPro/levelFail.png"));
        Texture saveGameTexture = new Texture(Gdx.files.internal("buttonPro/save.png"));
        Texture saveDownTexture = new Texture(Gdx.files.internal("buttonPro/saveDown.png"));
        Texture playSoundTexture = new Texture(Gdx.files.internal("buttonPro/soundOn.png"));
        Texture pauseSoundTexture = new Texture(Gdx.files.internal("buttonPro/soundOff.png"));
        catp = new Texture("slingshot.png");

        loadLevelHeader();

//        levelHeader = new Texture(Gdx.files.internal("buttonPro/level1.png"));
        //  textureRegionDrawable to make text button, not skin?
        //! can't confingure custom skin(implement)
//        pauseButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
//        playButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(pauseTexture)));
        restartButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(restartTexture)));
        exitButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(exitTexture)));
        levelSelectButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(levelSelectTexture)));
//        levelCompleteButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(levelCompleteTexture)));
        settingsButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(settinsTexture)));
//        levelFailButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(levelFailTexture)));
        saveGameButton = new ImageButton(new TextureRegionDrawable(new TextureRegion(saveGameTexture)));


        TextureRegionDrawable pauseButton = (new TextureRegionDrawable(pauseTexture));
        TextureRegionDrawable playButton = (new TextureRegionDrawable(playTexture));

        TextureRegionDrawable playSound = (new TextureRegionDrawable(playSoundTexture));
        TextureRegionDrawable pauseSound = (new TextureRegionDrawable(pauseSoundTexture));

        TextureRegionDrawable save = (new TextureRegionDrawable(saveGameTexture));
        TextureRegionDrawable saveDown = (new TextureRegionDrawable(saveDownTexture));

        // making them toggle
        ImageButton.ImageButtonStyle toggleStyle1 = new ImageButton.ImageButtonStyle();
        toggleStyle1.up = pauseButton;
        toggleStyle1.checked = playButton;

        ImageButton.ImageButtonStyle toggleStyle2 = new ImageButton.ImageButtonStyle();
        toggleStyle2.up = playSound;          // Default state (not checked)
        toggleStyle2.checked = pauseSound;    // State when toggled on

        ImageButton.ImageButtonStyle toggleStyle3 = new ImageButton.ImageButtonStyle();
        toggleStyle3.up = save;
        toggleStyle3.down = saveDown;

        playPauseToggleButton = new ImageButton(toggleStyle1);
        playPauseSoundButton = new ImageButton(toggleStyle2);
        saveToggleButton = new ImageButton(toggleStyle3);

        // Add listeners
        playPauseToggleButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                togglePauseGame(); // to be implemented
                return true; // consuming here so that input processors dont collide.
            }
        });

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.restartLevel(level);
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
//        levelCompleteButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                uiManager.showLevelCompleteScreen(level);
//            }
//        });
        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                uiManager.showSettingsScreen();
            }
        });
        playPauseSoundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isChecked = playPauseSoundButton.isChecked();
                SoundManager.getInstance().setMusicEnabled(!isChecked);
                SoundManager.getInstance().setSoundsEnabled(!isChecked);
            }
        });

        saveToggleButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                saveGameState();
            }
        });

        Image level1image = new Image(levelHeader);
        Table centralTable = new Table();
        centralTable.setFillParent(true);
        centralTable.top();
        centralTable.add(level1image).size(levelHeader.getWidth() / 2.5f, levelHeader.getHeight() / 2.5f);

        Table rightButtonTable = new Table();
        Table leftButtonTable = new Table();

        rightButtonTable.top().right();
        leftButtonTable.top().left();

        rightButtonTable.setFillParent(true);
        leftButtonTable.setFillParent(true);

        float buttonSize = Gdx.graphics.getHeight() * 0.1f; // 10% of screen height in pixels

        // right table
        float padding = 5;

//        rightButtonTable.add(levelCompleteButton).size(buttonSize).pad(padding);
        rightButtonTable.add(restartButton).size(buttonSize).pad(padding);
        rightButtonTable.add(levelSelectButton).size(buttonSize).pad(padding);
        rightButtonTable.add(playPauseToggleButton).size(buttonSize).pad(padding);
        rightButtonTable.add(saveToggleButton).size(buttonSize/1.1f).pad(padding);

        // Left table buttons
        leftButtonTable.add(exitButton).size(buttonSize).pad(padding);
        leftButtonTable.add(settingsButton).size(buttonSize).pad(padding);
        leftButtonTable.add(playPauseSoundButton).size(buttonSize).pad(padding);

        stage.addActor(centralTable);
        stage.addActor(rightButtonTable);
        stage.addActor(leftButtonTable);
    }

    public void loadLevelHeader(){
        if(level == 1){
            levelHeader = new Texture(Gdx.files.internal("buttonPro/level1.png"));
            System.out.println("Loaded level1.png for level 1");
        }
        else if(level == 2){
            levelHeader = new Texture(Gdx.files.internal("buttonPro/level2.png"));
            System.out.println("Loaded level2.png for level 2");
        }
        else if(level == 3){
            levelHeader = new Texture(Gdx.files.internal("buttonPro/level3.png"));
            System.out.println("Loaded level3.png for level 3");
        }
        else if(level == 4){
            levelHeader = new Texture(Gdx.files.internal("buttonPro/level4.png"));
            System.out.println("Loaded level4.png for level 4");
        }
        else {
            // Handle unknown level numbers
            System.err.println("Unknown level number: " + level);
            levelHeader = new Texture(Gdx.files.internal("buttonPro/level1.png")); // Provide a default texture
            System.out.println("Loaded defaultLevel.png for unknown level");
        }
    }

    // Save Game State
    public void saveGameState() {
        List<BirdData> birdDataList = new ArrayList<>();
        for (Bird bird : birds) {
            Vector2 position = bird.body.getPosition();
            float angle = bird.body.getAngle();
            String type = bird.getClass().getSimpleName();
            boolean hasLaunched = bird.hasLaunched;
            BirdData birdData = new BirdData(type, position.x, position.y, angle, hasLaunched);
            birdDataList.add(birdData);
        }

        List<PigData> pigDataList = new ArrayList<>();
        for (Pig pig : pigs) {
            Vector2 position = pig.body.getPosition();
            float angle = pig.body.getAngle();
            String type = pig.getClass().getSimpleName();
            int hitPoints = pig.getHitPoints();
            PigData pigData = new PigData(type, position.x, position.y, angle, hitPoints);
            pigDataList.add(pigData);
        }

        List<StructureData> structureDataList = new ArrayList<>();
        for (Structure structure : structures) {
            Vector2 position = structure.body.getPosition();
            float angle = structure.body.getAngle();
            String type = structure.getClass().getSimpleName();
            int hitPoints = structure.getHitPoints();
            StructureData structureData = new StructureData(type, position.x, position.y, angle, hitPoints);
            structureDataList.add(structureData);
        }

        GameState gameState = new GameState(level, birdDataList, pigDataList, structureDataList, currentBirdIndex);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("savegame.dat"))) {
            out.writeObject(gameState);
            System.out.println("Game saved successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean loadGameState() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("savegame.dat"))) {
            GameState gameState = (GameState) in.readObject();

            this.level = gameState.getLevel();
            System.out.println("Loaded level: " + this.level);

            // Clear current game objects
            birds.clear();
            pigs.clear();
            structures.clear();
            bodiesToDestroy.clear();
            world.dispose();
            world = new World(new Vector2(0, -9.81f), true);
            setupContactListener();

            createGround();

            // Recreate game objects from gameState
            for (BirdData birdData : gameState.getBirds()) {
                Bird bird = createBirdFromData(birdData);
                if (bird != null) {
                    birds.add(bird);
                } else {
                    System.err.println("Failed to create bird from BirdData: " + birdData.getType());
                }
            }

            for (PigData pigData : gameState.getPigs()) {
                Pig pig = createPigFromData(pigData);
                if (pig != null) {
                    pigs.add(pig);
                } else {
                    System.err.println("Failed to create pig from PigData: " + pigData.getType());
                }
            }

            for (StructureData structureData : gameState.getStructures()) {
                Structure structure = createStructureFromData(structureData);
                if (structure != null) {
                    structures.add(structure);
                } else {
                    System.err.println("Failed to create structure from StructureData: " + structureData.getType());
                }
            }

            currentBirdIndex = gameState.getCurrentBirdIndex();
            level = gameState.getLevel();

            initialiseUI();

            System.out.println("Game loaded successfully.");
            return true;

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Bird createBirdFromData(BirdData birdData) {
        Bird bird = null;
        float x = birdData.getX();
        float y = birdData.getY();
        float angle = birdData.getAngle();
        boolean hasLaunched = birdData.isHasLaunched();
        String type = birdData.getType();

        if (type.equals("RedBird")) {
            bird = new RedBird(this, x * ppm, y * ppm);
        } else if (type.equals("YellowBird")) {
            bird = new YellowBird(this, x * ppm, y * ppm);
        } else if (type.equals("BlueBird")) {
            bird = new BlueBird(this, x * ppm, y * ppm);
        }
        if (bird != null) {
            bird.body.setTransform(x, y, angle);
            bird.hasLaunched = hasLaunched;
            bird.body.setType(hasLaunched ? BodyDef.BodyType.DynamicBody : BodyDef.BodyType.StaticBody);
        }
        return bird;
    }

    private Pig createPigFromData(PigData pigData) {
        Pig pig = null;
        float x = pigData.getX();
        float y = pigData.getY();
        float angle = pigData.getAngle();
        int hitPoints = pigData.getHitPoints();
        String type = pigData.getType();

        if (type.equals("NormalPig")) {
            pig = new NormalPig(this, x * ppm, y * ppm);
        } else if (type.equals("HelmetPig")) {
            pig = new HelmetPig(this, x * ppm, y * ppm);
        }
        if (pig != null) {
            pig.body.setTransform(x, y, angle);
            pig.setHitPoints(hitPoints);
        }
        return pig;
    }

    private Structure createStructureFromData(StructureData structureData) {
        Structure structure = null;
        float x = structureData.getX();
        float y = structureData.getY();
        float angle = structureData.getAngle();
        int hitPoints = structureData.getHitPoints();
        String type = structureData.getType();

        if (type.equals("GlassStructure")) {
            structure = new GlassStructure(this, x * ppm, y * ppm, angle * MathUtils.radiansToDegrees);
        } else if (type.equals("WoodStructure")) {
            structure = new WoodStructure(this, x * ppm, y * ppm, angle * MathUtils.radiansToDegrees);
        }
        if (structure != null) {
            structure.body.setTransform(x, y, angle);
            structure.setHitPoints(hitPoints);
        }
        return structure;
    }

    private void setupInputHandling() {
        inputMultiplexer = new InputMultiplexer();

        inputMultiplexer.addProcessor(stage); // Adding the stage first
        inputMultiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (isPaused) return false;
                Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
                Vector2 touchPos = new Vector2(worldCoordinates.x, worldCoordinates.y);

                boolean birdTouched = false;
                for (Bird bird : birds) {
                    if (bird.startDrag(touchPos)) {
                        birdTouched = true;
                        start_collision = true;
                        break; // Stop after finding the first bird that's being dragged
                    }
                }

                return birdTouched;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (isPaused) return false;
                Vector3 worldCoordinates = camera.unproject(new Vector3(screenX, screenY, 0));
                Vector2 touchPos = new Vector2(worldCoordinates.x, worldCoordinates.y);

                boolean handled = false;
                for (Bird bird : birds) {
                    if (bird.isDragging()) {
                        bird.drag(touchPos);
                        handled = true;
                        break; // Only need to drag one bird
                    }
                }

                return handled;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (isPaused) return false;

                boolean handled = false;
                for (Bird bird : birds) {
                    if (bird.isDragging()) {
                        bird.release();
                        handled = true;
                        break; // Only need to release one bird
                    }
                }

                return handled;
            }
        });

        Gdx.input.setInputProcessor(inputMultiplexer);
    }


    private void setupContactListener() {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Object a = contact.getFixtureA().getBody().getUserData();
                Object b = contact.getFixtureB().getBody().getUserData();

                if (a instanceof Structure && start_collision) ((Structure) a).handleCollision();
                if (b instanceof Structure && start_collision) ((Structure) b).handleCollision();
                if (a instanceof Pig && start_collision) ((Pig) a).handleCollision();
                if (b instanceof Pig && start_collision) ((Pig) b).handleCollision();
            }

            @Override public void endContact(Contact contact) {}
            @Override public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override public void postSolve(Contact contact, ContactImpulse impulse) {}
        });
    }

    public void setupLevel(Array<Bird> birds, Array<Structure> glassBlocks, Array<Pig> pigs) {
        this.birds = birds;
        this.structures = glassBlocks;
        this.pigs = pigs;
    }



    private void drawSlingshotElastic() {
        shapeRenderer.setProjectionMatrix(stage.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1); // black color for the elastic

        for (Bird bird : birds) {
            if (bird.isDragging()) {
                Vector2 position = bird.getPositionVector();
                Vector2 slingshotPosition = bird.getSlingshotPosition();

                // draw line from slingshot to bird
                shapeRenderer.line(slingshotPosition.x, slingshotPosition.y, position.x, position.y);
                // implement a collection of sprites that can go back and forth.(clock same as that of bird)
            }
        }



        shapeRenderer.end();
    }

    @Override
    public void render(float delta) {
        // Clear the screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        if (!isPaused) {
            update(delta);
        }
        renderGameObjects();

        // Update and draw the stage (for UI elements)
        stage.act(delta);
        stage.draw();

        // If the game is paused, render the pause menu
        if (isPaused && pauseStage != null) {
            pauseStage.act(delta);
            pauseStage.draw();
        }
    }

    private void showPauseMenu() {
        if (pauseStage == null) {
            pauseStage = new Stage(new ScreenViewport());

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

            // Create a semi-transparent background
            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(0, 0, 0, 0.5f); // Black with 50% opacity
            pixmap.fill();
            Texture dimBackgroundTexture = new Texture(pixmap);
            pixmap.dispose();
            Image dimBackground = new Image(dimBackgroundTexture);
            dimBackground.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

            // Create buttons
            // Use your existing code to create resumeButton, mainMenuButton, exitButton, settingsButton
            // For example:
            TextButton resumeButton = new TextButton("Resume", textButtonStyle);
            TextButton mainMenuButton = new TextButton("Main Menu", textButtonStyle);
            TextButton settingsButton = new TextButton("Settings", textButtonStyle);
            TextButton exitButton = new TextButton("Exit", textButtonStyle);

            // Add listeners to buttons
            resumeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    togglePauseGame();
                }
            });
            mainMenuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    uiManager.showMainMenu();
                }
            });
            settingsButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    uiManager.showSettingsScreen();
                }
            });
            exitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Gdx.app.exit();
                }
            });

            // Arrange buttons in a table
            Table pauseTable = new Table();
            pauseTable.setFillParent(true);
            pauseTable.center();

            pauseTable.add(resumeButton).size(200, 50).pad(10);
            pauseTable.row();
            pauseTable.add(mainMenuButton).size(200, 50).pad(10);
            pauseTable.row();
            pauseTable.add(settingsButton).size(200, 50).pad(10);
            pauseTable.row();
            pauseTable.add(exitButton).size(200, 50).pad(10);

            // Add actors to pauseStage
            pauseStage.addActor(dimBackground);
            pauseStage.addActor(pauseTable);
        }
    }


    private void togglePauseGame() {
        isPaused = !isPaused;
        if (isPaused) {
            showPauseMenu();
            Gdx.input.setInputProcessor(pauseStage);
        } else {
            hidePauseMenu();
            Gdx.input.setInputProcessor(inputMultiplexer);
        }
    }


    private void hidePauseMenu() {
        if (pauseStage != null) {
            pauseStage.dispose();
            pauseStage = null;
        }
    }

    private void update(float delta) {
        // Update the physics world
        world.step(1 / 60f, 6, 2); // Time step, velocity iterations, position iterations

        // Update game objects
        updateGameObjects(delta);

        // Handle scheduled tasks
        processScheduledTasks();

        // Destroy bodies that are queued for removal
        destroyQueuedBodies();

        // Game logic
        // Update the current bird's position if it hasn't been launched or is not being dragged
        if (currentBirdIndex < birds.size) {
            Bird currentBird = birds.get(currentBirdIndex);
            if (!currentBird.hasLaunched && !currentBird.isDragging()) {
                currentBird.body.setTransform(launchX / ppm, launchY / ppm, 0); // Place at launch position
            } else if (currentBird.isReadyForRemoval()) {
                // Destroy the bird and advance to the next one
                bodiesToDestroy.add(currentBird.body);
                birds.removeIndex(currentBirdIndex);
                currentBirdIndex++; // Move to the next bird
            }
        }

        // Check win condition
        if (pigs.size == 0) {
            // Level completed
            uiManager.showLevelCompleteScreen(level);
        }

        // Check lose condition
        if (birds.size == 0 && pigs.size > 0) {
            // Game over
            uiManager.showGameOverScreen(level);
        }
    }

    private void updateGameObjects(float delta) {
        for (Bird bird : birds) {
            bird.update(delta);
        }
        for (Pig pig : pigs) {
            pig.update(delta);
        }
        for (Structure structure : structures) {
            structure.update(delta);
        }
    }

    private void processScheduledTasks() {
        while (!scheduledTasks.isEmpty()) {
            try {
                scheduledTasks.poll().run();
            } catch (Exception e) {
                Gdx.app.log("Error", "Exception during task execution: " + e.getMessage());
            }
        }
    }

    private void destroyQueuedBodies() {
        for (Body body : bodiesToDestroy) {
            if (body != null) {
                world.destroyBody(body);
            }
        }
        bodiesToDestroy.clear();
    }

    private void renderGameObjects() {
        game.batch.begin();

        // Draw background
        game.batch.draw(background, 0, 0, background.getWidth() / ppm, background.getHeight() / ppm);
        // Draw ground
        game.batch.draw(ground, 0, 0, ground.getWidth() / ppm, ground.getHeight() / ppm);
        game.batch.draw(catp, 503/ppm,203/ppm, (catp.getWidth()/ppm/ppm)*2.5f, (catp.getHeight()/ppm/ppm)*2.5f);
        game.batch.end();

        // Render the catapult
        Bird draggingBird = null;
        for (Bird bird : birds) {
            if (bird.isDragging()) {
                draggingBird = bird;
                break;
            }
        }

        // Render birds
        for (Bird bird : birds) {
            bird.render(0, game.batch);
        }

        // Render structures
        for (Structure structure : structures) {
            structure.render(0, game.batch);
        }

        // Render pigs
        for (Pig pig : pigs) {
            pig.render(0, game.batch);
        }

        catapult.render(0, draggingBird, game.batch);

        // Optionally render debug lines
//        b2dr.render(world, camera.combined);
    }

    //not required for now.
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


    @Override
    public void resize(int width, int height) {
//        viewport.update(width, height, true);
//        stage.getViewport().update(width, height, true);

    }



    @Override
    public void dispose() {
        if (background != null) {
            background.dispose();
        }
        game.batch.dispose();
        background.dispose();
        platform.dispose();
        slingshotTexture.dispose();
        stage.dispose();
//        skin.dispose();

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
        if(levelHeader != null){
            levelHeader.dispose();
        }
    }
    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

}
