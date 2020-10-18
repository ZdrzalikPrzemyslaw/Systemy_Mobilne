package tech.szymanskazdrzalik.zad_3;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;


public class DrawView extends View implements View.OnTouchListener {
    private boolean eraserMode = false;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private MaskFilter mEmboss;
    private MaskFilter mBlur;

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

    private void initPaint() {
        mPaint = new Paint();

        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(30);
        mEmboss = new EmbossMaskFilter(new float[]{1, 1, 1},
                0.4f, 6, 3.5f);


        mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        super.onSizeChanged(w, h, oldW, oldH);
        mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
    }

    private void initView() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        this.initPaint();
        this.mPath = new Path();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
                this.drawPath(event);
                break;
            case MotionEvent.ACTION_UP:
                this.drawPath(event);
                this.mPath.reset();
                break;
        }
        invalidate();
        return true;
    }

    private void drawPath(MotionEvent event) {
        this.mPath.lineTo(event.getX(), event.getY());
        this.mCanvas.drawPath(this.mPath, mPaint);
        if (this.mPaint.getMaskFilter() == this.mBlur) {
            this.mPath.reset();
            this.mPath.moveTo(event.getX(), event.getY());
        }
    }

    public void clearCanvas() {
        this.mBitmap.eraseColor(Color.TRANSPARENT);
        this.invalidate();
    }

    public void setBlur() {
        this.mPaint.setMaskFilter(this.mBlur);
        if (eraserMode)
            eraser();
    }

    public void setEmboss() {
        this.mPaint.setMaskFilter(this.mEmboss);
        if (eraserMode)
            eraser();
    }

    public void setNormal() {
        this.mPaint.setMaskFilter(null);
        if (eraserMode)
            eraser();
    }

    public void eraser() {
        if (!eraserMode) {
            this.mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
            this.eraserMode = true;
        } else {
            this.mPaint.setXfermode(null);
            this.eraserMode = false;
        }
    }

    public void setChosenColour(int colour) {
        this.mPaint.setColor(colour);
    }

    public void thicknessUp() {
        this.mPaint.setStrokeWidth(this.mPaint.getStrokeWidth() + 1);
    }

    public void setThickness(int val) {
        this.mPaint.setStrokeWidth(val);
    }

    public void thicknessDown() {
        this.mPaint.setStrokeWidth(this.mPaint.getStrokeWidth() - 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
