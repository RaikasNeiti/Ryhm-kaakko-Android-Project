package com.example.ryhmakaakkoapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String STEPCOUNTER = "STEPCOUNTER";
    public static final String ENTRY_TABLE = "ENTRY_TABLE";
    private static final String COL0 = "ID";
    private static final String COL1 = "glukoosi";


    public DatabaseHelper(@Nullable Context context) {
        super(context, "stepcounter.db", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + STEPCOUNTER + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DAY INTEGER, MONTH INTEGER, STEPS INTEGER, CALORIES INTEGER)";
        String createEntryTable = "CREATE TABLE " + ENTRY_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DAY INTEGER, MONTH INTEGER, YEAR INTEGER, " + COL1 +" TEXT, TIME STRING)";
        db.execSQL(createTable);
        db.execSQL(createEntryTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STEPCOUNTER);
        db.execSQL("DROP TABLE IF EXISTS " + ENTRY_TABLE);
        onCreate(db);
    }

    public boolean addToDB(int steps, int calories){        //pitää yhdistää addtodb ja addtoentrydb
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

    public boolean addtoEntryDB(String item, String tableName)    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DAY", TimeStamp.date());
        cv.put("MONTH", TimeStamp.month());
        cv.put("YEAR", TimeStamp.year());
        cv.put(COL1, item);
        cv.put("TIME", TimeStamp.hour() + ":" + TimeStamp.minute());

        long insert = db.insert(tableName, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }
    }

    public Cursor getData(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + table, null); //valitsee taulun nimen
        return data;
    }

    public int countRows(String tableName)  {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        if(data!=null &&  data.moveToFirst()) {
            int count = data.getInt(0);
            return count;
        } else  {
            return 0;
        }
    }

    public ArrayList getLatest(String tableName)    {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> latestRow = new ArrayList<>();
        Cursor data = db.rawQuery("SELECT * FROM " + tableName, null); //valitsee viimeisimmän arvon
        if(data!=null &&  data.moveToLast()) {

            latestRow.add(data.getString(1) + "." + data.getString(2)  + "." + data.getString(3));
            latestRow.add(data.getString(4));
            data.moveToPrevious();
            latestRow.add(data.getString(4));
            return latestRow;
        } else  {
            Log.d("db", "Error getting data");
            return latestRow;
        }
    }



}
