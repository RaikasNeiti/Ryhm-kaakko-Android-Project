package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;



import java.util.ArrayList;
import java.util.Objects;

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
    DatabaseHelper mDatabaseHelper;
    private int i = 0;
    private double total = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_display);
        entriesListView = findViewById(R.id.entriesListView);
        bloodsugar = findViewById(R.id.bloodsugar);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        dayOfMonth = extras.getInt("EXTRA_DAY");
        month = extras.getInt("EXTRA_MONTH");
        year = extras.getInt("EXTRA_YEAR");
        mDatabaseHelper = new DatabaseHelper(this);
        populateListView();

        entriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener()    {
            public void onItemClick(AdapterView <?>adapterView, View view, int i, long l)    {


            }
        });
    }

    private void populateListView() {
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<Double> doubleList = new ArrayList<>();
        Log.d("listview", Integer.toString(mDatabaseHelper.countRows()));
        while(data.moveToNext())    {
            dayData = data.getInt(1);
            monthData = data.getInt(2);
            yearData = data.getInt(3);
            Log.d("listview2", Integer.toString(i));
            doubleList.add(Double.parseDouble(data.getString(4)));
            listData.add(TimeStamp.hour() + ":" + TimeStamp.minute() + " " + data.getString(4) + " mmol/l");
            i++;
        }
        if((monthData == month) && (dayData == dayOfMonth) && (yearData == year)) {       //jos tietokannan timestamp on sama kuin klikatun
            entriesListView.setAdapter(new ArrayAdapter<>(          //päivämäärän
                    this,
                    R.layout.entry_item_layout,
                    listData
            ));
        } else  {
            Log.d("db", "päivämäärällä ei merkintöjä");
        }
        bloodsugar.setText(Double.toString(avgCalculator(doubleList)));
    }


    private double avgCalculator(ArrayList<Double> list)  {

        double sum = 0;
        for (int i = 0; i < list.size(); i++) {
            sum = sum + list.get(i);
        }
        double avg = sum / list.size();
        double roundedDouble = Math.round(avg * 100.0) / 100.0;
        return roundedDouble;
    }


}