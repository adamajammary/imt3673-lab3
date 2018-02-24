package com.ntnu.imt3673.imt3673_lab3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

/**
 * Main Activity
 */
public class MainActivity extends AppCompatActivity {

    private CanvasView canvas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.canvas = new CanvasView(MainActivity.this);
        setContentView(this.canvas);
    }

    /**
     * Canvas View - custom canvas view used for drawing 2D graphics.
     */
    private class CanvasView extends View {

        private RectF       borderPosition;
        private float       circleRadius;
        private PointF      circlePosition;
        private Paint       drawPen         = new Paint();
        private final float RECT_BORDER     = 5.0f;
        private final float RECT_MARGIN     = 50.0f;

        /**
         * Canvas View constructor - Sets the circle radius 5% of the display width.
         * @param context Context
         */
        public CanvasView(Context context) {
            super(context);
            setFocusable(true);

            Point displaySize = new Point();
            getWindowManager().getDefaultDisplay().getSize(displaySize);

            this.circleRadius = ((float)displaySize.x * 0.05f);
        }

        /**
         * Draw the border and circle on the canvas.
         * @param canvas Canvas
         */
        public void onDraw(Canvas canvas) {
            /**
             * Calculate the border and circle positions once based on the canvas.
             * Display dimensions are not guaranteed to be the same as drawable dimensions,
             * will depend on the device and how dimensions are calculated in relation to
             * top status bar, bottom navigation bar etc.
             */
            if (borderPosition == null) {
                borderPosition = new RectF(
                    RECT_MARGIN,
                    RECT_MARGIN,
                    (canvas.getWidth()  - RECT_MARGIN),
                    (canvas.getHeight() - RECT_MARGIN)
                );

                circlePosition = new PointF(borderPosition.centerX(), borderPosition.centerY());
            }

            // Set initial pen style
            this.drawPen.setAntiAlias(true);
            this.drawPen.setColor(Color.BLACK);
            this.drawPen.setStyle(Paint.Style.FILL);
            this.drawPen.setTextSize(30.0f);

            // Fill background
            this.drawPen.setStyle(Paint.Style.FILL);
            canvas.drawColor(Color.WHITE);

            // Draw border
            this.drawPen.setStyle(Paint.Style.STROKE);
            this.drawPen.setStrokeWidth(RECT_BORDER);
            canvas.drawRect(
                RECT_MARGIN, RECT_MARGIN,
                (canvas.getWidth() - RECT_MARGIN), (canvas.getHeight() - RECT_MARGIN),
                this.drawPen
            );

            // Draw circle
            this.drawPen.setStyle(Paint.Style.FILL);
            canvas.drawCircle(
                this.circlePosition.x, this.circlePosition.y, this.circleRadius, this.drawPen
            );
        }

    }

}
