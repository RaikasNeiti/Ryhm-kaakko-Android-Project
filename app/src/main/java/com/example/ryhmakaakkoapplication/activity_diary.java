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
                (view, year, month, dayOfMonth) -> {
                    month++;
                    katsoMerkinta(view, dayOfMonth, month, year);

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