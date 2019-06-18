package com.kv.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.Image;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    ImageView imageView;
    View relativeLayout;
    SensorManager sensorManager;
    Sensor sensor_accelerometer, sensor_proximity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initSensor();
    }

    private void initView() {
        imageView = findViewById(R.id.iv);
        relativeLayout = findViewById(R.id.rl_parent);
    }

    private void initSensor() {
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensor_accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensor_proximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        if (sensor_accelerometer == null) {
            Log.e("Sensor", "No Accelerometer Sensor!");
        } else {
            Log.e("Sensor", "Accelerometer Sensor!");
            sensorManager.registerListener(this, sensor_accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (sensor_proximity == null) {
            Log.e("Sensor", "No Proximity Sensor!");

        } else {
            Log.e("Sensor", "Proximity Sensor!");
            sensorManager.registerListener(this, sensor_proximity, SensorManager.SENSOR_DELAY_NORMAL);
        }

        Sensor TYPE_GYROSCOPE = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Log.d("TYPE_GYROSCOPE", TYPE_GYROSCOPE + "");

        Sensor TYPE_LIGHT = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        Log.d("TYPE_LIGHT", TYPE_LIGHT + "");

        Sensor TYPE_MAGNETIC_FIELD = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Log.d("TYPE_MAGNETIC_FIELD", TYPE_MAGNETIC_FIELD + "");

        Sensor TYPE_MOTION_DETECT = sensorManager.getDefaultSensor(Sensor.TYPE_MOTION_DETECT);
        Log.d("TYPE_MOTION_DETECT", TYPE_MOTION_DETECT + "");

        Sensor TYPE_GRAVITY = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
        Log.d("TYPE_GRAVITY", TYPE_GRAVITY + "");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_PROXIMITY) {
            if (event.values[0] == 0) {
                //Near
                Log.d("Proximity", "Near");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    relativeLayout.setBackgroundColor(getColor(R.color.black));
                }

            } else {
                //Away
                Log.d("Proximity", "Away");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    relativeLayout.setBackgroundColor(getColor(R.color.blue));
                }
            }

        } else if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            int x = (int) event.values[0];
            if (x > 0) {
                Log.d("Accelerometer", "Left " + x);
                imageView.setPadding(x, 0, -x, 0);
            } else {
                Log.d("Accelerometer", "Right " + x);
                imageView.setPadding(x, 0, Math.abs(x), 0);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

}
