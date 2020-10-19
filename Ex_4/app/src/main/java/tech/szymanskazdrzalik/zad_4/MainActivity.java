package tech.szymanskazdrzalik.zad_4;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private static final int SHAKE_THRESHOLD = 3;
    private TextView mNumber;
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private int howManyDice = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumber = findViewById(R.id.number);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                this.handleAccelerometerEvent(event);
                break;
        }
    }

    private void handleAccelerometerEvent(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float acceleration = (float) Math.sqrt(x * x + y * y + z * z) - SensorManager.GRAVITY_EARTH;
        if (acceleration > SHAKE_THRESHOLD || acceleration < -SHAKE_THRESHOLD) {
            this.generateNumbersAndSetText();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void buttonOneOnClick(View v) {
        howManyDice = 1;
    }

    public void buttonTwoOnClick(View v) {
        howManyDice = 2;
    }

    public void buttonThreeOnClick(View v) {
        howManyDice = 3;
    }

    private void generateNumbersAndSetText() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_pictures);
        linearLayout.removeAllViews();
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1; i <= howManyDice; i++) {
            int randomNum = this.generateRandomNumber();
            stringBuilder.append("Value number ").append(i).append(" equals ").append(randomNum).append("\n");
            ImageView imageView = new ImageView(this);
            switch (randomNum) {
                case 1:
                    imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.dice_1));
                    break;
                case 2:
                    imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.dice_2));
                    break;
                case 3:
                    imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.dice_3));
                    break;
                case 4:
                    imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.dice_4));
                    break;
                case 5:
                    imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.dice_5));
                    break;
                case 6:
                    imageView.setBackground(ContextCompat.getDrawable(this, R.drawable.dice_6));
                    break;
            }
            linearLayout.addView(imageView);
        }
        String s = stringBuilder.toString();
        this.mNumber.setText(s);
    }

    private int generateRandomNumber() {
        Random randomGenerator = new Random();
        return randomGenerator.nextInt(6) + 1;
    }
}