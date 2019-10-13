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

public class TwoPointFigureDrawView extends View {

    private DrawView mDrawView;

    private boolean isBoxType;

    private Paint mBoxPaint = new Paint();
    private Paint mLinePaint = new Paint();

    private List<TwoPointFigure> mBoxList = new ArrayList<>();
    private List<TwoPointFigure> mLineList = new ArrayList<>();

    private TwoPointFigure mCurrentFigure;

    public TwoPointFigureDrawView(Context context) {
        this(context, null, new DrawView(context));
    }

    public TwoPointFigureDrawView(Context context, DrawView drawView) {
        this(context, null, drawView);
    }

    public TwoPointFigureDrawView(Context context, @Nullable AttributeSet attrs, DrawView drawView) {
        super(context, attrs);

        mDrawView = drawView;
        setUpBoxPaint();
        setUpLinePaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentFigure = new TwoPointFigure(current);
                if (isBoxType) {
                    mBoxList.add(mCurrentFigure);
                } else {
                    mLineList.add(mCurrentFigure);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mCurrentFigure != null) {
                    mCurrentFigure.setCurrent(current);
                    mDrawView.invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                mCurrentFigure = null;
                break;
        }
        return true;
    }

    private void setUpBoxPaint() {
        mBoxPaint.setColor(Color.BLACK);
    }

    private void setUpLinePaint() {
        mLinePaint.setColor(Color.GREEN);
        mLinePaint.setStrokeWidth(10f);
    }

    public Paint getBoxPaint() {
        return mBoxPaint;
    }

    public Paint getLinePaint() {
        return mLinePaint;
    }

    public List<TwoPointFigure> getBoxes() {
        return mBoxList;
    }

    public List<TwoPointFigure> getLines() {
        return mLineList;
    }

    public void setBoxType() {
        isBoxType = true;
    }

    public void setLineType() {
        isBoxType = false;
    }

    public void clear() {
        mBoxList.clear();
        mLineList.clear();
        mDrawView.invalidate();
    }
}
