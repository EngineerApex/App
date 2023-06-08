package com.example.final_test.CameraFragment;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class DashedBorderView extends View {

    private static final float BORDER_STROKE_WIDTH = 10f; // border stroke width in pixels
    private static final float BORDER_DASH_LENGTH = 30f; // length of each dash in the border
    private static final float BORDER_GAP_LENGTH = 10f; // length of gap between dashes in the border
    private static final int BORDER_COLOR = Color.WHITE; // color of the border

    private Paint borderPaint;

    public DashedBorderView(Context context) {
        super(context);
        init();
    }

    public DashedBorderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DashedBorderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint.setStyle(Paint.Style.STROKE);
        borderPaint.setColor(BORDER_COLOR);
        borderPaint.setStrokeWidth(BORDER_STROKE_WIDTH);
        borderPaint.setPathEffect(new DashPathEffect(new float[]{BORDER_DASH_LENGTH, BORDER_GAP_LENGTH}, 0f));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Get the dimensions of the view
        int width = getWidth();
        int height = getHeight();

        // Calculate the position and size of the border rect
        float left = (width - 1600f) / 2f;
        float top = (height - 1200f) / 2f;
        float right = left + 1600f;
        float bottom = top + 1200f;
        RectF borderRect = new RectF(left, top, right, bottom);

        // Draw the border rect
        canvas.drawRoundRect(borderRect, 16, 16, borderPaint);
    }
}