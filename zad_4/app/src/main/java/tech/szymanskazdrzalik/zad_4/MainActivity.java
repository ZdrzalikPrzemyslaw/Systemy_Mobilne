package tech.szymanskazdrzalik.zad_4;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 1; i <= howManyDice; i++) {
            stringBuilder.append("Value number ").append(i).append(" equals ").append(this.generateRandomNumber()).append("\n");
        }
        String s = stringBuilder.toString();
        this.mNumber.setText(s);
    }

    private int generateRandomNumber() {
        Random randomGenerator = new Random();
        int randomNum = randomGenerator.nextInt(6) + 1;
        return randomNum;
    }
}