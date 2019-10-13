package ru.sberbankmobile.drawapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class PathDrawView extends View {

    private Path mPath = new Path();
    private Paint mDotPaint = new Paint();
    private Paint mBackgroundPaint = new Paint();

    private DrawView mDrawView;

    public PathDrawView(Context context) {
        this(context, null, new DrawView(context));
    }

    public PathDrawView(Context context, DrawView drawView) {
        this(context, null, drawView);
    }

    public PathDrawView(Context context, @Nullable AttributeSet attrs, DrawView drawView) {
        super(context, attrs);

        mDrawView = drawView;
        setUpPaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        float x = event.getX();
        float y = event.getY();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                break;
            default:
                return super.onTouchEvent(event);
        }

        mDrawView.invalidate();
        return true;
    }

    private void setUpPaint() {
        mBackgroundPaint.setColor(Color.WHITE);

        mDotPaint.setColor(Color.RED);
        mDotPaint.setAntiAlias(true);
        mDotPaint.setStrokeWidth(10f);
        mDotPaint.setStyle(Paint.Style.STROKE);
    }

    public void clear() {
        mPath.reset();
        mDrawView.invalidate();
    }

    public Paint getPaint() {
        return mDotPaint;
    }

    public Path getPath() {
        return mPath;
    }
}
