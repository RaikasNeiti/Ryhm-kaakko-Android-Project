package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;

public class activity_entry_display extends AppCompatActivity {
    static final String EXTRA_ENTRY_INDEX = "com.example.RyhmaKaakko_Application.EXTRA_PRESIDENT_INDEX";

    private int dayOfMonth;
    private int month;
    private int year;
    private int monthData;
    private int dayData;
    private int yearData;
    private ListView entriesListView;
    private TextView sugarView;
    private TextView stepsView;
    private TextView bloodsugarTitle;
    DatabaseHelper mDatabaseHelper;
    private int stepcount;
    private int stepGoal;
    private float minSugar;
    private float maxSugar;
    private double roundedDouble = 0;
    private String timeData;
    private Calculator calculator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_display);
        entriesListView = findViewById(R.id.entriesListView);
        sugarView = findViewById(R.id.bloodsugar);
        stepsView = findViewById(R.id.steps);
        bloodsugarTitle = findViewById(R.id.bloodsugarTitle);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        dayOfMonth = extras.getInt("EXTRA_DAY");
        month = extras.getInt("EXTRA_MONTH");
        year = extras.getInt("EXTRA_YEAR");
        mDatabaseHelper = new DatabaseHelper(this);
        this.calculator = new Calculator();
        updateListView();
        updateSteps();
        updateColor();
    }

    private void updateListView() {
        Cursor data = mDatabaseHelper.getData("ENTRY_TABLE");
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<Double> doubleList = new ArrayList<>();
        data.moveToLast();      //vaihdetaan tietokannan läpikäynti käänteiseksi, jotta listassa olisi aikajärjestys
        for(int i = 0; i < mDatabaseHelper.countRows("ENTRY_TABLE"); i++)    {//käydään läpi tietokanta keskiarvolaskuria ja oikean timestampin löytämistä varten
            dayData = data.getInt(1);
            monthData = data.getInt(2);
            yearData = data.getInt(3);
            timeData = data.getString(5);
            doubleList.add(Double.parseDouble(data.getString(4)));
            listData.add(dayData + "." + monthData + "." + yearData + " " + timeData + " " + data.getString(4) + " mmol/l");
            if((monthData == month) && (dayData == dayOfMonth) && (yearData == year)) {       //jos tietokannan timestamp on sama kuin klikatun
                entriesListView.setAdapter(new ArrayAdapter<>(          //päivämäärän
                        this,
                        R.layout.entry_item_layout,
                        listData
                ));
                sugarView.setText(Double.toString(calculator.avgCalc(doubleList)));
                Log.d("db", "ka asetettu");
            } else  {
                Log.d("db", "ka nollattu");
                sugarView.setText("0");
                bloodsugarTitle.setText("");
            }
            data.moveToPrevious();

        }
    }

    private void updateSteps()  {
        Cursor data = mDatabaseHelper.getData("STEPCOUNTER");

        while(data.moveToNext())    {                                                         //käydään läpi tietokanta keskiarvolaskuria ja oikean timestampin löytämistä varten
            dayData = data.getInt(1);
            monthData = data.getInt(2);
            yearData = data.getInt(3);

            if((monthData == month) && (dayData == dayOfMonth) && (yearData == year))   {
                stepcount = data.getInt(4);
                stepsView.setText(Integer.toString(stepcount));
            } else  {
                Log.d("db", "päivämäärällä ei askelmerkintöjä");
                stepsView.setText("0");
            }
        }
    }

    private void updateColor()  {
        SharedPreferences sp =
                getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
        stepGoal = sp.getInt("stepGoal", 10000);
        minSugar = sp.getFloat("minSugar", 0);
        maxSugar  = sp.getFloat("maxSugar", 14);
        calculator.colorCalc(stepGoal, stepcount, minSugar, maxSugar, roundedDouble, stepsView, sugarView);
    }






}