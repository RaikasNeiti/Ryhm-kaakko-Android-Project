package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;

/**
 * activity_diary -luokka sisältää päiväkirjanäkymän päivittämiseen liittyvän koodin
 * @author Olli Kolkki, Felix Uimonen, Joni Tahvanainen, Teemu Olkkonen
 * @version 1.0 12/2020
 */

public class activity_diary extends AppCompatActivity {

    /*
     * Funktio, joka kutsutaan kun aktiviteetti luodaan. Sisältää kuuntelijan kalenterille,
     * jolla saadaan käyttäjän valitsema päivämäärä
     * @param savedInstanceState = referenssi Bundle-objektiin, joka annetaan onCreate-funktiolle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary);
        CalendarView calendar = findViewById(R.id.calendar);


        calendar.setOnDateChangeListener(
                new CalendarView
                        .OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(
                            CalendarView view,
                            int year,
                            int month,
                            int dayOfMonth)
                    {
                        month++;
                        katsoMerkinta(view, dayOfMonth, month, year);
                    }
                });
    }


    /**
     * Funktio, jolla lähetetään aikatietoja toiseen aktiviteettiin
     * @param view view-elementti
     * @param dayOfMonth ajankohta
     * @param month ajankohta
     * @param year ajankohta
     */
    public void katsoMerkinta(View view, int dayOfMonth, int month, int year)  {      //Hakee klikatun päivämäärän ja lähettää sen EntryActivityyn
        Intent intent = new Intent(this, activity_diary_day.class);
        intent.putExtra("EXTRA_DAY", dayOfMonth);
        intent.putExtra("EXTRA_MONTH", month);
        intent.putExtra("EXTRA_YEAR", year);
        startActivity(intent);
    }

}