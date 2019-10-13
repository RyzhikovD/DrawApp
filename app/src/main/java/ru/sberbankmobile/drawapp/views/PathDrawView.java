package ru.sberbankmobile.drawapp.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PathDrawView extends View {

    private DrawView mDrawView;

    private HashMap<Path, Integer> mColoredPaths = new HashMap<>();
    private Path mPath;
    private Paint mPaint = new Paint();

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
                mPath = new Path();
                mColoredPaths.put(mPath, mPaint.getColor());
                mPath.moveTo(x, y);
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mPath = null;
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(x, y);
                mDrawView.invalidate();
                break;
            default:
                return super.onTouchEvent(event);
        }

        return true;
    }

    private void setUpPaint() {
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10f);
        mPaint.setStyle(Paint.Style.STROKE);
    }

    public void clear() {
        mColoredPaths.clear();
    }

    public Paint getPaint() {
        return mPaint;
    }

    public Set<Map.Entry<Path, Integer>> getPathSet() {
        return mColoredPaths.entrySet();
    }
}
