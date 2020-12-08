package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * activity_entry_details -Luokassa näytetään tarkempia tietoja käyttäjän valitsemasta listamerkinnästä
 * @author Olli Kolkki, Felix Uimonen, Joni Tahvanainen, Teemu Olkkonen
 * @version 1.0 12/2020
 */

public class activity_entry_details extends AppCompatActivity {

    private Calculator calculator;
    private int id;
    DatabaseHelper databaseHelper = new DatabaseHelper(activity_entry_details.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry_details);
        updateUI();
    }


    public void updateUI()    {
        float minSugar, maxSugar;
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        id = extras.getInt("EXTRA_ID");
        calculator = new Calculator();
        TextView sugarView = findViewById(R.id.sugarView2);
        TextView sugarDateView = findViewById(R.id.sugarDateView2);
        TextView differenceView = findViewById(R.id.differenceView2);
        id++;
        ArrayList<String> latest = databaseHelper.getPrevious("DIARY", id);
        SharedPreferences sp =
                getSharedPreferences("Kaakko", Context.MODE_PRIVATE);
        minSugar = sp.getFloat("minSugar", 0);
        maxSugar = sp.getFloat("maxSugar", 0);

        if(!latest.isEmpty())   {
            sugarDateView.setText(latest.get(0));
            sugarView.setText(latest.get(1));

            Log.d("asdasd1", latest.get(1));
            Log.d("asdasd2", latest.get(2));


            differenceView.setText("+/- " + Math.abs(Float.parseFloat(latest.get(2)) - Float.parseFloat(latest.get(1))));
            calculator.sugarColor(minSugar, maxSugar, Double.parseDouble(latest.get(1)), sugarView);
        } else  {
            sugarDateView.setText("Ei merkintöjä");
            sugarView.setText("0");
        }

    }


}