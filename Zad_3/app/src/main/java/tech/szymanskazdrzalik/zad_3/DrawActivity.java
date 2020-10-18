package tech.szymanskazdrzalik.zad_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DrawActivity extends AppCompatActivity {
    private DrawView drawView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawView = new DrawView(this);
        setContentView(drawView);
        drawView.requestFocus();
    }
}