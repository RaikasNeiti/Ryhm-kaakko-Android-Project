package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;


/**
 * activity_entry_details -Luokassa näytetään tarkempia tietoja käyttäjän valitsemasta listamerkinnästä
 * @author Olli Kolkki, Felix Uimonen, Joni Tahvanainen, Teemu Olkkonen
 * @version 1.0 12/2020
 */

public class activity_diary_details extends AppCompatActivity {

    private Calculator calculator;
    private int id;
    private TextView sugarView;
    private TextView sugarDateView;
    private TextView differenceView;
    private TextView detailsView;

    /*
     * Funktio, joka kutsutaan kun aktiviteetti luodaan.
     * @param savedInstanceState = referenssi Bundle-objektiin, joka annetaan onCreate-funktiolle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_details);
        sugarView = findViewById(R.id.sugarView2);
        sugarDateView = findViewById(R.id.sugarDateView2);
        differenceView = findViewById(R.id.differenceView2);
        detailsView = findViewById(R.id.detailsView);
        updateUI();
    }
    /**
     * Haetaan klikatun merkinnän tiedot ja muutetaan ne takaisin ArrayListiksi.
     * Päivitetään tiedoilla kentät.
     */

    public void updateUI()    {
        String doubleListjson;
        String listDatajson;
        String noteListjson;
        float minSugar, maxSugar;
        String dateData;
        ArrayList<Double> doubleList = new ArrayList<>();
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<String> noteList = new ArrayList<>();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = extras.getInt("EXTRA_ID");
        doubleListjson = extras.getString("EXTRA_DOUBLELIST");
        listDatajson = extras.getString("EXTRA_LISTDATA");
        noteListjson = extras.getString("EXTRA_NOTELIST");

        Gson gson = new Gson();
        Type listType = new TypeToken< ArrayList<Double> >(){}.getType();
        doubleList = gson.fromJson(doubleListjson, listType);

        listType = new TypeToken< ArrayList<String> >(){}.getType();
        listData = gson.fromJson(listDatajson, listType);

        listType = new TypeToken< ArrayList<String> >(){}.getType();
        noteList = gson.fromJson(noteListjson, listType);

        dateData = extras.getString("EXTRA_DATE");

        calculator = new Calculator();

        SharedPreferences sp =
                getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
        minSugar = sp.getFloat("minSugar", 0);
        maxSugar = sp.getFloat("maxSugar", 0);

        if(!doubleList.isEmpty())   {
            sugarDateView.setText(dateData);
            sugarView.setText(Double.toString(doubleList.get(id)));
            detailsView.setText(noteList.get(id));

            if (doubleList.size() != id+1) {
                differenceView.setText("+/- " + Math.round(  100.0 *   (Math.abs(doubleList.get(id) - doubleList.get(id + 1))))   /100.0  );
            } else{
                differenceView.setText("");
            }
            calculator.sugarColor(minSugar, maxSugar, doubleList.get(id), sugarView);
        } else  {
            sugarDateView.setText("Ei merkintöjä");
            sugarView.setText("0");
            detailsView.setText("");
        }

    }


}