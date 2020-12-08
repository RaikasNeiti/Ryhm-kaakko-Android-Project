package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * activity_diary_entry -luokassa päiväkirjaan lisätään merkintä
 * @author Olli Kolkki, Felix Uimonen, Joni Tahvanainen, Teemu Olkkonen
 * @version 1.0 12/2020
 */

public class activity_diary_new extends AppCompatActivity {       // tehty https://www.youtube.com/watch?v=aQAIMY-HzL8 ohjeen pohjalta

    DatabaseHelper mDatabaseHelper;
    private boolean insertData;

    /*
     * Funktio, joka kutsutaan kun aktiviteetti luodaan.
     * @param savedInstanceState = referenssi Bundle-objektiin, joka annetaan onCreate-funktiolle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_new);
        mDatabaseHelper = new DatabaseHelper(this);
    }

    /**
     * kutsutaan lähetetään tiedot tietokantaan
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
    public void sendEntry(View view)    {
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);

        String input = editText.getText().toString();
        String input2 = editText2.getText().toString();

        if (input.length() > 0 && input2.length() <= 50 && input2.length() > 0) {
            addData(input, input2);
            Toast.makeText(getApplicationContext(),input + " mmol/L lisätty päiväkirjaan",Toast.LENGTH_SHORT).show();
            Log.d("save", "save success");
        } else if (input.length() > 0 && input2.length() <= 0)  {
            addData(input, "Ei lisätietoja");
            Log.d("save", "no info");
            Toast.makeText(getApplicationContext(),input + " mmol/L lisätty päiväkirjaan",Toast.LENGTH_SHORT).show();
        } else  {
            Log.d("save", "save failed");
            Toast.makeText(getApplicationContext(),"Tallennus epäonnistui. Kenttien arvot ovat puutteeliset",Toast.LENGTH_SHORT).show();
        }

    }
}