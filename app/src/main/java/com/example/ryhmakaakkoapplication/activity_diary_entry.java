package com.example.ryhmakaakkoapplication;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.Toast;


public class activity_diary_entry extends AppCompatActivity {       // tehty https://www.youtube.com/watch?v=aQAIMY-HzL8 ohjeen pohjalta

    DatabaseHelper mDatabaseHelper;
    private boolean insertData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_entry);
        mDatabaseHelper = new DatabaseHelper(this);
    }

    public void addData(String newEntry)    {
        insertData = mDatabaseHelper.addtoEntryDB(newEntry, "ENTRY_TABLE");
        if (insertData) {
            Log.d("db", "Datan syöttö onnistui");
        }   else    {
            Log.d("db", "Datan syöttö epäonnistui");
            Toast.makeText(getApplicationContext(),"Datan syöttö epäonnistui",Toast.LENGTH_SHORT).show();
        }
    }

    public void sendEntry(View view)    {
        EditText editText = (EditText) findViewById(R.id.editText);
        String input = editText.getText().toString();

        if (input.length() > 0) {
            addData(input);
            Log.d("db", "Datan syöttö onnistui");
            Toast.makeText(getApplicationContext(),input + " mmol/L lisätty päiväkirjaan",Toast.LENGTH_SHORT).show();
        } else  {
            Log.d("db", "Datan syöttö eopäonnistui");
            Toast.makeText(getApplicationContext(),"Tallennus epäonnistui. Merkinnän on oltava numero",Toast.LENGTH_SHORT).show();
        }
        editText.setText("");
    }
}