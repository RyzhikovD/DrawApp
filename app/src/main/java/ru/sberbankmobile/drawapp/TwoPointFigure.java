package ru.sberbankmobile.drawapp;

import android.graphics.PointF;

public class TwoPointFigure {

    private PointF mOrigin;
    private PointF mCurrent;

    public TwoPointFigure(PointF origin) {
        mOrigin = origin;
        mCurrent = origin;
    }

    public PointF getOrigin() {
        return mOrigin;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public void setCurrent(PointF current) {
        mCurrent = current;
    }
}
