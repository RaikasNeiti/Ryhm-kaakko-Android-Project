package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
    private TextView bloodsugar;
    private TextView stepsView;
    private TextView bloodsugarTitle;
    DatabaseHelper mDatabaseHelper;
    private int stepcount;
    private int stepGoal;
    private float minSugar;
    private float maxSugar;
    private double roundedDouble = 0;
    private String timeData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_display);
        entriesListView = findViewById(R.id.entriesListView);
        bloodsugar = findViewById(R.id.bloodsugar);
        stepsView = findViewById(R.id.steps);
        bloodsugarTitle = findViewById(R.id.bloodsugarTitle);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        dayOfMonth = extras.getInt("EXTRA_DAY");
        month = extras.getInt("EXTRA_MONTH");
        year = extras.getInt("EXTRA_YEAR");
        mDatabaseHelper = new DatabaseHelper(this);
        updateListView();
        updateSteps();
        updateColor();

        entriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener()    {
            public void onItemClick(AdapterView <?>adapterView, View view, int i, long l)    {


            }
        });
    }

    private void updateListView() {
        Cursor data = mDatabaseHelper.getData("ENTRY_TABLE");
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<Double> doubleList = new ArrayList<>();
        data.moveToLast();
        for(int i = 0; i < mDatabaseHelper.countRows(); i++)    {//käydään läpi tietokanta keskiarvolaskuria ja oikean timestampin löytämistä varten
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
                bloodsugar.setText(Double.toString(avgCalculator(doubleList)));
                Log.d("db", "ka asetettu");
            } else  {
                Log.d("db", "ka nollattu");
                bloodsugar.setText("0");
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

    private void updateColor()  {       //säätää ympyröiden väriä tavoitteen mukaan
        SharedPreferences sp =
                getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
        stepGoal = sp.getInt("stepGoal", 10000);
        minSugar = sp.getFloat("minSugar", 0);
        maxSugar  = sp.getFloat("maxSugar", 14);
        StateListDrawable stepsViewBackground = (StateListDrawable) stepsView.getBackground();
        StateListDrawable sugarViewBackground = (StateListDrawable) bloodsugar.getBackground();

        if(stepcount < (stepGoal/2))   {
            stepsViewBackground.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
        } else if (stepcount < stepGoal) {
            stepsViewBackground.setColorFilter(Color.parseColor("#FFFF00"), PorterDuff.Mode.SRC_ATOP);
        } else  {
            stepsViewBackground.setColorFilter(Color.parseColor("#008000"), PorterDuff.Mode.SRC_ATOP);
        }

        if((roundedDouble <= maxSugar) && (roundedDouble >= minSugar))    {
            sugarViewBackground.setColorFilter(Color.parseColor("#008000"), PorterDuff.Mode.SRC_ATOP);
            } else  {
            sugarViewBackground.setColorFilter(Color.parseColor("#FF0000"), PorterDuff.Mode.SRC_ATOP);
        }
    }


    private double avgCalculator(ArrayList<Double> list)  {

        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum = sum + list.get(i);
        }
        double avg = sum / list.size();
        roundedDouble = Math.round(avg * 100.0) / 100.0;
        return roundedDouble;
    }




}