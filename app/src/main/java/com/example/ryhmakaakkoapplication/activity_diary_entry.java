package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;


public class activity_diary_entry extends AppCompatActivity {       // tehty https://www.youtube.com/watch?v=aQAIMY-HzL8 ohjeen pohjalta

    private int dayOfMonth;
    private int month;
    private int year;
    private Entry entry;
    DatabaseHelper mDatabaseHelper;
    private boolean insertData;
    private Animation fade;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry);
        month = TimeStamp.month();
        dayOfMonth = TimeStamp.date();
        year = TimeStamp.year();
        mDatabaseHelper = new DatabaseHelper(this);



    }

    public void addData(String newEntry)    {
        insertData = mDatabaseHelper.addtoEntryDB(newEntry);
        if (insertData) {
            Log.d("db", "Datan syöttö onnistui");
        }   else    {
            Log.d("db", "Datan syöttö eopäonnistui");
        }
    }

    public void sendEntry(View view)    {
        EditText editText = (EditText) findViewById(R.id.editText);
        String input = TimeStamp.hour() + ":" + TimeStamp.minute() + " " + editText.getText().toString();
        addData(input);
        editText.setText("");
        if (editText.length() != 0) {
            Log.d("db", "Datan syöttö onnistui");
        } else  {
            Log.d("db", "Datan syöttö eopäonnistui");
        }
        entry = new Entry(input, dayOfMonth, month, year);
        EntryData.getInstance().getArray().add(entry);
    }
}