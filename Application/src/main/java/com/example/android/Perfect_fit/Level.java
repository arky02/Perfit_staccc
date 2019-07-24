package com.example.android.Perfect_fit;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Level extends AppCompatActivity {

    private SensorManager msensorManager = null;
    private Sensor mAccelometerSensor;
    private SensorEventListener mAcclis;
    private TextView text_degree;
    //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_camera2_basic);

        text_degree = findViewById(R.id.text_degree);

        msensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        mAccelometerSensor = msensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAcclis = new AocelometerListener();

        msensorManager.registerListener(mAcclis, mAccelometerSensor, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        msensorManager.unregisterListener(mAcclis);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        msensorManager.unregisterListener(mAcclis);
    }

    private class AocelometerListener implements SensorEventListener {

        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            double accY = sensorEvent.values[1];
            double accZ = sensorEvent.values[2];

            double angleYZ = Math.abs((Math.atan2(accY, accZ) * 180/Math.PI) - 90.0);

            text_degree.setText(String.format("%.1f°", angleYZ));
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

}
