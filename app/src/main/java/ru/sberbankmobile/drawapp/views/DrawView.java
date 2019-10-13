package ru.sberbankmobile.drawapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Map;

import ru.sberbankmobile.drawapp.figures.TwoPointFigure;

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

        int currentColor = mPathPaint.getColor();

        for (Map.Entry<TwoPointFigure, Integer> coloredBox : mTwoPointFigureDrawView.getBoxSet()) {
            TwoPointFigure box = coloredBox.getKey();
            float left = Math.min(box.getCurrent().x, box.getOrigin().x);
            float right = Math.max(box.getCurrent().x, box.getOrigin().x);
            float top = Math.min(box.getCurrent().y, box.getOrigin().y);
            float bottom = Math.max(box.getCurrent().y, box.getOrigin().y);

            mBoxPaint.setColor(coloredBox.getValue());
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }

        for (Map.Entry<TwoPointFigure, Integer> coloredLine : mTwoPointFigureDrawView.getLineSet()) {
            TwoPointFigure line = coloredLine.getKey();
            float startX = line.getOrigin().x;
            float startY = line.getOrigin().y;
            float stopX = line.getCurrent().x;
            float stopY = line.getCurrent().y;

            mLinePaint.setColor(coloredLine.getValue());
            canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
        }

        for (Map.Entry<Path, Integer> coloredPath : mPathDrawView.getPathSet()) {
            mPathPaint.setColor(coloredPath.getValue());
            canvas.drawPath(coloredPath.getKey(), mPathPaint);
        }

        setColor(currentColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mCurrentView.onTouchEvent(event);
    }

    public void selectPathView() {
        mCurrentView = mPathDrawView;
    }

    public void selectBoxView() {
        mTwoPointFigureDrawView.selectBoxType();
        mCurrentView = mTwoPointFigureDrawView;
    }

    public void selectLineView() {
        mTwoPointFigureDrawView.selectLineType();
        mCurrentView = mTwoPointFigureDrawView;
    }

    public void clear() {
        mPathDrawView.clear();
        mTwoPointFigureDrawView.clear();
    }

    public void setColor(int color) {
        mLinePaint.setColor(color);
        mBoxPaint.setColor(color);
        mPathPaint.setColor(color);
        invalidate();
    }
}
