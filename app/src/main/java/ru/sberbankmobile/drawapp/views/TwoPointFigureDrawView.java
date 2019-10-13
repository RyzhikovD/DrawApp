package ru.sberbankmobile.drawapp.views;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import ru.sberbankmobile.drawapp.figures.TwoPointFigure;

public class TwoPointFigureDrawView extends View {

    private DrawView mDrawView;

    private boolean mIsBoxType;

    private Paint mBoxPaint = new Paint();
    private Paint mLinePaint = new Paint();

    private TwoPointFigure mCurrentFigure;

    private HashMap<TwoPointFigure, Integer> mColoredBoxes = new HashMap<>();
    private HashMap<TwoPointFigure, Integer> mColoredLines = new HashMap<>();

    public TwoPointFigureDrawView(Context context) {
        this(context, null, new DrawView(context));
    }

    public TwoPointFigureDrawView(Context context, DrawView drawView) {
        this(context, null, drawView);
    }

    public TwoPointFigureDrawView(Context context, @Nullable AttributeSet attrs, DrawView drawView) {
        super(context, attrs);

        mDrawView = drawView;
        setUpLinePaint();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        PointF current = new PointF(event.getX(), event.getY());
        int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mCurrentFigure = new TwoPointFigure(current);
                if (mIsBoxType) {
                    mColoredBoxes.put(mCurrentFigure, mBoxPaint.getColor());
                } else {
                    mColoredLines.put(mCurrentFigure, mBoxPaint.getColor());
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

    private void setUpLinePaint() {
        mLinePaint.setStrokeWidth(10f);
    }

    public Paint getBoxPaint() {
        return mBoxPaint;
    }

    public Paint getLinePaint() {
        return mLinePaint;
    }

    public Set<Map.Entry<TwoPointFigure, Integer>> getBoxSet() {
        return mColoredBoxes.entrySet();
    }

    public Set<Map.Entry<TwoPointFigure, Integer>> getLineSet() {
        return mColoredLines.entrySet();
    }

    public void selectBoxType() {
        mIsBoxType = true;
    }

    public void selectLineType() {
        mIsBoxType = false;
    }

    public void clear() {
        mColoredBoxes.clear();
        mColoredLines.clear();
    }
}
