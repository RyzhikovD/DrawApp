package ru.sberbankmobile.drawapp.figures;

import android.graphics.PointF;

public class TwoPointFigure {

    private PointF mOrigin = new PointF();
    private PointF mCurrent = new PointF();

    public TwoPointFigure(PointF origin) {
        mOrigin.x = origin.x;
        mOrigin.y = origin.y;
        mCurrent.x = origin.x;
        mCurrent.y = origin.y;
    }

    public void setCurrent(PointF current) {
        mCurrent.x = current.x;
        mCurrent.y = current.y;
    }

    public void setOrigin(PointF origin) {
        mOrigin.x = origin.x;
        mOrigin.y = origin.y;
    }

    public PointF getCurrent() {
        return mCurrent;
    }

    public PointF getOrigin() {
        return mOrigin;
    }
}
