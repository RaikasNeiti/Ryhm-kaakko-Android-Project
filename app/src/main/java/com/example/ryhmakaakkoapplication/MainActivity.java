package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.content.SharedPreferences;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    public static final String EXTRA_MESSAGE = "blaa";
    private TextView textView;
    Steps steps = new Steps();
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TEXT_NUM_STEPS = "Number of Steps: ";
    private static final int RESET = 0;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Databasetesting
        DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
        boolean test = databaseHelper.addToDB(21);
        Log.d("db", String.valueOf(test));
        //end
        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        TextView Steps = findViewById(R.id.tv_steps);
        Button BtnStart = findViewById(R.id.btn_start);
        Button BtnStop = findViewById(R.id.btn_stop);
        Button BtnSave = findViewById(R.id.btn_save);
        sharedpreferences = getPreferences(MODE_PRIVATE);
        if(sharedpreferences.contains("steps")) {
            Gson gson = new Gson();
            String json = sharedpreferences.getString("steps", "");
            steps = gson.fromJson(json, Steps.class);
        }
        Steps.setText(TEXT_NUM_STEPS + steps.steps());

        //startButton
        BtnStart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

            }
        });


        BtnStop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                steps.reset();
                Steps.setText(TEXT_NUM_STEPS + steps.steps());
                sensorManager.unregisterListener(MainActivity.this);
            }
        });

        BtnSave.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Steps.setText("Steps Saved");
                steps.reset();
                sensorManager.unregisterListener(MainActivity.this);


            }
        });



    }
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(steps);
        editor.putString("steps", json);
        editor.commit();
    }

    protected void onStart(){
        super.onStart();
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);
        }
    }

    @Override
    public void step(long timeNs) {
        TextView Steps = findViewById(R.id.tv_steps);
        steps.add();
        Steps.setText(TEXT_NUM_STEPS + steps.steps());
    }

    public void openDiary(View view)    {
        Intent intent = new Intent(this, activity_diary.class);
        startActivity(intent);
    }

    public void openNote(View view)    {
        Intent intent = new Intent(this, activity_diary_entry.class);
        startActivity(intent);
    }



}