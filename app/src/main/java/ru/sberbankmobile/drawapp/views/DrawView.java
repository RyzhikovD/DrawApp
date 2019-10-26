package ru.sberbankmobile.drawapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;

import ru.sberbankmobile.drawapp.figures.DrawType;
import ru.sberbankmobile.drawapp.figures.TwoPointFigure;

public class DrawView extends View {

    private DrawType mCurrentType = DrawType.Path;

    private TwoPointFigure mCurrentFigure;
    private Path mPath;

    private HashMap<Path, Integer> mColoredPaths = new HashMap<>();
    private HashMap<TwoPointFigure, Integer> mColoredBoxes = new HashMap<>();
    private HashMap<TwoPointFigure, Integer> mColoredLines = new HashMap<>();

    private Paint mPathPaint = new Paint();
    private Paint mBoxPaint = new Paint();
    private Paint mLinePaint = new Paint();

    public DrawView(Context context) {
        this(context, null);
    }

    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setUpPaints();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int currentColor = mPathPaint.getColor();

        for (Map.Entry<TwoPointFigure, Integer> coloredBox : mColoredBoxes.entrySet()) {
            TwoPointFigure box = coloredBox.getKey();
            float left = Math.min(box.getCurrent().x, box.getOrigin().x);
            float right = Math.max(box.getCurrent().x, box.getOrigin().x);
            float top = Math.min(box.getCurrent().y, box.getOrigin().y);
            float bottom = Math.max(box.getCurrent().y, box.getOrigin().y);

            mBoxPaint.setColor(coloredBox.getValue());
            canvas.drawRect(left, top, right, bottom, mBoxPaint);
        }

        for (Map.Entry<TwoPointFigure, Integer> coloredLine : mColoredLines.entrySet()) {
            TwoPointFigure line = coloredLine.getKey();
            float startX = line.getOrigin().x;
            float startY = line.getOrigin().y;
            float stopX = line.getCurrent().x;
            float stopY = line.getCurrent().y;

            mLinePaint.setColor(coloredLine.getValue());
            canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
        }

        for (Map.Entry<Path, Integer> coloredPath : mColoredPaths.entrySet()) {
            mPathPaint.setColor(coloredPath.getValue());
            canvas.drawPath(coloredPath.getKey(), mPathPaint);
        }

        setColor(currentColor);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (mCurrentType) {
            case Box:
            case Line:
                PointF current = new PointF(event.getX(), event.getY());
                int action = event.getAction();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mCurrentFigure = new TwoPointFigure(current);
                        if (mCurrentType == DrawType.Box) {
                            mColoredBoxes.put(mCurrentFigure, mBoxPaint.getColor());
                        } else {
                            mColoredLines.put(mCurrentFigure, mLinePaint.getColor());
                        }
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mCurrentFigure != null) {
                            mCurrentFigure.setCurrent(current);
                            invalidate();
                        }
                        break;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mCurrentFigure = null;
                        break;
                }
                break;

            case Path:
                action = event.getAction();
                float x = event.getX();
                float y = event.getY();

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        mPath = new Path();
                        mColoredPaths.put(mPath, mPathPaint.getColor());
                        mPath.moveTo(x, y);
                        return true;
                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        mPath = null;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        mPath.lineTo(x, y);
                        invalidate();
                        break;
                    default:
                        return super.onTouchEvent(event);
                }
                break;
        }
        return true;
    }

    public void setDrawType(DrawType drawType) {
        mCurrentType = drawType;
    }

    public void clear() {
        mColoredPaths.clear();
        mColoredBoxes.clear();
        mColoredLines.clear();
        invalidate();
    }

    private void setUpPaints() {
        mLinePaint.setStrokeWidth(10f);
        mPathPaint.setAntiAlias(true);
        mPathPaint.setStrokeWidth(10f);
        mPathPaint.setStyle(Paint.Style.STROKE);
    }

    public void setColor(int color) {
        mLinePaint.setColor(color);
        mBoxPaint.setColor(color);
        mPathPaint.setColor(color);
    }
}
