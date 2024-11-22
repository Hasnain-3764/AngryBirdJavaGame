package com.badlogic.hassubhoi;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class LevelManager {

    public static GameLevel loadLevel(int levelNumber) {
        World world = new World(new Vector2(0, -10), true);
        switch (levelNumber) {
            case 1:
                return new Level1(world);
            case 2:
                return new Level2(world);
            case 3:
                return new Level3(world);
            case 4:
                return new Level4(world);
            default:
                return new Level1(world); // default is level 1
        }
    }
}
