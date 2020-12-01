package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import java.util.ArrayList;

public class activity_entry_display extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "asdasd";
    private int dayOfMonth;
    private int month;
    private int year;
    private Entry testEntry;
    private TextView entryText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_display);
        entryText = findViewById(R.id.entryText);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        dayOfMonth = extras.getInt("EXTRA_DAY");
        month = extras.getInt("EXTRA_MONTH");
        year = extras.getInt("EXTRA_YEAR");
        ArrayList list = EntryData.getInstance().getArray();

        for(int i = 0; i < list.size(); i++)    {
            testEntry = EntryData.getInstance().getArray().get(i);
            Log.d("diary1", Integer.toString(dayOfMonth));
            Log.d("diary2", Integer.toString(testEntry.getDayOfMonth()));
            if((testEntry.getMonth() == month) && (testEntry.getDayOfMonth() == dayOfMonth) && (testEntry.getYear() == year))  {
                entryText.setText(testEntry.getGlukoosi());



            } else  {
                entryText.setText("Ei merkintää!");
                Log.d("asd", "ei loydy");
            }
        }

    }
}