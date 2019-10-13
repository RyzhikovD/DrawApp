package ru.sberbankmobile.drawapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class LineDrawView extends View {

    private DrawView mDrawView;

    private Paint mLinePaint = new Paint();
    private Box mCurrentBox;
    private List<Box> mBoxList = new ArrayList<>();

    public LineDrawView(Context context) {
        this(context, null, new DrawView(context));
    }

    public LineDrawView(Context context, DrawView drawView) {
        this(context, null, drawView);
    }

    public LineDrawView(Context context, @Nullable AttributeSet attrs, DrawView drawView) {
        super(context, attrs);

        mDrawView = drawView;
        setUpPaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentBox = new Box(current);
                mBoxList.add(mCurrentBox);
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentBox != null) {
                    mCurrentBox.setCurrent(current);
                    mDrawView.invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mCurrentBox = null;
                break;
        }
        return true;
    }

    public List<Box> getLines() {
        return mBoxList;
    }

    private void setUpPaint() {
        mLinePaint.setColor(Color.BLACK);
    }

    public Paint getPaint() {
        return mLinePaint;
    }

    public void clear() {
        mBoxList.clear();
        mDrawView.invalidate();
    }
}