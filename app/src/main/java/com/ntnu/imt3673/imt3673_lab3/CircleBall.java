package com.ntnu.imt3673.imt3673_lab3;

import android.graphics.PointF;
import android.graphics.RectF;

/**
 * Circle Ball
 */
class CircleBall {

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

    private float getSpeed() {
        return this.speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Updates the circle position based on accel sensor values.
     * Needs to swap X and Y because of landscape mode.
     * @param deltaTime Draw Delta Time
     * @param border Border Position (edges/walls)
     * @param sensorValues Sensor Values
     */
    public void move(final float deltaTime, final RectF border, final float[] sensorValues) {
        int     collisionType = Constants.COLLISION_NONE;
        float   deltaX        = 0;
        float   deltaY        = 0;
        PointF  oldPosition   = this.getPosition();
        PointF  newPosition   = new PointF(oldPosition.x, oldPosition.y);
        float   radius        = this.getRadius();
        float   speed         = (this.getSpeed() * deltaTime);

        // Calculate movement direction based on sensor values
        if (sensorValues[1] > Constants.SENSOR_MIN_VALUE)
            deltaX = (speed + (sensorValues[1] * Constants.SENSOR_MULTIPLIER));
        else if (sensorValues[1] < -Constants.SENSOR_MIN_VALUE)
            deltaX = (-speed + (sensorValues[1] * Constants.SENSOR_MULTIPLIER));

        if (sensorValues[0] > Constants.SENSOR_MIN_VALUE)
            deltaY = (speed + (sensorValues[0] * Constants.SENSOR_MULTIPLIER));
        else if (sensorValues[0] < -Constants.SENSOR_MIN_VALUE)
            deltaY = (-speed + (sensorValues[0] * Constants.SENSOR_MULTIPLIER));

        // Check collision with edges
        if (oldPosition.x - radius + deltaX <= border.left) {
            collisionType = Constants.COLLISION_LEFT;
            deltaX += radius;
        } else if (oldPosition.x + radius + deltaX >= border.right) {
            collisionType = Constants.COLLISION_RIGHT;
            deltaX -= radius;
        }

        if (oldPosition.y - radius + deltaY <= border.top) {
            collisionType = Constants.COLLISION_TOP;
            deltaY       += radius;
        } else if (oldPosition.y + radius + deltaY >= border.bottom) {
            collisionType = Constants.COLLISION_BOTTOM;
            deltaY       -= radius;
        }

        // Handle collision by vibrating and playing a sound effect
        if (collisionType != Constants.COLLISION_NONE) {
            HapticFeedbackManager.vibrate(Constants.VIBRATION_DURATION_MS);
            SoundManager.playSound(R.raw.ping_001);
        }

        // Update new position
        newPosition.x += deltaX;
        newPosition.y += deltaY;

        this.setPosition(newPosition);
    }

}
