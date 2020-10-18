package tech.szymanskazdrzalik.zad_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

public class DrawActivity extends AppCompatActivity {
    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        drawView = (DrawView) findViewById(R.id.drawView);
    }

    public void eraseButtonOnClick(View v) {
        this.drawView.erase();
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

}