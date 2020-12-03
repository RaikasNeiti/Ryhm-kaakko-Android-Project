package com.example.ryhmakaakkoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String STEPCOUNTER = "STEPCOUNTER";
    public static final String ENTRY_TABLE = "ENTRY_TABLE";
    private static final String COL0 = "ID";
    private static final String COL1 = "glukoosi";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "stepcounter.db", null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + STEPCOUNTER + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DAY INTEGER, MONTH INTEGER, STEPS INTEGER, CALORIES INTEGER)";
        String createEntryTable = "CREATE TABLE " + ENTRY_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DAY INTEGER, MONTH INTEGER, YEAR INTEGER, " + COL1 +" TEXT)";
        db.execSQL(createTable);
        db.execSQL(createEntryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STEPCOUNTER);
        db.execSQL("DROP TABLE IF EXISTS " + ENTRY_TABLE);
        onCreate(db);
    }

    public boolean addToDB(int steps, int calories){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DAY", TimeStamp.date());
        cv.put("MONTH", TimeStamp.month());
        cv.put("STEPS", steps);
        cv.put("CALORIES", calories);

        long insert = db.insert("STEPCOUNTER", null, cv);
        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addtoEntryDB(String item)    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DAY", TimeStamp.date());
        cv.put("MONTH", TimeStamp.month());
        cv.put("YEAR", TimeStamp.year());
        cv.put(COL1, item);

        long insert = db.insert("ENTRY_TABLE", null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + ENTRY_TABLE, null); //valitsee taulun nimen
        return data;
    }

    public int countRows()  {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT COUNT(*) FROM " + ENTRY_TABLE, null);
        if(data!=null &&  data.moveToFirst()) {
            int count = data.getInt(0);
            Log.d("db", Integer.toString(count));
            return count;
        } else  {
            return 0;
        }
    }

}
