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
    public static final String DIARY = "DIARY";
    private static final String COL1 = "glukoosi";

    /**
     * Luokan konstruktori
     * @param context https://docs.oracle.com/javase/7/docs/api/javax/naming/Context.html
     */
    public DatabaseHelper(@Nullable Context context) {
        super(context, "SugarSteps.db", null, 2);
    }

    /**
     * Määrittellään databasen taulut ja niiden columnit sekä luodaan tarvittaessa luodaan database.
     * @param db SQlite tietokannan luokka
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + STEPCOUNTER + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DAY INTEGER, MONTH INTEGER, STEPS INTEGER)";
        String createEntryTable = "CREATE TABLE " + DIARY + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DAY INTEGER, MONTH INTEGER, YEAR INTEGER, " + COL1 + " TEXT, TIME STRING)";
        db.execSQL(createTable);
        db.execSQL(createEntryTable);
    }

    /**
     * Tarkistaa onko databasen versio muuttuunut. tekee tarvittavat muutokset tietokantaan jos tietokannasta
     * on uusi versio saatavilla
     * @param db SQlite tietokannan luokka
     * @param oldVersion Olemassa olevan tietokannan versioumero
     * @param newVersion Tietokannan uusin versionumero
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STEPCOUNTER);
        db.execSQL("DROP TABLE IF EXISTS " + DIARY);
        onCreate(db);
    }

    /**
     * Lisää arvoja tauluun STEPCOUNTER.
     * @param steps int , askellaskurin askelmäärä
     * @return boolean tietokantaan syötön virhetarkistus (true onnistuessa, false epäonnistuessa)
     */
    public boolean addToStepCounter(int steps) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DAY", TimeStamp.date());
        cv.put("MONTH", TimeStamp.month());
        cv.put("STEPS", steps);

        long insert = db.insert("STEPCOUNTER", null, cv);
        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Lisää arvoja tauluun Diary
     * @param item Verensokerin syötetty arvo
     * @param tableName Taulun nimi
     * @return boolean tietokantaan syötön virhetarkistus (true onnistuessa, false epäonnistuessa)
     */
    public boolean addToDiary(String item, String tableName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("DAY", TimeStamp.date());
        cv.put("MONTH", TimeStamp.month());
        cv.put("YEAR", TimeStamp.year());
        cv.put(COL1, item);
        cv.put("TIME", TimeStamp.hour() + ":" + TimeStamp.minute());

        long insert = db.insert(tableName, null, cv);

        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Palauttaa tietokannan taulun
     * @param table Taulun nimi
     * @return  palauttaa halutun taulun sisällön
     */
    public Cursor getData(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + table, null); //valitsee taulun nimen
        return data;
    }

    /**
     * Laskee taulusa olevien rivien määrän
     * @param tableName Taulun nimi
     * @return int taulun rivien määrän
     */
    public int countRows(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor data = db.rawQuery("SELECT COUNT(*) FROM " + tableName, null);
        if (data != null && data.moveToFirst()) {
            int count = data.getInt(0);
            return count;
        } else {
            return 0;
        }
    }

    /**
     * hakee taulun kaksi viimeistä riviä
     * @param tableName Taulun nimi
     * @return ArrayList<String> taulun kaksi viimeistä arvoa
     */
    public ArrayList getTwoLatest(String tableName) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<String> latestRow = new ArrayList<>();
        Cursor data = db.rawQuery("SELECT * FROM " + tableName, null); //valitsee viimeisimmän arvon
        if (data != null && data.moveToLast()) {

            latestRow.add(data.getString(1) + "." + data.getString(2) + "." + data.getString(3) + " " + data.getString(5));
            latestRow.add(data.getString(4));

            if (data != null && data.moveToPrevious()) {
                latestRow.add(data.getString(4));
            } else {
                data.moveToNext();
                latestRow.add(data.getString(4));
            }
            return latestRow;

        } else {
            Log.d("db", "Error getting data");
            return latestRow;
        }
    }
}