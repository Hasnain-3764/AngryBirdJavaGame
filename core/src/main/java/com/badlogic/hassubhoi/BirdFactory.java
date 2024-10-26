package com.badlogic.hassubhoi;

import com.badlogic.gdx.graphics.Texture;

public class BirdFactory {
    public static Bird createBird(String type, Texture texture) {
        switch (type.toLowerCase()) {
            case "red":
                return new RedBird(texture);
            case "blue":
                return new BlueBird(texture);
            case "black":
                return new BlackBird(texture);
            case "yellow":
                return new YellowBird(texture);
            default:
                return null;
        }
    }
}
