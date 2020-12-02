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

public class activity_entry_display extends AppCompatActivity {
    static final String EXTRA_ENTRY_INDEX = "com.example.RyhmaKaakko_Application.EXTRA_PRESIDENT_INDEX";


    private int dayOfMonth;
    private int month;
    private int year;
    private int monthData;
    private int dayData;
    private ListView entriesListView;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_display);
        entriesListView = findViewById(R.id.entriesListView);

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
        while(data.moveToNext())    {
            dayData = data.getInt(1);
            monthData = data.getInt(2);
            listData.add(data.getString(3));
        }
        if((monthData == month) && (dayData == dayOfMonth)) {       //jos tietokannan timestamp on sama kuin klikatun
            entriesListView.setAdapter(new ArrayAdapter<>(          //päivämäärän
                    this,
                    R.layout.entry_item_layout,
                    listData
            ));
        } else  {
            Log.d("db", "päivämäärällä ei merkintöjä");
        }
    }


}