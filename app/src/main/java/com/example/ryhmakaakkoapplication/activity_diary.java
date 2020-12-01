package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;

public class activity_diary extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "blaa";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        CalendarView calendar = findViewById(R.id.calendar);


        calendar.setOnDateChangeListener(
                new CalendarView
                        .OnDateChangeListener() {
                    @Override

                    // In this Listener have one method
                    // and in this method we will
                    // get the value of DAYS, MONTH, YEARS
                    public void onSelectedDayChange(
                            CalendarView view,
                            int year,
                            int month,
                            int dayOfMonth)
                    {

                        // Store the value of date with
                        // format in String type Variable
                        // Add 1 in month because month
                        // index is start with 0
                        String Date
                                = dayOfMonth + "-"
                                + (month + 1) + "-" + year;

                        // set this date in TextView for Display
                        String test = Integer.toString(dayOfMonth);


                        katsoMerkinta(view, dayOfMonth, month, year);

                    }
                });
    }

    public void katsoMerkinta(View view, int dayOfMonth, int month, int year)  {      //Hakee klikatun päivämäärän ja lähettää sen EntryActivityyn
        Intent intent = new Intent(this, activity_entry_display.class);
        intent.putExtra("EXTRA_DAY", dayOfMonth);
        intent.putExtra("EXTRA_MONTH", month);
        intent.putExtra("EXTRA_YEAR", year);
        startActivity(intent);

    }

}