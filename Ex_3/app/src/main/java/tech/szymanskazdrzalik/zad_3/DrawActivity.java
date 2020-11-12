package tech.szymanskazdrzalik.zad_3;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

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

    public void sizeButtonOnClick(View v) {
        this.showDialog();
    }

    public void savePictureButtonOnClick(View v) {
        this.drawView.chooseFileName();
        try {
            this.drawView.savePicture();
        } catch (IOException e) {
            Toast.makeText(this, "Unable to save picture", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(this, "Saved Picture to Gallery", Toast.LENGTH_SHORT).show();
    }

    private void showDialog() {
        final Dialog d = new Dialog(DrawActivity.this);
        d.setTitle("NumberPicker");
        d.setContentView(R.layout.dialog);
        Button b1 = (Button) d.findViewById(R.id.buttonSet);
        Button b2 = (Button) d.findViewById(R.id.buttonCancel);
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
        np.setMaxValue(100);
        np.setMinValue(0);
        np.setWrapSelectorWheel(true);
        b1.setOnClickListener(v -> {
            drawView.setThickness(np.getValue());
            d.dismiss();
        });
        b2.setOnClickListener(v -> d.dismiss());
        d.show();
    }

}