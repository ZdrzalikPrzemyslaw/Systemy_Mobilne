package tech.szymanskazdrzalik.zad_3;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentValues;
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
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;


public class DrawView extends View implements View.OnTouchListener {
    private final SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.US);
    private boolean eraserMode = false;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Path mPath;
    private Paint mPaint;
    private MaskFilter mEmboss;
    private MaskFilter mBlur;
    private String fileName;

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

        setmBlurValue();
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
        if (this.mPaint.getMaskFilter() != null && this.mPaint.getMaskFilter().getClass() == this.mBlur.getClass()) {
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

    public void savePicture() throws IOException {
        OutputStream fos;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            ContentResolver resolver = getContext().getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName + ".png");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(Objects.requireNonNull(imageUri));
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            File image = new File(imagesDir, fileName + ".png");
            fos = new FileOutputStream(image);
        }
        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        Objects.requireNonNull(fos).close();
    }

    public void chooseFileName() {
        final Dialog d = new Dialog((Activity)(getContext()));
        d.setTitle("Choose FileName");
        d.setContentView(R.layout.dialog_choose_filename);
        Button b1 = (Button) d.findViewById(R.id.buttonSetText);
        Button b2 = (Button) d.findViewById(R.id.buttonCancelText);
        final EditText editText = (EditText) d.findViewById(R.id.edit_text_file_name);
        editText.setText("Paint Picture " + formatter.format(new Date()) + " your paint application picture!");
        b1.setOnClickListener(v -> {
            d.dismiss();
            fileName = editText.getText().toString();
            System.out.println(fileName);
            try {
                savePicture();
            } catch (IOException e) {
                Toast.makeText(getContext(), "Unable to save picture", Toast.LENGTH_SHORT).show();
                return;
            }
            Toast.makeText(getContext(), "Saved Picture to Gallery", Toast.LENGTH_SHORT).show();
        });
        b2.setOnClickListener(v -> d.dismiss());
        d.show();
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
        System.out.println(String.format("0x%08X", colour));
        this.mPaint.setColor(colour);
    }

    private void setmBlurValue() {
        boolean setMask = false;
        if (this.mPaint.getMaskFilter() != null && this.mPaint.getMaskFilter().getClass() == this.mBlur.getClass())
            setMask = true;
        int blursize = (int) (mPaint.getStrokeWidth() / 2);
        if (blursize <= 0)
            blursize = 1;
        mBlur = new BlurMaskFilter(blursize, BlurMaskFilter.Blur.NORMAL);
        if (setMask)
            this.mPaint.setMaskFilter(mBlur);
    }

    public void setThickness(int val) {
        this.mPaint.setStrokeWidth(val);
        setmBlurValue();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }
}
