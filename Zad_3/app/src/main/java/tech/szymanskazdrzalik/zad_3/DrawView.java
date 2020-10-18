package tech.szymanskazdrzalik.zad_3;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View implements View.OnTouchListener {

    private final Paint paintNormal = new Paint();
    private final Paint paintBlured = new Paint();
    private final List<Paint> paints = new ArrayList<>();

    private final List<Point> pointListNormal = new ArrayList<>();
    private final List<Point> pointListBlur = new ArrayList<>();
    private List<Point> chosenList = pointListNormal;
    private final List<List<Point>> allPointLists = new ArrayList<>();

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
        this.initPaints();
        this.initPointLists();
    }

    private void initPaints() {
        paints.add(this.paintNormal);
        paints.add(this.paintBlured);
        for (Paint i : paints) {
            i.setColor(Color.RED);
        }
        paintBlured.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));
    }

    private void initPointLists() {
        this.allPointLists.add(pointListBlur);
        this.allPointLists.add(pointListNormal);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();
        chosenList.add(point);
        invalidate();
        return true;
    }

    public void erase() {
        for (List<Point> i : this.allPointLists) {
            i.clear();
        }
        this.invalidate();
    }

    public void setBlur() {
        this.chosenList = this.pointListBlur;
    }

    public void setNormal() {
        this.chosenList = this.pointListNormal;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for (Point point : pointListNormal) {
            canvas.drawCircle(point.x, point.y, 30, paintNormal);
        }

        for (Point point : pointListBlur) {
            canvas.drawCircle(point.x, point.y, 30, paintBlured);
        }
    }
}
