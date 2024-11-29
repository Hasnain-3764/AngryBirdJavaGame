// src/test/java/com/badlogic/hassubhoi/LevelInitializationTest.java

import com.badlogic.hassubhoi.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LevelInitializationTest {

    private AngryBirdGames mockGame;
    private UIManager mockUIManager;

    @Before
    public void setUp() {
        // Mock or stub the necessary dependencies
        // You can use Mockito to create mocks
        mockGame = new AngryBirdGames();
        mockUIManager = new UIManager(mockGame);
    }

    @Test
    public void testLevel1Initialization() {
        Level1 level1 = new Level1(mockGame, mockUIManager);

        // Verify level number
        assertEquals("Level1 should have level number 1", 1, level1.level);

        // Verify levelHeader is loaded correctly
        assertNotNull("Level1's levelHeader should not be null", level1.levelHeader);
        // Optionally, verify the texture is the correct one
        // This requires access to the Texture's file name or properties
        // If not accessible, ensure no errors were logged during loading

        // Verify birds are initialized and none are null
        assertFalse("Level1 should have birds initialized", level1.birds.isEmpty());
        for (Bird bird : level1.birds) {
            assertNotNull("Bird in Level1 should not be null", bird);
        }

        // Verify pigs are initialized and none are null
        assertFalse("Level1 should have pigs initialized", level1.pigs.isEmpty());
        for (Pig pig : level1.pigs) {
            assertNotNull("Pig in Level1 should not be null", pig);
        }

        // Verify structures are initialized and none are null
        assertFalse("Level1 should have structures initialized", level1.structures.isEmpty());
        for (Structure structure : level1.structures) {
            assertNotNull("Structure in Level1 should not be null", structure);
        }
    }

    @Test
    public void testLevel2Initialization() {
        // Similar to Level1, assuming you have a Level2 class
        Level2 level2 = new Level2(mockGame, mockUIManager);

        // Verify level number
        assertEquals("Level2 should have level number 2", 2, level2.level);

        // Verify levelHeader is loaded correctly
        assertNotNull("Level2's levelHeader should not be null", level2.levelHeader);

        // Verify birds are initialized and none are null
        assertFalse("Level2 should have birds initialized", level2.birds.isEmpty());
        for (Bird bird : level2.birds) {
            assertNotNull("Bird in Level2 should not be null", bird);
        }

        // Verify pigs are initialized and none are null
        assertFalse("Level2 should have pigs initialized", level2.pigs.isEmpty());
        for (Pig pig : level2.pigs) {
            assertNotNull("Pig in Level2 should not be null", pig);
        }

        // Verify structures are initialized and none are null
        assertFalse("Level2 should have structures initialized", level2.structures.isEmpty());
        for (Structure structure : level2.structures) {
            assertNotNull("Structure in Level2 should not be null", structure);
        }
    }

    // Add similar tests for other levels
}
