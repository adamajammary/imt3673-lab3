package com.ntnu.imt3673.imt3673_lab3;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    private CanvasView          canvas;
    private Sensor              sensorAccel;
    private SensorManager       sensorManager;
    private AccelSensorListener sensorListener = new AccelSensorListener();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.canvas = new CanvasView(this);
        setContentView(this.canvas);

        this.initSensors();
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
            this.alertMessage(getString(R.string.error_no_accel));
            return;
        }

        this.sensorManager.registerListener(
            this.sensorListener, this.sensorAccel, SensorManager.SENSOR_DELAY_GAME
        );
    }

    /**
     * Helper utility - Displays the message in a pop-up alert dialog.
     * @param message Message to display
     */
    private void alertMessage(final String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle(R.string.app_name);
        dialog.setIcon(R.mipmap.ic_launcher);
        dialog.setMessage(message);
        dialog.setPositiveButton("OK", (d, i) -> d.dismiss());
        dialog.show();
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
