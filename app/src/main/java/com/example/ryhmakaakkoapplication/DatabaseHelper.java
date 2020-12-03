package com.example.ryhmakaakkoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String STEPCOUNTER = "STEPCOUNTER";
    public static final String GLUKOOSI = "GLUKOOSI";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "stepcounter.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + STEPCOUNTER + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DAY INTEGER, MONTH INTEGER, STEPS INTEGER, CALORIES INTEGER)";
        String createTable2 = "CREATE TABLE " + GLUKOOSI + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DAY INTEGER, MONTH INTEGER, GLUKOOSI INTEGER)";
        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STEPCOUNTER);
        db.execSQL("DROP TABLE IF EXISTS " + GLUKOOSI);
        onCreate(db);
    }

    public boolean addToDB(int steps, int calories){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DAY", TimeStamp.date());
        cv.put("MONTH", TimeStamp.month());
        cv.put("STEPS", steps);
        cv.put("CALORIES", calories);

        long insert = db.insert(STEPCOUNTER, null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addGlukoosi(int glukoosi){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DAY", TimeStamp.date());
        cv.put("MONTH", TimeStamp.month());
        cv.put("GLUKOOSI", glukoosi);

        long insert = db.insert(GLUKOOSI, null, cv);
        Log.d("db", String.valueOf(insert));
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }
}
