// src/test/java/com/badlogic/hassubhoi/GameStateTest.java

import com.badlogic.hassubhoi.BirdData;
import com.badlogic.hassubhoi.GameState;
import com.badlogic.hassubhoi.PigData;
import com.badlogic.hassubhoi.StructureData;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameStateTest {

    private final String SAVE_FILE = "test_savegame.dat";

    @Before
    public void setUp() {
        // Setup code if needed
    }

    @After
    public void tearDown() {
        // Delete the test save file after tests
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    public void testGameStateSerialization() {
        // Create sample data
        int level = 2;

        ArrayList<BirdData> birds = new ArrayList<>();
        birds.add(new BirdData("RedBird", 450f, 225f, 0f, false));
        birds.add(new BirdData("BlueBird", 300f, 220f, 0f, true));

        ArrayList<PigData> pigs = new ArrayList<>();
        pigs.add(new PigData("NormalPig", 1000f, 500f, 0f, 3));
        pigs.add(new PigData("HelmetPig", 1050f, 550f, 0f, 5));

        ArrayList<StructureData> structures = new ArrayList<>();
        structures.add(new StructureData("WoodStructure", 1000f, 300f, 0f, 1));
        structures.add(new StructureData("GlassStructure", 1000f, 350f, 90f, 2));

        GameState originalState = new GameState(level, birds, pigs, structures,1);

        // Serialize the GameState
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            out.writeObject(originalState);
        } catch (IOException e) {
            fail("Serialization failed with exception: " + e.getMessage());
        }

        // Deserialize the GameState
        GameState loadedState = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            loadedState = (GameState) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            fail("Deserialization failed with exception: " + e.getMessage());
        }

        // Verify the loaded state matches the original
        assertNotNull("Loaded GameState should not be null", loadedState);
        assertEquals("Level should match", originalState.getLevel(), loadedState.getLevel());

        // Verify birds
        assertEquals("Number of birds should match", originalState.getBirds().size(), loadedState.getBirds().size());
        for (int i = 0; i < birds.size(); i++) {
            BirdData originalBird = originalState.getBirds().get(i);
            BirdData loadedBird = loadedState.getBirds().get(i);
            assertEquals(originalBird.getType(), loadedBird.getType());
            assertEquals(originalBird.getX(), loadedBird.getX(), 0.001);
            assertEquals(originalBird.getY(), loadedBird.getY(), 0.001);
            assertEquals(originalBird.getAngle(), loadedBird.getAngle(), 0.001);
            assertEquals(originalBird.isHasLaunched(), loadedBird.isHasLaunched());
        }

        // Verify pigs
        assertEquals("Number of pigs should match", originalState.getPigs().size(), loadedState.getPigs().size());
        for (int i = 0; i < pigs.size(); i++) {
            PigData originalPig = originalState.getPigs().get(i);
            PigData loadedPig = loadedState.getPigs().get(i);
            assertEquals(originalPig.getType(), loadedPig.getType());
            assertEquals(originalPig.getX(), loadedPig.getX(), 0.001);
            assertEquals(originalPig.getY(), loadedPig.getY(), 0.001);
            assertEquals(originalPig.getAngle(), loadedPig.getAngle(), 0.001);
            assertEquals(originalPig.getHitPoints(), loadedPig.getHitPoints());
        }

        // Verify structures
        assertEquals("Number of structures should match", originalState.getStructures().size(), loadedState.getStructures().size());
        for (int i = 0; i < structures.size(); i++) {
            StructureData originalStructure = originalState.getStructures().get(i);
            StructureData loadedStructure = loadedState.getStructures().get(i);
            assertEquals(originalStructure.getType(), loadedStructure.getType());
            assertEquals(originalStructure.getX(), loadedStructure.getX(), 0.001);
            assertEquals(originalStructure.getY(), loadedStructure.getY(), 0.001);
            assertEquals(originalStructure.getAngle(), loadedStructure.getAngle(), 0.001);
        }
    }
}
