package com.ntnu.imt3673.imt3673_lab3;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.view.View;

/**
 * Canvas View - Custom canvas view used for drawing 2D graphics.
 */
public class CanvasView extends View {

    private final float  RECT_BORDER  = 5.0f;
    private final float  RECT_MARGIN  = 50.0f;
    private RectF        borderPosition;
    private CircleBall   circleBall   = new CircleBall();
    private Paint        drawPen      = new Paint();
    private long         lastDrawTime = 0;
    private float[]      sensorValues = new float[3];

    /**
     * Canvas View constructor - Sets the circle radius 5% of the display width.
     * @param context Context
     */
    public CanvasView(Context context) {
        super(context);
        setFocusable(true);

        Point displaySize = new Point();
        ((Activity)context).getWindowManager().getDefaultDisplay().getSize(displaySize);

        this.circleBall.setRadius((float)displaySize.x * 0.05f);
        this.circleBall.setSpeed(50.0f);
    }

    /**
     * Draws the border and circle on the canvas.
     * @param canvas Canvas
     */
    protected void onDraw(final Canvas canvas) {
        long  currentTime = System.currentTimeMillis();
        float deltaTime   = (1.0f / (float)(currentTime - this.lastDrawTime));

        this.lastDrawTime = currentTime;

        if (this.borderPosition == null)
            this.initPosition(canvas);

        this.updateCirclePosition(deltaTime);

        canvas.drawColor(Color.WHITE);
        this.drawBorder(canvas);
        this.drawCircle(canvas);
    }

    /**
     * Draws the border.
     */
    private void drawBorder(final Canvas canvas) {
        this.drawPen.setAntiAlias(true);
        this.drawPen.setColor(Color.BLACK);
        this.drawPen.setStyle(Paint.Style.STROKE);
        this.drawPen.setStrokeWidth(RECT_BORDER);

        canvas.drawRect(
            RECT_MARGIN, RECT_MARGIN,
            (canvas.getWidth() - RECT_MARGIN), (canvas.getHeight() - RECT_MARGIN),
            this.drawPen
        );
    }

    /**
     * Draws the circle representing a ball.
     */
    private void drawCircle(final Canvas canvas) {
        PointF position = this.circleBall.getPosition();

        this.drawPen.setStyle(Paint.Style.FILL);
        canvas.drawCircle(position.x, position.y, this.circleBall.getRadius(), this.drawPen);
    }

    /**
     * Calculates the border and circle positions once based on the canvas.
     * Display dimensions are not guaranteed to be the same as drawable dimensions,
     * will depend on the device and how dimensions are calculated in relation to
     * top status bar, bottom navigation bar etc.
     */
    private void initPosition(final Canvas canvas) {
        this.borderPosition = new RectF(
            RECT_MARGIN, RECT_MARGIN,
            (canvas.getWidth() - RECT_MARGIN), (canvas.getHeight() - RECT_MARGIN)
        );

        this.circleBall.setPosition(
            new PointF(this.borderPosition.centerX(), this.borderPosition.centerY())
        );
    }

    /**
     * Updates the values for the accelerator sensor.
     * @param sensorValues New sensor values
     */
    public void setSensorValues(float[] sensorValues) {
        this.sensorValues = sensorValues;
    }

    /**
     * Updates the circle position based on accel sensor values.
     * Needs to swap X and Y because of landscape mode.
     */
    private void updateCirclePosition(float deltaTime) {
        PointF  oldPosition  = this.circleBall.getPosition();
        PointF  newPosition  = new PointF(oldPosition.x, oldPosition.y);
        float   speed        = (this.circleBall.getSpeed() * deltaTime);

        if (this.sensorValues[1] > 1.0f)
            newPosition.x = (oldPosition.x + speed);
        else if (this.sensorValues[1] < -1.0f)
            newPosition.x = (oldPosition.x - speed);

        if (this.sensorValues[0] > 1.0f)
            newPosition.y = (oldPosition.y + speed);
        else if (this.sensorValues[0] < -1.0f)
            newPosition.y = (oldPosition.y - speed);

        this.circleBall.setPosition(newPosition);
    }

}
