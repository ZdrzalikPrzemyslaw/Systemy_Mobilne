package tech.szymanskazdrzalik.zad_3;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import top.defaults.colorpicker.ColorPickerPopup;

public class DrawActivity extends AppCompatActivity {
    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        drawView = (DrawView) findViewById(R.id.drawView);
    }

    public void clearCanvasButtonOnClick(View v) {
        this.drawView.clearCanvas();
    }

    public void setNormalButtonOnClick(View v) {
        this.drawView.setNormal();
    }

    public void setBlurButtonOnClick(View v) {
        this.drawView.setBlur();
    }

    public void setEmbossButtonOnClick(View v) {
        this.drawView.setEmboss();
    }

    public void eraserButtonOnClick(View v) {
        this.drawView.eraser();
    }

    public void colourButtonOnClick(View v) {
        new ColorPickerPopup.Builder(this)
                .initialColor(Color.RED) // Set initial color
                .enableBrightness(true) // Enable brightness slider or not
                .enableAlpha(true) // Enable alpha slider or not
                .okTitle("Choose")
                .cancelTitle("Cancel")
                .showIndicator(true)
                .showValue(true)
                .build()
                .show(v, new ColorPickerPopup.ColorPickerObserver() {
                    @Override
                    public void onColorPicked(int color) {
                        drawView.setChosenColour(color);
                    }
                });
    }

    public void hideButtonOnClick(View v) {
        if (findViewById(R.id.buttonsLinearLayout).getVisibility() == View.VISIBLE)
            findViewById(R.id.buttonsLinearLayout).setVisibility(View.INVISIBLE);
        else
            findViewById(R.id.buttonsLinearLayout).setVisibility(View.VISIBLE);
    }

    public void sizeUpButtonOnClick(View v) {
        this.drawView.thicknessUp();
    }

    public void sizeDownButtonOnClick(View v) {
        this.drawView.thicknessDown();
    }

}