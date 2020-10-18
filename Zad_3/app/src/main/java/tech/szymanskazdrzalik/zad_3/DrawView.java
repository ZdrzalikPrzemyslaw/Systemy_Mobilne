package tech.szymanskazdrzalik.zad_3;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class DrawView extends View implements View.OnTouchListener{

    private Paint paint = new Paint();
    private List<Point> pointList = new ArrayList<>();

    /**
     * {@inheritDoc}
     */
    public DrawView(Context context) {
        super(context);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setOnTouchListener(this);
        paint.setColor(Color.RED);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();
        pointList.add(point);
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        for(Point point: pointList) {
            canvas.drawCircle(point.x, point.y, 30, paint);
        }
    }
}
