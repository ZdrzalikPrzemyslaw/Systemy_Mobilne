package tech.szymanskazdrzalik.quadratic_equation;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.x_pow_2)).setText(Html.fromHtml("<i><b>X<sup>2</sup></b></i> +"));
        ((TextView) findViewById(R.id.x_pow_1)).setText(Html.fromHtml("<i><b>X</b></i> +"));
        ((TextView) findViewById(R.id.equals)).setText(Html.fromHtml("<b>= 0</b>"));
    }

    public void calculateButtonOnClick(View v) {
        try {
            double first_val = Double.parseDouble(((EditText) findViewById(R.id.first_input)).getText().toString());
            double second_val = Double.parseDouble(((EditText) findViewById(R.id.second_input)).getText().toString());
            double third_val = Double.parseDouble(((EditText) findViewById(R.id.third_input)).getText().toString());
            TextView textView = (TextView) findViewById(R.id.final_text);
            if (first_val != 0) {
                double discriminant = (second_val * second_val) - 4 * first_val * third_val;
                if (discriminant > 0) {
                    double result_1 = (-second_val - Math.sqrt(discriminant)) / (2 * first_val);
                    double result_2 = (-second_val + Math.sqrt(discriminant)) / (2 * first_val);
                    textView.setText(String.format("The quadratic equation has two real roots, the first root is %s, the second root is %s. The square discriminant equals %s", df2.format(result_1), df2.format(result_2), df2.format(discriminant)));
                } else if (discriminant == 0) {
                    double result_1 = (-second_val) / (2 * first_val);
                    textView.setText(String.format("The quadratic equation has one real roots, the first root is %s. The square discriminant equals %s", df2.format(result_1), df2.format(discriminant)));
                } else {
                    textView.setText(String.format("The quadratic equation has no real roots. The square discriminant equals %s", df2.format(discriminant)));
                }
            } else {
                textView.setText("The equation doesn't represent a quadratic function");
            }
        } catch (NumberFormatException ignored) {
        }

    }
}