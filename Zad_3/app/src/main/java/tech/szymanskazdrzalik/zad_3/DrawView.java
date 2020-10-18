package tech.szymanskazdrzalik.zad_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View implements View.OnTouchListener {

    private final List<CustomPoint> pointList = new ArrayList<>();

    private int chosenColour = Color.RED;
    private int radius = 30;
    private boolean isBlurred = false;

    private Bitmap mBitmap;
    private Canvas mCanvas;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    private class CustomPoint {

        private Point point;
        private int radius;
        private Paint paint;

        public CustomPoint(Point point, int radius, Paint paint) {
            this.point = point;
            this.radius = radius;
            this.paint = paint;
        }

        public Point getPoint() {
            return point;
        }

        public int getRadius() {
            return radius;
        }

        public Paint getPaint() {
            return paint;
        }
    }

    /**
     * {@inheritDoc}
     */
    public DrawView(Context context) {
        super(context);
        initView();
    }

    /**
     * {@inheritDoc}
     */
    public DrawView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    /**
     * {@inheritDoc}
     */
    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /**
     * {@inheritDoc}
     */
    public DrawView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        this.addPoint(event);
        invalidate();
        return true;
    }

    private void addPoint(MotionEvent event) {
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();
        Paint paint = new Paint();
        paint.setColor(this.chosenColour);
        if (this.isBlurred) {
            paint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
        }
        CustomPoint customPoint = new CustomPoint(point, radius, paint);
        this.mCanvas.drawCircle(customPoint.getPoint().x, customPoint.getPoint().y, customPoint.getRadius(), customPoint.getPaint());
        pointList.add(customPoint);
    }

    public void erase() {
        this.mBitmap.eraseColor(Color.TRANSPARENT);
        this.invalidate();
    }

    public void setBlur() {
        this.isBlurred = true;
    }

    public void setNormal() {
        this.isBlurred = false;
    }

    public void setChosenColour(int colour) {
        this.chosenColour = colour;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
