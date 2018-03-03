package com.ntnu.imt3673.imt3673_lab3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    private CanvasView          canvas;
    private ArrayList           resources      = new ArrayList<Integer>();
    private Sensor              sensorAccel;
    private SensorManager       sensorManager;
    private AccelSensorListener sensorListener = new AccelSensorListener();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.canvas = new CanvasView(this);
        setContentView(this.canvas);

        this.initSensors();
        HapticFeedbackManager.init(this);
        this.initResources();
        SoundManager.init(this.resources, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.sensorManager.unregisterListener(this.sensorListener);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (this.sensorAccel == null) {
            Utils.alertMessage(getString(R.string.error_no_accel), this);
            return;
        }

        this.sensorManager.registerListener(
            this.sensorListener, this.sensorAccel, SensorManager.SENSOR_DELAY_GAME
        );
    }

    /**
     * Sets up the resources needed like sound effects.
     */
    private void initResources() {
        this.resources.add(R.raw.ping_001);
        this.resources.add(R.raw.ping_002);
        this.resources.add(R.raw.ping_003);
    }

    /**
     * Sets up the sensors and event listeners needed.
     */
    private void initSensors() {
        this.sensorManager = getSystemService(SensorManager.class);
        this.sensorAccel   = this.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * Accelerator Sensor Listener
     */
    private class AccelSensorListener implements SensorEventListener {

        /**
         * Handles sensor events when a sensor has changed.
         * SENSOR_DELAY_GAME: Updates roughly about 60 times a second.
         * @param sensorEvent The sensor event
         */
        @Override
        public void onSensorChanged(final SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                canvas.setSensorValues(sensorEvent.values);
                canvas.invalidate();
            }
        }

        /**
         * Not used, but must be overridden to implement abstract interface SensorEventListener.
         * @param sensor The sensor
         * @param delta The new accuracy
         */
        @Override
        public void onAccuracyChanged(final Sensor sensor, int delta) {
        }

    }

}
