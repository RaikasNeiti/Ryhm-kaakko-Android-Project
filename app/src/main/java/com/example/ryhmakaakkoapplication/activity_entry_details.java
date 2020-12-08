package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * activity_entry_details -Luokassa näytetään tarkempia tietoja käyttäjän valitsemasta listamerkinnästä
 * @author Olli Kolkki, Felix Uimonen, Joni Tahvanainen, Teemu Olkkonen
 * @version 1.0 12/2020
 */

public class activity_entry_details extends AppCompatActivity {

    private Calculator calculator;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);
        updateUI();
    }


    public void updateUI()    {
        String doubleListjson;
        String listDatajson;

        ArrayList<Double> doubleList = new ArrayList<>();
        ArrayList<String> listData = new ArrayList<>();
        float minSugar, maxSugar;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = extras.getInt("EXTRA_ID");
        doubleListjson = extras.getString("EXTRA_DOUBLELIST");
        listDatajson = extras.getString("EXTRA_LISTDATA");

        Gson gson = new Gson();
        Type listType = new TypeToken< ArrayList<Double> >(){}.getType();
        doubleList = gson.fromJson(doubleListjson, listType);

        listType = new TypeToken< ArrayList<String> >(){}.getType();
        listData = gson.fromJson(listDatajson, listType);


        calculator = new Calculator();
        TextView sugarView = findViewById(R.id.sugarView2);
        TextView sugarDateView = findViewById(R.id.sugarDateView2);
        TextView differenceView = findViewById(R.id.differenceView2);

        SharedPreferences sp =
                getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
        minSugar = sp.getFloat("minSugar", 0);
        maxSugar = sp.getFloat("maxSugar", 0);

        if(!doubleList.isEmpty())   {
            sugarDateView.setText(listData.get(id));
            sugarView.setText(Double.toString(doubleList.get(id)));


            differenceView.setText("+/- " + Math.abs(doubleList.get(id) - doubleList.get(id+1)));
            calculator.sugarColor(minSugar, maxSugar, doubleList.get(id), sugarView);
        } else  {
            sugarDateView.setText("Ei merkintöjä");
            sugarView.setText("0");
        }

    }


}