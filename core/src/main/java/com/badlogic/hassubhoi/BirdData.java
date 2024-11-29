package com.badlogic.hassubhoi;

import java.io.Serializable;

public class BirdData implements Serializable {
    private String type;
    private float x;
    private float y;
    private float angle;
    private boolean hasLaunched;

    public BirdData(String type, float x, float y, float angle, boolean hasLaunched) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.hasLaunched = hasLaunched;
    }

    public String getType() {
        return type;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getAngle() {
        return angle;
    }

    public boolean isHasLaunched() {
        return hasLaunched;
    }
}
