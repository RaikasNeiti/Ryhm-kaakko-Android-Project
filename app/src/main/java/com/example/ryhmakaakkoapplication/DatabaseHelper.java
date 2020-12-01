package com.example.ryhmakaakkoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String STEPCOUNTER = "STEPCOUNTER";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "stepcounter.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + STEPCOUNTER + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DAY INTEGER, MONTH INTEGER, STEPS INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addToDB(int steps){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DAY", 23);
        cv.put("MONTH", 11);
        cv.put("STEPS", steps);

        long insert = db.insert("STEPCOUNTER", null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }
}
