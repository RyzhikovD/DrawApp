package ru.sberbankmobile.drawapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawView extends View {

    private TwoPointFigureDrawView mTwoPointFigureDrawView;
    private PathDrawView mPathDrawView;

    private View mCurrentView;

    private Paint mPathPaint;
    private Paint mBoxPaint;
    private Paint mLinePaint;

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mTwoPointFigureDrawView = new TwoPointFigureDrawView(context, this);
        mPathDrawView = new PathDrawView(context, this);

        mPathPaint = mPathDrawView.getPaint();
        mBoxPaint = mTwoPointFigureDrawView.getBoxPaint();
        mLinePaint = mTwoPointFigureDrawView.getLinePaint();

        selectPathView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (TwoPointFigure box : mTwoPointFigureDrawView.getBoxes()) {
            float left = Math.min(box.getCurrent().x, box.getOrigin().x);
            float right = Math.max(box.getCurrent().x, box.getOrigin().x);
            float top = Math.min(box.getCurrent().y, box.getOrigin().y);
            float bottom = Math.max(box.getCurrent().y, box.getOrigin().y);

            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }

        for (TwoPointFigure line : mTwoPointFigureDrawView.getLines()) {
            float startX = line.getOrigin().x;
            float startY = line.getOrigin().y;
            float stopX = line.getCurrent().x;
            float stopY = line.getCurrent().y;

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
        mTwoPointFigureDrawView.setBoxType();
        mCurrentView = mTwoPointFigureDrawView;
    }

    public void selectLineView() {
        mTwoPointFigureDrawView.setLineType();
        mCurrentView = mTwoPointFigureDrawView;
    }

    public void clear() {
        mPathDrawView.clear();
        mTwoPointFigureDrawView.clear();
    }
}
