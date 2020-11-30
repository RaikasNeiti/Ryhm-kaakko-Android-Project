package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;


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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    private TextView textView;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private int monthNumber;
    private int formattedDate;
    private static final String TAG = "MainActivity";
    private static final String TAG2 = "MainActivity";
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
        editor.putInt("date", formattedDate);
        editor.putInt("month", monthNumber);
        editor.commit();
    }

    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(steps);
        editor.putString("steps", json);
        editor.putInt("date", formattedDate);
        editor.putInt("month", monthNumber);
        editor.commit();
    }


    protected void onResume() {
        super.onResume();

        //Date
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat date = new SimpleDateFormat("dd", Locale.getDefault());
        formattedDate = Integer.parseInt(date.format(c));
        SimpleDateFormat month = new SimpleDateFormat("MMM", Locale.getDefault());
        String formattedMonth = month.format(c);

        //Changing the month to a int number
        if (formattedMonth.equals("Jan")) {
            monthNumber = 1;
        } else if (formattedMonth.equals("Feb")) {
            monthNumber = 2;
        } else if (formattedMonth.equals("Mar")) {
            monthNumber = 3;
        } else if (formattedMonth.equals("Apr")) {
            monthNumber = 4;
        } else if (formattedMonth.equals("May")) {
            monthNumber = 5;
        } else if (formattedMonth.equals("Jun")) {
            monthNumber = 6;
        } else if (formattedMonth.equals("Jul")) {
            monthNumber = 7;
        } else if (formattedMonth.equals("Aug")) {
            monthNumber = 8;
        } else if (formattedMonth.equals("Sep")) {
            monthNumber = 9;
        } else if (formattedMonth.equals("Oct")) {
            monthNumber = 10;
        } else if (formattedMonth.equals("Nov")) {
            monthNumber = 11;
        } else if (formattedMonth.equals("Dec")) {
            monthNumber = 12;
        }
        SimpleDateFormat year = new SimpleDateFormat("yyyy", Locale.getDefault());
        int formattedYear = Integer.parseInt(date.format(c));

        Log.d(TAG, "Date: " + formattedMonth);
        Log.d(TAG, "Date: " + monthNumber);

        //datesent to database

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
        Log.d(TAG2, "Stepcount: " + steps.steps());
    }
}
