package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_entry_display extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "asdasd";
    private int dayOfMonth;
    private int month;
    private int year;
    private Entry testEntry;
    DatabaseHelper mDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_display);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        dayOfMonth = extras.getInt("EXTRA_DAY");
        month = extras.getInt("EXTRA_MONTH");
        year = extras.getInt("EXTRA_YEAR");
        ArrayList list = EntryData.getInstance().getArray();

        for(int i = 0; i < list.size(); i++)    {
            testEntry = EntryData.getInstance().getArray().get(i);
            if((testEntry.getMonth() == month) && (testEntry.getDayOfMonth() == dayOfMonth) && (testEntry.getYear() == year))  {
                mDatabaseHelper = new DatabaseHelper(this);
                populateListView();
            }
        }
    }

    private void populateListView() {
        Cursor data = mDatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext())    {
            listData.add(data.getString(1));
        }
        ListView entriesListView = findViewById(R.id.entriesListView);
        entriesListView.setAdapter(new ArrayAdapter<>(
                this,
                R.layout.entry_item_layout,
                listData
        ));



    }
}