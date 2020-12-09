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
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * MainActivity-luokka sisältää UI-kenttien päivittämiseen liittyvän koodin
 * @author Olli Kolkki, Felix Uimonen, Joni Tahvanainen, Teemu Olkkonen
 * @version 2.0 3/2019
 */

public class activity_main extends AppCompatActivity implements SensorEventListener, StepListener {
    /**
     * Luodaan askeltunnistin
     */
    Steps steps = new Steps();
    private StepDetector simpleStepDetector;
    private SensorManager sensorManager;
    private Sensor accel;
    private static final String TAG = "MainActivity";
    private TextView sugarView;
    private TextView sugarDateView;
    private TextView differenceView;
    private TextView stepPercentageView;
    private ProgressBar progressBar;
    private TextView Steps;
    private Calculator calculator;



    DatabaseHelper databaseHelper = new DatabaseHelper(activity_main.this);
    SharedPreferences step = getSharedPreferences("Kaakko", MODE_PRIVATE);
    /*
     * Funktio, joka kutsutaan kun aktiviteetti luodaan.
     * @param savedInstanceState = referenssi Bundle-objektiin, joka annetaan onCreate-funktiolle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        calculator = new Calculator();
        sugarView = findViewById(R.id.sugarView);
        sugarDateView = findViewById(R.id.sugarDateView);
        differenceView = findViewById(R.id.differenceView);
        stepPercentageView = findViewById(R.id.stepPercentageView);
        progressBar = findViewById(R.id.progressBar);
        stepPercentageView = findViewById(R.id.stepPercentageView);
        Steps = findViewById(R.id.stepcountView);

        sensorManager.registerListener(activity_main.this, accel, SensorManager.SENSOR_DELAY_FASTEST);


        if(step.contains("steps")) {
            Gson gson = new Gson();
            String json = step.getString("steps", "");
            steps = gson.fromJson(json, Steps.class);
        }
        Steps.setText(Integer.toString(steps.value()));
        updateUI();

    }

    /**
     * Funktio tallentaa askeltietoja sovelluksen siirryttyä pause-tilaan
     */
    protected void onPause() {
        super.onPause();
        step = getSharedPreferences("Kaakko", MODE_PRIVATE);
        SharedPreferences.Editor editor = step.edit();
        Gson gson = new Gson();
        String json = gson.toJson(steps);
        editor.putString("steps", json);
        editor.putInt("date", TimeStamp.date());
        editor.putInt("month", TimeStamp.month());
        editor.commit();
    }

    /**
     * Kun sovellukseen palataan pause/destroyed tilasta, tämä funktio palauttaa tallennetut arvot.
     */
    protected void onResume() {
        super.onResume();
        updateUI();
        if(TimeStamp.date() != step.getInt("date", 0 ) || TimeStamp.month() != step.getInt("month", 0)){
            databaseHelper.addToStepCounter(steps.value(), step.getInt("date", 0), step.getInt("month",0));
            steps.reset();
            Steps.setText(Integer.toString(steps.value()));


            SharedPreferences sp =                                                             //Datan haku SharedPreferences
                    getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
            int stepGoal = sp.getInt("stepGoal", 10000);
            stepPercentageView.setText(calculator.percentCalc(steps.value(), stepGoal) + "%");
            progressBar.setProgress(Math.round(calculator.percentCalc(steps.value(), stepGoal)));
            calculator.progressColor(stepGoal, steps.value(), progressBar);

        }
    }

    /**
     * Kun sovellus sammutetaan tämä metodi varmistaa että askelmittaukseen tarvittavat sensorit sammutetaan.
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sensorManager.unregisterListener(activity_main.this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    /**
     * Kun sensori tunnistaa liikettä tämä methodi aktivoidaan.
     * @param event muuttuja sensorin muutokselle.
     */

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(
                    event.timestamp, event.values[0], event.values[1], event.values[2]);

            SharedPreferences sp =                                                             //Datan haku SharedPreferences
                    getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
            int stepGoal = sp.getInt("stepGoal", 10000);
            stepPercentageView.setText(calculator.percentCalc(steps.value(), stepGoal) + "%");
            progressBar.setProgress(Math.round(calculator.percentCalc(steps.value(), stepGoal)));
            calculator.progressColor(stepGoal, steps.value(), progressBar);


        }
    }

    /**
     * Päivittää askelkenttää lisäämällä yhden askeleen.
     */
    @Override
    public void step(long timeNs) {
        steps.add();
        Steps.setText(Integer.toString(steps.value()));
    }

    /**
     * Funktio joka kutsutaan kun nappuloita painetaan
     * @param view painettu view elementti
     */
    public void onButtonPressed(View view) {
        if (view == findViewById(R.id.diary)) {
            Intent intent = new Intent(this, activity_diary.class);
            startActivity(intent);
        } else if (view == findViewById(R.id.note)) {
            Intent intent = new Intent(this, activity_diary_new.class);
            startActivity(intent);
        } else if (view == findViewById(R.id.goalBtn)) {
            Intent intent = new Intent(this, activity_settings.class);
            startActivity(intent);
        }
    }




    /**
     * Päivittää askel-ja verensokerikenttien arvoja mitatulla tai päiväkirjaan merkityillä tuloksilla
     */
    public void updateUI()    {
        int stepGoal;
        float minSugar, maxSugar;

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
        progressBar.setProgress(Math.round(calculator.percentCalc(steps.value(), stepGoal)));
        calculator.progressColor(stepGoal, steps.value(), progressBar);
    }

}