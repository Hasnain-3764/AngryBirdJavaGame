
import com.badlogic.hassubhoi.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.files.FileHandle;

import static org.junit.Assert.*;

public class TextureLoadingTest {

    private AngryBirdGames mockGame;
    private UIManager mockUIManager;

    @Before
    public void setUp() {
        // Initialize a headless LibGDX application
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationAdapter() {}, config);

        mockGame = new AngryBirdGames();
        mockUIManager = new UIManager(mockGame);
    }

    @After
    public void tearDown() {
        // Dispose any loaded textures to prevent memory leaks
        // (Not strictly necessary in tests, but good practice)
        // Example:
        // if (levelHeader != null) {
        //     levelHeader.dispose();
        // }
    }

    @Test
    public void testLoadExistingLevelTexture() {
        Level1 level1 = new Level1(mockGame, mockUIManager);

        // Verify levelHeader is loaded correctly
        assertNotNull("Level1's levelHeader should not be null", level1.levelHeader);
    }

    @Test
    public void testLoadDefaultTextureForUnknownLevel() {
        // Create a GamePlayScreen with an unknown level
        GamePlayScreen unknownLevel = new GamePlayScreen(mockGame, mockUIManager, 999) {
            // If GamePlayScreen is abstract, provide necessary implementations
        };
        unknownLevel.loadLevelHeader(); // Ensure this method is accessible

        // Verify that the default texture is loaded
        assertNotNull("Unknown level's levelHeader should be loaded with default texture", unknownLevel.levelHeader);
    }

    @Test
    public void testHandleMissingTextureGracefully() {
        // Simulate missing texture by attempting to load a non-existent texture path
        // Create a subclass that attempts to load a faulty path

        class FaultyGamePlayScreen extends GamePlayScreen {
            public FaultyGamePlayScreen(AngryBirdGames game, UIManager uiManager, int level) {
                super(game, uiManager, level);
            }

            @Override
            public void loadLevelHeader() {
                // Provide an incorrect path to simulate missing texture
                String faultyPath = "buttonPro/nonexistent_level.png";
                try {
                    levelHeader = new Texture(Gdx.files.internal(faultyPath));
                    fail("Loading nonexistent texture should have failed");
                } catch (Exception e) {
                    // Expected exception, load default texture
                    levelHeader = new Texture(Gdx.files.internal("buttonPro/defaultLevel.png"));
                }
            }
        }

        FaultyGamePlayScreen faultyScreen = new FaultyGamePlayScreen(mockGame, mockUIManager, 1);
        faultyScreen.loadLevelHeader();

        // Verify that the default texture is loaded
        assertNotNull("Faulty level's levelHeader should load default texture", faultyScreen.levelHeader);
    }
}
