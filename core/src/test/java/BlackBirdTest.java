// src/test/java/com/badlogic/hassubhoi/BlackBirdTest.java

import com.badlogic.hassubhoi.*;
import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import static org.junit.Assert.*;

public class BlackBirdTest {

    private GamePlayScreen mockLevel;
    private World world;
    private BlackBird blackBird;

    @Before
    public void setUp() {
        // Initialize a headless world for testing
        world = new World(new Vector2(0, -9.81f), true);
        mockLevel = new GamePlayScreen(null, null, 1) {
            // Implement abstract methods if any
        };
        mockLevel.world = world; // Assign the test world

        // Initialize BlackBird
        blackBird = new BlackBird(mockLevel, 225f, 215f);
    }

    @Test
    public void testExplode_AffectsNearbyBodies() {
        // Create dummy bodies within explosion radius
        Pig dummyPig = new NormalPig(mockLevel, 225f, 215f + 4.5f); // Within 5f radius
        mockLevel.pigs.add(dummyPig);

        Structure dummyStructure = new WoodStructure(mockLevel, 225f + 3f, 215f, 0f); // Within 5f radius
        mockLevel.structures.add(dummyStructure);

        // Before explosion
        Vector2 initialPigVelocity = dummyPig.body.getLinearVelocity().cpy();
        Vector2 initialStructureVelocity = dummyStructure.body.getLinearVelocity().cpy();

        // Trigger explosion
        blackBird.explode();

        // After explosion, velocities should have changed
        Vector2 pigVelocityAfter = dummyPig.body.getLinearVelocity();
        Vector2 structureVelocityAfter = dummyStructure.body.getLinearVelocity();

        assertNotEquals("Pig's velocity should change after explosion", initialPigVelocity, pigVelocityAfter);
        assertNotEquals("Structure's velocity should change after explosion", initialStructureVelocity, structureVelocityAfter);

        // Verify that BlackBird is removed
        assertFalse("BlackBird should be removed from birds array", mockLevel.birds.contains(blackBird, true));
    }

    // Additional tests can be added to verify damage application, etc.
}
