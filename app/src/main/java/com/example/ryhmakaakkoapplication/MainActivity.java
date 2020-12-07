package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.ActionBar;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener, StepListener {
    public static final String EXTRA_MESSAGE = "blaa";
    Steps steps = new Steps();
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TAG = "MainActivity";
    private static final int RESET = 0;


    DatabaseHelper databaseHelper = new DatabaseHelper(MainActivity.this);
    SharedPreferences sharedpreferences;
    Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get an instance of the SensorManager
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        sensorManager.registerListener(MainActivity.this, accel, SensorManager.SENSOR_DELAY_FASTEST);

        TextView Steps = findViewById(R.id.stepcountView);
        sharedpreferences = getPreferences(MODE_PRIVATE);


        if(sharedpreferences.contains("steps")) {
            Gson gson = new Gson();
            String json = sharedpreferences.getString("steps", "");
            steps = gson.fromJson(json, Steps.class);
        }
        Steps.setText(Integer.toString(steps.value()));
        updateUI();

    }
    protected void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(steps);
        editor.putString("steps", json);
        editor.putInt("date", TimeStamp.date());
        editor.putInt("month", TimeStamp.month());
        editor.commit();
    }

    protected void onResume() {
        super.onResume();
        updateUI();
        TextView Steps = findViewById(R.id.tv_steps);
        if(TimeStamp.date() != sharedpreferences.getInt("date", 0 ) || TimeStamp.month() != sharedpreferences.getInt("month", 0)){
            Log.d(TAG, "Date Gotten" + sharedpreferences.getInt("date", 0));
            databaseHelper.addToStepCounter(steps.value());
            steps.reset();
            Steps.setText(Integer.toString(steps.value()));
            sensorManager.unregisterListener(MainActivity.this);

        } else{
            Log.d(TAG, "Date Same" + sharedpreferences.getInt("date", 0));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);

            TextView stepPercentageView = findViewById(R.id.stepPercentageView);
            ProgressBar progressBar = findViewById(R.id.progressBar);
            SharedPreferences sp =                                                             //Datan haku SharedPreferences
                    getSharedPreferences("Kaakko", Context.MODE_PRIVATE);

            int stepGoal = sp.getInt("stepGoal", 10000);
            stepPercentageView.setText(calculator.percentCalc(steps.value(), stepGoal) + "%");
            progressBar.setProgress(calculator.percentCalc(steps.value(),stepGoal));
            calculator.progressColor(stepGoal, steps.value(), progressBar);
        }
    }

    @Override
    public void step(long timeNs) {
        steps.add();
        TextView Steps = findViewById(R.id.stepcountView);
        Steps.setText(Integer.toString(steps.value()));
    }

    public void openDiary(View view)    {
        Intent intent = new Intent(this, activity_diary.class);
        startActivity(intent);
    }

    public void openNote(View view)    {
        Intent intent = new Intent(this, activity_diary_entry.class);
        startActivity(intent);
    }

    public void openGoal(View view) {
        Intent intent = new Intent(this, activity_goal_entry.class);
        startActivity(intent);
    }

    public void updateUI()    {
        int stepGoal;
        float minSugar, maxSugar;
        calculator = new Calculator();
        TextView sugarView = findViewById(R.id.sugarView);
        TextView sugarDateView = findViewById(R.id.sugarDateView);
        TextView differenceView = findViewById(R.id.differenceView);
        TextView stepPercentageView = findViewById(R.id.stepPercentageView);
        ProgressBar progressBar = findViewById(R.id.progressBar);

        ArrayList<String> latest = databaseHelper.getTwoLatest("DIARY");     //Datan haku tietokannasta
        SharedPreferences sp =                                                             //Datan haku SharedPreferences
                getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
        stepGoal = sp.getInt("stepGoal", 10000);
        minSugar = sp.getFloat("minSugar", 0);
        maxSugar = sp.getFloat("maxSugar", 0);

        if(!latest.isEmpty())   {
            sugarDateView.setText(latest.get(0));
            sugarView.setText(latest.get(1));
            differenceView.setText(calculator.diffCalc(latest));
            calculator.sugarColor(minSugar, maxSugar, Double.parseDouble(latest.get(1)), sugarView);
        } else  {
            sugarDateView.setText("Ei merkintöjä");
            sugarView.setText("0");
        }

        stepPercentageView.setText(calculator.percentCalc(steps.value(), stepGoal) + "%");
        progressBar.setProgress(calculator.percentCalc(steps.value(),stepGoal));
        calculator.progressColor(stepGoal, steps.value(), progressBar);
    }

}