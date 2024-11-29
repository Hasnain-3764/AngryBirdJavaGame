package com.badlogic.hassubhoi;

import java.io.Serializable;

public class PigData implements Serializable {
    private String type;
    private float x;
    private float y;
    private float angle;
    private int hitPoints;

    public PigData(String type, float x, float y, float angle, int hitPoints) {
        this.type = type;
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.hitPoints = hitPoints;
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

    public int getHitPoints() {
        return hitPoints;
    }
}
