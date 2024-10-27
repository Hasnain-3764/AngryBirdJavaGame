package com.badlogic.hassubhoi;

public class LevelManager {
    public static GameLevel loadLevel(int levelNumber) {
        switch (levelNumber) {
            case 1:
                return new Level1();
            case 2:
                return new Level2();
            case 3:
                return new Level3();
            case 4:
                return new Level4();
            default:
                return new Level1(); // default is level 1
        }
    }
}
