package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * activity_diary_new -luokassa päiväkirjaan lisätään merkintä
 * @author Olli Kolkki, Felix Uimonen, Joni Tahvanainen, Teemu Olkkonen
 * @version 1.0 12/2020
 */

public class activity_diary_new extends AppCompatActivity {       // tehty https://www.youtube.com/watch?v=aQAIMY-HzL8 ohjeen pohjalta

    DatabaseHelper mDatabaseHelper;
    private boolean insertData;
    private EditText editText;
    private EditText editText2;

    /*
     * Funktio, joka kutsutaan kun aktiviteetti luodaan.
     * @param savedInstanceState = referenssi Bundle-objektiin, joka annetaan onCreate-funktiolle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_new);
        mDatabaseHelper = new DatabaseHelper(this);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);

    }

    /**
     * kutsutaan kun halutaa lähettää tiedot tietokantaan
     * @param sugarEntry saadut tiedot
     */
    public void addData(String sugarEntry, String noteEntry)    {
        insertData = mDatabaseHelper.addToDiary(sugarEntry, noteEntry, "DIARY");
        if (!insertData) {
            Toast.makeText(getApplicationContext(),"Datan syöttö epäonnistui",Toast.LENGTH_SHORT).show();
        }   else    {
            Toast.makeText(getApplicationContext(),"Datan syöttö onnistui",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * kutsutaan, kun käyttäjä painaa "tallenna" nappia
     * @param view napin view-elementti
     */
    public void buttonPressed(View view)    {

        String input = editText.getText().toString();
        String input2 = editText2.getText().toString();

        if (input.length() > 0 && input2.length() <= 50 && input2.length() > 0) {
            addData(input, input2);
            Toast.makeText(getApplicationContext(),input + " mmol/L lisätty päiväkirjaan",Toast.LENGTH_SHORT).show();
        } else if (input.length() > 0 && input2.length() <= 0)  {
            addData(input, "Ei lisätietoja");
            Toast.makeText(getApplicationContext(),input + " mmol/L lisätty päiväkirjaan",Toast.LENGTH_SHORT).show();
        } else  {
            Toast.makeText(getApplicationContext(),"Tallennus epäonnistui. Kenttien arvot ovat puutteeliset",Toast.LENGTH_SHORT).show();
        }

    }
}