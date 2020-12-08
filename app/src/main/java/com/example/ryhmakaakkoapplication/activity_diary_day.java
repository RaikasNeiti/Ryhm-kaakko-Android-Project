package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * activity_diary_entry_display -luokassa näytetään tietoja valitun päivän merkinnöistä
 * @author Olli Kolkki, Felix Uimonen, Joni Tahvanainen, Teemu Olkkonen
 * @version 1.0 12/2020
 */

public class activity_diary_day extends AppCompatActivity {

    private int dayOfMonth;
    private int month;
    private int year;
    private int monthData;
    private int dayData;
    private ListView entriesListView;
    private TextView sugarView;
    private TextView stepsView;
    DatabaseHelper mDatabaseHelper;
    private int stepcount;
    private double roundedDouble = 0;
    private Calculator calculator;

    /*
     * Funktio, joka kutsutaan kun aktiviteetti luodaan.
     * @param savedInstanceState = referenssi Bundle-objektiin, joka annetaan onCreate-funktiolle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_day);
        entriesListView = findViewById(R.id.entriesListView);
        sugarView = findViewById(R.id.bloodsugar);
        stepsView = findViewById(R.id.steps);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        dayOfMonth = extras.getInt("EXTRA_DAY");
        month = extras.getInt("EXTRA_MONTH");
        year = extras.getInt("EXTRA_YEAR");
        mDatabaseHelper = new DatabaseHelper(this);
        this.calculator = new Calculator();
        UpdateListView();
        UpdateSteps();
        UpdateColor();
    }

    /**
     * Funktio listanäkymän päivittämistä varten
     * Aluksi haetaan tiedot tietokannasta, jos tietokannan timestamp
     * on sama kuin käyttäjän valitseman päivän, lista päivitetään sen päivän tiedoilla
     */
    private void UpdateListView() {
        Cursor data = mDatabaseHelper.getData("DIARY");
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<Double> doubleList = new ArrayList<>();
        ArrayList<String> noteList = new ArrayList<>();
        data.moveToLast();      //vaihdetaan tietokannan läpikäynti käänteiseksi, jotta listassa olisi aikajärjestys

        for(int i = 0; i < mDatabaseHelper.countRows("DIARY"); i++)    {//käydään läpi tietokanta keskiarvolaskuria ja oikean timestampin löytämistä varten
            dayData = data.getInt(1);
            monthData = data.getInt(2);
            int yearData = data.getInt(3);
            String timeData = data.getString(5);

            if((monthData == month) && (dayData == dayOfMonth) && (yearData == year)) {       //jos tietokannan timestamp on sama kuin klikatun päivämäärän
                doubleList.add(Double.parseDouble(data.getString(4)));
                listData.add(dayData + "." + monthData + "." + yearData + " " + timeData + " " + data.getString(4) + " mmol/L");
                noteList.add(data.getString(6));

                entriesListView.setAdapter(new ArrayAdapter<>(
                        this,
                        R.layout.entry_item_layout,
                        listData
                ));

                entriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    /**
                     * Funktio kutsutaan kun käyttäjä klikkaa list itemia
                     * @param parent
                     * @param view
                     * @param position
                     * @param id
                     */
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(activity_diary_day.this, activity_diary_details.class);

                        String doubleListjson = new Gson().toJson(doubleList);
                        String listDatajson = new Gson().toJson(listData);
                        String noteListjson = new Gson().toJson(noteList);

                        intent.putExtra("EXTRA_DOUBLELIST", doubleListjson);
                        intent.putExtra("EXTRA_LISTDATA", listDatajson);
                        intent.putExtra("EXTRA_NOTELIST", noteListjson);
                        intent.putExtra("EXTRA_ID", position);
                        startActivity(intent);
                    }
                });

                roundedDouble = calculator.avgCalc(doubleList);
                sugarView.setText(Double.toString(roundedDouble));
            }
            data.moveToPrevious();

        }

    }

    /**
     * Päivitetään näkymä päivän askelten määrällä, joka haetaan tietokannasta
     */
    private void UpdateSteps()  {
        Cursor data = mDatabaseHelper.getData("STEPCOUNTER");

        while(data.moveToNext())    {                                                         //käydään läpi tietokanta keskiarvolaskuria ja oikean timestampin löytämistä varten
            dayData = data.getInt(1);
            monthData = data.getInt(2);

            if((monthData == month) && (dayData == dayOfMonth))   {
                stepcount = data.getInt(3);
                stepsView.setText(Integer.toString(stepcount));
            } else  {
                stepsView.setText("0");
            }
        }
    }

    /**
     * Haetaan tiedot tiedot funktioille, jotka vaihtavat elementtien värejä.
     */
    private void UpdateColor()  {
        SharedPreferences sp =
                getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
        int stepGoal = sp.getInt("stepGoal", 10000);
        float minSugar = sp.getFloat("minSugar", 4);
        float maxSugar = sp.getFloat("maxSugar", 10);
        calculator.sugarColor(minSugar, maxSugar, roundedDouble, sugarView);
        calculator.stepsColor(stepGoal, stepcount, stepsView);
    }






}