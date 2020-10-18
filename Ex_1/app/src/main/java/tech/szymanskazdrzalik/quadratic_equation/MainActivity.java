package tech.szymanskazdrzalik.quadratic_equation;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private static DecimalFormat df2 = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.x_pow_2)).setText(Html.fromHtml(getString(R.string.x_squared_string)));
        ((TextView) findViewById(R.id.x_pow_1)).setText(Html.fromHtml(getString(R.string.x_power_1_string)));
        ((TextView) findViewById(R.id.equals)).setText(Html.fromHtml(getString(R.string.equals_0_string)));
    }

    public void calculateButtonOnClick(View v) {
        try {
            double first_val = Double.parseDouble(((EditText) findViewById(R.id.first_input)).getText().toString());
            double second_val = Double.parseDouble(((EditText) findViewById(R.id.second_input)).getText().toString());
            double third_val = Double.parseDouble(((EditText) findViewById(R.id.third_input)).getText().toString());
            TextView textView = (TextView) findViewById(R.id.final_text);
            if (first_val != 0) {
                double discriminant = (second_val * second_val) - 4 * first_val * third_val;
                double p = (- (second_val / (2 * first_val)));
                double q = (- (discriminant / (4 * first_val)));
                if (discriminant > 0) {
                    double result_1 = (-second_val - Math.sqrt(discriminant)) / (2 * first_val);
                    double result_2 = (-second_val + Math.sqrt(discriminant)) / (2 * first_val);
                    textView.setText(
                            String.format(getString(R.string.has_2_roots_result_string), df2.format(result_1),
                                    df2.format(result_2), df2.format(discriminant), df2.format(p), df2.format(q)));
                } else if (discriminant == 0) {
                    double result_1 = (-second_val) / (2 * first_val);
                    textView.setText(String.format(getString(R.string.has_one_root_result_string), df2.format(result_1), df2.format(discriminant), df2.format(p), df2.format(q)));
                    textView.setText(String.format(getString(R.string.has_no_roots_result_string), df2.format(discriminant), df2.format(p), df2.format(q)));
                }
            } else {
                textView.setText(R.string.not_quadratic_result_string);
            }
        } catch (NumberFormatException e) {
            Toast.makeText(this, R.string.invalid_input, Toast.LENGTH_SHORT).show();
        }
    }
}