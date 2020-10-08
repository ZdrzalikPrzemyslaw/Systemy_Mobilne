package tech.szymanskazdrzalik.quadratic_equation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.x_pow_2)).setText(Html.fromHtml("<b>X<sup>2</sup></b> + "));
    }
}