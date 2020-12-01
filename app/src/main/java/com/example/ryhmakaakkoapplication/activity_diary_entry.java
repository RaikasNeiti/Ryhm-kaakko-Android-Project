package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.EditText;
import java.util.Calendar;
import java.util.TimeZone;
import android.widget.Toast;

public class activity_diary_entry extends AppCompatActivity {

    private static final String EXTRA_MESSAGE = "asdasd";
    private String glukoosi;
    private int dayOfMonth;
    private int month;
    private int year;
    private Entry entry;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry);

        calendar = Calendar.getInstance(TimeZone.getDefault());

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

    }

    public void sendEntry(View view)    {
        Intent intent = new Intent(this, MainActivity.class);
        EditText editText = (EditText) findViewById(R.id.editText);
        String input = editText.getText().toString();
        entry = new Entry(input, dayOfMonth, month, year);
        EntryData.getInstance().getArray().add(entry);
        startActivity(intent);
    }


}