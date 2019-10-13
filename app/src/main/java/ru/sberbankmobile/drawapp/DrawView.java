package ru.sberbankmobile.drawapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {

    private BoxDrawView mBoxDrawView;
    private PathDrawView mPathDrawView;
    private LineDrawView mLineDrawView;

    private View mCurrentView;

    private Paint mPathPaint;
    private Paint mBoxPaint;
    private Paint mLinePaint;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBoxDrawView = new BoxDrawView(context, this);
        mPathDrawView = new PathDrawView(context, this);
        mLineDrawView = new LineDrawView(context, this);

        mPathPaint = mPathDrawView.getPaint();
        mBoxPaint = mBoxDrawView.getPaint();
        mLinePaint = mLineDrawView.getPaint();

        selectPathView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (Box box : mBoxDrawView.getBoxes()) {
            float left = Math.min(box.getCurrent().x, box.getOrigin().x);
            float right = Math.max(box.getCurrent().x, box.getOrigin().x);
            float top = Math.min(box.getCurrent().y, box.getOrigin().y);
            float bottom = Math.max(box.getCurrent().y, box.getOrigin().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }

        for (Box box : mLineDrawView.getLines()) {
            float startX = box.getOrigin().x;
            float startY = box.getOrigin().y;
            float stopX = box.getCurrent().x;
            float stopY = box.getCurrent().y;

            canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
        }

        canvas.drawPath(mPathDrawView.getPath(), mPathPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mCurrentView.onTouchEvent(event);
    }

    public void selectPathView() {
        mCurrentView = mPathDrawView;
    }

    public void selectBoxView() {
        mCurrentView = mBoxDrawView;
    }

    public void selectLineView() {
        mCurrentView = mLineDrawView;
    }

    public void clear() {
        mPathDrawView.clear();
        mBoxDrawView.clear();
        mLineDrawView.clear();
    }
}
