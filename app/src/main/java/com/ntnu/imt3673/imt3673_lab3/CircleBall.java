package com.ntnu.imt3673.imt3673_lab3;

import android.graphics.PointF;

/**
 * Circle Ball
 */
public class CircleBall {

    private float  radius;
    private PointF position;
    private float  speed;

    public float getRadius() {
        return this.radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public PointF getPosition() {
        return this.position;
    }

    public void setPosition(PointF position) {
        this.position = position;
    }

    public float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

}
